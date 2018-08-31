package com.gui.bean;

/**
 * Created by zhouwei on 2018/7/12.
 */

public class Bean2 {

    public Bean2(String bean2Name){
        this.bean2Name = bean2Name;
    }

    private String bean2Name;
    public int type = 2;

    public String getBean2Name() {
        return bean2Name;
    }

    public void setBean2Name(String bean1Name) {
        this.bean2Name = bean2Name;
    }
}
