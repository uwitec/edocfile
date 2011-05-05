package com.edoc.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.edoc.scheduler.service.SchedulerService;

public class SchedulerContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("======== 销毁调度器 ========");
	}

	public void contextInitialized(ServletContextEvent arg0) {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
        String tempFileDir = arg0.getServletContext().getRealPath("\\temp");
        Map<String,Object> taskParam = new HashMap<String,Object>();
		taskParam.put("tempFileDir", tempFileDir);
        if(context!=null){
        	SchedulerService schedulerService = (SchedulerService)context.getBean("schedulerService");
        	List<Task> tasks = schedulerService.getTasks();
        	if(tasks!=null && !tasks.isEmpty()){
	        	for(Task task:tasks){
	    			if(task.getState() ==1){
	    				try {
	    					QuartzManager.addJob(task,taskParam);
	    				} catch (Exception e) {
	    					e.printStackTrace();
	    					System.out.println("======== 启动调度器'"+task.getName()+"'时出错 ========");
	    				}
	    			}
	    		}
        	}
        }
		System.out.println("======== 启动调度器 ========");
	}
}
