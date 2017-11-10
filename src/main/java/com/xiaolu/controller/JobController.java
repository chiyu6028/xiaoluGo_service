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
import java.util.Map;

/**
 * Created by chinaD on 2017/11/10.
 */
@Controller
public class JobController {

    ParamsReqAndResp paramsReqAndResp = ParamsReqAndResp.getInstance();

    @Autowired
    JobService jobService;

    @RequestMapping(path = "/job",method = RequestMethod.GET)
    public String tojob() {
        return "job";
    }

    @RequestMapping(path = "/job/{jobId}",method = RequestMethod.GET)
    public void getJob(@PathVariable String jobId, HttpServletRequest request, HttpServletResponse response) {
        Map result = jobService.selectJobListById(Integer.valueOf(jobId));
        String str = paramsReqAndResp.getJSONString(result);
        paramsReqAndResp.renderData(response,str);
    }
}
