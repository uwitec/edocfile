package com.edoc.scheduler.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.SimpleTriggerBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edoc.dbsupport.PropertyFilter;
import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.scheduler.QuartzManager;
import com.edoc.scheduler.Task;
import com.edoc.scheduler.service.SchedulerService;
import com.edoc.utils.StringUtils;
import com.edoc.utils.Timer;
@Component("schedulerService")
@Transactional(readOnly=true)
public class SchedulerServiceImpl implements SchedulerService{

	@Resource(name="taskDao")
	private GenericDAO<Task,String> taskDao=null;
	
	@Resource(name="schedulerFactoryBean")
	private Scheduler scheduler = null;
	
	
	/**
	 * 获取任务
	 */
	public List<Task> getTasksByType(int type){
		List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
		PropertyFilter filter01 = new PropertyFilter("type",type,PropertyFilter.MatchType.EQ);
		filters.add(filter01);
		
		return taskDao.find(filters);
	}
	public Task getTask(String id) {
		
		return taskDao.get(id);
	}

	public List<Task> getTasks() {
	
		return taskDao.getAll();
	}
	
	
	/**
	 * 创建任务
	 * @param task
	 */
	@Transactional(readOnly=false)
	public void createNewTask(Task task){
		taskDao.save(task);
	}

	/**
	 * 修改任务
	 * @param task
	 */
	@Transactional(readOnly=false)
	public void updateTask(Task task){
		//首先获取对应的trigger
		try {
			if(task.getState()==1){
				Trigger trigger = scheduler.getTrigger(task.getName(), "DEFAULT_GROUP");
				// 设置开始时间
				if (!(task.getStartTime().trim().equals(""))) {
					trigger.setStartTime(Timer.convertToDate2(task.getStartTime()));
				} else {
					trigger.setStartTime(new Date());
				}
				if (!(task.getEndTime().trim().equals(""))) {
					trigger.setEndTime(Timer.convertToDate2(task.getEndTime()));
				}
				
				// 设置重复次数
				if (task.getRepeatCount().equals("-1")) {
					((SimpleTrigger) trigger).setRepeatCount(SimpleTriggerBean.REPEAT_INDEFINITELY);
				} else {
					((SimpleTrigger) trigger).setRepeatCount(Integer.parseInt(task.getRepeatCount()));
				}
				((SimpleTrigger) trigger).setRepeatInterval(Long.parseLong(task.getRepeatInterval()) * 1000L);
				
				
				//暂停并移去该trigger
				scheduler.pauseTrigger(task.getName(), "DEFAULT_GROUP");
				scheduler.unscheduleJob(task.getName(), "DEFAULT_GROUP");
				
				//重新调度
				scheduler.scheduleJob(trigger);
			
			}
			taskDao.update(task);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 启动任务
	 * @param id
	 */
	@Transactional(readOnly=false)
	public boolean startTask(String id){
		Task task = this.getTask(id);
		try {
			if(task.getState()==0){
				//首先获取对应的trigger
				Trigger trigger = scheduler.getTrigger(task.getName(), "DEFAULT_GROUP");
				if(!Timer.convertToString(trigger.getStartTime()).equals(Timer.convertToString(trigger.getEndTime()))){
					scheduler.resumeTrigger(task.getName(), "DEFAULT_GROUP");
				}else{
					//重新设置其endTime属性
					if(StringUtils.isValid(task.getEndTime())){
						trigger.setEndTime(Timer.convertToDate2(task.getEndTime()));
					}else{
						trigger.setEndTime(null);
					}
					
					//暂停并移去该trigger
					scheduler.pauseTrigger(task.getName(), "DEFAULT_GROUP");
					scheduler.unscheduleJob(task.getName(), "DEFAULT_GROUP");
					
					//重新调度
					scheduler.scheduleJob(trigger);
				}
				//更新task信息
				task.setState(1);
				taskDao.update(task);
				return true;
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 停止任务
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=false)
	public boolean stopTask(String id){
		Task task = this.getTask(id);
		if(task!=null){
			try {
				if(task.getState()==1){
					//暂停该trigger
					scheduler.pauseTrigger(task.getName(), "DEFAULT_GROUP");
					
					task.setState(0);
					QuartzManager.removeJob(id);
					taskDao.update(task);
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
		
	}
}
