package com.nimi.sqprotos.mvc;

import android.support.annotation.NonNull;

import com.nimi.sqprotos.AppAppliction;
import com.nimi.sqprotos.constance.BaseContanse;
import com.nimi.sqprotos.net.base.ApiInfo;
import com.nimi.sqprotos.net.base.SHttpLoader;
import com.nimi.sqprotos.parse.CommonJSONParser;
import com.nimi.sqprotos.until.Units;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author
 * @version 1.0
 * @date 2017/7/10
 */

public class BXPresenter implements XContract.Presenter, SHttpLoader.SHttpBackListener {
    @NonNull
    private final XContract.View contractView;
    private XModel mXModel;
    private List<String> urls;

    public BXPresenter(@NonNull XContract.View contractView) {
        this.contractView = Units.checkNotNull(contractView);
        // contractView.setPresenter(this);
        urls = new ArrayList<>();
    }

    @Override
    public void subscribe() {
        mXModel = contractView.getXmodel();
        contractView.showStausViewOrToast(mXModel.apiInfo.posi, 1);
        if (Units.verifyNetwork(AppAppliction.applictions)) {
            urls.add(mXModel.apiInfo.url);
            mXModel.setListener(this).builder();
        } else {
            dealFail(mXModel.apiInfo.posi, new Throwable("网络错误"), BaseContanse.NETWORD_ERROR, null);
        }
    }


    @Override
    public void unsubscribe() {
        mXModel.cancel(urls);
    }


    @Override
    public void onfinish(ApiInfo apiInfo) {
        mXModel.cancel(apiInfo.url);
        if (apiInfo.isSuccess) {
            if (apiInfo.isfile) {
                deal(apiInfo.posi, apiInfo.file);
            } else {
                deal(apiInfo.posi, apiInfo.json);
            }
        } else {
            dealFail(apiInfo.posi, apiInfo.throwable, BaseContanse.HTTP_ERROR, null);
        }
    }

    @Override
    public void onstart(ApiInfo apiInfo) {

    }

    @Override
    public void onLoad(ApiInfo apiInfo) {

    }

    @Override
    public void onCancel(ApiInfo apiInfo) {

    }

    public void deal(int posi, File file) {

    }

    private final void deal(int posi, String json) {
        Map<String, Object> map = new CommonJSONParser().parse(json);
        if (map != null) {
            int status = Integer.parseInt(map.get("status") + "");
            if (status == 1) {
                Object data = map.get("data");
                try {
                    dealSuccess(posi, map.get("message") + "", data);
                } catch (Exception e) {
                    dealFail(posi, new Throwable("解析数据错误"), BaseContanse.PARSE_ERROR, map);
                }
            } else {
                if (status == 0 || status == -1 || status == -1) {
                    dealFail(posi, new Throwable(map.get("message") + ""), BaseContanse.STATUS_ERROR, map);
                } else {
                    dealPublic(posi, map);
                }
            }
        } else {
            dealFail(posi, new Throwable("解析数据错误"), BaseContanse.PARSE_ERROR, map);
        }
    }

    public void dealSuccess(int posi, String mess, Object data) {
        contractView.dissStausViewOrToast(posi, 1);
        contractView.backInfo(posi, mess, data);
    }

    public void dealPublic(int posi, Map<String, Object> map) {
        contractView.dissStausViewOrToast(posi, 2);
        contractView.dealPublic(posi, map);
    }

    @Override
    public void dealFail(int posi, Throwable t, int stat, Map<String, Object> map) {
        contractView.dissStausViewOrToast(posi, stat);
        contractView.showErrToast(t.getMessage(), posi, map);
    }
}
