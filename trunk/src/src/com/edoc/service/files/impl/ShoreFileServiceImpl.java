package com.edoc.service.files.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.dbsupport.PropertyFilter;
import com.edoc.entity.baseinfo.User;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.ShoreFile;
import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.service.files.ShoreFileService;
import com.edoc.utils.StringUtils;

/**
 * ShoreFileService��ʵ����
 * @author �³� 2010-11-16
 *
 */
@Component("shoreFileService")
@Transactional(readOnly=true)
public class ShoreFileServiceImpl implements ShoreFileService{

	@Resource(name="shoreFileDao")
	private GenericDAO<ShoreFile,String> shoreFileDao=null;
	
	/**
	 * ��ȡ�����ļ���Ϣ
	 * @param currentPage
	 * @param pageSize
	 * @param id
	 * @param parentId
	 * @param queryFileName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageValueObject<ShoreFile> getShoredFiles(int currentPage,
			int pageSize, String userId, String parentId, String fileName){
		PageValueObject<ShoreFile> page = null;
		try{
			page = new PageValueObject<ShoreFile>(currentPage,pageSize);
			String sql = "select a.ID,a.C_SOURCE_ID,a.C_SHOREUSERNAME,a.C_SHOREUSERID,a.D_SHORE_STARTTIME,a.D_SHORE_ENDTIME,"
				+" a.C_PARENTID,b.C_FILENAME,b.C_FILETYPE,b.D_CREATETIME,b.D_UPDATETIME,b.F_FILESIZE,b.I_ISFOLDER,b.C_FILESUFFIX,"
				+" b.C_ICON,b.C_DESC,b.C_NEWFILENAME,b.C_CURRENTVERSION,c.I_PERVIEW,c.I_PERDOWNLOAD from sys_shorefile as a,"
				+" sys_fileinfo as b,sys_visituserinfo as c where a.C_SOURCE_ID = b.ID and b.ID=c.C_SOURCEFILEID and"
				+" c.C_VISITUSERID='"+userId+"' and a.C_PARENTID='"+parentId+"' and b.I_ISSHORED=1";
			
			String countSQL = "select count(*) from sys_shorefile as a, sys_fileinfo as b,sys_visituserinfo as c where  a.C_SOURCE_ID"
				+" = b.ID and b.ID=c.C_SOURCEFILEID and  c.C_VISITUSERID='"+userId+"' and a.C_PARENTID='"+parentId+"' and b.I_ISSHORED=1";
			
			if(StringUtils.isValid(fileName)){
				sql += " and b.C_FILENAME like '%"+fileName+"%'";
				countSQL += " and b.C_FILENAME like '%"+fileName+"%'";
			}
			page.setTotalRows(shoreFileDao.excuteGetCountSQL(countSQL));
			List r = shoreFileDao.excuteQuerySQL(sql, page.getFirstResult(), page.getPageSize());
			page.setResult(parserResult(r));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return page;
	}
	/**
	 * ��ȡ�����ļ�����Ϣ
	 * @param parentId	�ϼ��˵�Id
	 * @param userId	�û�Id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ShoreFile> getShoredFileByParentId(String parentId, String userId){
		try{
			String sql = "select a.ID,a.C_SOURCE_ID,a.C_SHOREUSERNAME,a.C_SHOREUSERID,a.D_SHORE_STARTTIME,a.D_SHORE_ENDTIME,"
					+" a.C_PARENTID,b.C_FILENAME,b.C_FILETYPE,b.D_CREATETIME,b.D_UPDATETIME,b.F_FILESIZE,b.I_ISFOLDER,b.C_FILESUFFIX,"
					+" b.C_ICON,b.C_DESC,b.C_NEWFILENAME,b.C_CURRENTVERSION,c.I_PERVIEW,c.I_PERDOWNLOAD from sys_shorefile as a,"
					+" sys_fileinfo as b,sys_visituserinfo as c where a.C_SOURCE_ID = b.ID and b.ID=c.C_SOURCEFILEID and"
					+" c.C_VISITUSERID='"+userId+"' and a.C_PARENTID='"+parentId+"' and b.I_ISFOLDER=1";
			List r = shoreFileDao.excuteQuery(sql);
			
			return parserResult(r);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private List<ShoreFile> parserResult(List r){
		if(r!=null && !r.isEmpty()){
			List<ShoreFile> results = new LinkedList<ShoreFile>();
			for(Object o :r){
				Object[] vs = (Object[])o;
				ShoreFile s = new ShoreFile();
				s.setId((String)vs[0]);
				s.setSourceFileId((String)vs[1]);
				s.setShoreUserName((String)vs[2]);
				s.setShoreUserId((String)vs[3]);
				s.setShoreStartTime(new Date());
				s.setShoreEndTime(new Date());
				s.setParentId((String)vs[6]);
				s.setFileName((String)vs[7]);
				s.setFileType((String)vs[8]);
				s.setCreateTime(new Date());
				s.setUpdateTime(new Date());
				s.setFileSize(1);
				s.setIsFolder((Integer)vs[12]);
				s.setFileSuffix((String)vs[13]);
				s.setIcon((String)vs[14]);
				s.setDesc((String)vs[15]);
				s.setNewFileName((String)vs[16]);
				s.setCurrentVersion((String)vs[17]);
				s.setPerView(((Integer)vs[18]).intValue());
				s.setPerDownLoad(((Integer)vs[19]).intValue());
				results.add(s);
			}
			return results;
		}
		return null;
	}
	
	/**
	 * �����µĹ����ļ���Ϣ
	 * @param shoreFile �����ļ���Ϣ
	 * @author 			�³� 2010-11-16
	 */
	@Transactional(readOnly=false)
	public void insertShoreFile(ShoreFile shoreFile, List<EdocFile> mulus,User user,int shoreMuluFlag) {
		//���жϸ��ļ��Ƿ��Ѿ�����
		//���û���ϼ�Ŀ¼�Ļ��򽫸ù����ļ������"�����ļ���"��Ŀ¼��,�������α����ϼ�Ŀ¼
		List<ShoreFile> list = new LinkedList<ShoreFile>();
		if(shoreMuluFlag==1){
			if(mulus!=null && !mulus.isEmpty()){
				String parentId = "-1";
				for(int i=0;i<mulus.size();i++){
					EdocFile mulu = mulus.get(i);
					if(isExistSourceFile(mulu.getId())){
						ShoreFile tempShoreFile = new ShoreFile();
						tempShoreFile.setParentId(parentId);
						tempShoreFile.setSourceFileId(mulu.getId());
						tempShoreFile.setShoreUserId(user.getId());
						tempShoreFile.setShoreUserName(user.getTrueName());
						tempShoreFile.setFileName(mulu.getFileName());
						
						parentId = tempShoreFile.getId();
						list.add(tempShoreFile);
					}
				}
				shoreFile.setParentId(parentId);
			}else{
				shoreFile.setParentId("-1");
			}
		}else{
			shoreFile.setParentId("-1");
		}
		list.add(shoreFile);
		shoreFileDao.save(list);
	}
	
