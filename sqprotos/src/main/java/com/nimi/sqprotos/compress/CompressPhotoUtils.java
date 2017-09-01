package com.nimi.sqprotos.compress;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;


import com.nimi.sqprotos.listener.CallBackListener;
import com.nimi.sqprotos.until.Units;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/4.
 */

public class CompressPhotoUtils {
    private Context context;
    private CompressCallBack callBack;

    public void CompressPhoto(Context context, ArrayList<String> list, CompressCallBack callBack) {
        CompressTask task = new CompressTask(list);
        this.context = context;
        this.callBack = callBack;
        task.execute();
    }

    class CompressTask extends AsyncTask<Void, Integer, String> {

        private ArrayList<String> list;


        CompressTask(ArrayList<String> list) {
            this.list = list;
        }

        /**
         * 运行在UI线程中，在调用doInBackground()之前执行
         */
        @Override
        protected void onPreExecute() {

        }

        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected String doInBackground(Void... params) {
            Units.getCompressPhoto(context, list, new CallBackListener() {
                @Override
                public void callBack(long code, long stat, Object value1, Object value2) {
                    Message mes = new Message();
                    mes.obj = value1;
                    mHandler.sendMessage(mes);
                }
            });
            return null;
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(String path) {
            // callBack.success(path);
        }

        /**
         * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            callBack.success(msg.obj + "");
            super.handleMessage(msg);
        }
    };


    public interface CompressCallBack {
        void success(String list);
    }

}
