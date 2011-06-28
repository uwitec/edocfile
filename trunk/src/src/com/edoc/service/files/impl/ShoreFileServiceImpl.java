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
import com.edoc.entity.files.VisitUserInfo;
import com.edoc.entity.tools.SendMsg;
import com.edoc.orm.hibernate.dao.FileDAO;
import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.service.files.FileService;
import com.edoc.service.files.ShoreFileService;
import com.edoc.service.files.VisitUserService;
import com.edoc.service.tools.MessageService;
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
	
	@Resource(name="edocFileDao")
	private FileDAO edocFileDao=null;
	
	@Resource(name="fileService")
	private FileService fileService = null;
	
	@Resource(name="visitUserService")
	private VisitUserService visitUserService = null;
	
	@Resource(name="messageService")
	private MessageService messageService = null;
	
	/**
	 * �����ļ��������ڹ�����ļ���ͬʱҪ�����ļ����ϲ��ļ������ó��ѹ���(���ļ�������������ļ������������)
	 * ������"�����ļ���"�¼���Ƿ����һ������Ϊ: ����(��¼��) ���ļ���,����������򴴽�,Ȼ�󽫹����ļ���
	 * parentId ���óɸ��ļ��е�ID
	 * 
	 * @param shoreFile			�����ļ�����Ϣ
	 * @param visitUserInfos	�����û�����Ϣ
	 * @param user				������ļ����û���Ϣ
	 * @param shoreNowFlag		�Ƿ���������
	 * @author �³� 2011-06-02
	 */
	@Transactional(readOnly=false)
	public boolean shoreFile(ShoreFile shoreFile,List<VisitUserInfo> visitUserInfos,
			User user, boolean shoreNowFlag, boolean sendMsgFlag){
		try{
			EdocFile sourceFile = fileService.getFileById(shoreFile.getSourceFileId());
			//��ȡ�����ļ����ϼ�Ŀ¼
			List<EdocFile> mulus = edocFileDao.getParentFiles(shoreFile.getSourceFileId(), 0, 0);
			
			//��ӷ��ʸ��ļ����û���Ϣ
			visitUserService.insertVisitUserInfo(visitUserInfos, mulus);					
			String msg = "";
			//�����������Ļ���ִ�����²���,������ݲ�����Ļ���Ҫ�Ȳ鿴���ļ��Ƿ��Ѿ�������,����Ѿ��������������ó�δ����
			//���δ���������κβ���
			if(shoreNowFlag){
				msg = user.getTrueName()+"�������ļ�'"+sourceFile.getFileName()+"'";
				if(sourceFile.getIsShored()==0){
					//"�����ļ���"���û��ϴ��ļ���Ŀ¼��Ϣ
					ShoreFile userHome = createUserDirIfNotExist(user);
					
					//��ӹ����ļ�����Ϣ
					if(!isExist(shoreFile.getId())){
						insertShoreFile(shoreFile,mulus,user,userHome);	
					}
					//�޸��ļ�����״̬,�������ļ����ϼ�Ŀ¼
					String updateSQL = "update EdocFile set isShored = 1 where id in('"+shoreFile.getSourceFileId()+"'";
					if(mulus!=null && !mulus.isEmpty()){
						for(EdocFile e:mulus){
							updateSQL += ",'"+e.getId()+"'";
						}
					}
					updateSQL += ")";
					edocFileDao.executeUpdate(updateSQL);
				}
			}else{
				msg = user.getTrueName()+"ȡ���˶��ļ�'"+sourceFile.getFileName()+"'�Ĺ���";
				if(sourceFile.getIsShored()==1){
					sourceFile.setIsShored(0);
					edocFileDao.update(sourceFile);
				}
			}
			if(sendMsgFlag){
				sendMsg(visitUserInfos,user.getTrueName(),msg);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
		
		
		
	}
	
	private void sendMsg(List<VisitUserInfo> visitUserInfos,String userName,String msg) {
		if(visitUserInfos!=null && !visitUserInfos.isEmpty()){
			SendMsg sendMsg = new SendMsg();
			sendMsg.setState(1);
			sendMsg.setTitle("ϵͳ��Ϣ");
			sendMsg.setContent(msg);
			sendMsg.setFromUserName("ϵͳ��Ϣ");
			String[] receiverIds = new String[visitUserInfos.size()];
			String[] receiverNames = new String[visitUserInfos.size()];
			int size = visitUserInfos.size();
			for(int i=0;i<size;i++){
				VisitUserInfo v = visitUserInfos.get(i);
				receiverIds[i] = v.getVisitUserId();
				receiverNames[i] = v.getVisitUserName();
			}
			
			messageService.saveMessage(sendMsg, receiverIds, receiverNames);
		}
		
	}

	/**
	 * ����Ը��û������������ļ��в����ڵĻ��򴴽��ù����ļ���Ϣ
	 * @param user
	 * @return
	 */
	@Transactional(readOnly=false)
	private ShoreFile createUserDirIfNotExist(User user) {
		String fileName = user.getTrueName()+"("+user.getLoginName()+")";
		List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
		PropertyFilter filter01 = new PropertyFilter("fileName",fileName,PropertyFilter.MatchType.EQ);
		filters.add(filter01);
		List<ShoreFile> results  = shoreFileDao.find(filters);
		if(results!=null && !results.isEmpty()){
			return results.get(0);
		}else{
			EdocFile efile = new EdocFile();
			efile.setCreatorId(user.getId());
			efile.setCreatorName(user.getTrueName());
			efile.setFileName(fileName);
			efile.setParentId("-1");			//���ó�-1,����"�ҵ��ļ���"����ʾ
			efile.setIsShored(1);
			edocFileDao.save(efile);
			
			ShoreFile tempShoreFile = new ShoreFile();
			tempShoreFile.setFileName(fileName);
			tempShoreFile.setShoreUserId(user.getId());
			tempShoreFile.setShoreUserName(user.getTrueName());
			tempShoreFile.setParentId("-1");
			tempShoreFile.setIsHome(1);
			tempShoreFile.setSourceFileId(efile.getId());
			shoreFileDao.save(tempShoreFile);
			return tempShoreFile;
		}
	}
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
			String sql = "";
			String countSQL = "";
			if(!parentId.equals("-1")){
				sql = "select a.ID,a.C_SOURCE_ID,a.C_SHOREUSERNAME,a.C_SHOREUSERID,a.D_SHORE_STARTTIME,a.D_SHORE_ENDTIME,"
					+" a.C_PARENTID,b.C_FILENAME,b.C_FILETYPE,b.D_CREATETIME,b.D_UPDATETIME,b.F_FILESIZE,b.I_ISFOLDER,b.C_FILESUFFIX,"
					+" b.C_ICON,b.C_DESC,b.C_NEWFILENAME,b.C_CURRENTVERSION,c.I_PERVIEW,c.I_PERDOWNLOAD from sys_shorefile as a,"
					+" sys_fileinfo as b,sys_visituserinfo as c where a.C_SOURCE_ID = b.ID and b.ID=c.C_SOURCEFILEID and"
					+" c.C_VISITUSERID='"+userId+"' and a.C_PARENTID='"+parentId+"' and b.I_ISSHORED=1";
				
				countSQL = "select count(*) from sys_shorefile as a, sys_fileinfo as b,sys_visituserinfo as c where  a.C_SOURCE_ID"
					+" = b.ID and b.ID=c.C_SOURCEFILEID and  c.C_VISITUSERID='"+userId+"' and a.C_PARENTID='"+parentId+"' and b.I_ISSHORED=1";
			}else if(parentId.equals("-1")){
				sql = "select a.ID,a.C_SOURCE_ID,a.C_SHOREUSERNAME,a.C_SHOREUSERID,a.D_SHORE_STARTTIME,a.D_SHORE_ENDTIME,"
					+" a.C_PARENTID,b.C_FILENAME,b.C_FILETYPE,b.D_CREATETIME,b.D_UPDATETIME,b.F_FILESIZE,b.I_ISFOLDER,b.C_FILESUFFIX,"
					+" b.C_ICON,b.C_DESC,b.C_NEWFILENAME,b.C_CURRENTVERSION from sys_shorefile as a,"
					+" sys_fileinfo as b where a.C_SOURCE_ID = b.ID and a.C_PARENTID='"+parentId+"' and b.I_ISSHORED=1";
				
				countSQL = "select count(*) from sys_shorefile as a, sys_fileinfo as b where  a.C_SOURCE_ID"
					+" = b.ID and a.C_PARENTID='"+parentId+"' and b.I_ISSHORED=1";
			}
			
			
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
	public List<ShoreFile> getShoredFoldersByParentId(String parentId, String userId){
		try{
			String sql = "";
			if(!parentId.equals("-1")){
				sql = "select a.ID,a.C_SOURCE_ID,a.C_SHOREUSERNAME,a.C_SHOREUSERID,a.D_SHORE_STARTTIME,a.D_SHORE_ENDTIME,"
					+" a.C_PARENTID,b.C_FILENAME,b.C_FILETYPE,b.D_CREATETIME,b.D_UPDATETIME,b.F_FILESIZE,b.I_ISFOLDER,b.C_FILESUFFIX,"
					+" b.C_ICON,b.C_DESC,b.C_NEWFILENAME,b.C_CURRENTVERSION,c.I_PERVIEW,c.I_PERDOWNLOAD from sys_shorefile as a,"
					+" sys_fileinfo as b,sys_visituserinfo as c where a.C_SOURCE_ID = b.ID and b.ID=c.C_SOURCEFILEID and"
					+" c.C_VISITUSERID='"+userId+"' and a.C_PARENTID='"+parentId+"' and b.I_ISFOLDER=1";
			}else if(parentId.equals("-1")){
				sql = "select a.ID,a.C_SOURCE_ID,a.C_SHOREUSERNAME,a.C_SHOREUSERID,a.D_SHORE_STARTTIME,a.D_SHORE_ENDTIME,"
					+" a.C_PARENTID,b.C_FILENAME,b.C_FILETYPE,b.D_CREATETIME,b.D_UPDATETIME,b.F_FILESIZE,b.I_ISFOLDER,b.C_FILESUFFIX,"
					+" b.C_ICON,b.C_DESC,b.C_NEWFILENAME,b.C_CURRENTVERSION from sys_shorefile as a,"
					+" sys_fileinfo as b where a.C_SOURCE_ID = b.ID and a.C_PARENTID='"+parentId+"' and b.I_ISFOLDER=1";
			}
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
				s.setCreateTime((Date)vs[9]);
				s.setUpdateTime((Date)vs[10]);
				s.setFileSize(1);
				s.setIsFolder((Integer)vs[12]);
				s.setFileSuffix((String)vs[13]);
				s.setIcon((String)vs[14]);
				s.setDesc((String)vs[15]);
				s.setNewFileName((String)vs[16]);
				s.setCurrentVersion((String)vs[17]);
				if(vs.length>18){
					s.setPerView(((Integer)vs[18]).intValue());
					s.setPerDownLoad(((Integer)vs[19]).intValue());
				}else{
					s.setPerView(1);
				}
				
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
	public void insertShoreFile(ShoreFile shoreFile, List<EdocFile> mulus,User user,ShoreFile userHome) {
		//���жϸ��ļ��Ƿ��Ѿ�����
		//���û���ϼ�Ŀ¼�Ļ��򽫸ù����ļ������"�����ļ���"��Ŀ¼��,�������α����ϼ�Ŀ¼
		List<ShoreFile> list = new LinkedList<ShoreFile>();
		if(mulus!=null && !mulus.isEmpty()){
			String parentId = userHome.getId();
			for(int i=0;i<mulus.size();i++){
				EdocFile mulu = mulus.get(i);
				
				//�����Ŀ¼�Ѿ�������Ļ�������ӹ����ļ�(ShoreFile)��Ϣ
				//�����Ŀ¼δ������Ļ�����Ҫ��ӹ����ļ���(ShoreFile)��Ϣ
				if(mulu.getIsShored()==1){	
					ShoreFile existShoreFile = this.getShoreFileBySourceFileId(mulu.getId());
					if(existShoreFile!=null){
						parentId = existShoreFile.getId();
					}
				}else{
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
			shoreFile.setParentId(userHome.getId());
		}
		
		list.add(shoreFile);
		shoreFileDao.save(list);
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
				List<EdocFile> edocList = edocFileDao.getSubFileInfos(sFileId,1);
				if(edocList!=null && !edocList.isEmpty()){
					for(EdocFile e:edocList){
						sql += ",'"+e.getId()+"'";
					}
				}
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
