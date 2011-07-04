package com.edoc.lucene.index.search;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.lucene.index.EdocDocument;
import com.edoc.service.files.FileService;
import com.edoc.service.files.RecycleService;
import com.edoc.service.files.ShoreFileService;
import com.edoc.utils.StringUtils;
@Component("defaultLSearchService")
public class DefaultLSearchServiceImpl implements LSearchService {
	@Resource(name="recycleService")
	private RecycleService recycleService = null;
	
	@Resource(name="fileService")
	private FileService fileService = null;
	
	@Resource(name="shoreFileService")
	private ShoreFileService shoreFileService = null;
	

	public PageValueObject<EdocDocument> keyWordSearch(String keyWord,String userId,int currentPage,int pageSize) {
		PageValueObject<EdocDocument> rs = null;
		if(StringUtils.isValid(keyWord)){
			rs = new PageValueObject<EdocDocument>(currentPage,pageSize);
			SearchManager searchManager = new SearchManager();
			
//			//���һ���վ�е��ĵ�,���ĵ����ڲ�ѯ�������ʾ
//			String[] recycleFileIds = recycleService.getRecycleFileIds();
			
			//��ȡ��ǰ�û��ܹ��鿴�����ļ�ID
			String[] fileIds = getCanViewFileIds(userId);
			
			
			//��ҳ��ѯ
			if(rs.getFirstResult()==0){
				rs.setResult(searchManager.keyWordSearch(keyWord,null,fileIds,rs.getFirstResult(), rs.getPageSize()));
			}else{
				rs.setResult(searchManager.keyWordSearch(keyWord,null,fileIds,rs.getFirstResult(), rs.getPageSize()));
			}
			
			rs.setTotalRows(searchManager.getRecordCount());
		}
		return rs;
	}
	
	/**
	 * ��ȡ�û��ܲ鿴�������ļ�Id
	 * @param userId
	 * @return
	 */
	private String[] getCanViewFileIds(String userId){
		List<String> fileIds = new LinkedList<String>();
		String[] myFileIds = fileService.getMyFileIds(userId,FileService.FILETYPE_FILE);
		if(myFileIds!=null && myFileIds.length>0){
			for(String s:myFileIds){
				if(!fileIds.contains(s)){
					fileIds.add(s);
				}
			}
		}
		
		String[] shoredViewFileIds = shoreFileService.getShoredViewSourceFileId(userId);
		if(shoredViewFileIds!=null && shoredViewFileIds.length>0){
			for(String s:shoredViewFileIds){
				if(!fileIds.contains(s)){
					fileIds.add(s);
				}
			}
		}
		if(!fileIds.isEmpty()){
			String[] fileIdStr = new String[fileIds.size()];
			int index = 0;
			for(String s:fileIds){
				fileIdStr[index] = s;
				index++;
			}
			return fileIdStr;
		}
		return null;
	}

}
