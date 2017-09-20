package com.xiaolu.dao;

import com.xiaolu.domain.User;

/**
 * Created by chinaD on 2017/9/18.
 */
public interface UserMapper {

    User selectByPrimaryKey(String userId);
}
