package com.luopeng.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.luopeng.model.User;

//@Repository
@Component
public interface UserDao   {//implements InitializingBean

    public int insert(User user);
    
    public int insertBaseInfo(User user);
     
    public int update(User user);
   
    public int delete(String userName);
   
    public List<User> selectAll();
   
    public int countAll();
   
    public User findByUserName(String userName);

	public List<Map<String,Object>> selectByMap(Map<String,Object> map);
	
	public User selectUserNameAndPwd(User user);
	
	
}
