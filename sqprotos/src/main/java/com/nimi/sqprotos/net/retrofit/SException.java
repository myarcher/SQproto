package com.nimi.sqprotos.net.retrofit;

import android.util.Log;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;


/**
 * retry条件
 * Created by WZG on 2016/10/17.
 */
public class SException implements Function<Observable<? extends Throwable>, ObservableSource<?>> {
    //    retry次数
    private int count = 1;
    //    延迟
    private long delay = 5000;
    //    叠加延迟
    private long increaseDelay = 5000;

    public SException() {

    }

    public SException(int count, long delay) {
        this.count = count;
        this.delay = delay;
    }

    public SException(int count, long delay, long increaseDelay) {
        this.count = count;
        this.delay = delay;
        this.increaseDelay = increaseDelay;
    }


    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable.zipWith(Observable.range(1, count + 1), new BiFunction<Throwable, Integer, Wrapper>() {

            @Override
            public Wrapper apply(Throwable throwable, Integer integer) throws Exception {
                return new Wrapper(throwable, integer);
            }
        }).flatMap(new Function<Wrapper, Observable<?>>() {
            @Override
            public Observable<?> apply(Wrapper wrapper) {
                if ((wrapper.throwable instanceof ConnectException || wrapper.throwable instanceof SocketTimeoutException || wrapper.throwable instanceof TimeoutException) && wrapper.index < count + 1) { //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                    Log.e("tag", "retry---->" + wrapper.index);
                    return Observable.timer(delay + (wrapper.index - 1) * increaseDelay, TimeUnit.MILLISECONDS);

                }
                return Observable.error(wrapper.throwable);
            }
        });
        
    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }

}