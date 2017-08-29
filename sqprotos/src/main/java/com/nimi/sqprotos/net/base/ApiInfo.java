package com.nimi.sqprotos.net.base;

import java.io.File;

import retrofit2.Call;

/**
 * @author
 * @version 1.0
 * @date 2017/8/28
 */

public class ApiInfo {
    public String url;
    public int posi;
    public boolean start=false;
    public boolean stop=false;
    public Throwable throwable;
    public String json;
    public File file;
    public boolean isfile=false;
    public long total;
    public long current;
    public String save_path;
    public  boolean isSuccess=false;
    public Call mCall;
    public boolean isFinish;
}
