/**
* Copyright (C) 2008 Happy Fish / YuQing
*
* FastDFS Java Client may be copied only under the terms of the GNU Lesser
* General Public License (LGPL).
* Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
**/

package com.luopeng.fastdfs;

import java.io.*;
import java.net.*;
import java.util.*;

import org.csource.common.*;
import org.csource.fastdfs.*;
import org.junit.Test;

/**
* client test
* @author Happy Fish / YuQing
* @version Version 1.18
*/
public class TestDown
{
	
	
	/**
	* entry point
	* @param args comand arguments
	*     <ul><li>args[0]: config filename</li></ul>
	*     <ul><li>args[1]: local filename to upload</li></ul>
	*/
 @Test
  public void download()
  {
  	
  	System.out.println("java.version=" + System.getProperty("java.version"));
  	  
  	String conf_filename = "E:\\fdfs_client.conf";
  	String local_filename = "E:\\fdfs_client.conf";
  	
  	try
  	{
  		ClientGlobal.init(conf_filename);
  		System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
  		System.out.println("charset=" + ClientGlobal.g_charset);
 
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient1 client = new StorageClient1(trackerServer, storageServer);

        NameValuePair[] metaList = new NameValuePair[1];
        metaList[0] = new NameValuePair("fileName", local_filename);
        String fileId = client.upload_file1(local_filename, null, metaList);
        System.out.println("upload success. file id is: " + fileId);
        int i = 0;
        byte[] result = client.download_file1(fileId);
        if(result != null && result.length > 0){
        	
        	System.out.println(i + ", download result is: " + result.length);
        	File file = new File("E:\\download.conf");
        	file.createNewFile();
        	FileOutputStream fos = new FileOutputStream(file);
        	fos.write(result);
        }
        
  		trackerServer.close();
  	}
  	catch(Exception ex)
  	{
  		ex.printStackTrace();
  	}
  }
}
