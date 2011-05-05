package com.edoc.action.files;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.baseinfo.User;
import com.edoc.entity.files.RecycleInfo;
import com.edoc.service.files.RecycleService;

/**
 * 回收站管理
 * 
 * @author 陈超	2011-4-11
 *
 */
@Component("recycleAction")
@Scope("prototype")
public class RecycleAction  extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private RecycleInfo recycleInfo = null;
	
	@Resource(name="recycleService")
	private RecycleService recycleService = null;
	
	/**
	 * 获取回收站列表信息
	 * @return
	 */
	public String getRecycleList(){
		String fileName = this.getParameter("fileName");
		User user = (User)this.getSession().getAttribute("DOCUSER");
		PageValueObject<RecycleInfo> pageVO = recycleService.getRecycleList(getCurrentPage(), getPageSize(),user.getId(), fileName);
		
		this.setAttribute("pageVO", pageVO);
		this.setAttribute("fileName", fileName);
		return "showRecycleList";
	}
	
	/**
	 * 还原文件操作
	 * @return
	 */
	public String revert(){
		String[] recycleIds = this.getParameterValues("recycleIds");
		if(recycleIds!=null){
			recycleService.revert(recycleIds);
		}
		
		return getRecycleList();
	} 
	
	/**
	 * 清空回收站
	 * @return
	 */
	public String clearAll(){
		
		recycleService.clearAll();
		return getRecycleList();
	}
	
	public String delete(){
		String[] fileIds = this.getParameterValues("recycleIds");
		if(fileIds!=null){
			recycleService.delete(fileIds);
		}
		return getRecycleList();
	}

	public RecycleInfo getRecycleInfo() {
		return recycleInfo;
	}

	public void setRecycleInfo(RecycleInfo recycleInfo) {
		this.recycleInfo = recycleInfo;
	}
	

	
}
