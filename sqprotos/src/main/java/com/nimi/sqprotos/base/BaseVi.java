package com.nimi.sqprotos.base;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @version 1.0
 * @date 2017/5/31
 */

public interface BaseVi {
    //列表网络请求返回数据处理
    public void setData(List<Map<String, Object>> list);
    //数据加载完的停止刷新和加载
    public void stopReLoad();
    //状态视图的显示处理
    public void showStatus(int type);
    
}
