package com.edoc.lucene.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;

/**
 * �����ļ�������
 * 
 * @author �³� 2010-8-28
 * 
 */
public class IndexManager {
	private static RAMDirectory ramDir = null;

	/**
	 * ����ĵ��������ĵ���������(�ݲ�֧�ֶ��̲߳���)
	 * 
	 * @param indexDir
	 *            ����Ŀ¼
	 * @param analyzer
	 *            �ʷ���
	 * @param doc.getDoc()
	 * @author �³� 2010-8-28
	 * @return
	 * @throws Exception
	 */
	public static void addDoc(File indexDir, Analyzer analyzer, EdocDocument doc)
			throws Exception {
		try {
			// ����IndexWriter,������������
			//�ж��Ƿ���� segments  �ļ�
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
	
	public static void deleteDoc(File indexDir, Analyzer analyzer, String[] recycleFileIds){
		if(recycleFileIds!=null && recycleFileIds.length>0){
			try {
				// ����IndexWriter,������������
				IndexWriter writer = new IndexWriter(FSDirectory.open(indexDir),analyzer, false, IndexWriter.MaxFieldLength.UNLIMITED);
				Term[] terms = new Term[recycleFileIds.length];
				int index = 0;
				for(String fileId:recycleFileIds){
					terms[index] = new Term(EdocDocument.FIELD_SOURCEFILEID,fileId);
					index++;
				}
				
				writer.deleteDocuments(terms);
				writer.optimize();
				writer.commit();
				writer.close();
				
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (LockObtainFailedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return;
	}

	public static void updateDoc(File indexDir, StandardAnalyzer analyzer, EdocDocument doc) {
		try {
			// ����IndexWriter,������������
			IndexWriter writer = new IndexWriter(FSDirectory.open(indexDir),analyzer, false, IndexWriter.MaxFieldLength.UNLIMITED);
			
			Term term = new Term(EdocDocument.FIELD_SOURCEFILEID,doc.getSourceFileId());
			writer.updateDocument(term, doc.getDoc());
			writer.optimize();
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
