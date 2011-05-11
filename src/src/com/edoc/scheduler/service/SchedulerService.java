package com.edoc.scheduler.service;

import java.util.List;

import com.edoc.scheduler.Task;

/**
 * 任务调度服务接口,实现类 {@link SchedulerServiceImpl}
 * @author 陈超 2011-4-25
 *
 */
public interface SchedulerService {
	//SimpleTrigger 2=CronTrigger定期 3=CronTrigger定周
	public static final int TYPE_SIMPLE = 1;
	public static final int TYPE_CRON1 = 2;
	public static final int TYPE_CRON2 = 3;
	
	/**
	 * 获取所有的任务信息
	 * @return
	 */
	public List<Task> getTasks();
	
	/**
	 * 获取单个任务信息
	 * @param id
	 * @return
	 */
	public Task getTask(String id);

	
	/**
	 * 创建任务
	 * @param task
	 */
	public void createNewTask(Task task);

	/**
	 * 修改任务
	 * @param task
	 */
	public void updateTask(Task task);

	/**
	 * 启动任务
	 * @param id
	 */
	public boolean startTask(String id);

	/**
	 * 停止任务
	 * @param id
	 * @return
	 */
	public boolean stopTask(String id);

	/**
	 * 获取任务
	 */
	public List<Task> getTasksByType(int type);
	
	
	
}
