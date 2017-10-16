package com.xiaolu.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取properties文件
 * Created by chinaD on 2017/10/16.
 */
public class PropertiesUtil {

    private PropertiesUtil() {
    }

    private static volatile PropertiesUtil instance = null;

    public PropertiesUtil getInstance(){
        if (instance == null) {
            synchronized (PropertiesUtil.class) {
                if (instance == null) {
                    instance = new PropertiesUtil();
                }
            }
        }
        return instance;
    }

    static String getProperties(String key) {
        InputStream proStr = PropertiesUtil.class.getResourceAsStream("/conf.properties");
        Properties pro = new Properties();
        String str = null;
        try {
            pro.load(proStr);
            str = pro.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (proStr != null) {
                try {
                    proStr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

}
