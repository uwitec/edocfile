package com.edoc.scheduler;

import java.util.Date;
import java.util.List;

import org.springframework.scheduling.quartz.SimpleTriggerBean;
import com.edoc.scheduler.service.SchedulerService;
import com.edoc.utils.Timer;

/**
 * 自定义SimpleTrigger,从数据库中获取配置信息
 * 
 * @author 陈超 2011-5-10
 * 
 */
public class MySimpleTrigger extends SimpleTriggerBean {
	private static final long serialVersionUID = 1L;

	private SchedulerService schedulerService = null;
	private String taskName = "";

	public MySimpleTrigger() {
	}

	public SchedulerService getSchedulerService() {
		return schedulerService;
	}

	public void setSchedulerService(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
		
		List<Task> tasks = schedulerService.getTasksByType(SchedulerService.TYPE_SIMPLE);
		if (tasks != null && !tasks.isEmpty()) {
			for (Task t : tasks) {
				if (t.getName().equals(taskName)) {
					this.setName(t.getName());
					this.setGroup("DEFAULT_GROUP");
					// 设置开始时间
					Date startTime = null;
					if (!(t.getStartTime().trim().equals(""))) {
						startTime = Timer.convertToDate2(t.getStartTime());
					} else {
						startTime = new Date();
					}
					setStartTime(startTime);
					
					
					//设置结束时间：如果当前状态为"暂停",则设置该任务的截止时间等于其开始时间以达到不执行任务的目的(同时加载任务)
					//在启动任务的时候要重新设置截止时间
					if(t.getState()==0){
						setEndTime(startTime);
					}else{
						if (!(t.getEndTime().trim().equals(""))) {
							setEndTime(Timer.convertToDate2(t.getEndTime()));
						}
					}
					
					
					// 设置重复次数
					if (t.getRepeatCount().equals("-1")) {
						setRepeatCount(SimpleTriggerBean.REPEAT_INDEFINITELY);
					} else {
						setRepeatCount(Integer.parseInt(t.getRepeatCount()));
					}
					setRepeatInterval(Long.parseLong(t.getRepeatInterval()) * 1000L);
				}
			}
		}
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}
