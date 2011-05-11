package com.edoc.scheduler;

import java.util.Date;
import java.util.List;

import org.springframework.scheduling.quartz.SimpleTriggerBean;
import com.edoc.scheduler.service.SchedulerService;
import com.edoc.utils.Timer;

/**
 * �Զ���SimpleTrigger,�����ݿ��л�ȡ������Ϣ
 * 
 * @author �³� 2011-5-10
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
					// ���ÿ�ʼʱ��
					Date startTime = null;
					if (!(t.getStartTime().trim().equals(""))) {
						startTime = Timer.convertToDate2(t.getStartTime());
					} else {
						startTime = new Date();
					}
					setStartTime(startTime);
					
					
					//���ý���ʱ�䣺�����ǰ״̬Ϊ"��ͣ",�����ø�����Ľ�ֹʱ������俪ʼʱ���Դﵽ��ִ�������Ŀ��(ͬʱ��������)
					//�����������ʱ��Ҫ�������ý�ֹʱ��
					if(t.getState()==0){
						setEndTime(startTime);
					}else{
						if (!(t.getEndTime().trim().equals(""))) {
							setEndTime(Timer.convertToDate2(t.getEndTime()));
						}
					}
					
					
					// �����ظ�����
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
