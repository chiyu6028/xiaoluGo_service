package com.xiaolu.util;

/**
 * 常量类
 * Created by chinaD on 2017/11/13.
 */
public class Constant {

    public static final String SCHEDULE_JOB_ID = "SCHEDULE_JOB_ID";

    /*
    * 定时任务的状态
    * */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
