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
import org.springframework.web.servlet.ModelAndView;

import com.luopeng.comm.PageHelper;
import com.luopeng.comm.RC4;
import com.luopeng.dao.FoodDao;
import com.luopeng.dao.PlaceDao;
import com.luopeng.dao.UserDao;
import com.luopeng.model.Food;
import com.luopeng.model.Place;
import com.luopeng.model.ResponseModel;
import com.luopeng.model.User;
import com.luopeng.parse.ParseFood;
import com.luopeng.util.DateTimeUtil;
import com.luopeng.util.GsonUtil;
import com.luopeng.util.MStringUtils;
import com.luopeng.util.ModelAndViewUtil;


@Controller
@RequestMapping(value="/food")
public class FoodController {

	@Autowired
	private FoodDao foodDao;
	
	private static Logger logger = LogManager.getLogger(FoodController.class.getName());	
	
	@RequestMapping(value="/getAllFood")
	public void getAllFood(HttpServletRequest request,	HttpServletResponse response){
		logger.debug("getAllFood");
		List<Food> list = foodDao.selectAll();
		ResponseModel rsp = new ResponseModel();
		rsp.put("foodlist", list);
		rsp.put("totalCount", list.size());
		ModelAndViewUtil.write(rsp, response);
	}	
	
	@RequestMapping(value="/getFoodByType")
	public void getFoodByType(HttpServletRequest request,	HttpServletResponse response){
		String foodType = request.getParameter("foodType");
		System.out.println("FoodType:"+foodType);
		List<Food> list = foodDao.selectByType(Integer.valueOf(foodType));
		ResponseModel rsp = new ResponseModel();
		rsp.put("foodlist", list);
		rsp.put("totalCount", list.size());
		ModelAndViewUtil.write(rsp, response);
	}
	
	@RequestMapping(value="/addFoods")
	public void addFoods(HttpServletRequest request,	HttpServletResponse response){
		System.out.println("addFood");
		String newRecords = request.getParameter("newFoods");
		String updatedRecords = request.getParameter("updateFoods");
		String removeRecords = request.getParameter("removeFoods");
		
		System.out.println("newFoods:"+newRecords);
		System.out.println("updateFoods:"+updatedRecords);
		List<Food> newFoods = ParseFood.str2FoodList(newRecords);
		List<Food> updateFoods = ParseFood.str2FoodList(updatedRecords);
		List<Integer> removedFoods = GsonUtil.str2List(removeRecords);
		
		newFoods.addAll(updateFoods);
		if(!newFoods.isEmpty())
			foodDao.updatebatch(newFoods);
		if(!removedFoods.isEmpty())
			foodDao.deleteFoods(removedFoods);
		
		ModelAndViewUtil.writeString("{success:true}", response);
	}
	
	@RequestMapping(value="/getMenuDetail")
	public void getMenuDetail(HttpServletRequest request,	HttpServletResponse response){
		// select * from tb_food 
		//where id in 
		//(select food_id from tb_menu_food 
		//where menu_id == (select id from tb_menu where id = 0 )) s
		
		//name,food_state,menu_food.state
		//
		
		//select * from tb_food f,tb_menu_food mf,tb_menu m
		//where f.food_id in mf.food_id and mf.menu_id == m.id
	}
	
	@RequestMapping(value="/addFood")
	public void addFood(HttpServletRequest request,	HttpServletResponse response){
		String name = request.getParameter("foodName");
		String type = request.getParameter("type");
		String state = request.getParameter("state");
		int error = 0;
//		try{
//			Food food = new Food();
//			food.setName(name);
//			food.setState(Integer.valueOf(state));
//			food.setType(Integer.valueOf(type));
//			foodDao.insert(food);
//		}catch(Exception e){
//			e.printStackTrace();
//			error = -1;
//		}
		ResponseModel rsp = new ResponseModel();
		rsp.put("error", error);
		ModelAndViewUtil.write(rsp, response);
	}
	
}
