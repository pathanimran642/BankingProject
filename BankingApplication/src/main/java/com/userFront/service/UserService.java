package com.userFront.service;

import java.util.List;

import com.userFront.domain.User;

public interface UserService {
	
	User findByUserId(Long userId);

	User findByUsername(String username);
	
	User saveUser(User user);
	
	List<User> findUserList();

   User updateUser(Long userId,String mobileNo);
   
   boolean deleteUser(User user);
}
