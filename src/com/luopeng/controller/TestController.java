package com.luopeng.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luopeng.comm.PageHelper;
import com.luopeng.comm.RC4;
import com.luopeng.dao.PlaceDao;
import com.luopeng.dao.UserDao;
import com.luopeng.model.Place;
import com.luopeng.model.ResponseModel;
import com.luopeng.model.User;
import com.luopeng.util.DateTimeUtil;
import com.luopeng.util.GsonUtil;
import com.luopeng.util.MStringUtils;
import com.luopeng.util.ModelAndViewUtil;


@Controller
@RequestMapping(value="/test")
public class TestController {

	private static Logger logger = LogManager.getLogger(TestController.class.getName());	
	
	@Autowired
	private UserDao userDao; 
	
	@Autowired
	private PlaceDao placeDao;
	
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
	
	@RequestMapping(value="/getRequest")
	public void getRequest(HttpServletRequest request,	HttpServletResponse response){
//		request.getParameterMap();request.getParameterValues("name")
	   System.out.println("getUsers parse start "+ System.currentTimeMillis());
	   request.getParameter("username");
	   List<User> users = selectUsers();
	   ResponseModel rsp = new ResponseModel();
	   rsp.put("userlist", users);
	   ModelAndViewUtil.write(rsp, response);	   
	}
	
	@RequestMapping(value="/getMyRequest")
	public void getMyRequest(HttpServletRequest request,	HttpServletResponse response){
//		request.getParameterMap();request.getParameterValues("name")
	   System.out.println("getUsers parse start "+ System.currentTimeMillis());
	   request.getParameter("params");
	   ModelAndViewUtil.getMultiParamterMap2(request);
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
