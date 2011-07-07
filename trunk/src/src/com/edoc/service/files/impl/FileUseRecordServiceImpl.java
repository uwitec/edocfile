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
	 * ����ļ�ʹ�ü�¼
	 * @param user			ʹ���û���Ϣ
	 * @param edocFile		�ļ���Ϣ
	 * @param useType		ʹ������
	 */
	@Transactional(readOnly = false)
	public void addFileUseRecord(User user, EdocFile edocFile,int useType){
		
		FileUseRecord fileUseRecord = new FileUseRecord(user,edocFile,useType);
		fileUseRecordDao.save(fileUseRecord);
	}

	/**
	 * ����ļ�ʹ�ü�¼
	 * @param fileUseRecord
	 */
	@Transactional(readOnly = false)
	public void addFileUseRecord(FileUseRecord fileUseRecord) {
		fileUseRecordDao.save(fileUseRecord);
		
	}

	/**
	 * �����û�ID��ҳ��ѯ���û����ļ�ʹ�ü�¼��Ϣ
	 * @param userId		�û�ID
	 * @param type			ʹ������
	 * @param startTime		ʱ���������ʼʱ��
	 * @param endTime       ʱ���������ֹʱ��
	 * @param currentPage	��ҳ��ѯ��������ǰҳ��
	 * @param pageSize		��ҳ��ѯ������ÿҳ��¼��
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
	 * ��ȡ�ļ�ʹ�ü�¼��Ϣ
	 * @param type			ʹ������
	 * @param startTime		ʱ���������ʼʱ��
	 * @param endTime       ʱ���������ֹʱ��
	 * @param currentPage	��ҳ��ѯ��������ǰҳ��
	 * @param pageSize		��ҳ��ѯ������ÿҳ��¼��
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
	 * �������ʹ�õ��ļ���¼��Ϣ(ȡǰnum����¼)
	 * @param userId		�û�ID
	 * @param type			ʹ������
	 * @param num			ǰnum����¼,��� num=-1 ��ʾ��ȥ���еļ�¼
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
