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
		System.out.println("#### 开始执行临时文件夹管理任务 ####");
		//获取任务执行的参数
		JobDataMap jobDataMap = arg0.getJobDetail().getJobDataMap();
		Map<String,Object> taskParam = (Map<String,Object>)jobDataMap.get("taskParam");
		if(taskParam!=null){
			//获取要删除的文件目录
			String tempFileDir = (String)taskParam.get("tempFileDir");
			if(StringUtils.isValid(tempFileDir)){
				File dir = new File(tempFileDir);
				if(dir.exists() && dir.isDirectory()){	//判断路径指定的文件是否存在且为目录
					
					//删除该目录下的所有文件
					File[] files = dir.listFiles();
					if(files!=null && files.length>0){
						for(File f:files){
							f.delete();	
						}
					}
				}else{
					System.out.println("#### 临时文件夹管理任务：指定的路径不存在或者不是文件夹! ####");
				}
			}else{
				System.out.println("#### 临时文件夹管理任务：没有指定文件夹的路径! ####");
			}
		}
	}

}
