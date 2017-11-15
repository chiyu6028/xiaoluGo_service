package com.xiaolu.controller;

import com.xiaolu.domain.User;
import com.xiaolu.service.JobService;
import com.xiaolu.util.ParamsReqAndResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chinaD on 2017/11/10.
 */
@Controller
@RequestMapping("/job")
public class JobController {

    ParamsReqAndResp paramsReqAndResp = ParamsReqAndResp.getInstance();

    @Autowired
    JobService jobService;

    @RequestMapping(method = RequestMethod.GET)
    public String tojob() {
        return "job";
    }

    @RequestMapping(path = "/all",method = RequestMethod.GET)
    public void getJobAll(HttpServletRequest request, HttpServletResponse response) {
        List result = jobService.selectJobAll();
        Map map = new HashMap();
        map.put("list",result);
        String str = paramsReqAndResp.getJSONString(map);
        paramsReqAndResp.renderData(response,str);
    }

    @RequestMapping(path = "/{jobId}",method = RequestMethod.GET)
    public void getJob(@PathVariable String jobId, HttpServletRequest request, HttpServletResponse response) {
        Map result = jobService.selectJobListById(Integer.valueOf(jobId));
        String str = paramsReqAndResp.getJSONString(result);
        paramsReqAndResp.renderData(response,str);
    }

    @RequestMapping(path = "/execJob",method = RequestMethod.GET)
    public void execJob(HttpServletRequest request, HttpServletResponse response) {
        String jobId = request.getParameter("jobId");
        String msg = "执行成功";
        int flag = 0 ;
        Map resultMap = new HashMap();
        if (!"".equals(jobId) && null != jobId){
            jobService.execJob(Integer.valueOf(jobId));
            flag = 0;
        }else{
            msg = "执行失败";
            flag = -1;
        }

        resultMap.put("msg",msg);
        resultMap.put("flag",flag);
        String str = paramsReqAndResp.getJSONString(resultMap);
        paramsReqAndResp.renderData(response,str);
    }

    @RequestMapping(path = "/pauseJob",method = RequestMethod.GET)
    public void pauseJob(HttpServletRequest request, HttpServletResponse response) {
        String jobId = request.getParameter("jobId");
        String msg = "执行成功";
        int flag = 0 ;
        Map resultMap = new HashMap();
        if (!"".equals(jobId) && null != jobId){
            jobService.pauseJob(Integer.valueOf(jobId));
            flag = 0;
        }else{
            msg = "执行失败";
            flag = -1;
        }

        resultMap.put("msg",msg);
        resultMap.put("flag",flag);
        String str = paramsReqAndResp.getJSONString(resultMap);
        paramsReqAndResp.renderData(response,str);
    }

    @RequestMapping(path = "/resumeJob",method = RequestMethod.GET)
    public void resumeJob(HttpServletRequest request, HttpServletResponse response) {
        String jobId = request.getParameter("jobId");
        String msg = "执行成功";
        int flag = 0 ;
        Map resultMap = new HashMap();
        if (!"".equals(jobId) && null != jobId){
            jobService.resumeJob(Integer.valueOf(jobId));
            flag = 0;
        }else{
            msg = "执行失败";
            flag = -1;
        }

        resultMap.put("msg",msg);
        resultMap.put("flag",flag);
        String str = paramsReqAndResp.getJSONString(resultMap);
        paramsReqAndResp.renderData(response,str);
    }

    @RequestMapping(path = "/deleteScheduleJob",method = RequestMethod.GET)
    public void deleteScheduleJob(HttpServletRequest request, HttpServletResponse response) {
        String jobId = request.getParameter("jobId");
        String msg = "执行成功";
        int flag = 0 ;
        Map resultMap = new HashMap();
        if (!"".equals(jobId) && null != jobId){
            jobService.deleteScheduleJob(Integer.valueOf(jobId));
            flag = 0;
        }else{
            msg = "执行失败";
            flag = -1;
        }

        resultMap.put("msg",msg);
        resultMap.put("flag",flag);
        String str = paramsReqAndResp.getJSONString(resultMap);
        paramsReqAndResp.renderData(response,str);
    }
}
