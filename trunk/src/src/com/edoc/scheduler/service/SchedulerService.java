package com.edoc.scheduler.service;

import java.util.List;

import com.edoc.scheduler.Task;

/**
 * ������ȷ���ӿ�,ʵ���� {@link SchedulerServiceImpl}
 * @author �³� 2011-4-25
 *
 */
public interface SchedulerService {
	//SimpleTrigger 2=CronTrigger���� 3=CronTrigger����
	public static final int TYPE_SIMPLE = 1;
	public static final int TYPE_CRON1 = 2;
	public static final int TYPE_CRON2 = 3;
	
	/**
	 * ��ȡ���е�������Ϣ
	 * @return
	 */
	public List<Task> getTasks();
	
	/**
	 * ��ȡ����������Ϣ
	 * @param id
	 * @return
	 */
	public Task getTask(String id);

	
	/**
	 * ��������
	 * @param task
	 */
	public void createNewTask(Task task);

	/**
	 * �޸�����
	 * @param task
	 */
	public void updateTask(Task task);

	/**
	 * ��������
	 * @param id
	 */
	public boolean startTask(String id);

	/**
	 * ֹͣ����
	 * @param id
	 * @return
	 */
	public boolean stopTask(String id);

	/**
	 * ��ȡ����
	 */
	public List<Task> getTasksByType(int type);
	
	
	
}
