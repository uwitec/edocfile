package com.edoc.lucene.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.io.InputStreamReader;

/**
 * txtË÷Òý
 * 
 * @author zsx
 * @version 
 * @todo 
 * @History
 */
public class TXTReader {

  public static String getContent(File uTXTFile) {
    StringBuffer sb = new StringBuffer();
    try {
      FileInputStream in = new FileInputStream(uTXTFile);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String line = null;
      while ((line = br.readLine()) != null) {
        sb.append(line);
        sb.append("\n");
      }
      in.close();
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex1) {
      ex1.printStackTrace();
    }
    return sb.toString();
  }

  public static void main(String[] args) {

  }

}