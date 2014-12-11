package com.luopeng.fastdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FileManager implements FileManagerConfig {
	private static final long serialVersionUID = 1L;  
	  
    private static Logger logger  = LogManager.getLogger(FileManager.class.getName());  
    private static final String TAG = "FastDFS FileManager";
    
    private static TrackerClient  trackerClient;  
    private static TrackerServer  trackerServer;  
    private static StorageServer  storageServer;  
    private static StorageClient  storageClient; 
    
    static { // Initialize Fast DFS Client configurations  
        
        try {  
            String classPath = new File(FileManager.class.getResource("/").getFile()).getCanonicalPath();  
              
            String fdfsClientConfigFilePath = classPath + File.separator + CLIENT_CONFIG_FILE;  
              
            logger.info("Fast DFS configuration file path:" + fdfsClientConfigFilePath);  
            ClientGlobal.init(fdfsClientConfigFilePath);  
              
            trackerClient = new TrackerClient();  
            trackerServer = trackerClient.getConnection();  
              
            storageClient = new StorageClient(trackerServer, storageServer);  
              
        } catch (Exception e) {  
        	logger.error(TAG,  e);  
              
        }  
    }  
    
    public static String upload(FastDFSFile file) {  
    	logger.info(TAG, "File Name: " + file.getName() + "     File Length: " + file.getContent().length);  
          
        NameValuePair[] meta_list = new NameValuePair[3];  
        meta_list[0] = new NameValuePair("width", "120");  
        meta_list[1] = new NameValuePair("heigth", "120");  
        meta_list[2] = new NameValuePair("author", "Diandi");  
          
        long startTime = System.currentTimeMillis();  
        String[] uploadResults = null;  
        try {  
            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);  
        } catch (IOException e) {  
            logger.error("IO Exception when uploadind the file: " + file.getName(), e);  
        } catch (Exception e) {  
            logger.error("Non IO Exception when uploadind the file: " + file.getName(), e);  
        }  
        logger.info("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");  
          
        if (uploadResults == null) {  
        	logger.error("upload file fail, error code: " + storageClient.getErrorCode());  
        }  
          
        String groupName        = uploadResults[0];  
        String remoteFileName   = uploadResults[1];  
          
        String fileAbsolutePath = PROTOCOL + trackerServer.getInetSocketAddress().getHostName()   
                + ":"  
                + TRACKER_NGNIX_PORT  
                + SEPARATOR   
                + groupName   
                + SEPARATOR   
                + remoteFileName;  
          
          
        logger.info("upload file successfully!!!  " +"group_name: " + groupName + ", remoteFileName:"  
                + " " + remoteFileName);  
          
        return fileAbsolutePath;  
          
    }  
      
    public static FileInfo getFile(String groupName, String remoteFileName) {  
        try {  
            return storageClient.get_file_info(groupName, remoteFileName);  
        } catch (IOException e) {  
            logger.error("IO Exception: Get File from Fast DFS failed", e);  
        } catch (Exception e) {  
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);  
        }  
        return null;  
    }  
    
    public static File downFile(String groupName, String remoteFileName){
    	File file = null;
    	try {
			byte[] bytes = storageClient.download_file(groupName, remoteFileName);
			if(bytes != null && bytes.length>0){
				file = new File("E:/520.png");
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(bytes);
			}else
				System.out.println("Down file failed bytes is "+bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IO Exception: Down File from Fast DFS failed", e);  
		} catch (MyException e) {
			// TODO Auto-generated catch block
			logger.error("Non IO Exception: Down File from Fast DFS failed", e);  
		}
    	return file;
    }
 
    public static void deleteFile(String url) {  
    	String[] results = getGroupAndRemouteName(url);
    	if(results != null){
    		System.out.println("deleteFile groupName:"+ results[0]+" remoteFileName:"+results[1]);
    		int result = -100;
			try {
				result = storageClient.delete_file(results[0], results[1]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		if(result == 0){
    			System.out.println("delete file success");
    		}else{
    			if( result == 2){
    				System.out.println("already deleted");
    			}else
    				System.out.println("delete file failed");
    		}
    	}else{
    		System.err.print("get groupName and remoteFileName fail from url");
    	}
    }    
    
    private static String[] getGroupAndRemouteName(String url){
    	String[] results = null;
    	String[] subUrl = StringUtils.substringsBetween(url, "/", "/");
    	if(subUrl != null && subUrl.length >=1){
    		results = new String[2];
    		results[0] = subUrl[1];//groupName
    		results[1] = StringUtils.substringAfter(url, subUrl[1]+"/");//remoteFileName
    	}
    	return results;
    }
    
    public static void deleteFile(String groupName, String remoteFileName) throws Exception {  
        int reuslt = storageClient.delete_file(groupName, remoteFileName);
        if(reuslt == 0 ){
        	System.out.println("delete file success");
        }else{
        	System.out.println("delete file fail");
        }
    }  
      
    public static StorageServer[] getStoreStorages(String groupName) throws IOException {  
        return trackerClient.getStoreStorages(trackerServer, groupName);  
    }  
      
    public static ServerInfo[] getFetchStorages(String groupName, String remoteFileName) throws IOException {  
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);  
    }      
    
    
}
