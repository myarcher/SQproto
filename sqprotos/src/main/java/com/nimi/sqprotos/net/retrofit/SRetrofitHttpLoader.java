package com.nimi.sqprotos.net.retrofit;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.ArrayMap;

import com.nimi.sqprotos.base.RxActionManager;
import com.nimi.sqprotos.listener.CallBackListener;
import com.nimi.sqprotos.net.base.ApiInfo;
import com.nimi.sqprotos.net.base.HttpType;
import com.nimi.sqprotos.net.base.SHttpLoader;

import org.reactivestreams.Publisher;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author
 * @version 1.0
 * @date 2017/8/28
 */

public class SRetrofitHttpLoader extends SHttpLoader {
    Disposable disposable;
    private ArrayMap<String, Disposable> maps;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public SRetrofitHttpLoader() {
        maps = new ArrayMap<>();
    }

    @Override
    public void load(final ApiInfo apiInfo, HttpType type, Map<String, Object> params, final SHttpBackListener listener) {
        if (type == HttpType.POST || type == HttpType.GET || HttpType.UP == type) {
            create(apiInfo.mCall, new SCallback<String>(apiInfo, listener));
        } else if (type == HttpType.DOWN) {
            creates(apiInfo, new SCallback<File>(apiInfo, listener), listener);
        }
    }
    
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void cancel(String tag) {
        if (maps.isEmpty()) {
            return;
        }
        if (maps.get(tag) == null) {
            return;
        }
        if (!maps.get(tag).isDisposed()) {
            maps.get(tag).dispose();
            maps.remove(tag);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void cancelAll(List<String> urls) {
        if (maps.isEmpty()) {
            return;
        }
        for (String url : urls) {
            cancel(url);
        }
    }
    public void create(final Call call, final Callback callback) {
        Flowable flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public final void subscribe(final FlowableEmitter<String> e) throws Exception {
                e.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        if (!call.isCanceled()) {
                            call.cancel();
                        }
                    }
                });
                call.enqueue(callback);
            }

        }, BackpressureStrategy.BUFFER).compose(background());
        disposable = flowable.subscribe();
    }


    public <T> FlowableTransformer<T, T> background() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(new IoScheduler()).observeOn(AndroidSchedulers.mainThread()).onErrorResumeNext(Flowable.<T>empty());
            }
        };
    }


    public Observable<String> create(Observable observable, Observer observer) {
        observable.retryWhen(new SException()).subscribeOn(Schedulers.io()).
                unsubscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
        return observable;
    }

    public void creates(final ApiInfo apiInfo, final Callback callback, final SHttpBackListener listener) {
        SClitent.getInstance().setListener(new CallBackListener() {
            @Override
            public void callBack(long code, long stat, Object value1, Object value2) {
                apiInfo.current = code;
                apiInfo.total = stat;
                apiInfo.isFinish = (boolean) value1;
                listener.onLoad(apiInfo);
            }
        });
        apiInfo.mCall.enqueue(callback);
    }
}
