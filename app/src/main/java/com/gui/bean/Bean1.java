package com.gui.bean;

/**
 * Created by zhouwei on 2018/7/12.
 */

public class Bean1 {

    public Bean1(String bean1Name){
        this.bean1Name = bean1Name;
    }

    private String bean1Name;

    public int type = 1;

    public String getBean1Name() {
        return bean1Name;
    }

    public void setBean1Name(String bean1Name) {
        this.bean1Name = bean1Name;
    }
}
