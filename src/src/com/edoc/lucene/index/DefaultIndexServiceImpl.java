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
		doc = new EdocDocument(sourceFileName,file.getName(),FileReader.getContent(file), "2010-8-28");
		
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
			if(type.equals("txt") || type.equals("doc")){
				return true;
			}
		}
		
		return false;
	}

}