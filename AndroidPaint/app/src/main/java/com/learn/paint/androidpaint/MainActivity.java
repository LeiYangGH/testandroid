package com.learn.paint.androidpaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    DrawingView dv ;
    private Paint mPaint;
    private int drawType=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dv = new DrawingView(this);
        setContentView(dv);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
    }

    public class DrawingView extends View {

        public int width;
        public  int height;
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint   mBitmapPaint;
        Context context;

        public DrawingView(Context c) {
            super(c);
            context=c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);


                                  if(drawType==1) {
                                    //  mCanvas.drawLine(0, 0, 100, 100, mPaint);
                                      mCanvas.drawLine(mX0, mY0, mX, mY, mPaint);
                                  }
                                                else if(drawType==4) {
                                      canvas.drawPath( mPath,  mPaint);
            }
        }

        private float mX, mY;
        private float mX0, mY0;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {

            if(drawType==1) {
                mX0 = x;
                mY0 = y;
            }
            else if(drawType==4) {
                mPath.reset();
                mPath.moveTo(x, y);
                mX = x;
                mY = y;
            }


        }

        private void touch_move(float x, float y) {

            if(drawType==1) {
                float dx = Math.abs(x - mX0);
                float dy = Math.abs(y - mY0);
                if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {

                    mX = x;
                    mY = y;
                }
            }
            else if(drawType==4) {
                float dx = Math.abs(x - mX);
                float dy = Math.abs(y - mY);
                if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                    mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                    mX = x;
                    mY = y;
                }
            }
        }

        private void touch_up() {
            if(drawType==1) {

            }
            else if(drawType==4) {
                mPath.lineTo(mX, mY);
                // commit the path to our offscreen
                mCanvas.drawPath(mPath, mPaint);
                // kill this so we don't double draw
                mPath.reset();
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    drawType++;
                    if(drawType>4)
                        drawType=1;
                    touch_start(x, y);
                    if(drawType==4) {
                        invalidate();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    if(drawType==4) {
                        invalidate();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                   invalidate();
                    break;
            }
            return true;
        }
    }
}
