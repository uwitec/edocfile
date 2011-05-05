package com.edoc.scheduler;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edoc.entity.AbstractBaseEntity;
import com.edoc.utils.RandomGUID;

@Entity
@Table(name = "qrtz_task")
public class Task extends AbstractBaseEntity{
	@Id
	@Column(name = "ID")
	private String id="";
	
	@Column(name = "C_NAME", nullable = true)
	private String name="";
	
	@Column(name = "I_STATE", nullable = true)
	private int state=0;					//1=启动 0=停止
	
	@Column(name = "I_TYPE", nullable = true)
	private int type = 1;					//1=SimpleTrigger 2=CronTrigger定期 3=CronTrigger定周
	
	@Column(name = "C_CONTENT", nullable = true)
	private String content ="0 0 23 * * ?";	//说明
	
	@Column(name = "C_CLASSNAME", nullable = true)
	private String classname="";			//全路径类名
	
	@Column(name = "C_STARTTIME", nullable = true)
	private String startTime="";			//开始时间
	
	@Column(name = "C_ENDTIME", nullable = true)
	private String endTime="";				//结束时间
	
	@Column(name = "C_REPEATCOUNT", nullable = true)
	private String repeatCount="-1";		//重复次数
	
	@Column(name = "C_REPEATINTERVAL", nullable = true)
	private String repeatInterval="3600";	//间隔时间
	
	@Column(name = "I_ISDELETE", nullable = true)
	private int isDelete =0;							//是否删除,0未删除,1已删除
	
	public Task(){
		super();
		id = new RandomGUID().toString();
	}
	
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getRepeatCount() {
		return repeatCount;
	}
	public void setRepeatCount(String repeatCount) {
		this.repeatCount = repeatCount;
	}
	public String getRepeatInterval() {
		return repeatInterval;
	}
	public void setRepeatInterval(String repeatInterval) {
		this.repeatInterval = repeatInterval;
	}
	
	
	
}
