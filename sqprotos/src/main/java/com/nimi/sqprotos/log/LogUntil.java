package com.nimi.sqprotos.log;

import android.util.Log;

/**
 * @author
 * @version 1.0
 * @date 2017/8/29
 */

public class LogUntil {

        public static boolean DEBUG = true;
        private static String Tag = "Retrofit";
        //  私有化构造
        private LogUntil()
        {
            throw new UnsupportedOperationException("cannot be instantiated");
        }
        /**
         *   过滤Error信息
         * @param msg  信息体
         */
        public static void e(String msg)
        {
            if (DEBUG)
            {
                Log.e(Tag, msg);
            }
        }

        public static void e(String Tag,String msg)
        {
            if(DEBUG)
            {
                Log.e(Tag, msg);
            }
        }

        /**
         *  过滤info信息
         * @param msg  信息体
         */
        public static void i(String msg)
        {
            if(DEBUG)
            {
                Log.i(Tag, msg);
            }
        }

        /**
         *  过滤verbose信息
         * @param msg
         */
        public static void v(String msg)
        {
            if(DEBUG)
            {
                Log.v(Tag, msg);
            }
        }

        /**
         *  过滤DEBUG信息
         * @param msg
         */
        public static void d(String msg)
        {
            if(DEBUG)
            {
                Log.d(Tag, msg);
            }
        }

        /**
         *  自定义TAG函数
         */
        /**
         *  过滤info信息
         * @param TAG
         * @param msg
         */
        public static void i(String TAG,String msg)
        {
            if(DEBUG)
            {
                Log.i(TAG, msg);
            }
        }

        /**
         *  过滤DEBUG信息
         * @param TAG
         * @param msg
         */
        public static void d(String TAG,String msg)
        {
            if(DEBUG)
            {
                Log.d(TAG, msg);
            }
        }
        /**
         *  过滤verbose信息
         * @param TAG
         * @param msg
         */
        public static void v(String TAG,String msg)
        {
            if(DEBUG)
            {
                Log.v(TAG, msg);
            }
        }
    


}
