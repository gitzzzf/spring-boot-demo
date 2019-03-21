package com.example.demo.bean.msg;


import org.apache.commons.lang3.StringUtils;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-19 上午10:21
 **/
public class BaseMsg {
    // 消息类型
    protected String type;
    // 携带的信息
    protected Object info;
    // 携带的时间戳
    protected  long sendTime;

    public BaseMsg() {
    }

    public BaseMsg(String type, long sendTime) {
        this.type = type;
        this.sendTime = sendTime;
    }

    public BaseMsg(String type, Object info, long sendTime) {
        this.type = type;
        this.info = info;
        this.sendTime = sendTime;
    }

    /**
     * 验证
     */
    public void validate(){
        if (StringUtils.isEmpty(type)){
            throw new RuntimeException("type should not null");
        }
    }

    public String getType() {
        return type;
    }

    public BaseMsg setType(String type) {
        this.type = type;
        return this;
    }

    public Object getInfo() {
        return info;
    }

    public BaseMsg setInfo(Object info) {
        this.info = info;
        return this;
    }

    public long getSendTime() {
        return sendTime;
    }

    public BaseMsg setSendTime(long sendTime) {
        this.sendTime = sendTime;
        return this;
    }
}
