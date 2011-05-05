package com.edoc.orm.hibernate.dao;

import java.util.List;

import com.edoc.entity.files.EdocFile;

public interface FileDAO extends GenericDAO<EdocFile,String>{
	/**
	 * 根据当前节点的ID获取其上层所有父节点的相关信息
	 * @param childFileId
	 * @param isShowRoot
	 * @return
	 * @author 					陈超 2011-3-22
	 */
	public List<EdocFile> getParentFiles(String childFileId,int showRoot,int showSelf);
	
	
	/**
	 * 根据当前节点的ID获取其上层所有父节点的相关信息(共享文件夹)
	 * @param childFileId
	 * @param isShowRoot
	 * @return
	 * @author 					陈超 2011-3-22
	 */
	public List<EdocFile> getShoredParentFiles(String childFileId,int showRoot,int showSelf);


	/**
	 * 获取子文件信息
	 * @param sourceId
	 * @return
	 */
	public List<EdocFile> getSubFileInfos(String sourceId,int showSelf);
}
