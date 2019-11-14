package com.userFront.service.UserServiceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userFront.dao.UserDao;
import com.userFront.domain.User;
import com.userFront.service.AccountService;
import com.userFront.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserDao userDao;

  @Autowired
  private AccountService accountService;

  public User findByUserId(Long userId) {
    return userDao.findOne(userId);
  }

  public List<User> findUserList() {
    return userDao.findAll();
  }

  @Override
  public User updateUser(Long userId,String mobileNo) {

     User user=userDao.findOne(userId);
    
      
        user.setPhone(mobileNo);
        return userDao.save(user);
        
  }

  @Override
  public boolean deleteUser(User user) {
      
      ((CrudRepository<User, Long>) user).findOne(user.getUserId());
     if(user!=null) { 
      userDao.delete(user);
      return true;
     }
     else 
       return false;
     
  }

  @Override
  public User saveUser(User user) {
    if (user.getAccountType() == "Primary") {
      user.setPrimaryAccount(accountService.createPrimaryAccount());
      User createdUser = userDao.save(user);
      return createdUser;
    }

    else {
      user.setSavingsAccount(accountService.createSavingsAccount());
      User createdUser = userDao.save(user);

      return createdUser;
    }

  }

  @Override
  public User findByUsername(String username) {
    return userDao.findByFirstName(username);
    
  }

}
