package com.edoc.orm.hibernate.dao;

import java.util.List;

import com.edoc.entity.files.EdocFile;

public interface FileDAO extends GenericDAO<EdocFile,String>{
	/**
	 * ���ݵ�ǰ�ڵ��ID��ȡ���ϲ����и��ڵ�������Ϣ
	 * @param childFileId
	 * @param isShowRoot
	 * @return
	 * @author 					�³� 2011-3-22
	 */
	public List<EdocFile> getParentFiles(String childFileId,int showRoot,int showSelf);
	
	
	/**
	 * ���ݵ�ǰ�ڵ��ID��ȡ���ϲ����и��ڵ�������Ϣ(�����ļ���)
	 * @param childFileId
	 * @param isShowRoot
	 * @return
	 * @author 					�³� 2011-3-22
	 */
	public List<EdocFile> getShoredParentFiles(String childFileId,int showRoot,int showSelf);


	/**
	 * ��ȡ���ļ���Ϣ
	 * @param sourceId
	 * @return
	 */
	public List<EdocFile> getSubFileInfos(String sourceId,int showSelf);
}
