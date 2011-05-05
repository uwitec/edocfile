package com.edoc.lucene.index.search;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
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
	private static SearchManager instance = null;
	private static IndexSearcher searcher = null;
	private static IndexReader indexReader = null;
	private SearchManager(){
		super();
	}
	
	public static SearchManager getSingleInstance(){
		if(instance==null){
			instance = new SearchManager();
			init();
		}
		return instance;
	}
	
	/**
	 * 初始化IndexReader实例
	 * 从硬盘中读取索引文件,并加载到内存中
	 */
	private static void init(){
		//获取索引目录
		File indexDir = new File(ConfigResource.getConfig(ConfigResource.EDOCINDEXFILE));
		try {
//			MultiReader a = null;
			indexReader = IndexReader.open(FSDirectory.open(indexDir));		//从硬盘中读取索引文件
			searcher = new IndexSearcher(indexReader);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关键字查询
	 * @param keyWord	查询关键字
	 * @return
	 */
	public List<EdocDocument> keyWordSearch(String keyWord,int currentPage,int pageSize){
		List<EdocDocument> rs = null;
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			QueryParser parser = new QueryParser(Version.LUCENE_30, FieldName.FIELD_CONTENT, analyzer);
			Query query = parser.parse(keyWord);
			TopScoreDocCollector collector = TopScoreDocCollector.create(TOP_NUM , false);		//查询前TOP_NUM条符合条件的记录
			searcher.search(query, collector);
			ScoreDoc[] hits = collector.topDocs((currentPage-1)*pageSize,pageSize).scoreDocs;
			
			Highlighter highlighter = null; 
			SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color=\"red\">", "</font>"); 
		    highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query)); 
		    highlighter.setTextFragmenter(new SimpleFragmenter(200)); 
			if(hits!=null){
				rs = new ArrayList<EdocDocument>(hits.length);
				EdocDocument edoc = null;
				Document doc = null;
				for(ScoreDoc sdoc:hits){
					doc = searcher.doc(sdoc.doc);//new method is.doc()
					TokenStream tokenStream = analyzer.tokenStream(EdocDocument.FIELD_CONTENT,new StringReader(doc.get(EdocDocument.FIELD_CONTENT)));
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
					
					rs.add(edoc);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
