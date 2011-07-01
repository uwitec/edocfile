package com.edoc.lucene.index.search;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.lucene.index.EdocDocument;
import com.edoc.service.files.RecycleService;
import com.edoc.utils.StringUtils;
@Component("defaultLSearchService")
public class DefaultLSearchServiceImpl implements LSearchService {
	@Resource(name="recycleService")
	private RecycleService recycleService = null;
	

	public PageValueObject<EdocDocument> keyWordSearch(String keyWord,int currentPage,int pageSize) {
		PageValueObject<EdocDocument> rs = null;
		if(StringUtils.isValid(keyWord)){
			rs = new PageValueObject<EdocDocument>(currentPage,pageSize);
			SearchManager searchManager = new SearchManager();
			
			//查找回收站中的文档
			String[] recycleFileIds = recycleService.getRecycleFileIds();
			
			//分页查询
			if(rs.getFirstResult()==0){
				rs.setResult(searchManager.keyWordSearch(keyWord,recycleFileIds,rs.getFirstResult(), rs.getPageSize()));
			}else{
				rs.setResult(searchManager.keyWordSearch(keyWord,recycleFileIds,rs.getFirstResult(), rs.getPageSize()));
			}
			
			rs.setTotalRows(searchManager.getRecordCount());
		}
		return rs;
	}

}
