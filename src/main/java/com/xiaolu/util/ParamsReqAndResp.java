package com.xiaolu.util;

import com.alibaba.fastjson.JSON;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 数据传递和json转换
 * Created by chinaD on 2017/9/26.
 */
public class ParamsReqAndResp {
    private static volatile ParamsReqAndResp instance;

    public static ParamsReqAndResp getInstance() {
        if (instance == null) {
            synchronized (ParamsReqAndResp.class) {
                if (instance == null) {
                    instance = new ParamsReqAndResp();
                }
            }
        }
        return instance;
    }

    public String getJSONString(Map result) {
        List<Map<String, Object>> datas = new ArrayList();
        datas.add(result);
        String jsonResult = JSON.toJSONString(datas);
        return jsonResult;
    }

    public void renderData(HttpServletResponse response, String data) {
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.print(data);

            if (null != printWriter) {
                printWriter.flush();
                printWriter.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ParamsReqAndResp.class.getName()).log(Level.SEVERE, null, ex);

            if (null != printWriter) {
                printWriter.flush();
                printWriter.close();
            }
        } finally {
            if (null != printWriter) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }
}
