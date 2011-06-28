package com.edoc.entity;
/**
 * 文件类型枚举类
 * @author 陈超 2010-8-7
 *
 */
public enum FileTypeEnum {
	EXCEL("xls","excel文档"),
	WORD("doc","word文档"),
	WORDX("docx","word2007文档"),
	PDE("pdf","pdf文档"),
	TXT("txt","txt文档"),
	PPT("ppt","ppt文档"),
	JPG("jpg","jpg图片"),
	GIF("gif","gif图片"),
	PNG("png","png图片"),
	BMP("bmp","bmp图片"),
	JPEG("jpeg","jpeg图片"),
	TIFF("tiff","tiff图片"),
	MP3("mp3","mp3媒体文件"),
	AVI("avi","avi媒体文件"),
	MOV("mov","mov媒体文件"),
	WMV("wmv","wmv媒体文件"),
	ASF("asf","asf媒体文件"),
	RMVB("rmvb","rmvb媒体文件");
	private String suffix;
	private String type;
	
	FileTypeEnum(String suffix,String type) {
		this.suffix = suffix;
		this.type = type;
	}

	public static String getType(String code) {
		for (FileTypeEnum status : FileTypeEnum.values()) {
			if (status.getSuffix().toLowerCase().equalsIgnoreCase(code.toLowerCase())) {
				return status.getType();
			}
		}
		return "";
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
