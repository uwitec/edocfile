package com.edoc.lucene.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;

/**
 * 索引文件管理类
 * 
 * @author 陈超 2010-8-28
 * 
 */
public class IndexManager {
	private static RAMDirectory ramDir = null;

	/**
	 * 添加文档并对新文档建立索引(暂不支持多线程操作)
	 * 
	 * @param indexDir
	 *            索引目录
	 * @param analyzer
	 *            词分器
	 * @param doc.getDoc()
	 * @author 陈超 2010-8-28
	 * @return
	 * @throws Exception
	 */
	public static void addDoc(File indexDir, Analyzer analyzer, EdocDocument doc)
			throws Exception {
		try {
			// 创建IndexWriter,增量创建索引
			//判断是否存在 segments  文件
			boolean segExistFlag = false;		
			if(indexDir.exists() && indexDir.isDirectory()){
				File[] files = indexDir.listFiles();
				for(File f:files){
					if(f.getName().startsWith("segments")){
						segExistFlag = true;
						break;
					}
				}
			}
			IndexWriter writer = null;
			if(segExistFlag){
				writer = new IndexWriter(FSDirectory.open(indexDir),analyzer, false, IndexWriter.MaxFieldLength.UNLIMITED);
			}else{
				writer = new IndexWriter(FSDirectory.open(indexDir),analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
			}
			writer.addDocument(doc.getDoc());
			writer.commit();
			writer.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public static void deleteDoc(File indexDir, Analyzer analyzer, EdocDocument doc)
			throws Exception {
		try {
			// 创建IndexWriter,增量创建索引
			IndexWriter writer = new IndexWriter(FSDirectory.open(indexDir),analyzer, false, IndexWriter.MaxFieldLength.UNLIMITED);
			writer.addDocument(doc.getDoc());
			writer.close();
			
			IndexReader indexReader = IndexReader.open(FSDirectory.open(indexDir));		//从硬盘中读取索引文件
			
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	public static void updateDoc(File indexDir, StandardAnalyzer analyzer, EdocDocument doc) {
		try {
			// 创建IndexWriter,增量创建索引
			//判断是否存在 segments  文件
			boolean segExistFlag = false;		
			if(indexDir.exists() && indexDir.isDirectory()){
				File[] files = indexDir.listFiles();
				for(File f:files){
					if(f.getName().startsWith("segments")){
						segExistFlag = true;
						break;
					}
				}
			}
			IndexWriter writer = null;
			if(segExistFlag){
				writer = new IndexWriter(FSDirectory.open(indexDir),analyzer, false, IndexWriter.MaxFieldLength.UNLIMITED);
			}else{
				writer = new IndexWriter(FSDirectory.open(indexDir),analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
			}
			
			Term term = new Term(EdocDocument.FIELD_SOURCEFILEID,doc.getSourceFileId());
			writer.updateDocument(term, doc.getDoc());
			writer.commit();
			writer.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
		
	}

}
