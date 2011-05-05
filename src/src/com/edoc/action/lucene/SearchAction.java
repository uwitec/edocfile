package com.edoc.action.lucene;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.dbsupport.PageValueObject;
import com.edoc.lucene.index.EdocDocument;
import com.edoc.lucene.index.search.LSearchService;

@Component("searchAction")
@Scope("prototype")
public class SearchAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private String keyWord = null;
	@Resource(name="defaultLSearchService")
	private LSearchService search = null;
	
	/**
	 * È«ÎÄ¼ìË÷
	 * @return
	 * @author 		³Â³¬ 2010-9-2
	 */
	public String documentSearch(){
		PageValueObject<EdocDocument> docs = search.keyWordSearch(keyWord,this.getCurrentPage(),this.getPageSize());
		getRequest().setAttribute("docs", docs);
		this.setAttribute("keyWord", keyWord);
		return "showSearchResults";
	}
	
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
}
