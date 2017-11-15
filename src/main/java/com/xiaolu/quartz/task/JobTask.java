package com.xiaolu.quartz.task;

/**
 * 这个是被调的任务，用来测试
 * Created by chinaD on 2017/11/10.
 */
public class JobTask {

    public void task(){
        System.out.println("hello world!");
    }

    public void justDo(String params){
        System.out.println("just Do it : " + params );
    }
}
