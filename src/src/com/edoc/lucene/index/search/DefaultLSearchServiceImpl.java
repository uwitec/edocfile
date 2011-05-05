package com.edoc.lucene.index.search;

import org.springframework.stereotype.Component;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.lucene.index.EdocDocument;
import com.edoc.utils.StringUtils;
@Component("defaultLSearchService")
public class DefaultLSearchServiceImpl implements LSearchService {

	public PageValueObject<EdocDocument> keyWordSearch(String keyWord,int currentPage,int pageSize) {
		PageValueObject<EdocDocument> rs = null;
		if(StringUtils.isValid(keyWord)){
			rs = new PageValueObject<EdocDocument>(currentPage,pageSize); 
			//获取索引目录
			if(rs.getFirstResult()==0){
				rs.setResult(SearchManager.getSingleInstance().keyWordSearch(keyWord,1, rs.getPageSize()));
			}else{
				rs.setResult(SearchManager.getSingleInstance().keyWordSearch(keyWord,rs.getFirstResult(), rs.getPageSize()));
			}
		}
		return rs;
	}

}
