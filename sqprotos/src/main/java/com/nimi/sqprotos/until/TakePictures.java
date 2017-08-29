package com.nimi.sqprotos.until;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;


import com.nimi.sqprotos.R;
import com.nimi.sqprotos.constance.BaseContanse;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakePictures {
    public static File setUpPhotoFile(Context context,String name) throws IOException {
        File f = createImageFile(context,name);
        return f;
    }
    private static File createImageFile(Context context,String name) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = BaseContanse.JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir(context,name);
        File imageF = File.createTempFile(imageFileName, BaseContanse.JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }
    private static File getAlbumDir(Context context,String name) {
        File storageDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = getAlbumStorageDir(name);
            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(context.getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    public static File getAlbumStorageDir(String albumName) {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
    }
    public static  String handlePickImage(Context context,Intent intent) {
    /*    Uri selectedImage = intent.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();*/
        String picturePath = Files.getImageAbsolutePath(context, intent.getData());
      return  picturePath;
    }

    public static void galleryAddPic(String srcPath,Context context) {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(srcPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }
}
