package com.edoc.service.tools;

import org.springframework.transaction.annotation.Transactional;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.tools.ReceiveMsg;
import com.edoc.entity.tools.SendMsg;
/**
 * ��Ϣ����ӿ�{@link MessageServiceImpl}
 * @author dell
 *
 */
public interface MessageService {
	
	public static final int STATE_Y = 1;	//�ѷ���
	public static final int STATE_N	= 0;	//δ����
	
	/**
	 * ��ȡ�û����͵���Ϣ��Ϣ
	 * @param id
	 * @param stateY
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public PageValueObject<SendMsg> getMySendMessages(String userId, int state,
			int currentPage, int pageSize);

	/**
	 * �������Ϣ��Ϣ
	 * @param message
	 * @param receiverIds 
	 * @param receiverNames 
	 */
	public void saveMessage(SendMsg message, String[] receiverIds, String[] receiverNames);

	/**
	 * ��ȡ�ռ�����Ϣ
	 * @param id
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public PageValueObject<ReceiveMsg> getMyRecMessages(String userId,
			int currentPage, int pageSize);

	/**
	 * ��ȡ���յ����ʼ�
	 * @param id
	 * @return
	 */
	public ReceiveMsg getReceiveMsg(String id);
	
	/**
	 * ��ȡ���͵��ʼ�
	 * @param id
	 * @return
	 */
	public SendMsg getSendMsg(String id);

	/**
	 * ɾ�����͵��ʼ�
	 * @param sendMsgIds
	 */
	public void deleteSendMessages(String[] sendMsgIds);
	
	/**
	 * ɾ�����յ��ʼ�
	 * @param sendMsgIds
	 */
	public void deleteRecMessages(String[] recMsgIds);
	

}
