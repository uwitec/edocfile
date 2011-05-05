package com.edoc.orm.hibernate.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;

import com.edoc.entity.files.EdocFile;
import com.edoc.orm.hibernate.dao.FileDAO;

public class FileDAOImpl extends GenericDAOImpl<EdocFile,String> implements FileDAO{

	/**
	 * 获取子文件信息
	 * @param sourceId
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public List<EdocFile> getSubFileInfos(String sourceId,int showSelf){
		List<EdocFile> results = null;
		Session session = getSession();
		Connection conn = session.connection();
		try {
			ResultSet rs =null;  
			CallableStatement call = conn.prepareCall("{Call getSubFileInfo(?,?)}");
			call.setString(1, sourceId);
			call.setInt(2, showSelf);
			rs = call.executeQuery();
			if(rs!=null){
				results = new LinkedList<EdocFile>();
				EdocFile e = null;
				while(rs.next()){
					e = new EdocFile();
					e.setId(rs.getString("fileID"));
					e.setFileName(rs.getString("fileName"));
					results.add(e);
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}  
		return results;
	}
	/**
	 * 根据当前节点的ID获取其上层所有父节点的相关信息(共享文件夹)
	 * @param childFileId
	 * @param isShowRoot
	 * @return
	 * @author 					陈超 2011-3-22
	 */
	@SuppressWarnings("deprecation")
	public List<EdocFile> getShoredParentFiles(String childFileId,int showRoot,int showSelf){
		List<EdocFile> results = null;
		Session session = getSession();
		Connection conn = session.connection();
		try {
			ResultSet rs =null;  
			CallableStatement call = conn.prepareCall("{Call getShoredFileInfo(?,?,?)}");
			call.setString(1, childFileId);
			call.setInt(2, showRoot);
			call.setInt(3, showSelf);
			rs = call.executeQuery();
			if(rs!=null){
				results = new LinkedList<EdocFile>();
				EdocFile e = null;
				while(rs.next()){
					e = new EdocFile();
					e.setId(rs.getString("fileID"));
					e.setFileName(rs.getString("fileName"));
					results.add(e);
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}  
		return results;
	}
	/**
	 * 根据当前节点的ID获取其上层所有父节点的相关信息
	 * @param childFileId
	 * @param isShowRoot
	 * @return
	 * @author 					陈超 2011-3-22
	 */
	@SuppressWarnings("deprecation")
	public List<EdocFile> getParentFiles(String childFileId,int showRoot,int showSelf){
		List<EdocFile> results = null;
		Session session = getSession();
		Connection conn = session.connection();
		try {
			ResultSet rs =null;  
			CallableStatement call = conn.prepareCall("{Call getParentFileInfo(?,?,?)}");
			call.setString(1, childFileId);
			call.setInt(2, showRoot);
			call.setInt(3, showSelf);
			rs = call.executeQuery();
			if(rs!=null){
				results = new LinkedList<EdocFile>();
				EdocFile e = null;
				while(rs.next()){
					e = new EdocFile();
					e.setId(rs.getString("fileID"));
					e.setFileName(rs.getString("fileName"));
					results.add(e);
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}  
		return results;
	}
}
