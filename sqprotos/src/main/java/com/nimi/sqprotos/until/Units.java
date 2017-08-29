package com.nimi.sqprotos.until;


import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nimi.sqprotos.AppAppliction;
import com.nimi.sqprotos.bean.ProBean;
import com.nimi.sqprotos.bean.ToastBean;
import com.nimi.sqprotos.listener.CallBackListener;
import com.nimi.sqprotos.toast.MyToast;
import com.nimi.sqprotos.toast.Types;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;

public class Units {
    public static int getSW(Context context) {
        DisplayMetrics disp = new DisplayMetrics();
        return disp.widthPixels;
    }

    public static int getSH(Context context) {
        DisplayMetrics disp = new DisplayMetrics();
        return disp.heightPixels;
    }

    public static int dpTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int pxTodip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @SuppressWarnings("static-access")
    public static float dp2Px(Context context, float value) {
        if (context == null) {
            return 0;
        }
        TypedValue typedValue = new TypedValue();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return typedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics);
    }

    @SuppressWarnings("static-access")
    public static float sp2Px(Context context, float value) {
        TypedValue typedValue = new TypedValue();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return typedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, metrics);
    }

    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    //获取设备的唯一信息
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        return DEVICE_ID;
    }

    public String getImsi(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = tm.getSubscriberId();
        if (imsi == null) {
            return "9.3.3";
        }
        return imsi;
    }



  

    public static String getImageStr(String imgFilePath) { // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null; // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            //Bitmap bitmap=BitmapFactory.decodeStream(in);
            // Log.i("json","w>>"+bitmap.getWidth()+"h>>"+bitmap.getHeight());
            //  Log.i("json","size>>"+ImagwTool.getBitmapSize(bitmap));
            data = new byte[in.available()];

            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    //获取地址数据  本地有缓存就去本地的，没有就解析
    public static String getAddress(final Context context, final CallBackListener listener) {
        new AsyncTask<String, Void, ArrayList>() {
            MyToast toast = new MyToast(context, new ToastBean("正在加载", Types.GO), null);

            @Override
            protected void onPreExecute() {
                toast.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(ArrayList been) {
                super.onPostExecute(been);
                if (toast != null) {
                    toast.dissmiss();
                }
                listener.callBack(2, 1, been, null);
            }

            @Override
            protected ArrayList doInBackground(String... params) {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList>() {
                }.getType();
                String sa = AppAppliction.applictions.until.getString("address", "");
                ArrayList la = null;
                if (!TextUtils.isEmpty(sa) && !sa.equals("")) {
                    la = gson.fromJson(sa, type);
                } else {
                    List<ProBean> list = getProBean(context);
                    la = getAddressList(list);
                    String address = gson.toJson(la, type);
                    AppAppliction.applictions.until.putString("address", address);
                }
                return la;
            }
        }.execute();
        return "";
    }

    /**
     * 封装地址数据
     *
     * @param list
     * @return
     */
    public static ArrayList getAddressList(List<ProBean> list) {
        ArrayList lists = new ArrayList();
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<ArrayList<String>> list2 = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<String>>> list3 = new ArrayList<>();
        for (int a = 0; a < list.size(); a++) {
            ProBean b1 = list.get(a);
            list1.add(b1.getText());
            List<ProBean> cb = b1.getChildren();
            ArrayList<ArrayList<String>> list6 = new ArrayList<>();
            ArrayList<String> list4 = new ArrayList<>();
            if (cb != null) {
                for (int b = 0; b < cb.size(); b++) {
                    ProBean b2 = cb.get(b);
                    list4.add(b2.getText());
                    List<ProBean> cb2 = b2.getChildren();
                    ArrayList<String> list5 = new ArrayList<>();
                    if (cb2 != null) {
                        for (int c = 0; c < cb2.size(); c++) {
                            ProBean b3 = cb2.get(c);
                            list5.add(b3.getText());
                        }
                    }
                    list6.add(list5);
                }
                list3.add(list6);
                list2.add(list4);
            }
        }
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        return lists;
    }

    /**
     * 从 access 中获取地址数据
     *
     * @param context
     * @return
     */
    public static List<ProBean> getProBean(Context context) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProBean>>() {
        }.getType();
        String json = getFromAssets(context, "city.txt");
        List<ProBean> mList = gson.fromJson(json, type);
        return mList;
    }

    /**
     * 从 access 中获取数据
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

   
   

    public static String getVersionName()//应用程序版本
    {
        try {
            PackageManager packageManager = AppAppliction.applictions.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(AppAppliction.applictions.getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean checkVersion(String webversion) {
        String version = getVersionName();
        String[] webs = webversion.split("\\.");
        String[] vers = version.split("\\.");
        int lenth = 0;

        if (webs.length >= vers.length) {
            lenth = vers.length;
        } else {
            lenth = webs.length;
        }
        boolean isTrue = false;
        for (int i = 0; i < lenth; i++) {
            int count1 = Integer.parseInt(webs[i]);
            int count2 = Integer.parseInt(vers[i]);
            if (count1 < count2) {
                isTrue = false;
                break;
            } else if (count1 > count2) {
                isTrue = true;
            } else {
                continue;
            }
        }
        return isTrue;
    }

    public static void getCompressPhoto(Context context, ArrayList<String> list, final CallBackListener listener) {
        final List<String> fileLists = new ArrayList<>();
        final List<File> files = new ArrayList<>();
        if (list.isEmpty()) {
            listener.callBack(2, 1, "", null);
            return;
        }
        for (String s : list) {
            files.add(new File(s));
        }
        Luban.compress(context, files).putGear(Luban.CUSTOM_GEAR)
                //.putGear(Luban.THIRD_GEAR)
                .setMaxSize(100)                // 限制最终图片大小（单位：Kb）
                .setMaxHeight(400)             // 限制图片高度
                .setMaxWidth(400).launch(new OnMultiCompressListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List<File> fileList) {
                int size = fileList.size();
                while (size-- > 0) {
                    fileLists.add(fileList.get(size).getPath());
                }
                String s = Units.getImageStrs(fileLists);
                listener.callBack(2, 1, s + "", null);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });

    }
    public static String getImageStrs(List<String> paths) {
        String s = "";
        if (paths != null) {
            for (String info : paths) {
                s = s + getImageStr(info) + ",";
            }
            if (s.length() > 0) {
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }
    

    public static BitmapDrawable getBitmap(Context context, int re) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(re);
        Bitmap bm = BitmapFactory.decodeStream(is, null, opt);
        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bm);
        return bd;
    }

    public static <T> T checkNotNull(T reference) {
        if(reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    public static String ToSBC(String input) {
        // 半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127 && c[i] > 32)
                c[i] = (char) (c[i] + 65248);
        }
        String s = new String(c);
        try {
            s = StringFilter(s);
        } catch (Exception e) {

        }
        return s;
    }

    // 全角转化为半角的方法
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (isChinese(c[i])) {
                if (c[i] == 12288) {
                    c[i] = (char) 32;
                    continue;
                }
                if (c[i] > 65280 && c[i] < 65375)
                    c[i] = (char) (c[i] - 65248);
            }
        }
        String s = new String(c);
        try {
            s = StringFilter(s);
        } catch (Exception e) {

        }
        return s;
    }

    // 替换、过滤特殊字符  
    public static String StringFilter(String str) throws PatternSyntaxException {
        str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!");//替换中文标号  
        String regEx = "[『』]"; // 清除掉特殊字符  
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    //  ps：利用编码的方式判断字符是否为汉字的方法：
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
    //判断一个activity是否在后台运行
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
                    return true;
                } else {
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 判断有没有网络
     *
     * @return
     */
    public static boolean verifyNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null) {
            if (activeNetInfo.isConnected()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
