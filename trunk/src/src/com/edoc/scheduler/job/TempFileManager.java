package com.edoc.scheduler.job;

import java.io.File;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.edoc.utils.StringUtils;

public class TempFileManager implements StatefulJob{

	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("#### ��ʼִ����ʱ�ļ��й������� ####");
		//��ȡ����ִ�еĲ���
		JobDataMap jobDataMap = arg0.getJobDetail().getJobDataMap();
		Map<String,Object> taskParam = (Map<String,Object>)jobDataMap.get("taskParam");
		if(taskParam!=null){
			//��ȡҪɾ�����ļ�Ŀ¼
			String tempFileDir = (String)taskParam.get("tempFileDir");
			if(StringUtils.isValid(tempFileDir)){
				File dir = new File(tempFileDir);
				if(dir.exists() && dir.isDirectory()){	//�ж�·��ָ�����ļ��Ƿ������ΪĿ¼
					
					//ɾ����Ŀ¼�µ������ļ�
					File[] files = dir.listFiles();
					if(files!=null && files.length>0){
						for(File f:files){
							f.delete();	
						}
					}
				}else{
					System.out.println("#### ��ʱ�ļ��й�������ָ����·�������ڻ��߲����ļ���! ####");
				}
			}else{
				System.out.println("#### ��ʱ�ļ��й�������û��ָ���ļ��е�·��! ####");
			}
		}
	}

}
