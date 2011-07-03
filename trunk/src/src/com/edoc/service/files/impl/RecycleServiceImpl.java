package com.edoc.service.files.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.FileVersion;
import com.edoc.entity.files.RecycleInfo;
import com.edoc.lucene.index.IndexService;
import com.edoc.orm.hibernate.dao.FileDAO;
import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.service.files.RecycleService;
import com.edoc.utils.StringUtils;
/**
 * RecycleService实现类
 * @author 陈超
 *
 */
@Component("recycleService")
@Transactional(readOnly=true)
public class RecycleServiceImpl implements RecycleService{

	@Resource(name="recycleDao")
	private GenericDAO<RecycleInfo,String> recycleDao=null;
	
	@Resource(name="edocFileDao")
	private FileDAO edocFileDao=null;
	
	@Resource(name="fileVersionDao")
	private GenericDAO<FileVersion,String> fileVersionDao=null;
	
	@Resource(name="defaultIndexService")
	private IndexService indexService = null;
	
	/**
	 * 获取回收站中的原始文件ID
	 * @return
	 */
	public String[] getRecycleFileIds(){
		String[] ids = null;
		List<RecycleInfo> recycleInfos = recycleDao.getAll();
		
		//如果recycleInfos不为空,遍历recycleInfos中的元素并设置ids
		if(recycleInfos!=null && !recycleInfos.isEmpty()){
			ids = new String[recycleInfos.size()];
			int index = 0;
			for(RecycleInfo r:recycleInfos){
				ids[index] = r.getSourceId();
				index++;
			}
		}
		return ids;
	}
	
	/**
	 * 清空回收站,清空回收站的同时要删除文件的信息包括：文件记录、版本信息、共享信息等等
	 */
	@Transactional(readOnly=false)
	public void clearAll() {
		String[] recycleFileIds = this.getRecycleFileIds();
		if(recycleFileIds!=null && recycleFileIds.length>0){
			
			//第一步：删除sys_fileinfo表中对于的文件
			String sql = "delete from sys_fileinfo where id in ('000'";					//删除文件信息
			String sql1 = "delete from sys_fileversion where C_EDOCFILEID in ('000'";	//删除文件版本信息
			for(String id:recycleFileIds){
				List<EdocFile> edocList = edocFileDao.getSubFileInfos(id,1);
				if(edocList!=null && !edocList.isEmpty()){
					for(EdocFile e:edocList){
						sql += ",'"+e.getId()+"'";
						sql1 += ",'"+e.getId()+"'";
					}
				}
			}
			sql += ")";
			sql1 += ")";
			edocFileDao.delete(sql);
			//删除版本信息
			fileVersionDao.delete(sql1);
			
			//第二步：清空回收站的信息
			String sql4 = "delete from sys_recycle";
			recycleDao.delete(sql4);
			
			
			indexService.deleteIndex(recycleFileIds);
		}
	}

	/**
	 * 删除回收站中的文件,删除文件的同时要删除文件的信息包括：文件记录、版本信息、共享信息等等
	 */
	@Transactional(readOnly=false)
	public void delete(String[] recycleInfoIds) {
		if(recycleInfoIds!=null){
			//查询要被删除的回收站中的信息
			String querySQL = "select ID,C_SOURCE_ID,C_TABLENAME,C_DISPLAY_NAME,C_CREATOR_ID,D_DELETE_TIME,I_ISDELETE from sys_recycle where id in ('000'";
			for(String id:recycleInfoIds){
				querySQL += ",'"+id+"'";
			}
			querySQL += ")";
			List<RecycleInfo> recycles = this.parserRecycleResult(recycleDao.excuteQuery(querySQL));
			
			String sql = "delete from sys_fileinfo where id in ('000'";
			String sql2 = "delete from sys_recycle where id in('000'";
			String sql3 = "delete from sys_fileversion where C_EDOCFILEID in ('000'";	//删除文件版本信息
			
			String[] sourceFileIds = new String[recycles.size()];
			int index = 0;
			for(RecycleInfo r:recycles){
				List<EdocFile> edocList = edocFileDao.getSubFileInfos(r.getSourceId(),1);
				if(edocList!=null && !edocList.isEmpty()){
					for(EdocFile e:edocList){
						sql += ",'"+e.getId()+"'";
						sql3 += ",'"+e.getId()+"'";
					}
				}
				sql2 += ",'"+r.getId()+"'";
				sourceFileIds[index] = r.getSourceId();
				index++;
			}
			
			sql += ")";
			sql2 += ")";
			sql3 += ")";
			
			//第一步：删除sys_fileinfo表中对于的文件
			edocFileDao.delete(sql);
			
			//第二步：删除回收站中的信息
			recycleDao.delete(sql2);
			
			fileVersionDao.delete(sql3);
			
			indexService.deleteIndex(sourceFileIds);
		}
	}

