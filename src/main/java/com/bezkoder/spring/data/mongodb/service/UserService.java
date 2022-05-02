package com.bezkoder.spring.data.mongodb.service;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.bezkoder.spring.data.mongodb.dao.UserDao;
import com.bezkoder.spring.data.mongodb.model.User;
 
/**
 * @author raidentrance
 *
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public List<User> getUsers() {
        return userDao.findAll();
    }
}