package com.nimi.sqprotos.net.retrofit;

import com.nimi.sqprotos.net.base.ApiInfo;
import com.nimi.sqprotos.net.base.SHttpLoader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author
 * @version 1.0
 * @date 2017/8/28
 */

public class SCallback<T> implements Callback<T> {
    private ApiInfo apiInfo;
    private SHttpLoader.SHttpBackListener listener;

    public SCallback(ApiInfo apiInfo, SHttpLoader.SHttpBackListener listener) {
        this.apiInfo = apiInfo;
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (apiInfo.isfile) {
            onBody((Response<ResponseBody>) response);
            apiInfo.isSuccess=true;
            listener.onfinish(apiInfo);
        } else {
            if (response.isSuccessful()) {
                apiInfo.json = (String) response.body();
                apiInfo.isSuccess=true;
                listener.onfinish(apiInfo);
            } else {
                apiInfo.json = "";
                apiInfo.isSuccess=false;
                listener.onfinish(apiInfo);
            }
        }

    }

    private void onBody(Response<ResponseBody> response) {
        try {
            InputStream is = response.body().byteStream();
            File file = new File(apiInfo.save_path);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                fos.flush();
            }
            fos.close();
            bis.close();
            is.close();
            apiInfo.file = file;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        apiInfo.throwable = t;
        apiInfo.isSuccess=false;
        listener.onfinish(apiInfo);
    }
}
