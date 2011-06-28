package com.edoc.utils;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
/**
 * ϵͳ���ò���
 * @author �³� 2010-82
 */
public class ConfigResource {
	private static final String propFile = "edoc.properties";
	
	public static final String EDOCUPLOADDIR = "edocUploadDir";			//�ϴ��ļ���Ŀ¼
	public static final String EDOCINDEXFILE = "edocIndexFile";			//�����ļ�Ŀ¼
	public static final String TEMPEDOCINDEXFILE = "tempEdocIndexFile";			//�����ļ���ʱ���Ŀ¼
	public static final String MAXSIZEINDEXINRAMMB = "maxSizeIndexInRamMB";		//�ڴ��д������������С(��λMB)
	
    private static Map<String,String> configs = null;
    
    public static String getConfig(String key) {
        if(configs==null){
        	initConfig();
        }
        String value = configs.get(key);
        if(value == null){
            value = "";
        }
        return value;
    }
    
	@SuppressWarnings("unchecked")
	private static void initConfig() {
		InputStream is = null;
        Properties props = new Properties();
        
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = ConfigResource.class.getClassLoader();
        }
        is = classLoader.getResourceAsStream(propFile);
        if (is != null) {
            try {
                props.load(is);
                
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (props.size() < 1) {
            return ;
        }
        configs = new HashMap<String,String>();
        synchronized (configs) {
            Iterator names = props.keySet().iterator() ;
            while (names.hasNext()) {
                String key = (String) names.next() ;
                configs.put(key, props.getProperty(key)) ;
            }
        }
	}
}
