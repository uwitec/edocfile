package com.edoc.scheduler;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.servlet.ServletContext;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.edoc.utils.Timer;


public class QuartzManager {
	private static SchedulerFactory sf = new StdSchedulerFactory();
	private static String JOB_GROUP_NAME = "group1";
	private static String TRIGGER_GROUP_NAME = "trigger1";
	
	 /** *//**
	    * ���һ����ʱ����ʹ��Ĭ�ϵ�������������������������������
	    * @param task ���������
	    * @param ServletContext     Ӧ�ó���������
	    * @throws SchedulerException
	    * @throws ParseException 
	    * @throws ParseException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	    */
	   public static void addJob(Task task,ServletContext context) throws SchedulerException, ParseException, InstantiationException, IllegalAccessException, ClassNotFoundException {
	       Scheduler sched = sf.getScheduler();
	       Job imp = (Job) Class.forName(task.getClassname()).newInstance();
	       JobDetail jobDetail = new JobDetail(task.getId(), JOB_GROUP_NAME, imp.getClass());//�������������飬����ִ����
	       jobDetail.getJobDataMap().put("ServletContext", context);
	       //������
	       Trigger trigger = null;
	       if(task.getType()==1){
	    	  trigger = getSimpleTrigger(task);
	       }else{
	    	  trigger = getCronTrigger(task);
	       }
	       sched.scheduleJob(jobDetail,trigger);
	       //����
	       if(!sched.isShutdown()){
	          sched.start();
	       }
	   }
	   /**
	    * �������
	    * @param task
	 * @throws SchedulerException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	    */
	   public static void addJob(Task task,Map<String, Object> taskParam) throws SchedulerException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		   Scheduler sched = sf.getScheduler();
	       Job imp = (Job) Class.forName(task.getClassname()).newInstance();
	       JobDetail jobDetail = new JobDetail(task.getId(), JOB_GROUP_NAME, imp.getClass());//�������������飬����ִ����
	       if(taskParam!=null){
	    	   jobDetail.getJobDataMap().put("taskParam", taskParam);
	       }
	       
