package com.userFront.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.userFront.domain.User;
import com.userFront.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/user/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") Long id,UriComponentsBuilder builder) {
    User user=userService.findByUserId(id);
    if(user!=null) {
    HttpHeaders h= new HttpHeaders();
    h.setLocation(builder.path("/user/{id}").buildAndExpand(user.getUserId()).toUri());
    return new ResponseEntity<User>(h, HttpStatus.OK);
    }
      return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
  }
  
  
  @GetMapping(value="/getAllUsers")
  public ResponseEntity<List<User>> getAllArticles() {
    
    List<User> users=userService.findUserList();
    if(users!=null) {
      return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
    return new ResponseEntity<List<User>>(users, HttpStatus.NO_CONTENT);
  }
  

	@RequestMapping(value = "/register/user", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User newUser) {
	
		   User user=userService.saveUser(newUser);
		   HttpHeaders headers = new HttpHeaders();
		   if(user!=null) {
		       return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		   }
		   return new ResponseEntity<Void>(headers, HttpStatus.CONFLICT);
		   
	}
	
	
	@PutMapping("/user/{id}/{mobileNo}")
  public ResponseEntity<User> updateArticle(@PathVariable("id") Long userId,@PathVariable("mobileNo") String mobileNo) {
	  User user=userService.updateUser(userId,mobileNo);
	  if(user!=null) {
     return new ResponseEntity<User>(user, HttpStatus.OK);
	  }
	  return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
  }
  
  
  @DeleteMapping("user/{id}")
  public ResponseEntity<Void> deleteArticle(@RequestBody User user) {
    Boolean status=userService.deleteUser(user);
    if(status==true) {
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
  } 

}
