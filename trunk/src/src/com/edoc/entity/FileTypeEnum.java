package com.edoc.entity;
/**
 * �ļ�����ö����
 * @author �³� 2010-8-7
 *
 */
public enum FileTypeEnum {
	EXCEL("xls","excel�ĵ�"),
	WORD("doc","word�ĵ�"),
	WORDX("docx","word2007�ĵ�"),
	PDE("pdf","pdf�ĵ�"),
	TXT("txt","txt�ĵ�"),
	PPT("ppt","ppt�ĵ�"),
	JPG("jpg","jpgͼƬ"),
	GIF("gif","gifͼƬ"),
	PNG("png","pngͼƬ"),
	BMP("bmp","bmpͼƬ"),
	JPEG("jpeg","jpegͼƬ"),
	TIFF("tiff","tiffͼƬ"),
	MP3("mp3","mp3ý���ļ�"),
	AVI("avi","aviý���ļ�"),
	MOV("mov","mový���ļ�"),
	WMV("wmv","wmvý���ļ�"),
	ASF("asf","asfý���ļ�"),
	RMVB("rmvb","rmvbý���ļ�");
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