	       //������
	       Trigger trigger = null;
	       if(task.getType()==1){
	    	  trigger = getSimpleTrigger(task);
	       }else{
	    	  trigger = getCronTrigger(task);
	       }
	       sched.scheduleJob(jobDetail,trigger);
	       //����
	       if(!sched.isShutdown()){
	          sched.start();
	       }
		}
	 /** *//**
	    * ���һ����ʱ����ʹ��Ĭ�ϵ�������������������������������
	    * @param jobName ������
	    * @param job     ����
	    * @param time    ʱ�����ã��ο�quartz˵���ĵ�
	    * @throws SchedulerException
	    * @throws ParseException 
	    * @throws ParseException
	    */
	   public static void addJob(String jobName,Job job,String time) throws SchedulerException, ParseException {
	       Scheduler sched = sf.getScheduler();
	       JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, job.getClass());//�������������飬����ִ����
	       //������
	       CronTrigger trigger =  new CronTrigger(jobName, TRIGGER_GROUP_NAME);//��������,��������
	       trigger.setCronExpression(time);//������ʱ���趨
	       sched.scheduleJob(jobDetail,trigger);
	       //����
	       if(!sched.isShutdown()){
	          sched.start();
	       }
	   }
	   
	   /** *//**
	    * ���һ����ʱ����ʹ��Ĭ�ϵ�������������������������������
	    * @param jobName ������
	    * @param job     ����
	    * @param time    ʱ�����ã��ο�quartz˵���ĵ�
	    * @throws SchedulerException
	    * @throws ParseException 
	    * @throws ParseException
	    */
	   public static void addJob(String jobName,Job job,String time,ServletContext context) throws SchedulerException, ParseException {
	       Scheduler sched = sf.getScheduler();
	       JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, job.getClass());//�������������飬����ִ����
	       jobDetail.getJobDataMap().put("ServletContext", context);
	       //������
	       CronTrigger trigger =  new CronTrigger(jobName, TRIGGER_GROUP_NAME);//��������,��������
	       trigger.setCronExpression(time);//������ʱ���趨
	       sched.scheduleJob(jobDetail,trigger);
	       //����
	       if(!sched.isShutdown()){
	          sched.start();
	       }
	   }
	   
	   /** *//**
	    * ���һ����ʱ����
	    * @param jobName ������
	    * @param jobGroupName ��������
	    * @param triggerName ��������
	    * @param triggerGroupName ����������
	    * @param job     ����
	    * @param time    ʱ�����ã��ο�quartz˵���ĵ�
	    * @throws SchedulerException
	    * @throws ParseException
	    */
	   public static void addJob(String jobName,String jobGroupName, String triggerName,String triggerGroupName, Job job,String time) throws SchedulerException, ParseException{
	       Scheduler sched = sf.getScheduler();
	       JobDetail jobDetail = new JobDetail(jobName, jobGroupName, job.getClass());//�������������飬����ִ����
	       //������
	       CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);//��������,��������
	       trigger.setCronExpression(time);//������ʱ���趨
	       sched.scheduleJob(jobDetail,trigger);
	       if(!sched.isShutdown()){
	          sched.start();
	       }
	   }
	   
	   /** *//**
	    * �޸�һ������Ĵ���ʱ��(ʹ��Ĭ�ϵ�������������������������������)
	    * @param jobName
	    * @param time
	    * @throws SchedulerException
	    * @throws ParseException
	    */
	   public static void modifyJobTime(String jobName,String time) throws SchedulerException, ParseException{
	       Scheduler sched = sf.getScheduler();
	       Trigger trigger = sched.getTrigger(jobName,TRIGGER_GROUP_NAME);
	       if(trigger != null){
	           CronTrigger ct = (CronTrigger)trigger;
	           ct.setCronExpression(time);
	           sched.resumeTrigger(jobName,TRIGGER_GROUP_NAME);
	       }
	   }

	   /** *//**
	    * �޸�һ������Ĵ���ʱ��
	    * @param triggerName
	    * @param triggerGroupName
	    * @param time
	    * @throws SchedulerException
	    * @throws ParseException
	    */
	   public static void modifyJobTime(String triggerName,String triggerGroupName,String time) throws SchedulerException, ParseException{
	       Scheduler sched = sf.getScheduler();
	       Trigger trigger = sched.getTrigger(triggerName,triggerGroupName);
	       if(trigger != null){
	           CronTrigger ct = (CronTrigger)trigger;
	           //�޸�ʱ��
	           ct.setCronExpression(time);
	           //����������
	           sched.resumeTrigger(triggerName,triggerGroupName);
	       }
	   }
	   
	   /** *//**
	    * �Ƴ�һ������(ʹ��Ĭ�ϵ�������������������������������)
	    * @param jobName
	    * @throws SchedulerException
	    */
	   public static void removeJob(String jobName) throws SchedulerException{
	       Scheduler sched = sf.getScheduler();
	       sched.pauseTrigger(jobName,TRIGGER_GROUP_NAME);//ֹͣ������
	       sched.unscheduleJob(jobName,TRIGGER_GROUP_NAME);//�Ƴ�������
	       sched.deleteJob(jobName,JOB_GROUP_NAME);//ɾ������
	   }
	   
	   /** *//**
	    * �Ƴ�һ������
	    * @param jobName
	    * @param jobGroupName
	    * @param triggerName
	    * @param triggerGroupName
	    * @throws SchedulerException
	    */
	   public static void removeJob(String jobName,String jobGroupName, String triggerName,String triggerGroupName) throws SchedulerException{
	       Scheduler sched = sf.getScheduler();
	       sched.pauseTrigger(triggerName,triggerGroupName);//ֹͣ������
	       sched.unscheduleJob(triggerName,triggerGroupName);//�Ƴ�������
	       sched.deleteJob(jobName,jobGroupName);//ɾ������
	   }

	public static Trigger getSimpleTrigger(Task task){
		SimpleTrigger trigger = new SimpleTrigger(task.getId(),JOB_GROUP_NAME);
		if(!(task.getStartTime().trim().equals(""))){
			trigger.setStartTime(parseDateString(task.getStartTime()));
		}else{
			trigger.setStartTime(new Date());
		}
		if(!(task.getEndTime().trim().equals(""))){
			trigger.setEndTime(parseDateString(task.getEndTime()));
		}
		if(task.getRepeatCount().equals("-1")){
			trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		}else{
			trigger.setRepeatCount(Integer.parseInt(task.getRepeatCount()));
		}
		trigger.setRepeatInterval(Long.parseLong(task.getRepeatInterval())*1000L);
		return trigger;
	}
	
	public static Trigger getCronTrigger(Task task){
		CronTrigger trigger =  new CronTrigger(task.getId(), TRIGGER_GROUP_NAME);//��������,��������
		try {
			trigger.setCronExpression(task.getContent());
		} catch (ParseException e) {
			e.printStackTrace();
		}//������ʱ���趨
		return trigger;
	}
	
	private static Date parseDateString(String str){
//		String[] s = str.split("-");
//		int year = Integer.parseInt(s[0]);
//		int month = Integer.parseInt(s[1])-1;
//		int date = Integer.parseInt(s[2]);
//		int hour = Integer.parseInt(s[3]);
//		int minute = Integer.parseInt(s[4]);
//		int second = Integer.parseInt(s[5]);
//		Calendar cal = new GregorianCalendar();
//		cal.set(year, month, date, hour, minute, second);
		
//		return cal.getTime();
		
		return Timer.convertToDate2(str);
	}
	 /**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar cal = new GregorianCalendar();
		cal.set(2009, 0, 3, 16, 20, 50);
		Date startTime = cal.getTime();
		System.out.print(startTime);
		
	}

}
