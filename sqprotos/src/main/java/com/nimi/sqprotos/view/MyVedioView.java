package com.nimi.sqprotos.view;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nimi.sqprotos.R;

public class MyVedioView extends View {
    private Context context;

    private MediaPlayer mediaPlayer;
    private String filename;
    private SurfaceView my_vd_surfaceView;
    private ImageView my_vd_bt;
    private ProgressBar my_vd_pro;
    private String path;
    private String bg;

    public MyVedioView(Context context, String path, String bg) {
        super(context);
        this.context = context;
        this.path = path;
        this.bg = bg;
        init();
    }


    public MyVedioView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        View views = LayoutInflater.from(context).inflate(R.layout.my_vedioview, null);
        my_vd_surfaceView = (SurfaceView) views.findViewById(R.id.my_vd_surfaceView);
        my_vd_bt = (ImageView) views.findViewById(R.id.my_vd_bt);
        my_vd_pro = (ProgressBar) views.findViewById(R.id.my_vd_pro);
        my_vd_surfaceView.getHolder().addCallback(callback);
        my_vd_bt.setOnClickListener(click);
    }

    private Callback callback = new Callback() {
        // SurfaceHolder被修改的时候回调
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

            // 销毁SurfaceHolder的时候记录当前的播放位置并停止播放
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {

            //       play(0);

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

    };


    private OnClickListener click = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.my_vd_bt) {
                if (!TextUtils.isEmpty(path) && !TextUtils.isEmpty(bg)) {
                    play(0);
                }

            }
        }
    };


    /*
    * 停止播放
     */
    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;

        }
    }

    /**
     * 开始播放
     *
     * @param msec 播放初始位置
     */
    protected void play(final int msec) {
        // 获取视频文件地址
        Uri uri = Uri.parse(path);
        Uri uri1 = Uri.parse(bg);
        try {
            mediaPlayer = MediaPlayer.create(context, uri1);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置播放的视频源
            mediaPlayer.setDataSource(context, uri);
            // 设置显示视频的SurfaceHolder
            mediaPlayer.setDisplay(my_vd_surfaceView.getHolder());

            mediaPlayer.prepareAsync();
            my_vd_pro.setVisibility(View.VISIBLE);
            my_vd_bt.setVisibility(View.GONE);
            mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {

                    my_vd_pro.setVisibility(View.GONE);
                    mediaPlayer.start();
                    // 按照初始位置播放
                    mediaPlayer.seekTo(msec);
                    // 设置进度条的最大进度为视频流的最大播放时长

                    my_vd_bt.setEnabled(false);
                }
            });
            mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // 在播放完毕被回调
                    my_vd_bt.setVisibility(View.VISIBLE);
                    my_vd_bt.setEnabled(true);
                }
            });

            mediaPlayer.setOnErrorListener(new OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // 发生错误重新播放

                    play(0);

                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 暂停或继续
     */
    public void pause() {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();

        }
    }

    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();

        }
        return false;
    }


    public void setPath(String path, String bg) {
        this.path = path;
        this.bg = bg;
    }
}
