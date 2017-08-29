package com.nimi.sqprotos.constance;

/**
 * @author
 * @version 1.0
 * @date 2017/4/19
 */

public class BaseContanse {
    public static String SHARE_NAME = "sqprotos";
    public static final int MYTOAST_NO = -2;
    public static final int MYTOAST_ER = -1;
    public static final String PASS_MA = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
    public static final String BASE_URL ="" ;
    

    public static String address_ma = "请选择";

    public static String SAVE_PATH ="";
    public static final String JPEG_FILE_PREFIX = "IMG_";
    public static final String JPEG_FILE_SUFFIX = ".jpg";

    public static final int RESULT_PICK_IMAGE = 1;
    public static final int RESULT_TAKE_IMAGE = 2;


    /**
     * 未知错误
     */
    public static final int STATUS_ERROR = 1000;
    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络错误
     */
    public static final int NETWORD_ERROR = 1002;
    /**
     * 协议出错
     */
    public static final int HTTP_ERROR = 1003;
}