	/**
	 * 获取回收站的列表信息
	 */
	@SuppressWarnings("unchecked")
	public PageValueObject<RecycleInfo> getRecycleList(int currentPage,
			int pageSize, String userId, String fileName) {
		PageValueObject<RecycleInfo> page = new PageValueObject<RecycleInfo>(currentPage,pageSize);
		String sql = "select a.ID,a.C_SOURCE_ID,a.C_TABLENAME,b.C_FILENAME,a.C_CREATOR_ID,a.D_DELETE_TIME,"
			+" b.C_FILETYPE,b.D_CREATETIME,b.D_UPDATETIME,b.F_FILESIZE,b.I_ISFOLDER,b.C_FILESUFFIX,"
			+" b.C_ICON,b.C_DESC,b.C_NEWFILENAME,b.C_CURRENTVERSION from sys_recycle as a, sys_fileinfo as b where"
			+" a.C_SOURCE_ID = b.ID ";
		
		String countSQL = "select count(*) from sys_recycle as a, sys_fileinfo as b where  a.C_SOURCE_ID = b.ID ";
		if(StringUtils.isValid(userId)){
			sql += " and a.C_CREATOR_ID='"+userId+"' ";
			countSQL += " and a.C_CREATOR_ID='"+userId+"' ";
		}
		if(StringUtils.isValid(fileName)){
			sql += " and b.C_FILENAME='"+fileName+"'";
			countSQL += " and b.C_FILENAME='"+fileName+"'";
		}
		
		List r = recycleDao.excuteQuerySQL(sql, page.getFirstResult(), page.getPageSize());
		page.setResult(parserResult(r));
		page.setTotalRows(recycleDao.excuteGetCountSQL(countSQL));
		
		return page;
	}

	@SuppressWarnings("unchecked")
	private List<RecycleInfo> parserRecycleResult(List r) {
		if(r!=null && !r.isEmpty()){
			List<RecycleInfo> results = new LinkedList<RecycleInfo>();
			for(Object o :r){
				Object[] vs = (Object[])o;
				RecycleInfo s = new RecycleInfo();
				s.setId((String)vs[0]);
				s.setSourceId((String)vs[1]);
				s.setTableName((String)vs[2]);
				s.setDisplayName((String)vs[3]);
				s.setCreatorId((String)vs[4]);
				s.setDeleteTime(new Date());
				s.setIsDelete(((Integer)vs[6]).intValue());
				
				results.add(s);
			}
			return results;
		}
		return null;
	}
	/**
	 * 解析查询结果集
	 * @param r
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<RecycleInfo> parserResult(List r) {
		if(r!=null && !r.isEmpty()){
			List<RecycleInfo> results = new LinkedList<RecycleInfo>();
			for(Object o :r){
				Object[] vs = (Object[])o;
				RecycleInfo s = new RecycleInfo();
				s.setId((String)vs[0]);
				s.setSourceId((String)vs[1]);
				s.setTableName((String)vs[2]);
				s.setDisplayName((String)vs[3]);
				s.setCreatorId((String)vs[4]);
				s.setDeleteTime(new Date());
				
				s.setFileType((String)vs[6]);
				s.setCreateTime(new Date());
				s.setUpdateTime(new Date());
				s.setFileSize(1);
				s.setIsFolder((Integer)vs[10]);
				s.setFileSuffix((String)vs[11]);
				s.setIcon((String)vs[12]);
				s.setDesc((String)vs[13]);
				s.setNewFileName((String)vs[14]);
				s.setCurrentVersion((String)vs[15]);
				results.add(s);
			}
			return results;
		}
		return null;
	}

	/**
	 * 还原项目
	 */
	@Transactional(readOnly=false)
	public void revert(String[] recycleIds) {
		if(recycleIds!=null){
			String deleteSql = "delete from sys_recycle where id in ('000'";
			String sql = "update sys_fileinfo set I_ISDELETE = 0 where id in (select c_source_id from sys_recycle where id in('000'";
			for(String id:recycleIds){
				sql += ",'"+id+"'";
				deleteSql += ",'"+id+"'";
			}
			
			sql += "))";
			deleteSql += ")";
			edocFileDao.update(sql);
			recycleDao.delete(deleteSql);
		}
	}

}
