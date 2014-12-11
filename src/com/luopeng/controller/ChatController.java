package com.luopeng.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luopeng.comm.PageHelper;
import com.luopeng.comm.RC4;
import com.luopeng.dao.PlaceDao;
import com.luopeng.dao.UserDao;
import com.luopeng.fastdfs.FastDFSFile;
import com.luopeng.fastdfs.FileManager;
import com.luopeng.model.Food;
import com.luopeng.model.Place;
import com.luopeng.model.ResponseModel;
import com.luopeng.model.User;
import com.luopeng.util.DateTimeUtil;
import com.luopeng.util.FileUtil;
import com.luopeng.util.GsonUtil;
import com.luopeng.util.MStringUtils;
import com.luopeng.util.ModelAndViewUtil;


@Controller
@RequestMapping(value="/chat")
public class ChatController {

	private static Logger logger = LogManager.getLogger(ChatController.class.getName());	
	
	@Autowired
	private UserDao userDao; 
	
	@Autowired
	private PlaceDao placeDao;
	
	@RequestMapping(value="/uploadFile")
	public void uploadFile(HttpServletRequest request,	HttpServletResponse response){
		List<FileItem> fileList = new ArrayList<FileItem>();
		int error = -1;
		String fastDFSUrl = "";
	    try {
	    	System.out.println(DateTimeUtil.getTime()+": uploadFile paraterMap start");
			ModelAndViewUtil.getMultiParamterMap(request, fileList);
			System.out.println(DateTimeUtil.getTime()+": end paraterMap start");
			if(!fileList.isEmpty()){
				if(fileList.size() >= 2){
					FileItem fileItem = fileList.get(1);
					System.out.println("filelist:"+fileList.get(1).getName()+" filename:"+fileList.get(1).getFieldName());
					InputStream is = null;
					FileOutputStream fos = null;
					try {
						is =  fileItem.getInputStream();
						if(is != null){
							System.out.println(DateTimeUtil.getTime()+": BufferedInputStream read start");
							BufferedInputStream bis = new BufferedInputStream(is);
							if(bis != null){
								byte[] file_buff = new byte[bis.available()];
								bis.read(file_buff);
								System.out.println(DateTimeUtil.getTime()+": BufferedInputStream read end length:"+bis.available());
								String[] fileInfo = FileUtil.getFileNameAndType(fileItem.getName());
								if(fileInfo != null && fileInfo.length ==2){
									System.out.println(DateTimeUtil.getTime()+" upload fileName:"+fileInfo[0]+", fileType:"+fileInfo[1]);
									FastDFSFile fastFile = new FastDFSFile(fileInfo[0], file_buff, fileInfo[1]);  
									fastDFSUrl = FileManager.upload(fastFile); 	
									System.out.println(DateTimeUtil.getTime()+" fastDfsFile upload sucess fastDFSUrl:"+fastDFSUrl);
									error = 0;
								}
							}
							
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						try {
							if(is != null)
								is.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							try {
								if(fos != null)
								fos.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ResponseModel rsp = new ResponseModel();
	    rsp.put("error", error);
	    rsp.put("url", fastDFSUrl);
	    ModelAndViewUtil.write(rsp, response);
    }	
	
	@RequestMapping(value="/getImg")
	public void getImg(HttpServletRequest request,	HttpServletResponse response){
		String url = request.getParameter("url");
		File file = new File("C:\\520.png");
		FileInputStream fis = null;
		ServletOutputStream sos = null;
		try {
			fis = new FileInputStream(file);
			byte[] bytes = new byte[fis.available()];
			sos = response.getOutputStream();
			sos.write(bytes);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(sos != null){
						try {
							sos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		
	}
	
	@RequestMapping(value="/deleteFile")
	public void deleteFile(HttpServletRequest request,	HttpServletResponse response){
		Map<String,String> paramMap = ModelAndViewUtil.getMultiParamterMap(request);
		System.out.println(paramMap);
		if(paramMap != null && paramMap.containsKey("url")){
			FileManager.deleteFile(paramMap.get("url"));
		}
	   ResponseModel rsp = new ResponseModel();
	   rsp.put("foodlist", foodlist());
	   ModelAndViewUtil.write(rsp, response);
    }	
	
	@RequestMapping(value="/getAllFood")
	public void getAllFood(HttpServletRequest request,	HttpServletResponse response){
	   ResponseModel rsp = new ResponseModel();
	   rsp.put("foodlist", foodlist());
	   ModelAndViewUtil.write(rsp, response);
    }	
	
	private List<Food> foodlist(){
		List<Food> list = new ArrayList<Food>();
		for(int i=0; i<25; i++){
			Food food = new Food();
			food.setId(i);
			food.setName("food"+i);
			list.add(food);
		}
		return list;
	}
	
	public List<User> getUsers(){
		System.out.println("woshi");
		logger.debug("我是debug msg");
		return userDao.selectAll();
	}

	@RequestMapping(value="/getUsers")
	public void getUsers(HttpServletRequest request,	HttpServletResponse response){
//		request.getParameterMap();request.getParameterValues("name")
	   System.out.println("getUsers parse start "+ System.currentTimeMillis());
		Map<String,String> paramMap = ModelAndViewUtil.getMultiParamterMap2(request);
		System.out.println("getUsers parse end "+ System.currentTimeMillis());
		if(paramMap != null && paramMap.size() >0){
			Set<String> keySets = paramMap.keySet();
			for(String key : keySets){
				System.out.println(key+":"+paramMap.get(key));
			}
		}		
	   System.out.println("--------------------");
	   if(userDao == null){
			return;
	   }
	   regisert();
	   List<User> users = selectUsers();
	   ResponseModel rsp = new ResponseModel();
	   rsp.put("userlist", users);
	   ModelAndViewUtil.write(rsp, response);
   }
	
   private void regisert(){	
	   User user = new User();
	   user.setAge(24);
	   user.setEmail("990740109@qq.com");
	   user.setPassword("123456");
	   user.setSex("m");
	   user.setUsername("luopeng");
	   userDao.insert(user);
   }

   @RequestMapping(value="/login")
   public void login(HttpServletRequest request,HttpServletResponse response){
	   System.out.println("login parse start "+ System.currentTimeMillis());
	   Map<String,String> paramMap = ModelAndViewUtil.getMultiParamterMap(request);
	   System.out.println("login parse end "+ System.currentTimeMillis());
	   System.out.println("--------------------");
	   if(paramMap != null && paramMap.size() >0){
		   Set<String> keySets = paramMap.keySet();
		   for(String key : keySets){
			   System.out.println(key+":"+paramMap.get(key));
		   }
	   }
	   System.out.println("--------------------");
	   List<User> users = selectUsers();
	   ResponseModel rsp = new ResponseModel();
	   rsp.put("userlist", users);
	   ModelAndViewUtil.write(rsp, response);		
   }
   
	public List<User> selectUsers(){
		List<User> userList = new ArrayList<User>();
			for(int i=0; i<10; i++){
				User user = new User();
				user.setId(i);
				user.setUsername("luopeng");
				userList.add(user);
			}
		return userList;
	}   
	
	public void findAllPlace(){
		System.out.println("start findAllPlace"+DateTimeUtil.getTime());
		PageHelper.startPage(1, 10,false);
		List<Place> list = placeDao.selectAll();
		System.out.println("end findAllPlace"+DateTimeUtil.getTime());
		System.out.println("list size:"+list.size());
	}
	
	
	
}
