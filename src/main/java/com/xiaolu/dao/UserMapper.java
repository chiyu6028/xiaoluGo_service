package com.xiaolu.dao;

import com.xiaolu.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by chinaD on 2017/9/18.
 */
public interface UserMapper {

    User selectByPrimaryKey(String user_id);

    User selectUserBypassword(@Param("user_id")String user_id, @Param("user_password")String user_password);

    int insertUser(User user);
}
