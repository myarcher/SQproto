package com.nimi.sqprotos.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 地址解析的实体类
 * access 文件夹里
 * Created by Administrator on 2017/3/2.
 */

public class ProBean implements Serializable {
    private String value;
    private String text;
    private List<ProBean> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ProBean> getChildren() {
        return children;
    }

    public void setChildren(List<ProBean> children) {
        this.children = children;
    }
}