	@SuppressWarnings("unchecked")
	private boolean isExistSourceFile(String sourceFileId){
		List<ShoreFile> rs = shoreFileDao.excuteQuery("select * from sys_shorefile where C_SOURCE_ID='"+sourceFileId+"'");
		if(rs!=null && !rs.isEmpty()){
			return true;
		}
		return false;
	}
	
	/**
	 * �жϼ�¼�Ƿ����
	 * @param id
	 * @return
	 */
	public boolean isExist(String id){
		ShoreFile s = shoreFileDao.get(id);
		if(s!=null){
			return true;
		}
		return false;
	}
	/**
	 * ɾ��������ļ���Ϣ
	 * @param sourceFileIds
	 * @author 			�³�
	 */
	@Transactional(readOnly=false)
	public void deleteShoreFileBySourceFileId(String[] sourceFileIds){
		String sql = "delete from sys_shorefile where C_SOURCE_ID in('000'";
		if(sourceFileIds!=null){
			for(String sFileId:sourceFileIds){
				sql += ",'"+sFileId+"'";
			}
		}
		sql += ")";
		
		shoreFileDao.update(sql);
		
	}
	
	/**
	 * ����Դ�ļ�Id��ȡ���ڵĹ����ļ���Ϣ
	 * @param sourceFileId
	 * @return
	 */
	public ShoreFile getShoreFileBySourceFileId(String sourceFileId){
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>(1);
		PropertyFilter filter = new PropertyFilter("sourceFileId",sourceFileId,PropertyFilter.MatchType.EQ);
		filters.add(filter);
		List<ShoreFile> rs = shoreFileDao.find(filters);
		if(rs!=null && !rs.isEmpty()){
			return rs.get(0);
		}
		return null;
	}

}
