package com.example.mapper;

import com.example.domain.User;

public interface UserMapper {
	
	User getUserByName(String name);
}