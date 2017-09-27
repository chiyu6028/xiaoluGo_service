package com.xiaolu.service;

import com.xiaolu.domain.User;

/**
 * Created by chinaD on 2017/9/19.
 */
public interface UserService {


    User selectByPrimaryKey(String userId);

    User selectUserBypassword(String userId, String userPassword);
}
