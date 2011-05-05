package com.edoc.scheduler.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.scheduler.QuartzManager;
import com.edoc.scheduler.Task;
import com.edoc.scheduler.service.SchedulerService;
@Component("schedulerService")
@Transactional(readOnly=true)
public class SchedulerServiceImpl implements SchedulerService{

	@Resource(name="taskDao")
	private GenericDAO<Task,String> taskDao=null;
	public Task getTask(String id) {
		
		return taskDao.get(id);
	}

	public List<Task> getTasks() {
	
		return taskDao.getAll();
	}
	
	
	/**
	 * ��������
	 * @param task
	 */
	@Transactional(readOnly=false)
	public void createNewTask(Task task){
		taskDao.save(task);
	}

	/**
	 * �޸�����
	 * @param task
	 */
	@Transactional(readOnly=false)
	public void updateTask(Task task){
		taskDao.update(task);
	}
	
	/**
	 * ��������
	 * @param id
	 */
	@Transactional(readOnly=false)
	public boolean startTask(String id,Map<String, Object> taskParam){
		Task task = this.getTask(id);
		if(task!=null){
			try {
				task.setState(1);
				QuartzManager.removeJob(id);
				QuartzManager.addJob(task,taskParam);
				taskDao.update(task);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	/**
	 * ֹͣ����
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=false)
	public boolean stopTask(String id){
		Task task = this.getTask(id);
		if(task!=null){
			try {
				task.setState(0);
				QuartzManager.removeJob(id);
				taskDao.update(task);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
		
	}
}
