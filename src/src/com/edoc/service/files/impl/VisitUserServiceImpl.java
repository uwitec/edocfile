package com.edoc.service.files.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edoc.dbsupport.PropertyFilter;
import com.edoc.entity.files.VisitUserInfo;
import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.service.files.VisitUserService;

@Component("visitUserService")
@Transactional(readOnly=true)
public class VisitUserServiceImpl implements VisitUserService{

	@Resource(name="visitUserInfoDao")
	private GenericDAO<VisitUserInfo,String> visitUserInfoDao=null;
	
	/**
	 * ɾ�������û���Ϣ
	 * @param id
	 */
	@Transactional(readOnly=false)
	public void deleteVisitUserInfo(String id){
		String[] ids = {id};
		visitUserInfoDao.delete(ids);
	}
	/**
	 * ��ȡ������ʸ��ļ����û���Ϣ
	 * @param sourceFileId		�ļ�ID
	 * @return
	 */
	public List<VisitUserInfo> getVisitUsers(String sourceFileId){
		List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
		PropertyFilter filter01 = new PropertyFilter("sourceFileId",sourceFileId,PropertyFilter.MatchType.EQ);
		filters.add(filter01);
		
		List<VisitUserInfo> vusers = visitUserInfoDao.find(filters);
		
		return vusers;
	}
	/**
	 * ɾ�������û�����Ϣ
	 * @param fileId
	 */
	@Transactional(readOnly=false)
	public void deleteVisitUserBySourceFileId(String[] sourceFileIds){
//		visitUserInfoDao.update("delete sys_visituserinfo where C_SOURCEFILEID='"+fileId+"'");
//		visitUserInfoDao.update("delete from sys_visituserinfo where C_SOURCEFILEID = '"+fileId+"'");
		
		String sql = "delete from sys_visituserinfo where C_SOURCEFILEID in('000'";
		if(sourceFileIds!=null){
			for(String sFileId:sourceFileIds){
				sql += ",'"+sFileId+"'";
			}
		}
		sql += ")";
		
		visitUserInfoDao.update(sql);
	}
	
	@Transactional(readOnly=false)
	public void insertVisitUserInfo(List<VisitUserInfo> visitUserInfos) {
		if(visitUserInfos!=null && !visitUserInfos.isEmpty()){
			for(VisitUserInfo v:visitUserInfos){
				visitUserInfoDao.saveOrUpdate(v);
			}
		}
	}

}
