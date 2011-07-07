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
 * �ļ�ʹ�ü�¼��Ϣ
 * @author �³� 2011-07-04
 */
@Component("fileUseRecordAction")
@Scope("prototype")
public class FileUseRecordAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	@Resource(name="fileUseRecordService")
	private FileUseRecordService fileUseRecordService = null;
	
	/**
	 * ��ʾ����ļ�ʹ�ü�¼,����������ҳչʾ��
	 * @return
	 */
	public String workSpaceCurrentFileUseRecords(){
		User user = (User)this.getSession().getAttribute("DOCUSER");
		
		//��ȡ��ʾ�ļ�¼��
		int recordCount = 10;
		String recordCountStr = this.getParameter("recordCount");
		if(StringUtils.isValid(recordCountStr)){
			recordCount = Integer.parseInt(recordCountStr);
		}
		
		//��ѯ���ʹ�õ��ļ���¼��Ϣ
		List<FileUseRecord> fileUseRecords = fileUseRecordService.getRecentUseFileRecord(user.getId(), FileUseRecordService.USETYPE_ALL, recordCount);
		this.setAttribute("fileUseRecords", fileUseRecords);
		return "workSpaceCurrentFileUseRecord";
	}
	
}
