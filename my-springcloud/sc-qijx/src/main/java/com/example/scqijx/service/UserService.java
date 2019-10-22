package com.example.scqijx.service;


import com.example.scqijx.domain.User;
import org.springframework.stereotype.Service;


@Service
public class UserService {
	
//	@Resource
//    private UserMapper userMapper;
	
	public User getUserByName(String name) {
		//User user = userMapper.getUserByid(name);
		User user = new User();
		user.setName(name);
		user.setAddr("nowhere");
		return user;
	}
}