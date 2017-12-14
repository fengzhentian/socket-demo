package com.jjq.demo.common;

/**
 * 全局静态参数配置
 * 
 * @author jingjq
 * @version 2017-12-01 10:06:57
 */
public class GlobalCons {

    /**
     * 消息头部
     */
    public static final String TAG_START = "11aa22bb";
    /**
     * 心跳包头部
     */
    public static final String TAG_HEART = "11cc22dd";

    /**
     * 消息头部
     */
    public static final int TAG_START_FLG = 0x11aa22bb;
    /**
     * 心跳包头部
     */
    public static final int TAG_HEART_FLG = 0x11cc22dd;

    /**
     * 消息内容编码
     */
    public static final String CONTENT_CHARSET_NAME = "GBK";

}
