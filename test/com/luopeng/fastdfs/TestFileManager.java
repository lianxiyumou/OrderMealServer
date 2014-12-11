package com.luopeng.fastdfs;

import java.io.File;  
import java.io.FileInputStream;  
  


import org.csource.fastdfs.FileInfo;  
import org.csource.fastdfs.ServerInfo;  
import org.csource.fastdfs.StorageServer;  
import org.junit.Test;  
import org.springframework.util.Assert;  
  
  
/** 
 * @author Josh Wang(Sheng) 
 * 
 * @email  josh_wang23@hotmail.com 
 */  
public class TestFileManager {  
  
    @Test  
    public void upload() throws Exception {  
        File content = new File("C:\\520.png");  
          
        FileInputStream fis = new FileInputStream(content);  
        byte[] file_buff = null;  
        if (fis != null) {  
            int len = fis.available();  
            file_buff = new byte[len];  
            fis.read(file_buff);  
        }  
          
        FastDFSFile file = new FastDFSFile("520", file_buff, "png");  
          
        String fileAbsolutePath = FileManager.upload(file);  
        System.out.println(fileAbsolutePath);  
        fis.close();  
    }  
   //@Test  
    public void getFile() throws Exception {  
        FileInfo file = FileManager.getFile("group1", "M00/00/00/wKiAglRhpvGASh1_AANqSvIgy8s288.png");  
        Assert.notNull(file);  
        String sourceIpAddr = file.getSourceIpAddr();  
        long size = file.getFileSize();  
        System.out.println("ip:" + sourceIpAddr + ",size:" + size);  
    }  
   
    //@Test
    public void downFile(){
    	File file = FileManager.downFile("group1", "M00/00/00/wKiAglRhpvGASh1_AANqSvIgy8s288.png");
    	Assert.notNull(file);
    	System.out.println(""+file.getTotalSpace());
    }
    
    
      
    //@Test  
    public void getStorageServer() throws Exception {  
        StorageServer[] ss = FileManager.getStoreStorages("group1");  
        Assert.notNull(ss);  
          
        for (int k = 0; k < ss.length; k++){  
            System.err.println(k + 1 + ". " + ss[k].getInetSocketAddress().getAddress().getHostAddress() + ":" + ss[k].getInetSocketAddress().getPort());  
        }  
    }  
      
    //@Test  
    public void getFetchStorages() throws Exception {  
        ServerInfo[] servers = FileManager.getFetchStorages("group1", "M00/00/00/wKgBm1N1-CiANRLmAABygPyzdlw073.jpg");  
        Assert.notNull(servers);  
          
        for (int k = 0; k < servers.length; k++) {  
            System.err.println(k + 1 + ". " + servers[k].getIpAddr() + ":" + servers[k].getPort());  
        }  
    }  
    
    //@Test
    public void testDeleteFileByUrl(){
    	try {
			FileManager.deleteFile("http://192.168.80.97:8888/group1/M00/00/00/wKhQYVRkUnaAY8fXAACc-gX5SVE524.jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //@Test
    public void testDeleteFile(){
    	try {
			FileManager.deleteFile("group1","M00/00/00/wKiAglRhpvGASh1_AANqSvIgy8s288.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
