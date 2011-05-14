package com.edoc.service.tools;

import org.springframework.transaction.annotation.Transactional;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.tools.ReceiveMsg;
import com.edoc.entity.tools.SendMsg;
/**
 * 消息服务接口{@link MessageServiceImpl}
 * @author dell
 *
 */
public interface MessageService {
	
	public static final int STATE_Y = 1;	//已发送
	public static final int STATE_N	= 0;	//未发送
	
	/**
	 * 获取用户发送的消息信息
	 * @param id
	 * @param stateY
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public PageValueObject<SendMsg> getMySendMessages(String userId, int state,
			int currentPage, int pageSize);

	/**
	 * 保存短消息信息
	 * @param message
	 * @param receiverIds 
	 * @param receiverNames 
	 */
	public void saveMessage(SendMsg message, String[] receiverIds, String[] receiverNames);

	/**
	 * 获取收件箱信息
	 * @param id
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public PageValueObject<ReceiveMsg> getMyRecMessages(String userId,
			int currentPage, int pageSize);

	/**
	 * 获取接收到的邮件
	 * @param id
	 * @return
	 */
	public ReceiveMsg getReceiveMsg(String id);
	
	/**
	 * 获取发送的邮件
	 * @param id
	 * @return
	 */
	public SendMsg getSendMsg(String id);

	/**
	 * 删除发送的邮件
	 * @param sendMsgIds
	 */
	public void deleteSendMessages(String[] sendMsgIds);
	
	/**
	 * 删除接收的邮件
	 * @param sendMsgIds
	 */
	public void deleteRecMessages(String[] recMsgIds);
	

}
