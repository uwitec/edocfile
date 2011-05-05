package com.edoc.dbsupport;

import java.util.List;

public class PageValueObject<T> {
	private int totalRows = 0; // 记录总数
	private int totalPages = 0; // 总页数
	private int pageSize = 20; // 每页显示数据条数，默认为20条记录
	private int currentPage = 1; // 当前页数
	private boolean hasPrevious = false; // 判断是否有上一页
	private boolean hasNext = false; // 判断是否有下一页
	private String[] ascFields = null;
	private String[] descFields = null;
	private int nextPage = 0;
	private int prePage = 0;

	private List<T> result = null;

	public PageValueObject(int currentPage, int pageSize) {
		if (currentPage > 0) {
			this.currentPage = currentPage;
		}
		if (pageSize > 0 || pageSize == -1) {// 当pageSize大于0或等于-1才设置该参数,-1不执行分页操作
			this.pageSize = pageSize;
		}
	}

	/*
	 * public Page(int totalRows,int pageSize){ this.totalRows=totalRows;
	 * this.pageSize=pageSize; totalPages = ((totalRows + pageSize) - 1) /
	 * pageSize; refresh(); }
	 */

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		if(pageSize==-1){
			this.totalPages=1;
			this.totalRows=totalRows;
		}else{
			this.totalRows = totalRows;
			this.totalPages = ((totalRows + pageSize) - 1) / pageSize;
		}
		
		refresh();
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	public int getFirstResult() {
		return (currentPage-1) * pageSize;
	}

	// 刷新当前页面信息
	private void refresh() {
		if (totalPages <= 1) {
			hasPrevious = false;
			hasNext = false;
		} else if (currentPage == 0) {
			hasPrevious = false;
			hasNext = true;
		} else if (currentPage == totalPages - 1) {
			hasPrevious = true;
			hasNext = false;
		} else {
			hasPrevious = true;
			hasNext = true;
		}
	}

	public String[] getAscFields() {
		return ascFields;
	}

	public void setAscFields(String[] ascFields) {
		this.ascFields = ascFields;
	}

	public String[] getDescFields() {
		return descFields;
	}

	public void setDescFields(String[] descFields) {
		this.descFields = descFields;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}
	public int getNextPage(){
		if((this.currentPage+1)>this.totalPages){
			nextPage = totalPages;
		}else{
			nextPage = currentPage+1;
		}
		return nextPage;
	}
	
	public int getPrePage(){
		if((this.currentPage-1)<1){
			prePage = 1;
		}else{
			prePage = currentPage-1;
		}
		return prePage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
}
