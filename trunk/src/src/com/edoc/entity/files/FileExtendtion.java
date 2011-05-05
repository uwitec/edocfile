package com.edoc.entity.files;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edoc.entity.AbstractBaseEntity;
import com.edoc.utils.RandomGUID;

/**
 * 文件扩展信息
 * @author 陈超 2011-03-24
 *
 */
@Entity
@Table(name = "sys_fileextend")
public class FileExtendtion extends AbstractBaseEntity{
	@Id
	@Column(name = "ID")
	private String id = null;
	
	@Column(name = "C_FIELDNAME", nullable = true)
	private String fieldName = "";
	
	@Column(name = "C_FIELDVALUE", nullable = true)
	private String fieldValue = "";
	
	@Column(name = "C_SOURCEFILEID", nullable = true)
	private String sourceFileId = "";
	
	@Column(name = "D_CREATETIME", nullable = true)
	private Date createTime = null;						//创建时间
	
	@Column(name = "D_UPDATETIME", nullable = true)
	private Date updateTime = null;						//上一次修改时间
	
	public FileExtendtion(){
		super();
		id = new RandomGUID().toString();
		createTime = new Date();
		updateTime = new Date();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}
	
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getSourceFileId() {
		return sourceFileId;
	}

	public void setSourceFileId(String sourceFileId) {
		this.sourceFileId = sourceFileId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
