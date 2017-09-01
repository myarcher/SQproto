package com.nimi.sqprotos.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.nimi.sqprotos.R;
import com.nimi.sqprotos.until.Units;

public class DeleteImgView extends ImageView {
    private Bitmap mDeleteBitmap;
    private IDeleteListener mListener;
    public DeleteImgView(Context context) {
        super(context);
        mDeleteBitmap= BitmapFactory.decodeResource(context.getResources(), R.mipmap.delete);
        mDeleteBitmap=Bitmap.createScaledBitmap(mDeleteBitmap, Units.dpTopx(context,20),Units.dpTopx(context,20),true);
    }

    public void setDeleteListener(IDeleteListener listener){
        this.mListener=listener;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mDeleteBitmap,getWidth()-mDeleteBitmap.getWidth(),0,new Paint());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                boolean touchableX = event.getX() > (getWidth() - mDeleteBitmap.getWidth())
                        && (event.getX() < getWidth());
            boolean touchableY= event.getX() > (getHeight() - mDeleteBitmap.getHeight())
                    && (event.getY() < getHeight());
            if(touchableX&&touchableY){
                if (mListener!=null){
                    mListener.delete(this);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public interface IDeleteListener{
        void delete(View v);
    }
}
