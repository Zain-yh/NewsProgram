package com.example.news.bean;

import java.util.List;

public class Channels {

    /**
     * msg : ok
     * result : ["头条","新闻","国内","国际","政治","财经","体育","娱乐","军事","教育","科技","NBA","股票","星座","女性","健康","育儿"]
     * status : 0
     */
    private String msg;
    private List<String> channels;
    private int status;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public List<String> getChannels() {
        return channels;
    }

    public int getStatus() {
        return status;
    }
}
