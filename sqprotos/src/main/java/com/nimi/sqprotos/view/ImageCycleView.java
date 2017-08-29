package com.nimi.sqprotos.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nimi.sqprotos.R;
import com.nimi.sqprotos.base.SActivity;
import com.nimi.sqprotos.fragment.MyImagerFragment;
import com.nimi.sqprotos.pre_img.PreviewActivity;
import com.nimi.sqprotos.listener.CallBackListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 广告图片自动轮播控件</br>
 * <p>
 * <pre>
 *   集合ViewPager和指示器的一个轮播控件，主要用于一般常见的广告图片轮播，具有自动轮播和手动轮播功能
 *   使用：只需在xml文件中使用{@code <com.minking.imagecycleview.ImageCycleView/>} ，
 *   然后在页面中调用
 *
 *   另外提供{@link #startImageCycle() } \ {@link #pushImageCycle() }两种方法，用于在Activity不可见之时节省资源；
 *   因为自动轮播需要进行控制，有利于内存管理
 * </pre>
 */
public class ImageCycleView extends LinearLayout {


    /**
     * 图片轮播视图
     */
    private CycleViewPager mBannerPager = null;

    /**
     * 滚动图片视图适配器
     */
    private ImageCycleAdapter mAdvAdapter;

    /**
     * 图片轮播指示器控件
     */
    private ViewGroup mGroup;

    /**
     * 图片轮播指示器-个图
     */
    private ImageView mImageView = null;

    /**
     * 滚动图片指示器-视图列表
     */
    private ImageView[] mImageViews = null;

    /**
     * 图片滚动当前图片下标
     */
    private int mImageIndex = 1;

    /**
     * 手机密度
     */
    private float mScale;

    /**
     * @param context
     */
    public ImageCycleView(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */

    public ImageCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScale = context.getResources().getDisplayMetrics().density;
        LayoutInflater.from(context).inflate(R.layout.view_banner_content, this);
        mBannerPager = (CycleViewPager) findViewById(R.id.pager_banner);
        //	LayoutParams pa=(LayoutParams)mBannerPager.getLayoutParams();
        //pa.height=pa.width/2;
        //mBannerPager.setLayoutParams(pa);
        mBannerPager.setOnPageChangeListener(new GuidePageChangeListener());
        mBannerPager.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 开始图片滚动
                        startImageTimerTask();
                        break;
                    default:
                        // 停止图片滚动
                        stopImageTimerTask();
                        break;
                }
                return false;
            }
        });
        // 滚动图片右下指示器视图
        mGroup = (ViewGroup) findViewById(R.id.viewGroup);
    }

    /**
     * 装填图片数据
     */
    SActivity activity;
    private List<Map<String, Object>> infolists;
     int finalPos = 0;
    public void setImageResources(SActivity mActivity, List<Map<String, Object>> infoList) {
        setImageResources(mActivity, infoList, 0);
    }

    public void setImageResources(SActivity mActivity, List<Map<String, Object>> infoList, int po) {
        this.infolists = infoList;
        this.activity = mActivity;
        // 清除所有子视图
        mGroup.removeAllViews();
        // 图片广告数量
        final int imageCount = infoList.size();
        mImageViews = new ImageView[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageView = new ImageView(mActivity);
            int w = (int) getResources().getDimension(R.dimen.dimen_8px);
            LayoutParams layout = new LayoutParams(w, w);
            layout.leftMargin = 4;
            layout.rightMargin = 4;
            mImageView.setLayoutParams(layout);
            //mImageView.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
            mImageViews[i] = mImageView;
            if (i == 0) {
                mImageViews[i].setBackgroundResource(R.mipmap.news_point_blue);
            }
            else {
                mImageViews[i].setBackgroundResource(R.mipmap.dot_blur);
            }
            mGroup.addView(mImageViews[i]);
            Map<String,Object> map = infoList.get(i);
            map.put("pos",i);
            infoList.set(i,map);
        }
        if(imageCount==1){
            mGroup.setVisibility(INVISIBLE);
        }
        mAdvAdapter = new ImageCycleAdapter(infoList, po);
        mBannerPager.setAdapter(mAdvAdapter);
        startImageTimerTask();
    }

    /**
     * 开始轮播(手动控制自动轮播与否，便于资源控制)
     */
    public void startImageCycle() {
        startImageTimerTask();
    }

    /**
     * 暂停轮播——用于节省资源
     */
    public void pushImageCycle() {
        stopImageTimerTask();
    }

    /**
     * 开始图片滚动任务
     */
    private void startImageTimerTask() {
        stopImageTimerTask();
        // 图片每3秒滚动一次
        mHandler.postDelayed(mImageTimerTask, 3000);
    }

    /**
     * 停止图片滚动任务
     */
    private void stopImageTimerTask() {
        mHandler.removeCallbacks(mImageTimerTask);
    }

    private Handler mHandler = new Handler();

    /**
     * 图片自动轮播Task
     */
    private Runnable mImageTimerTask = new Runnable() {

        @Override
        public void run() {
            if (mImageViews != null) {
                //下标等于图片列表长度说明已滚动到最后一张图片,重置下标
                if ((++mImageIndex) == mImageViews.length + 1) {
                    mImageIndex = 1;
                }
                mBannerPager.setCurrentItem(mImageIndex);
            }
        }
    };

    /**
     * 轮播图片状态监听器
     *
     * @author minking
     */
    private final class GuidePageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE)
                startImageTimerTask(); // 开始下次计时
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int index) {

            if (index == 0 || index == mImageViews.length + 1) {
                return;
            }
            // 设置图片滚动指示器背景
            mImageIndex = index;
            index -= 1;
            finalPos=index;
            mImageViews[index].setBackgroundResource(R.mipmap.news_point_blue);
            for (int i = 0; i < mImageViews.length; i++) {
                if (index != i) {
                    mImageViews[i].setBackgroundResource(R.mipmap.dot_blur);
                }
            }

        }

    }
    
    private class ImageCycleAdapter extends PagerAdapter {

        /**
         * 图片视图缓存列表
         */
        private ArrayList<View> mImageViewCacheList;

        /**
         * 图片资源列表
         */
        private List<Map<String, Object>> mAdList = new ArrayList<Map<String, Object>>();

        private int po;

        public ImageCycleAdapter(List<Map<String, Object>> infoList, int po) {
            mAdList = infoList;
            this.po = po;
            mImageViewCacheList = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return mAdList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = null;
            if (!mAdList.isEmpty()) {
                int pos = position % infolists.size();
                if (position < 0) {
                    pos = infolists.size() + pos;
                }
                Object imageUrl = mAdList.get(pos);
                if (mImageViewCacheList.isEmpty()) {
                    view = new MyImagerFragment(activity, imageUrl, pos, po, mAdList, new CallBackListener() {
                        @Override
                        public void callBack(long code, long stat, Object value1, Object value2) {
                            preview(finalPos);
                        }
                    }).onCreateView(LayoutInflater.from(activity), container, new Bundle());
                }
                else {
                    view = mImageViewCacheList.remove(0);
                }
                view.setTag(imageUrl);
                container.addView(view);
            }

            return view;
        }
        private void preview(int po) {
            ArrayList<String> list = new ArrayList<>();
            if (infolists != null) {
                for (Map<String, Object> ma : infolists) {
                    String url = ma.get("ad_img") + "";
                    list.add(url);
                }
            }
            Intent in=new Intent(activity, PreviewActivity.class);
            in.putExtra(PreviewActivity.DATAS_PHOTO_INDEX,po);
            in.putExtra(PreviewActivity.DATAS_PHOTO_URLS,list);
            activity.startActivity(in);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
            mImageViewCacheList.add(view);
        }

    }

}
