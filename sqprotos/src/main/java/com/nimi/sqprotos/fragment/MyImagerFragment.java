package com.nimi.sqprotos.fragment;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.nimi.sqprotos.R;
import com.nimi.sqprotos.base.SActivity;
import com.nimi.sqprotos.base.SFragment;
import com.nimi.sqprotos.listener.CallBackListener;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/10/27.
 */
@SuppressLint("ValidFragment")
public class MyImagerFragment extends SFragment {
    ImageView information_header_item_img;
    private Map<String, Object> info;
    private CallBackListener listener;
    private SActivity activitys;
    int position;
    int po;
    List<Map<String, Object>> infoList;

    public MyImagerFragment() {
    }

    @Override
    protected void loadData() {
      
    }


    public MyImagerFragment(Object obj, CallBackListener listener) {
        info = (Map<String, Object>) obj;
        this.listener = listener;
    }

    @Override
    public void initView() {
        information_header_item_img=$(R.id.information_header_item_img);
        listener.callBack(1,1,information_header_item_img,info);
    }
    public MyImagerFragment(SActivity activitys, Object obj, int position, int po, List<Map<String, Object>> infoList, CallBackListener listener) {
        super(activitys);
        info = (Map<String, Object>) obj;
        this.activitys = activitys;
        this.position = position;
        this.po = po;
        this.infoList = infoList;
        this.listener=listener;
    }

    public MyImagerFragment(SActivity activitys, Object obj, int position, int po, List<Map<String, Object>> infoList) {
        super(activitys);
        info = (Map<String, Object>) obj;
        this.activitys = activitys;
        this.position = position;
        this.po = po;
        this.infoList = infoList;
    } 

    


    @Override
    public int getLayoutId() {
        return R.layout.fragment_information_header_item;
    }

}
