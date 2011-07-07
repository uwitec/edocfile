package com.edoc.entity.files;

import java.util.Date;

/**
 * 评注信息
 * @author 陈超
 *
 */
public class Comment {
	private String id = "";				//ID
	private String content = "";		//评论内容
	private String userId = "";			//评论人ID
	private String userName = "";		//评论人姓名
	private String sFileId = "";		//评论文件ID
	private String sFileVersion = "";	//评论文件版本信息
	private Date crateTime = null;		//评论事件
	
	public Comment(){
		
		
	}
}
