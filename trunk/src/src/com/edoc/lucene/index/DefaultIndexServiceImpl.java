package com.edoc.lucene.index;

import java.io.File;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;

import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.FileVersion;
import com.edoc.lucene.reader.FileReader;
import com.edoc.utils.ConfigResource;
import com.edoc.utils.FileUtils;
@Component("defaultIndexService")
public class DefaultIndexServiceImpl implements IndexService{

	@SuppressWarnings("deprecation")
	public boolean addIndex(String sourceFileName,File file, FileVersion fileVersion){
		if(!canMakeIndex(file)){
			return false;
		}
		//��ȡ����Ŀ¼
		String indexDirPath = ConfigResource.getConfig(ConfigResource.EDOCINDEXFILE);
		File indexDir = new File(indexDirPath);
		if(!indexDir.exists() || !indexDir.isDirectory()){
			indexDir.mkdirs();
		}
		
		//����Document
		EdocDocument doc = null;
		doc = new EdocDocument(sourceFileName,file.getName(),FileReader.getContent(file), fileVersion.getCreateTime().toString(),fileVersion.getEdocFileId(),fileVersion.getVersion(),
				fileVersion.getCreatorName(),fileVersion.getCreatorId(),Float.toString(fileVersion.getFileSize()));
		
		//��������
		try {
			IndexManager.addDoc(indexDir, new StandardAnalyzer(Version.LUCENE_CURRENT), doc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	
	@SuppressWarnings("deprecation")
	public boolean addIndex(String sourceFileName, File file, EdocFile edocFile) {
		if(!canMakeIndex(file)){
			return false;
		}
		//��ȡ����Ŀ¼
		String indexDirPath = ConfigResource.getConfig(ConfigResource.EDOCINDEXFILE);
		File indexDir = new File(indexDirPath);
		if(!indexDir.exists() || !indexDir.isDirectory()){
			indexDir.mkdirs();
		}
		
		//����Document
		EdocDocument doc = null;
		doc = new EdocDocument(sourceFileName,file.getName(),FileReader.getContent(file), edocFile.getCreateTime().toString(),edocFile.getId(),edocFile.getCurrentVersion(),
				edocFile.getCreatorName(),edocFile.getCreatorId(),Float.toString(edocFile.getFileSize()));
		
		//��������
		try {
			IndexManager.addDoc(indexDir, new StandardAnalyzer(Version.LUCENE_CURRENT), doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private boolean canMakeIndex(File file){
		if(file!=null){
			String type = FileUtils.getFileType(file).toLowerCase();
			if(type.equals("txt") || type.equals("doc") || type.equals("docx") || type.equals("xls") || type.equals("xlsx") || type.equals("pdf")){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * ����������Ϣ
	 * @param file			�����ļ�
	 * @param fileVersion   �ļ���Ϣ
	 * @return
	 */
	public boolean updateIndex(File file, FileVersion fileVersion){
		if(!canMakeIndex(file)){
			return false;
		}
		//��ȡ����Ŀ¼
		String indexDirPath = ConfigResource.getConfig(ConfigResource.EDOCINDEXFILE);
		File indexDir = new File(indexDirPath);
		if(!indexDir.exists() || !indexDir.isDirectory()){
			indexDir.mkdirs();
		}
		
		//����Document
		EdocDocument doc = null;
		doc = new EdocDocument(fileVersion.getFileName(),file.getName(),FileReader.getContent(file), fileVersion.getCreateTime().toString(),fileVersion.getEdocFileId(),fileVersion.getVersion(),
				fileVersion.getCreatorName(),fileVersion.getCreatorId(),Float.toString(fileVersion.getFileSize()));
		
		//��������
		try {
			IndexManager.updateDoc(indexDir, new StandardAnalyzer(Version.LUCENE_CURRENT), doc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * ɾ������
	 * @param recycleFileIds
	 */
	public void deleteIndex(String[] recycleFileIds){
		//��ȡ����Ŀ¼
		String indexDirPath = ConfigResource.getConfig(ConfigResource.EDOCINDEXFILE);
		File indexDir = new File(indexDirPath);
		if(!indexDir.exists() || !indexDir.isDirectory()){
			indexDir.mkdirs();
		}
		IndexManager.deleteDoc(indexDir, new StandardAnalyzer(Version.LUCENE_CURRENT), recycleFileIds);
	}

}
