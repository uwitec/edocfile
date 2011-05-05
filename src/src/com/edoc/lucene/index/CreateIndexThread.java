package com.edoc.lucene.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

/**
 * 索引文件管理类
 * @author 陈超 2010-8-28
 *
 */
public class CreateIndexThread {
	
	/**
	 * 添加文档并对新文档建立索引(暂不支持多线程操作)
	 * @param indexDir		索引目录
	 * @param analyzer		词分器
	 * @param doc.getDoc()			
	 * @author 				陈超 2010-8-28
	 * @return
	 */
	public static void addDoc(File indexDir, Analyzer analyzer, EdocDocument doc){
		
		try {
			//创建IndexWriter,增量创建索引
			IndexWriter writer = new IndexWriter(FSDirectory.open(indexDir), analyzer, false, IndexWriter.MaxFieldLength.UNLIMITED);
			writer.addDocument(doc.getDoc());
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
