package com.nimi.sqprotos.until;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Build;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;



public class ImageUntil {
//第一：质量压缩法：
	
	private  static  Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>10) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
//第二：图片按比例大小压缩方法（根据路径获取图片并压缩）：
	public static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }//图片比例压缩时， 我看到一个算法，说比较快。。
	//be = (int) ((w / STANDARD_WIDTH + h/ STANDARD_HEIGHT) / 2);
	//结论二：图片比例压缩倍数 就是 （宽度压缩倍数+高度压缩倍数）/2..
	//第三：图片按比例大小压缩方法（根据Bitmap图片压缩）：
	public static Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();        
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 120;//这里设置高度为800f
        float ww = 120;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h ) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }
	
	public  Bitmap getTransparentBitmap(Bitmap sourceImg, int number){  //number 0--100
	     int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];  

	     sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg  

	             .getWidth(), sourceImg.getHeight());// 获得图片的ARGB值  

	     number = number * 255 / 100;  

	     for (int i = 0; i < argb.length; i++) {  

	         argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);  

	     }  

	     sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg  

	             .getHeight(), Config.RGB_565);  

	     return sourceImg;  
	 } 
	
	public static Bitmap upImageSize(Context context,Bitmap bmp, int width,int height) {
	    if(bmp==null){
	        return null;
	    }
	    // 计算比例
	    float scaleX = (float)width / bmp.getWidth();// 宽的比例
	    float scaleY = (float)height / bmp.getHeight();// 高的比例
	    //新的宽高
	    int newW = 0;
	    int newH = 0;
	    if(scaleX < scaleY){
	        newW = (int) (bmp.getWidth() * scaleX);
	        newH = (int) (bmp.getHeight() * scaleX);
	    }else if(scaleX >= scaleY){
	        newW = (int) (bmp.getWidth() * scaleY);
	        newH = (int) (bmp.getHeight() * scaleY);
	    }
	    return Bitmap.createScaledBitmap(bmp, newW, newH, true);
	}
    /** 保存方法 */
    public static void saveBitmap(String path) {
        Bitmap bp=getimage(path);
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static String getImageStrs(List<String> paths){
      String    values="";
         for(String pa:paths){
           String fs= getImageStr(pa);
             values=values+fs+";";
         }
        if(values.length()>0) {
            values = values.substring(0, values.length() - 1);
        }
        return values;
    }
    public static  String getImageStr(String  imgFilePath){ // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
             byte[] data = null; // 读取图片字节数组
             try {
                 InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data); in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
    return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    public static int getBitmapSize(Bitmap bitmap){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){     //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//API 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }
}
