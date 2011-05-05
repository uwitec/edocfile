package com.edoc.lucene.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

/**
 * �����ļ�������
 * @author �³� 2010-8-28
 *
 */
public class CreateIndexThread {
	
	/**
	 * ����ĵ��������ĵ���������(�ݲ�֧�ֶ��̲߳���)
	 * @param indexDir		����Ŀ¼
	 * @param analyzer		�ʷ���
	 * @param doc.getDoc()			
	 * @author 				�³� 2010-8-28
	 * @return
	 */
	public static void addDoc(File indexDir, Analyzer analyzer, EdocDocument doc){
		
		try {
			//����IndexWriter,������������
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
