package com.xiaolu.service.impl;

import com.xiaolu.dao.LoginLogMapper;
import com.xiaolu.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by chinaD on 2017/11/7.
 */
@Service("loginLogService")
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    LoginLogMapper loginLogMapper;

    @Override
    public int insertLoginLog(Map map) {
        return  loginLogMapper.insertLoginLog(map);
    }
}
