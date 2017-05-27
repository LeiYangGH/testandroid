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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;

public class MainActivity extends AppCompatActivity implements ColorPickerDialogListener,OnClickListener  {
    DrawingView dv ;
    private Paint mPaint;
    private int drawType=1;
    private RadioButton rdbLine;
    private RadioButton rdbRect;
    private RadioButton rdbCircle;
    private RadioButton rdbErase;
    private Button btnColor;
    private void resetPaint()
    {
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        ColorPickerDialog.newBuilder()
                .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                .setAllowPresets(false)
                .setDialogId(0)
                .setColor(Color.BLACK)
                .setShowAlphaSlider(true)
                .show(this);
    }

    @Override public void onDialogDismissed(int dialogId) {

    }
    @Override public void onColorSelected(int dialogId, int color) {
        switch (dialogId) {
            case 0:
                // We got result from the dialog that is shown when clicking on the icon in the action bar.
                Toast.makeText(MainActivity.this, "Selected Color: #" + Integer.toHexString(color), Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.buttomLayout);
        this.rdbLine=(RadioButton) findViewById(R.id.rdbLine);
        this.rdbRect=(RadioButton) findViewById(R.id.rdbRect);
        this.rdbCircle=(RadioButton) findViewById(R.id.rdbCircle);
        this.rdbErase=(RadioButton) findViewById(R.id.rdbErase);
        this.btnColor=(Button) findViewById(R.id.btnColor);
        btnColor.setOnClickListener(this);
        rdbLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawType=1;
                rdbRect.setChecked(false);
                rdbCircle.setChecked(false);
                rdbErase.setChecked(false);
            }
        });
        rdbRect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawType=2;
                rdbLine.setChecked(false);
                rdbCircle.setChecked(false);
                rdbErase.setChecked(false);
            }
        });
        rdbCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawType=3;
                rdbLine.setChecked(false);
                rdbRect.setChecked(false);
                rdbErase.setChecked(false);
            }
        });
        rdbErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawType=4;
                rdbLine.setChecked(false);
                rdbRect.setChecked(false);
                rdbCircle.setChecked(false);

            }
        });


        dv = new DrawingView(this);
if(linearLayout==null) {
    setContentView(dv);
}
else {
    linearLayout.addView(dv);
    dv.width=200;
    dv.height=200;
}
//*/

        mPaint = new Paint();
        /*
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
        */
        resetPaint();
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
                  resetPaint();
                  mCanvas.drawLine(mX0, mY0, mX, mY, mPaint);
              }
          else  if(drawType==2) {
                  resetPaint();
                mCanvas.drawRect(mX0, mY0, mX, mY, mPaint);
            }
              else  if(drawType==3) {
                  resetPaint();
                  double r=Math.sqrt((mX-mX0)*(mX-mX0)+(mY-mY0)*(mY-mY0));
                  mCanvas.drawCircle(mX0, mY0,  (float) r, mPaint);
              }
             else if(drawType==4) {
                  mPaint.setColor(Color.WHITE);
                  mPaint.setStrokeWidth(200);
               canvas.drawPath( mPath,  mPaint);
            }
        }

        private float mX, mY;
        private float mX0, mY0;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {

            if(drawType==1 ||drawType==2 ||drawType==3) {
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

            if(drawType==1 ||drawType==2 ||drawType==3) {
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
