package com.nimi.sqprotos.until;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.lijunhuayc.downloader.downloader.DownloadProgressListener;
import com.lijunhuayc.downloader.downloader.DownloaderConfig;
import com.lijunhuayc.downloader.downloader.FileDownloader;
import com.lijunhuayc.downloader.downloader.HistoryCallback;
import com.lijunhuayc.downloader.downloader.WolfDownloader;
import com.nimi.sqprotos.R;
import com.nimi.sqprotos.base.SActivity;
import com.nimi.sqprotos.dialog.DownDialog;
import com.nimi.sqprotos.net.base.ApiInfo;
import com.nimi.sqprotos.net.base.HttpType;
import com.nimi.sqprotos.net.base.SHttpLoader;
import com.nimi.sqprotos.net.xuntil.SXUtilsHttpLoader;
import com.nimi.sqprotos.toast.Types;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/12/21.
 */

public class DownUntil {
    private SActivity context;
    private String path;
    private DownDialog mDialog;
    private String savepath;
    WolfDownloader wolfDownloader;

    public DownUntil(SActivity context, String path) {
        this.context = context;
        this.path = path;
        savepath = Environment.getExternalStorageDirectory().getPath() + File.separator + "shoping";
        int x = (int) context.getResources().getDimension(R.dimen.dimen_360px);
        int y = (int) context.getResources().getDimension(R.dimen.dimen_240px);
        //   mDialog = new DownDialog(context, x, y);
        File file = new File(savepath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public void downs() {
        //path=Units.checkUlr(path);
        File saveDir = null;
        System.out.println(Environment.getExternalStorageState() + "------" + Environment.MEDIA_MOUNTED);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//开始下载文件
            saveDir = Environment.getExternalStorageDirectory();
        } else {
            context.showMess("SDCard不存在或者写保护", -1, Types.NOTI, null);
        }
        wolfDownloader = new DownloaderConfig().setThreadNum(3).setDownloadUrl(path).setSaveDir(saveDir).addDownloadListener(new DownloadProgressListener() {
            @Override
            public void onDownloadTotalSize(int totalSize) {
                //  progressBar.setMax(totalSize);//设置进度条的最大刻度为文件的长度
                // mDialog.setMax(totalSize);
            }

            @Override
            public void updateDownloadProgress(int size, float percent, float speed) {
                //resultView.setText(getFormatStr(size, percent, speed));
                // mDialog.setProgress(size);
                // progressBar.setProgress(size);
            }

            @Override
            public void onDownloadSuccess(String apkPath) {
                //  Toast.makeText(MainActivity.this, "下载成功\n" + apkPath, Toast.LENGTH_SHORT).show();
                //   startBt.setText("开始");
                //  stopBt.setVisibility(View.GONE);
                //instance(new File(apkPath));

            }

            @Override
            public void onDownloadFailed() {
                       /* if (mDialog != null&&mDialog.isShowing()) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                        context.showMess("下载失败了",-1, MyToast.Types.NOTI,null);*/
            }

            @Override
            public void onPauseDownload() {
                //      Toast.makeText(MainActivity.this, "下载暂停", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopDownload() {
                       /* Toast.makeText(MainActivity.this, "下载停止", Toast.LENGTH_SHORT).show();
                        startBt.setText("开始");
                        stopBt.setVisibility(View.GONE);*/
            }
        }).buildWolf(context);


        wolfDownloader.readHistory(new HistoryCallback() {
            @Override
            public void onReadHistory(int downloadLength, int fileSize) {
                if (fileSize != 0) {
                    //   mDialog.setMax(fileSize);
                    // mDialog.setProgress(downloadLength);
                    // mDialog.setSText(getFormatStr(downloadLength, FileDownloader.calculatePercent(downloadLength, fileSize), 0));
                }
            }
        });
        wolfDownloader.startDownload();
        //  mDialog.show();
    }

    private String getFormatStr(int size, float percent, float speed) {
        StringBuilder sBuilder = new StringBuilder();
        // sBuilder.append("  ").append(FileDownloader.formatSpeed(speed));
        sBuilder.append("  ").append(String.valueOf(percent + "%"));
        sBuilder.append("  ").append(FileDownloader.formatSize(size)).append("/");
        //  .append(FileDownloader.formatSize(mDialog.getMax()));
        return sBuilder.toString();
    }

    /**
     * 下载包
     *
     * @param
     */
    @SuppressLint("SdCardPath")
    public void setDownLoad() {
        // path=Units.checkUlr(path);
        savepath = savepath + "/shoping.apk";
        final ProgressDialog progressDialog = new ProgressDialog(context);
        final SXUtilsHttpLoader httpLoader = new SXUtilsHttpLoader();
        ApiInfo apiInfo = new ApiInfo();
        apiInfo.url=path;
        httpLoader.load(apiInfo, HttpType.DOWN, new HashMap<String, Object>(), new SHttpLoader.SHttpBackListener() {
            @Override
            public void onfinish(ApiInfo apiInfo) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                //    instance(arg0);

                // context.showMess("更新失败",-1, MyToast.Types.NOTI,null);
            }

            @Override
            public void onstart(ApiInfo apiInfo) {
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("亲，努力下载中。。。");
                progressDialog.show();
                progressDialog.setMax(0);
                progressDialog.setProgress(0);
            }

            @Override
            public void onLoad(ApiInfo apiInfo) {
                Message mess = new Message();
                mess.arg1 = (int) apiInfo.current;
                mess.arg2 = (int) apiInfo.total;
                mess.obj = progressDialog;
                mHandler.sendMessage(mess);
                // progressDialog.setProgress((int) arg1);
                // progressDialog.setMax((int) arg0);
            }

            @Override
            public void onCancel(ApiInfo apiInfo) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                // context.showMess("下载取消了",-1, MyToast.Types.NOTI,null);
            }
        });

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                httpLoader.cancel(path);
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ProgressDialog progressBar = (ProgressDialog) msg.obj;
            progressBar.setProgress((int) msg.arg1);
            progressBar.setMax((int) msg.arg2);
        }
    };

    public void downFiles() {
      /*  BaseP baseP = new BaseP(context);
        mDialog.show();
        baseP.mBClient.setListener(new CallBackListener() {
            @Override
            public void callBack(long code, long stat, Object value1, Object value2) {
                if (code == 1) {
                    SProces sProces = (SProces) value1;
                    mDialog.setProgress(sProces.BytesRead);
                    mDialog.setMax(sProces.ontentLength);
                    if (sProces.Done) {
                        mDialog.dismiss();
                    }
                }
            }
        });

        baseP.DOWN(baseP.getBService().DownApk(path), new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    InputStream is = response.body().byteStream();
                    File file = new File(savepath);
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
                    instance(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mDialog.dismiss();
            }
        });

    }

    private void instance(File file) {
        mDialog.dismiss();
        Intent intent = getFileIntent(file);
        context.startActivity(intent);
    }

    public Intent getFileIntent(File file) {
        //Uri uri = Uri.parse("http://m.ql18.com.cn/hpf10/1.pdf");   
        Uri uri = Uri.fromFile(file);
        String type = getMIMEType(file);
        Log.i("tag", "type=" + type);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, type);
        return intent;
    }

    private String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();     
      /* 取得扩展名 */
        // String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();   
         
      /* 依扩展名的类型决定MimeType */
       /* if (end.equals("pdf")) {
            type = "application/pdf";//   
        } else if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            type = "audio/*";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video/*";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            type = "image/*";
        } else if (end.equals("apk")) {
            type = "application/vnd.android.package-archive";
        } else {
        //    type = "*///*";
        // }
        //   return type;
    }
}
