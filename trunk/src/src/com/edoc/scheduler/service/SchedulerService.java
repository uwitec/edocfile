package com.edoc.scheduler.service;

import java.util.List;
import java.util.Map;

import com.edoc.scheduler.Task;

/**
 * ������ȷ���ӿ�,ʵ���� {@link SchedulerServiceImpl}
 * @author �³� 2011-4-25
 *
 */
public interface SchedulerService {
	
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
	 * @param taskParam 
	 */
	public boolean startTask(String id, Map<String, Object> taskParam);

	/**
	 * ֹͣ����
	 * @param id
	 * @return
	 */
	public boolean stopTask(String id);
	
	
	
}
