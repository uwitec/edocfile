package com.edoc.lucene;

import java.util.List;

import com.edoc.lucene.index.EdocDocument;
import com.edoc.lucene.index.search.SearchManager;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<EdocDocument> r = new SearchManager().keyWordSearch("1",1,10);
		for(EdocDocument e:r){
			System.out.println(e.getContents());
		}
	}

}
