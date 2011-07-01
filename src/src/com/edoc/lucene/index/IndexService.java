package com.edoc.lucene.index;

import java.io.File;

import com.edoc.entity.files.EdocFile;
import com.edoc.entity.files.FileVersion;

/**
 * 索引服务类
 * @author 陈超 2010-8-28
 *
 */
public interface IndexService {
	/**
	 * 对单个文件创建索引
	 * @param file		索引文件
	 * @param edocFile	文件信息
	 * @return
	 */
	public boolean addIndex(String sourceFileName, File file, EdocFile edocFile);
	
	/**
	 * 对单个文件创建索引
	 * @param file		索引文件
	 * @param edocFile	文件信息
	 * @return
	 */
	public boolean addIndex(String sourceFileName, File file, FileVersion fileVersion);
	
	/**
	 * 更新索引信息
	 * @param file			索引文件
	 * @param fileVersion   文件信息
	 * @return
	 */
	public boolean updateIndex(File file, FileVersion fileVersion);
}
