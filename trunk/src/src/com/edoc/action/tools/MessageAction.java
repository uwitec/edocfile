package com.edoc.action.tools;


import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.baseinfo.User;
import com.edoc.entity.tools.ReceiveMsg;
import com.edoc.entity.tools.SendMsg;
import com.edoc.service.tools.MessageService;
import com.edoc.utils.StringUtils;
/**
 * 消息发送控制类
 * @author 陈超 2011-5-13
 *
 */
@Component("messageAction")
@Scope("prototype")
public class MessageAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private SendMsg message = null;
	private int msgType = 0;		//邮件类型1已发送邮件,2待发送邮件
	@Resource(name="messageService")
	private MessageService messageService = null;
	
	/**
	 * 获取发送信息
	 * @return
	 */
	public String getSendMessages(){
		User user = (User)this.getSession().getAttribute("DOCUSER");
		PageValueObject<SendMsg> messages = null;
		if(msgType==1){
			messages = messageService.getMySendMessages(user.getId(),MessageService.STATE_Y,this.getCurrentPage(),this.getPageSize());
		}else if(msgType==2){
			messages = messageService.getMySendMessages(user.getId(),MessageService.STATE_N,this.getCurrentPage(),this.getPageSize());
		}
		this.setAttribute("msgType", msgType);
		this.setAttribute("filePageVO", messages);
		return "showSendMsgListPage";
	}
	/**
	 * 获取收件箱信息
	 * @return
	 */
	public String getReceiveMessages(){
		User user = (User)this.getSession().getAttribute("DOCUSER");
		int readSate = -1;
		if(StringUtils.isValid(this.getParameter("readSate"))){
			readSate = Integer.parseInt(this.getParameter("readSate"));
		}
		PageValueObject<ReceiveMsg> messages = messageService.getMyRecMessages(user.getId(),readSate, this.getCurrentPage(),this.getPageSize());
		this.setAttribute("filePageVO", messages);
		
		if(StringUtils.isValid(this.getForward())){
			return this.getForward();
		}
		return "showRecMsgListPage";
	}
	
	/**
	 * 查看接收到的短消息
	 * @return
	 */
	public String showRecMsg(){
		messageService.setRecMessageReaded(this.getParameter("receiveMsgId"));
		ReceiveMsg msg = messageService.getReceiveMsg(this.getParameter("receiveMsgId"));
		this.setAttribute("receiveMsg", msg);
		return "showRecMsg";
	}
	
	/**
	 * 查看发送的短消息
	 * @return
	 */
	public String showSendMsg(){
		SendMsg msg = messageService.getSendMsg(this.getParameter("sendMsgId"));
		this.setAttribute("sendMsg", msg);
		return "showSendMsg";
	}
	
	/**
	 * 删除发送的邮件
	 * @return
	 */
	public String deleteSendMessages(){
		String[] sendMsgIds = this.getParameterValues("sendMsgIds");
		messageService.deleteSendMessages(sendMsgIds);
		return this.getSendMessages();
	}
	
	/**
	 * 删除接收的邮件
	 * @return
	 */
	public String deleteRecMessages(){
		String[] recMsgIds = this.getParameterValues("recMsgIds");
		messageService.deleteRecMessages(recMsgIds);
		return this.getReceiveMessages();
	}
	
	public String saveMessage(){
		String receiverIdStr = this.getParameter("receiverIds");
		String[] receiverIds = null;
		if(StringUtils.isValid(receiverIdStr)){
			receiverIds = receiverIdStr.split(",");
		}
		
		String receiverNameStr = this.getParameter("receiverNames");
		String[] receiverNames = null;
		if(StringUtils.isValid(receiverNameStr)){
			receiverNames = receiverNameStr.split(",");
		}
		
		message.setReceiverIds(receiverIdStr);
		message.setReceiverNames(receiverNameStr);
		messageService.saveMessage(message,receiverIds,receiverNames);
		if(message.getState()==0){
			this.showMessage(this.getResponse(), "短消息保存成功!", true);
		}else if(message.getState()==1){
			this.showMessage(this.getResponse(), "短消息发送成功!", true);
		}
		int sendFlag = 0;
		String sendFlagStr = this.getParameter("sendFlag");
		if(StringUtils.isValid(sendFlagStr)){
			sendFlag = Integer.parseInt(sendFlagStr);
		}
		if(sendFlag==0){
			this.getReceiveMessages();
		}else if(sendFlag==1){
			this.getSendMessages();
		}
		return null;
	}
	
	
	
	public SendMsg getMessage() {
		return message;
	}
	public void setMessage(SendMsg message) {
		this.message = message;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
}
