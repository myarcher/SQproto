package com.nimi.sqprotos.mvc;

import java.util.Map;

import retrofit2.Call;

/**
 * @author
 * @version 1.0
 * @date 2017/7/10
 */

public interface XContract {
    interface View extends XView<Presenter> {
        void showStausViewOrToast(int posi, int type);
        void dissStausViewOrToast(int posi, int type);
        void backInfo(int posi, String mess, Object data);

        void showErrToast(String title, int posi, Object object);
         
        void dealPublic(int posi, Map<String, Object> map);
        
        XModel getXmodel();

        Call getCall(int posi,Map<String,Object> para);

    }

    interface Presenter extends XPresenter {
        void dealFail(int posi, Throwable t, int stat, Map<String, Object> map);
    }
}
