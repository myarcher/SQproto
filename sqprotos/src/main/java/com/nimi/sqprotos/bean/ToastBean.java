package com.nimi.sqprotos.bean;

import com.nimi.sqprotos.toast.Types;

import java.io.Serializable;

public class ToastBean implements Serializable {
	private String title;
	private Types type;
 private int flag;
 private Object obj;
 private String url;
public int getFlag() {
	return flag;
}
public void setFlag(int flag) {
	this.flag = flag;
}
public Object getObj() {
	return obj;
}
public void setObj(Object obj) {
	this.obj = obj;
}

public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public Types getType() {
	return type;
}
public void setType(Types type) {
	this.type = type;
}


public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public ToastBean(String title, Types type, int flag, Object obj) {
	this.title = title;
	this.type = type;
	this.flag = flag;
	this.obj = obj;
}

public ToastBean(String title, Types type, int flag) {
	this.title = title;
	this.type = type;
	this.flag = flag;
	this.obj = null;
	this.url=null;
}
public ToastBean(String title, Types type) {
	this.title = title;
	this.type = type;
	this.flag = -1;
	this.obj = null;
	this.url=null;
}
public ToastBean(String title, Types type, String url) {
	this.title = title;
	this.type = type;
	this.flag = -1;
	this.obj = null;
	this.url=url;
}


}
