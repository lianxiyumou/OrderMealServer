package com.luopeng.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.luopeng.dao.FoodDao;
import com.luopeng.dao.MenuDao;
import com.luopeng.dao.PlaceDao;
import com.luopeng.dao.UserDao;
import com.luopeng.model.Food;
import com.luopeng.model.Menu;
import com.luopeng.model.Place;
import com.luopeng.model.ResponseModel;
import com.luopeng.model.User;
import com.luopeng.parse.ParseFood;
import com.luopeng.util.DateTimeUtil;
import com.luopeng.util.GsonUtil;
import com.luopeng.util.MStringUtils;
import com.luopeng.util.ModelAndViewUtil;


@Controller
@RequestMapping(value="/menu")
public class MenuController {

	@Autowired
	private MenuDao menuDao;
	@Autowired
	private FoodDao foodDao;	
	
	private static Logger logger = LogManager.getLogger(MenuController.class.getName());	
	
	@RequestMapping(value="/getTodayMenu")
	public void getTodayMenu(HttpServletRequest request,	HttpServletResponse response){
		logger.info("getTodayMenu");
		Date now = Calendar.getInstance().getTime();
		List<Menu> menulist = menuDao.selectMenuByDate(now);
		Menu menu;
		if(menulist.isEmpty()){
			menu = new Menu();
			menu.setCreateDate(now);
			menu.setTitle("温馨一餐");
			menuDao.insert(menu);
		}else{
			menu = menulist.get(0);
		}
		ResponseModel rsp = new ResponseModel();
		String foods = GsonUtil.obj2string(foodDao.selectMenuFood(now));
		rsp.put("foods", foods);
		rsp.put("menu", GsonUtil.obj2string(menu));
		System.out.println("foods:"+foods);
		System.out.println("menu:"+menu);
		ModelAndViewUtil.write(rsp, response);
	}	
	
	@RequestMapping(value="/addFoodForMenu")
	public void addFoodForMenu(HttpServletRequest request,	HttpServletResponse response){
		String foodId = request.getParameter("foodId");
		String menuId = request.getParameter("menuId");
		System.out.println("foodId"+foodId+" menuId:"+menuId);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("foodId", foodId);
		params.put("menuId", menuId);
		params.put("createDate", Calendar.getInstance().getTime());
		List<Map<String,Object>> reuslts = menuDao.selectMenuFood(params);
		int error = 0;
		if(reuslts.isEmpty()){
			menuDao.insertFood2Menu(params);
		}else{
			error = -100;
		}
		ResponseModel rm = new ResponseModel();
		rm.put("error", error);
		ModelAndViewUtil.write(rm, response);
	}	
	
	@RequestMapping(value="/deleteFoodForMenu")
	public void deleteFoodForMenu(HttpServletRequest request,	HttpServletResponse response){
		String foodId = request.getParameter("foodId");
		String menuId = request.getParameter("menuId");
		System.out.println("foodId"+foodId+" menuId:"+menuId);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("foodId", foodId);
		params.put("menuId", menuId);
		params.put("createDate", Calendar.getInstance().getTime());
		menuDao.deleteFood2Menu(params);

		ResponseModel rm = new ResponseModel();
		rm.put("error", 0);
		ModelAndViewUtil.write(rm, response);
	}	
	
	@RequestMapping(value="/isAddedFood")
	public void isAddedFood(HttpServletRequest request,	HttpServletResponse response){
		String foodId = request.getParameter("foodId");
		String menuId = request.getParameter("menuId");
		System.out.println("foodId"+foodId+" menuId:"+menuId);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("foodId", foodId);
		params.put("menuId", menuId);
		params.put("createDate", Calendar.getInstance().getTime());
		List<Map<String,Object>> reuslts = menuDao.selectMenuFood(params);	
		int error = 0;
		if(!reuslts.isEmpty()){
			error = -100;
		}
		ResponseModel rsp = new ResponseModel();
		rsp.put("error", error);
		ModelAndViewUtil.write(rsp, response);
	}
	
	@RequestMapping(value="/getAllMenu")
	public void getAllMenu(HttpServletRequest request,	HttpServletResponse response){
		ResponseModel rsp = new ResponseModel();
		String menus = GsonUtil.obj2string(menuDao.selectAll());
		rsp.put("menus", menus);
		System.out.println("menus:"+menus);
		ModelAndViewUtil.write(rsp, response);
	}		
	
	@RequestMapping(value="/addMenu")
	public void addMenu(HttpServletRequest request,	HttpServletResponse response){
		String menuTitle = request.getParameter("menuTitle");
		Menu menu = new Menu();
		menu.setTitle(menuTitle);
		menu.setCreateDate(Calendar.getInstance().getTime());
		System.out.println("menuTitle: "+menuTitle);
		ResponseModel rsp = new ResponseModel();
		menuDao.insert(menu);
		if(menu.getId() <= 0){
			rsp.put("error", -1);
		}else{
			rsp.put("error", 0);
		}
		ModelAndViewUtil.write(rsp, response);
	}
	
}
