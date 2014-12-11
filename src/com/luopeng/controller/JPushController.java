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
import com.luopeng.model.Food;
import com.luopeng.model.Place;
import com.luopeng.model.ResponseModel;
import com.luopeng.model.User;
import com.luopeng.util.DateTimeUtil;
import com.luopeng.util.GsonUtil;
import com.luopeng.util.MStringUtils;
import com.luopeng.util.ModelAndViewUtil;


@Controller
@RequestMapping(value="/food")
public class JPushController {

	private static Logger logger = LogManager.getLogger(JPushController.class.getName());	
	
	@Autowired
	private UserDao userDao; 
	
	@Autowired
	private PlaceDao placeDao;
	
	@RequestMapping(value="/getAllFood")
	public void getAllFood(HttpServletRequest request,	HttpServletResponse response){
	   ResponseModel rsp = new ResponseModel();
//	   ModelAndViewUtil.write(rsp, response);
    }	
	
	
}
