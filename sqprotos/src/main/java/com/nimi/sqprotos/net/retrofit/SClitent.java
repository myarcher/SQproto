package com.nimi.sqprotos.net.retrofit;


import android.os.Message;

import com.nimi.sqprotos.AppAppliction;
import com.nimi.sqprotos.constance.BaseContanse;
import com.nimi.sqprotos.listener.CallBackListener;
import com.nimi.sqprotos.manager.AppManager;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author
 * @version 1.0
 * @date 2017/4/28
 */

public class SClitent {

    private CallBackListener mListener;
    private static SClitent mBClient;
    private Retrofit mRetrofit;

    public void setListener(CallBackListener listener) {
        mListener = listener;
    }

    private SClitent() {
        mRetrofit = createRetrofit();
    }

    public static SClitent getInstance() {
        if (mBClient == null) {
            synchronized (SClitent.class) {
                if (mBClient == null) {
                    mBClient = new SClitent();
                }
            }
        }
        return mBClient;
    }

    /**
     * 创建相应的服务接口
     */
    public <T> T create(Class<T> service) {
        checkNotNull(service, "service is null");
        return mRetrofit.create(service);
    }


    private <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    private Retrofit createRetrofit() {
        //初始化OkHttp
        LauncherTrust launcherTrust = new LauncherTrust();
        Cache cache = new Cache(new File(AppAppliction.applictions.getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
        OkHttpClient.Builder builder = new OkHttpClient
                .Builder()
                .cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)    //设置连接超时 9s
                .readTimeout(20, TimeUnit.SECONDS)      //设置读取超时 10s
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .sslSocketFactory(sslSocketFactory(launcherTrust), launcherTrust);

        if (AppManager.getInstance().isDebug) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        if (mListener != null) {
            builder.addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder().body(new SRetrofitDown(originalResponse.body(), mListener)).build();
                }
            });
        }
        // 返回 Retrofit 对象
        return new Retrofit.Builder().baseUrl(BaseContanse.BASE_URL).client(builder.build()) // 传入请求客户端
                .addConverterFactory(ScalarsConverterFactory.create()) // 添加Gson转换工厂
                //.addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换工厂
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加RxJava2调用适配工厂
                .build();

    }

    private SSLSocketFactory sslSocketFactory(LauncherTrust launcherTrust) {

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{launcherTrust}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }


    static class LauncherTrust implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
