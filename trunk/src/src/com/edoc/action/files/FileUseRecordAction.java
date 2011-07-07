package com.edoc.action.files;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.entity.baseinfo.User;
import com.edoc.entity.files.FileUseRecord;
import com.edoc.service.files.FileUseRecordService;
import com.edoc.utils.StringUtils;

/**
 * 文件使用记录信息
 * @author 陈超 2011-07-04
 */
@Component("fileUseRecordAction")
@Scope("prototype")
public class FileUseRecordAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	@Resource(name="fileUseRecordService")
	private FileUseRecordService fileUseRecordService = null;
	
	/**
	 * 显示最近文件使用记录,是用来在首页展示的
	 * @return
	 */
	public String workSpaceCurrentFileUseRecords(){
		User user = (User)this.getSession().getAttribute("DOCUSER");
		
		//获取显示的记录数
		int recordCount = 10;
		String recordCountStr = this.getParameter("recordCount");
		if(StringUtils.isValid(recordCountStr)){
			recordCount = Integer.parseInt(recordCountStr);
		}
		
		//查询最近使用的文件记录信息
		List<FileUseRecord> fileUseRecords = fileUseRecordService.getRecentUseFileRecord(user.getId(), FileUseRecordService.USETYPE_ALL, recordCount);
		this.setAttribute("fileUseRecords", fileUseRecords);
		return "workSpaceCurrentFileUseRecord";
	}
	
}
