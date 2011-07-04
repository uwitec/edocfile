package com.edoc.lucene.index.search;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.lucene.index.EdocDocument;

/**
 * lucene��ѯ
 * @author �³� 2010-9-4,ʵ����{@link DefaultLSearchServiceImpl}
 *
 */
public interface LSearchService {
	/**
	 * �ؼ��ּ���
	 * @param keyWord	�ؼ���
	 * @return		
	 * @author 			�³� 2010-9-4
	 */
	public PageValueObject<EdocDocument> keyWordSearch(String keyWord,String userId,int currentPage,int pageSize);
}
