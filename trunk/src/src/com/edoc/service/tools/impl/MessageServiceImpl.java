package com.edoc.service.tools.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.dbsupport.PropertyFilter;
import com.edoc.entity.files.EdocFile;
import com.edoc.entity.tools.ReceiveMsg;
import com.edoc.entity.tools.SendMsg;
import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.service.tools.MessageService;
import com.edoc.utils.StringUtils;
@Component("messageService")
@Transactional(readOnly=true)
public class MessageServiceImpl implements MessageService{
	
	@Resource(name="sendMsgDao")
	private GenericDAO<SendMsg,String> sendMsgDao=null;
	
	@Resource(name="receiveMsgDao")
	private GenericDAO<ReceiveMsg,String> receiveMsgDao=null;
	

	/**
	 * ɾ�����͵��ʼ�
	 * @param sendMsgIds
	 */
	@Transactional(readOnly=false)
	public void deleteSendMessages(String[] sendMsgIds){
		if(sendMsgIds!=null && sendMsgIds.length>0){
			//�޸��ļ�����״̬
			String deleteSQL = "delete from sys_msg_send where id in('000'";
			for(String id:sendMsgIds){
				deleteSQL += ",'"+id+"'";
			}
			deleteSQL += ")";
			sendMsgDao.delete(deleteSQL);
		}
	}
	
	/**
	 * ɾ�����յ��ʼ�
	 * @param sendMsgIds
	 */
	@Transactional(readOnly=false)
	public void deleteRecMessages(String[] recMsgIds){
		if(recMsgIds!=null && recMsgIds.length>0){
			//�޸��ļ�����״̬
			String deleteSQL = "delete from sys_msg_receive where id in('000'";
			for(String id:recMsgIds){
				deleteSQL += ",'"+id+"'";
			}
			deleteSQL += ")";
			receiveMsgDao.delete(deleteSQL);
		}
	}
	/**
	 * ��ȡ���͵��ʼ�
	 * @param id
	 * @return
	 */
	public SendMsg getSendMsg(String id){
		return sendMsgDao.get(id);
	}
	/**
	 * ��ȡ���յ����ʼ�
	 * @param id
	 * @return
	 */
	public ReceiveMsg getReceiveMsg(String id){
		return receiveMsgDao.get(id);
	}
	/**
	 * �������Ϣ��Ϣ
	 * @param message
	 */
	@Transactional(readOnly=false)
	public void saveMessage(SendMsg sendMsg,String[] receiverIds, String[] receiverNames){
		sendMsgDao.saveOrUpdate(sendMsg);
		
//		//ɾ�������˵���Ϣ
//		String deleteSQL = "delete sys_msg_receive where C_MSGID='"+sendMsg.getId()+"'";
//		sendMsgDao.update(deleteSQL);
		
		if(receiverIds!=null && receiverNames!=null && receiverIds.length==receiverNames.length){
			//��ʾ�����ʼ�,�����ʼ���ʱ��Ҫͬʱ���ռ����в��������Ϣ
			//�����ʱ�������ʼ�,������ʱ���в����ռ��˵���Ϣ
			if(sendMsg.getState()==1){
				//���´��������˵���Ϣ
				String msgId = sendMsg.getId();
				List<ReceiveMsg> rMsg = new ArrayList<ReceiveMsg>(receiverIds.length);
				for(int i=0;i<receiverIds.length;i++){
					ReceiveMsg r = new ReceiveMsg();
					r.setContent(sendMsg.getContent());
					r.setTitle(sendMsg.getTitle());
					r.setCreateDate(sendMsg.getCreateDate());
					r.setToUserId(receiverIds[i]);
					r.setToUsernName(receiverNames[i]);
					r.setFromUserId(sendMsg.getFromUserId());
					r.setFromUserName(sendMsg.getFromUserName());
					r.setSendMsgId(msgId);
					rMsg.add(r);
				}
				
				receiveMsgDao.save(rMsg);
			}
		}
		
	}
	/**
	 * ��ȡ�û����͵���Ϣ��Ϣ
	 * @param id
	 * @param stateY
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public PageValueObject<SendMsg> getMySendMessages(String userId, int state,int currentPage, int pageSize){
		PageValueObject<SendMsg> page = null;
		if(StringUtils.isValid(userId)){
			page = new PageValueObject<SendMsg>(currentPage,pageSize);
			List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
			PropertyFilter filter01 = new PropertyFilter("fromUserId",userId,PropertyFilter.MatchType.EQ);
			filters.add(filter01);
			
			PropertyFilter filter02 = new PropertyFilter("state",state,PropertyFilter.MatchType.EQ);
			filters.add(filter02);
			
			page.setResult(sendMsgDao.find(filters, page.getFirstResult(), page.getPageSize()));
			page.setTotalRows(sendMsgDao.getCount(filters));
		}
		return page;
	}
	
	/**
	 * ��ȡ�ռ�����Ϣ
	 * @param id
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public PageValueObject<ReceiveMsg> getMyRecMessages(String userId,
			int currentPage, int pageSize){
		PageValueObject<ReceiveMsg> page = null;
		if(StringUtils.isValid(userId)){
			page = new PageValueObject<ReceiveMsg>(currentPage,pageSize);
			List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
			PropertyFilter filter01 = new PropertyFilter("toUserId",userId,PropertyFilter.MatchType.EQ);
			filters.add(filter01);
			
			page.setResult(receiveMsgDao.find(filters, page.getFirstResult(), page.getPageSize()));
			page.setTotalRows(receiveMsgDao.getCount(filters));
		}
		return page;
	}
}
