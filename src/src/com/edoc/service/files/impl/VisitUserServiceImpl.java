package com.edoc.service.files.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edoc.dbsupport.PropertyFilter;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.ShoreFile;
import com.edoc.entity.files.VisitUserInfo;
import com.edoc.orm.hibernate.dao.FileDAO;
import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.service.files.VisitUserService;

@Component("visitUserService")
@Transactional(readOnly=true)
public class VisitUserServiceImpl implements VisitUserService{

	@Resource(name="visitUserInfoDao")
	private GenericDAO<VisitUserInfo,String> visitUserInfoDao=null;
	
	@Resource(name="edocFileDao")
	private FileDAO edocFileDao=null;
	
	/**
	 * 验证用户对文件的操作权限
	 * @param currentUserId
	 * @param sourceFileId
	 * @param perType
	 * @return
	 */
	public boolean checkPermission(String currentUserId, String sourceFileId,
			String perType){
		EdocFile efile = edocFileDao.get(sourceFileId);
		if(efile!=null){
			if(efile.getCreatorId().equals(currentUserId)){
				return true;
			}else{
				List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
				filters.add(new PropertyFilter("sourceFileId",sourceFileId,PropertyFilter.MatchType.EQ));
				
				if(perType.equals(VisitUserService.PERTYPE_VIEW)){
					filters.add(new PropertyFilter("perView",1,PropertyFilter.MatchType.EQ));
				}else if(perType.equals(VisitUserService.PERTYPE_DOWNLOAD)){
					filters.add(new PropertyFilter("perDownLoad",1,PropertyFilter.MatchType.EQ));
				}else if(perType.equals(VisitUserService.PERTYPE_EDIT)){
					filters.add(new PropertyFilter("perEdit",1,PropertyFilter.MatchType.EQ));
				}
				
				int count = visitUserInfoDao.getCount(filters);
				if(count>0){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 删除共享用户信息
	 * @param id
	 */
	@Transactional(readOnly=false)
	public void deleteVisitUserInfo(String id){
		String[] ids = {id};
		visitUserInfoDao.delete(ids);
	}
	/**
	 * 获取允许访问该文件的用户信息
	 * @param sourceFileId		文件ID
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
	 * 删除访问用户的信息
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
	public void insertVisitUserInfo(List<VisitUserInfo> visitUserInfos, List<EdocFile> mulus) {
		if(visitUserInfos!=null && !visitUserInfos.isEmpty()){
			
			if(mulus!=null && !mulus.isEmpty()){
//				EdocFile temp = new EdocFile();
//				temp.setId(userHome.getSourceFileId());
//				mulus.add(temp);
				List<VisitUserInfo> tempVisitUserInfo = new LinkedList<VisitUserInfo>(visitUserInfos);
				for(EdocFile m:mulus){
					for(VisitUserInfo v:tempVisitUserInfo){
						VisitUserInfo visitUserInfo = new VisitUserInfo();
						visitUserInfo.setVisitUserId(v.getVisitUserId());
						visitUserInfo.setVisitUserName(v.getVisitUserName());
						visitUserInfo.setSourceFileId(m.getId());
						visitUserInfo.setPermissions(new String[]{"view"});
						visitUserInfos.add(visitUserInfo);
					}
				}
			}
			for(VisitUserInfo v:visitUserInfos){
				visitUserInfoDao.saveOrUpdate(v);
			}
		}
	}

}
