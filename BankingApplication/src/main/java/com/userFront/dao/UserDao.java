package com.userFront.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.userFront.domain.User;

public interface UserDao extends CrudRepository<User, Long> {

	User findByFirstName(String username);

	List<User> findAll();
}
