package com.edoc.service.files;

import java.util.LinkedList;
import java.util.List;

import com.edoc.dbsupport.PropertyFilter;
import com.edoc.dbsupport.QueryParam;
import com.edoc.dbsupport.PropertyFilter.MatchType;
import com.edoc.utils.StringUtils;

/**
 * 文件查询参数类,包含查询
 * @author dell
 *
 */
public class FileQueryParams extends QueryParam{
	private int isShored = -1;			//是否共享,0未共享,1已共享,-1不作为查询参数
	private String fileName = null;
	
	public List<PropertyFilter> getPropertyFilters() {
		List<PropertyFilter> filters = new LinkedList<PropertyFilter>();
		PropertyFilter filter = null;
		if(isShored!=-1){
			filter = new PropertyFilter("isShored",isShored,MatchType.EQ);
			filters.add(filter);
		}
		if(StringUtils.isValid(fileName)){
			filter = new PropertyFilter("fileName",fileName,MatchType.LIKE);
			filters.add(filter);
		}
		
		return filters;
	}



	public int getIsShored() {
		return isShored;
	}
	public void setIsShored(int isShored) {
		this.isShored = isShored;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
