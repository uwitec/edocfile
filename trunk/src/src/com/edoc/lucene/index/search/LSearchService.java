package com.edoc.lucene.index.search;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.lucene.index.EdocDocument;

/**
 * lucene查询
 * @author 陈超 2010-9-4,实现类{@link DefaultLSearchServiceImpl}
 *
 */
public interface LSearchService {
	/**
	 * 关键字检索
	 * @param keyWord	关键字
	 * @return		
	 * @author 			陈超 2010-9-4
	 */
	public PageValueObject<EdocDocument> keyWordSearch(String keyWord,String userId,int currentPage,int pageSize);
}
