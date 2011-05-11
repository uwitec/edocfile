package com.edoc.scheduler.action;


import java.util.List;
import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.scheduler.Task;
import com.edoc.scheduler.service.SchedulerService;
/**
 * 任务调度控制类
 * @author 陈超 2010-7-24
 *
 */
@Component("schedulerAction")
@Scope("prototype")
public class SchedulerAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private Task task = null;
	@Resource(name="schedulerService")
	private SchedulerService schedulerService = null;
	/**
	 * 获取认为列表
	 * @return
	 */
	public String getTasks(){
		List<Task> tasks = schedulerService.getTasks();
		this.setAttribute("tasks", tasks);
		
		return "showTasksPage";
	}
	
	public String startTask(){
		String id = this.getParameter("id");
		boolean flag = schedulerService.startTask(id);
		if(!flag){
			this.showMessage2(this.getResponse(), "任务启动操作失败:任务信息可能已经丢失或者启动过程中出现异常!", true);
			return null;
		}
		return getTasks();
	}
	
	public String stopTask(){
		String id = this.getParameter("id");
		boolean flag = schedulerService.stopTask(id);
		if(!flag){
			this.showMessage2(this.getResponse(), "任务暂停操作失败:任务信息可能已经丢失或者启动过程中出现异常!", true);
			return null;
		}
		return getTasks();
	}
	
	/**
	 * 修改任务
	 * @return
	 */
	public String updateTask(){
		String id = this.getParameter("id");
		task.setId(id);
		schedulerService.updateTask(task);
		this.showMessage(this.getResponse(), "修改任务成功!", true);
		return null; 
	}
	
	/**
	 * 修改任务信息前的准备工作
	 * @return
	 */
	public String breforeUpdateTask(){
		String id = this.getParameter("id");
		Task task = schedulerService.getTask(id);
		this.setAttribute("task", task);
		
		return "showUpdateTaskPage";
	}
	/**
	 * 创建新的任务
	 * @return
	 */
	public String createNewTask(){
		schedulerService.createNewTask(task);
		this.showMessage(this.getResponse(), "创建任务成功!", true);
		return null;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
