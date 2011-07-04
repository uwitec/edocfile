package com.edoc.service.files;

import java.util.Date;
import java.util.List;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.files.FileUseRecord;

/**
 * 文件使用记录接口
 * @author 陈超
 *
 */
public interface FileUseRecordService {
	public static int USETYPE_ALL = -1;
	public static int USETYPE_VIEW = 0;
	public static int USETYPE_EDIT = 1;
	public static int USETYPE_DOWNLOAD = 2;
	public static int USETYPE_BORROW = 3;
	
	/**
	 * 添加文件使用记录
	 * @param fileUseRecord
	 */
	public void addFileUseRecord(FileUseRecord fileUseRecord);
	
	
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
	public PageValueObject<FileUseRecord> getFileUseRecords(String userId,int type,Date startTime,Date endTime, int currentPage, int pageSize);
	
	/**
	 * 获取文件使用记录信息
	 * @param type			使用类型
	 * @param startTime		时间参数：开始时间
	 * @param endTime       时间参数：截止时间
	 * @param currentPage	分页查询参数：当前页码
	 * @param pageSize		分页查询参数：每页记录数
	 * @return
	 */
	public PageValueObject<FileUseRecord> getFileUseRecords(int type,Date startTime,Date endTime, int currentPage, int pageSize);
	
	/**
	 * 查找最近使用的文件记录信息(取前num条记录)
	 * @param userId		用户ID
	 * @param type			使用类型
	 * @param num			前num条记录,如果 num=-1 表示回去所有的记录
	 * @return
	 */
	public List<FileUseRecord> getRecentUseFileRecord(String userId,int type, int num);
	
}
