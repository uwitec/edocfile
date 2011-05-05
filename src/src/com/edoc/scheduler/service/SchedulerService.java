package com.edoc.scheduler.service;

import java.util.List;
import java.util.Map;

import com.edoc.scheduler.Task;

/**
 * 任务调度服务接口,实现类 {@link SchedulerServiceImpl}
 * @author 陈超 2011-4-25
 *
 */
public interface SchedulerService {
	
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
	 * @param taskParam 
	 */
	public boolean startTask(String id, Map<String, Object> taskParam);

	/**
	 * 停止任务
	 * @param id
	 * @return
	 */
	public boolean stopTask(String id);
	
	
	
}
