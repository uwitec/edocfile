package com.edoc.lucene.index.search;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.edoc.lucene.FieldName;
import com.edoc.lucene.index.EdocDocument;
import com.edoc.utils.ConfigResource;

/**
 * lucene查询管理类(单利模式)
 * @author 陈超 2010-9-4
 *
 */
public class SearchManager {
	private static final int TOP_NUM = 100;
	private SearchManager instance = null;
	private IndexSearcher searcher = null;
	private IndexReader indexReader = null;
	private int recordCount = 0;
	public SearchManager(){
		super();
	}
//	public static SearchManager getSingleInstance(){
//		if(instance==null){
//			instance = new SearchManager();
////			init();
//		}
//		return instance;
//	}
	
//	/**
//	 * 初始化IndexReader实例
//	 * 从硬盘中读取索引文件,并加载到内存中
//	 */
//	private static void init(){
//		//获取索引目录
//		File indexDir = new File(ConfigResource.getConfig(ConfigResource.EDOCINDEXFILE));
//		try {
////			MultiReader a = null;
//			indexReader = IndexReader.open(FSDirectory.open(indexDir));		//从硬盘中读取索引文件
//			searcher = new IndexSearcher(indexReader);
//		} catch (CorruptIndexException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 关键字查询
	 * @param keyWord	查询关键字
	 * @return
	 */
	public List<EdocDocument> keyWordSearch(String keyWord,String[] excludeFileIds, int currentPage,int pageSize){
		List<EdocDocument> rs = null;
		try {
			//从本地硬盘上加装索引文件信息
			File indexDir = new File(ConfigResource.getConfig(ConfigResource.EDOCINDEXFILE));
			indexReader = IndexReader.open(FSDirectory.open(indexDir));
			searcher = new IndexSearcher(indexReader);
			
			//创建查询query
			Query query = createQuery(keyWord,excludeFileIds);						
			
			//分页查询设置
			TopScoreDocCollector collector = TopScoreDocCollector.create(TOP_NUM , false);
			searcher.search(query, collector);
			setRecordCount(collector.getTotalHits());
			ScoreDoc[] hits = collector.topDocs(currentPage,pageSize).scoreDocs;
			
			//高亮设置
			Highlighter highlighter = makeHighlighterFormatter(query); 
			
			//解析并返回查询结果集
		    return parserQueryResults(hits,highlighter);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return rs;
	}

	/**
	 * 高亮显示设置
	 * @param query		查询query
	 * @return
	 */
	private Highlighter makeHighlighterFormatter(Query query) {
		Highlighter highlighter = null;
		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color=\"red\">", "</font>"); 
	    highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query)); 
	    highlighter.setTextFragmenter(new SimpleFragmenter(200)); 
		return highlighter;
	}

	/**
	 * 创建查询Query
	 * @param keyWord			查询关键词
	 * @param excludeFileIds	过滤文档id
	 * @return
	 * @throws ParseException
	 */
	private Query createQuery(String keyWord, String[] excludeFileIds) throws ParseException {
		//Query query 
		QueryParser parser = new QueryParser(Version.LUCENE_30, FieldName.FIELD_CONTENT, new StandardAnalyzer(Version.LUCENE_30));
		Query query = parser.parse(keyWord);
		BooleanQuery bQuery = new BooleanQuery() ;
		bQuery.add(query, BooleanClause.Occur.MUST);
		//过滤不可见的id对应的文档
		if(excludeFileIds!=null && excludeFileIds.length>0){
			for(String fileId:excludeFileIds){
				bQuery.add( new TermQuery(new Term(EdocDocument.FIELD_SOURCEFILEID,fileId)), BooleanClause.Occur.MUST_NOT );
			}
		}
		return bQuery;
	}
	
	/**
	 * 解析查询出来的结果
	 * @param  hits								查询结果
	 * @param  highlighter						高亮显示设置
	 * @return
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 * @throws InvalidTokenOffsetsException 
	 */
	public List<EdocDocument> parserQueryResults(ScoreDoc[] hits,Highlighter highlighter) throws CorruptIndexException, IOException, InvalidTokenOffsetsException{
		List<EdocDocument> rs = null;
		if(hits!=null){
			rs = new ArrayList<EdocDocument>(hits.length);
			EdocDocument edoc = null;
			Document doc = null;
			for(ScoreDoc sdoc:hits){
				doc = searcher.doc(sdoc.doc);//new method is.doc()
				TokenStream tokenStream = new StandardAnalyzer(Version.LUCENE_30).tokenStream(EdocDocument.FIELD_CONTENT,new StringReader(doc.get(EdocDocument.FIELD_CONTENT)));
				edoc = new EdocDocument();
				edoc.setContents(highlighter.getBestFragment(tokenStream, doc.get(FieldName.FIELD_CONTENT)));
				if(doc.getField(EdocDocument.FIELD_CREATETIME)!=null){
					edoc.setCreateTime(doc.getField(EdocDocument.FIELD_CREATETIME).stringValue());
				}
				if(doc.getField(EdocDocument.FIELD_CREATORID)!=null){
					edoc.setCreatorId(doc.getField(EdocDocument.FIELD_CREATORID).stringValue());
				}
				if(doc.getField(EdocDocument.FIELD_CREATORNAME)!=null){
					edoc.setCreatorName(doc.getField(EdocDocument.FIELD_CREATORNAME).stringValue());
				}
				if(doc.getField(EdocDocument.FIELD_FILENAME)!=null){
					edoc.setFileName(doc.getField(EdocDocument.FIELD_FILENAME).stringValue());
				}
				if(doc.getField(EdocDocument.FIELD_FILESIZE)!=null){
					edoc.setFileSize(doc.getField(EdocDocument.FIELD_FILESIZE).stringValue());
				}
				if(doc.getField(EdocDocument.FIELD_SOURCEFILEID)!=null){
					edoc.setSourceFileId(doc.getField(EdocDocument.FIELD_SOURCEFILEID).stringValue());
				}
				if(doc.getField(EdocDocument.FIELD_VERSIONNUM)!=null){
					edoc.setVersionNum(doc.getField(EdocDocument.FIELD_VERSIONNUM).stringValue());
				}
				if(doc.getField(EdocDocument.FIELD_SOURCEFILENAME)!=null){
					edoc.setSourceFileName(doc.getField(EdocDocument.FIELD_SOURCEFILENAME).stringValue());
				}
				
				rs.add(edoc);
			}
		}
		return rs;
	}
	

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	
	
	
}
