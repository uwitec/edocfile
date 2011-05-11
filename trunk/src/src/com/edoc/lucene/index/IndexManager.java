package com.edoc.lucene.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
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
	 * �����ĵ��������ĵ���������(�ݲ�֧�ֶ��̲߳���)
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
			IndexWriter writer = new IndexWriter(FSDirectory.open(indexDir),analyzer, false, IndexWriter.MaxFieldLength.UNLIMITED);
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