package com.xiaolu.service.impl;

import com.xiaolu.dao.UserMapper;
import com.xiaolu.domain.User;
import com.xiaolu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chinaD on 2017/9/19.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User selectByPrimaryKey(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public User selectUserBypassword(String userId, String userPassword) {
        return userMapper.selectUserBypassword(userId, userPassword);
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }
}
