package com.edoc.scheduler.action;


import java.util.List;
import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.scheduler.Task;
import com.edoc.scheduler.service.SchedulerService;
/**
 * ������ȿ�����
 * @author �³� 2010-7-24
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
	 * ��ȡ��Ϊ�б�
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
			this.showMessage2(this.getResponse(), "������������ʧ��:������Ϣ�����Ѿ���ʧ�������������г����쳣!", true);
			return null;
		}
		return getTasks();
	}
	
	public String stopTask(){
		String id = this.getParameter("id");
		boolean flag = schedulerService.stopTask(id);
		if(!flag){
			this.showMessage2(this.getResponse(), "������ͣ����ʧ��:������Ϣ�����Ѿ���ʧ�������������г����쳣!", true);
			return null;
		}
		return getTasks();
	}
	
	/**
	 * �޸�����
	 * @return
	 */
	public String updateTask(){
		String id = this.getParameter("id");
		task.setId(id);
		schedulerService.updateTask(task);
		this.showMessage(this.getResponse(), "�޸�����ɹ�!", true);
		return null; 
	}
	
	/**
	 * �޸�������Ϣǰ��׼������
	 * @return
	 */
	public String breforeUpdateTask(){
		String id = this.getParameter("id");
		Task task = schedulerService.getTask(id);
		this.setAttribute("task", task);
		
		return "showUpdateTaskPage";
	}
	/**
	 * �����µ�����
	 * @return
	 */
	public String createNewTask(){
		schedulerService.createNewTask(task);
		this.showMessage(this.getResponse(), "��������ɹ�!", true);
		return null;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
