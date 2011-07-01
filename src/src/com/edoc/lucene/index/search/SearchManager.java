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
 * lucene��ѯ������(����ģʽ)
 * @author �³� 2010-9-4
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
//	 * ��ʼ��IndexReaderʵ��
//	 * ��Ӳ���ж�ȡ�����ļ�,�����ص��ڴ���
//	 */
//	private static void init(){
//		//��ȡ����Ŀ¼
//		File indexDir = new File(ConfigResource.getConfig(ConfigResource.EDOCINDEXFILE));
//		try {
////			MultiReader a = null;
//			indexReader = IndexReader.open(FSDirectory.open(indexDir));		//��Ӳ���ж�ȡ�����ļ�
//			searcher = new IndexSearcher(indexReader);
//		} catch (CorruptIndexException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * �ؼ��ֲ�ѯ
	 * @param keyWord	��ѯ�ؼ���
	 * @return
	 */
	public List<EdocDocument> keyWordSearch(String keyWord,String[] excludeFileIds, int currentPage,int pageSize){
		List<EdocDocument> rs = null;
		try {
			//�ӱ���Ӳ���ϼ�װ�����ļ���Ϣ
			File indexDir = new File(ConfigResource.getConfig(ConfigResource.EDOCINDEXFILE));
			indexReader = IndexReader.open(FSDirectory.open(indexDir));
			searcher = new IndexSearcher(indexReader);
			
			//������ѯquery
			Query query = createQuery(keyWord,excludeFileIds);						
			
			//��ҳ��ѯ����
			TopScoreDocCollector collector = TopScoreDocCollector.create(TOP_NUM , false);
			searcher.search(query, collector);
			setRecordCount(collector.getTotalHits());
			ScoreDoc[] hits = collector.topDocs(currentPage,pageSize).scoreDocs;
			
			//��������
			Highlighter highlighter = makeHighlighterFormatter(query); 
			
			//���������ز�ѯ�����
		    return parserQueryResults(hits,highlighter);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return rs;
	}

	/**
	 * ������ʾ����
	 * @param query		��ѯquery
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
	 * ������ѯQuery
	 * @param keyWord			��ѯ�ؼ���
	 * @param excludeFileIds	�����ĵ�id
	 * @return
	 * @throws ParseException
	 */
	private Query createQuery(String keyWord, String[] excludeFileIds) throws ParseException {
		//Query query 
		QueryParser parser = new QueryParser(Version.LUCENE_30, FieldName.FIELD_CONTENT, new StandardAnalyzer(Version.LUCENE_30));
		Query query = parser.parse(keyWord);
		BooleanQuery bQuery = new BooleanQuery() ;
		bQuery.add(query, BooleanClause.Occur.MUST);
		//���˲��ɼ���id��Ӧ���ĵ�
		if(excludeFileIds!=null && excludeFileIds.length>0){
			for(String fileId:excludeFileIds){
				bQuery.add( new TermQuery(new Term(EdocDocument.FIELD_SOURCEFILEID,fileId)), BooleanClause.Occur.MUST_NOT );
			}
		}
		return bQuery;
	}
	
	/**
	 * ������ѯ�����Ľ��
	 * @param  hits								��ѯ���
	 * @param  highlighter						������ʾ����
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
