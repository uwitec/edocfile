package com.edoc.service.files.impl;

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
import com.edoc.entity.files.FileUseRecord;
import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.service.files.FileUseRecordService;

@Component("fileUseRecordService")
@Transactional(readOnly=true)
public class FileUseRecordServiceImpl implements FileUseRecordService{
	
	@Resource(name="fileUseRecordDao")
	private GenericDAO<FileUseRecord,String> fileUseRecordDao=null;
	
	/**
	 * 添加文件使用记录
	 * @param user			使用用户信息
	 * @param edocFile		文件信息
	 * @param useType		使用类型
	 */
	@Transactional(readOnly = false)
	public void addFileUseRecord(User user, EdocFile edocFile,int useType){
		
		FileUseRecord fileUseRecord = new FileUseRecord(user,edocFile,useType);
		fileUseRecordDao.save(fileUseRecord);
	}

	/**
	 * 添加文件使用记录
	 * @param fileUseRecord
	 */
	@Transactional(readOnly = false)
	public void addFileUseRecord(FileUseRecord fileUseRecord) {
		fileUseRecordDao.save(fileUseRecord);
		
	}

	/**
	 * 根据用户ID分页查询该用户的文件使用记录信息
	 * @param userId		用户ID
	 * @param type			使用类型
	 * @param startTime		时间参数：开始时间
	 * @param endTime       时间参数：截止时间
	 * @param currentPage	分页查询参数：当前页码
	 * @param pageSize		分页查询参数：每页记录数
	 * @return
	 */
	public PageValueObject<FileUseRecord> getFileUseRecords(String userId,int type,Date startTime,
			Date endTime, int currentPage, int pageSize) {
		List<PropertyFilter> filterList = new LinkedList<PropertyFilter>();
		PropertyFilter filter0 = new PropertyFilter("userId",userId,PropertyFilter.MatchType.EQ);
		filterList.add(filter0);
		
		if(type!=FileUseRecordService.USETYPE_ALL){
			PropertyFilter filter1 = new PropertyFilter("userType",type,PropertyFilter.MatchType.EQ);
			filterList.add(filter1);
		}
		
		if(startTime!=null){
			PropertyFilter filter2 = new PropertyFilter("createTime",startTime,PropertyFilter.MatchType.LE);
			filterList.add(filter2);
		}
		
		if(endTime!=null){
			PropertyFilter filter3 = new PropertyFilter("createTime",endTime,PropertyFilter.MatchType.GE);
			filterList.add(filter3);
		}
		PageValueObject<FileUseRecord> page = new PageValueObject<FileUseRecord>(currentPage,pageSize);
		page.setResult(fileUseRecordDao.find(filterList, page.getFirstResult(), page.getPageSize()));
		page.setTotalRows(fileUseRecordDao.getCount(filterList));
		return page;
	}

	/**
	 * 获取文件使用记录信息
	 * @param type			使用类型
	 * @param startTime		时间参数：开始时间
	 * @param endTime       时间参数：截止时间
	 * @param currentPage	分页查询参数：当前页码
	 * @param pageSize		分页查询参数：每页记录数
	 * @return
	 */
	public PageValueObject<FileUseRecord> getFileUseRecords(int type,Date startTime,
			Date endTime, int currentPage, int pageSize) {
		List<PropertyFilter> filterList = new LinkedList<PropertyFilter>();
		if(type!=FileUseRecordService.USETYPE_ALL){
			PropertyFilter filter1 = new PropertyFilter("userType",type,PropertyFilter.MatchType.EQ);
			filterList.add(filter1);
		}
		
		if(startTime!=null){
			PropertyFilter filter2 = new PropertyFilter("createTime",startTime,PropertyFilter.MatchType.LE);
			filterList.add(filter2);
		}
		
		if(endTime!=null){
			PropertyFilter filter3 = new PropertyFilter("createTime",endTime,PropertyFilter.MatchType.GE);
			filterList.add(filter3);
		}
		PageValueObject<FileUseRecord> page = new PageValueObject<FileUseRecord>(currentPage,pageSize);
		page.setResult(fileUseRecordDao.find(filterList, page.getFirstResult(), page.getPageSize()));
		page.setTotalRows(fileUseRecordDao.getCount(filterList));
		return page;
	}

	/**
	 * 查找最近使用的文件记录信息(取前num条记录)
	 * @param userId		用户ID
	 * @param type			使用类型
	 * @param num			前num条记录,如果 num=-1 表示回去所有的记录
	 * @return
	 */
	public List<FileUseRecord> getRecentUseFileRecord(String userId, int type,
			int num) {
		List<PropertyFilter> filterList = new LinkedList<PropertyFilter>();
		PropertyFilter filter0 = new PropertyFilter("userId",userId,PropertyFilter.MatchType.EQ);
		filterList.add(filter0);
		
		if(type!=FileUseRecordService.USETYPE_ALL){
			PropertyFilter filter1 = new PropertyFilter("userType",type,PropertyFilter.MatchType.EQ);
			filterList.add(filter1);
		}
		if(num==-1){
			return fileUseRecordDao.find(filterList);
		}else{
			PageValueObject<FileUseRecord> page = new PageValueObject<FileUseRecord>(0,num);
			return fileUseRecordDao.find(filterList, page.getFirstResult(), page.getPageSize());
		}
	}

}
