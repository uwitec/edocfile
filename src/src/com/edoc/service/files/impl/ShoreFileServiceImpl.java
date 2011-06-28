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
 * ShoreFileService的实现类
 * @author 陈超 2010-11-16
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
	 * 共享文件操作。在共享该文件的同时要将该文件的上层文件夹设置成已共享(但文件夹下面的其他文件不做共享操作)
	 * 首先在"共享文件夹"下检查是否存在一个名称为: 姓名(登录名) 的文件夹,如果不存在则创建,然后将共享文件的
	 * parentId 设置成该文件夹的ID
	 * 
	 * @param shoreFile			共享文件夹信息
	 * @param visitUserInfos	共享用户的信息
	 * @param user				共享该文件的用户信息
	 * @param shoreNowFlag		是否立即共享
	 * @author 陈超 2011-06-02
	 */
	@Transactional(readOnly=false)
	public boolean shoreFile(ShoreFile shoreFile,List<VisitUserInfo> visitUserInfos,
			User user, boolean shoreNowFlag, boolean sendMsgFlag){
		try{
			EdocFile sourceFile = fileService.getFileById(shoreFile.getSourceFileId());
			//获取共享文件的上级目录
			List<EdocFile> mulus = edocFileDao.getParentFiles(shoreFile.getSourceFileId(), 0, 0);
			
			//添加访问该文件的用户信息
			visitUserService.insertVisitUserInfo(visitUserInfos, mulus);					
			String msg = "";
			//如果立即共享的话则执行以下操作,如果是暂不共享的话则要先查看该文件是否已经共享了,如果已经共享了则将其设置成未共享
			//如果未共享则不作任何操作
			if(shoreNowFlag){
				msg = user.getTrueName()+"共享了文件'"+sourceFile.getFileName()+"'";
				if(sourceFile.getIsShored()==0){
					//"共享文件夹"下用户上传文件的目录信息
					ShoreFile userHome = createUserDirIfNotExist(user);
					
					//添加共享文件的信息
					if(!isExist(shoreFile.getId())){
						insertShoreFile(shoreFile,mulus,user,userHome);	
					}
					//修改文件共享状态,包括该文件的上级目录
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
				msg = user.getTrueName()+"取消了对文件'"+sourceFile.getFileName()+"'的共享";
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
			sendMsg.setTitle("系统消息");
			sendMsg.setContent(msg);
			sendMsg.setFromUserName("系统消息");
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
	 * 如果以该用户名称命名的文件夹不存在的话则创建该共享文件信息
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
			efile.setParentId("-1");			//设置成-1,不在"我的文件夹"中显示
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
	 * 获取共享文件信息
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
	 * 获取共享文件夹信息
	 * @param parentId	上级菜单Id
	 * @param userId	用户Id
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
	 * 插入新的共享文件信息
	 * @param shoreFile 共享文件信息
	 * @author 			陈超 2010-11-16
	 */
	@Transactional(readOnly=false)
	public void insertShoreFile(ShoreFile shoreFile, List<EdocFile> mulus,User user,ShoreFile userHome) {
		//先判断该文件是否已经共享
		//如果没有上级目录的话则将该共享文件存放在"共享文件夹"的目录下,否则依次保持上级目录
		List<ShoreFile> list = new LinkedList<ShoreFile>();
		if(mulus!=null && !mulus.isEmpty()){
			String parentId = userHome.getId();
			for(int i=0;i<mulus.size();i++){
				EdocFile mulu = mulus.get(i);
				
				//如果该目录已经共享过的话则不再添加共享文件(ShoreFile)信息
				//如果该目录未共享过的话则需要添加共享文件夹(ShoreFile)信息
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
	 * 判断记录是否存在
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
	 * 删除共享的文件信息
	 * @param sourceFileIds
	 * @author 			陈超
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
	 * 根据源文件Id获取对于的共享文件信息
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
