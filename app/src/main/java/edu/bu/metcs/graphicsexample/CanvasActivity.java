package edu.bu.metcs.graphicsexample;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;

public class CanvasActivity extends AppCompatActivity {
    private CustomCanvasView myCanvas = null;
    private CustomDrawableView myOval = null;
    private String TAG = this.getClass().getSimpleName();
    private Button clearBtn, addCanvasBtn, addOvalBtn;
    private LinearLayout mLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_canvas);

        clearBtn = findViewById (R.id.clearCanvas);
        clearBtn.setEnabled (false);

        addCanvasBtn = findViewById (R.id.addCanvas);
        addOvalBtn = findViewById (R.id.drawOval);
        mLinearLayout = (LinearLayout) findViewById (R.id.canvasLinearLayout);

    }

    public void drawOval(View v) {

        if (myOval != null)
            return;


        //dynamically add a view that draw a custom shape

        myOval = new CustomDrawableView (this);
        myOval.setLayoutParams (new LinearLayout.LayoutParams // width / height
                (LinearLayout.LayoutParams.WRAP_CONTENT, 150));

        mLinearLayout.addView (myOval);
        addOvalBtn.setEnabled (false);
    }

    public void addCanvas(View v){
        if (myCanvas != null)
            return;


        // dynamically add a view that set up a canvas to draw.
        myCanvas = new CustomCanvasView(this);
        myCanvas.setLayoutParams(new LinearLayout.LayoutParams // width / height
               (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        mLinearLayout.addView(myCanvas);

        myCanvas.setOnTouchListener(new CustomCanvasView.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent e) {
                return ((CustomCanvasView) v).myOnTouchEvent(e);
            }
        });

        clearBtn.setEnabled (true);
        addCanvasBtn.setEnabled (false);
        addOvalBtn.setEnabled (false);

    }

    public void clearCanvas(View v) {
        myCanvas.clearCanvas();
    }


    class CustomDrawableView extends View {
        private ShapeDrawable mDrawable;

        public CustomDrawableView(Context context) {
            super(context);
            int x = 10;
            int y = 10;
            int width = 600;
            int height = 100;

            //If no bounds set, shape not drawn. If no color set, defaults to black.
            mDrawable = new ShapeDrawable(new OvalShape());
            mDrawable.getPaint().setColor(0xFFFF0000);
            mDrawable.setBounds(x, y, x + width, y + height);

        }

        protected void onDraw(Canvas canvas) {
             mDrawable.draw(canvas);
            /*
            // we can also use paint directly draw on canvas
            Paint myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            myPaint.setColor(0xFFFF0000);
            myPaint.setStyle(Paint.Style.FILL);
            canvas.drawOval(new RectF(10, 10, 600, 100), myPaint);
            */

        }
    }




    //adapted from https://examples.javacodegeeks.com/android/
    // core/graphics/canvas-graphics/android-canvas-example/
    class CustomCanvasView extends View {
        private Path mPath;
        private Paint mPaint;
        private float mX, mY;

        public CustomCanvasView(Context context) {
            super(context);
            mPath = new Path();
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.BLUE);
            mPaint.setStrokeWidth(4f);

        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawPath(mPath, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);

        }

        private void startTouch(float x, float y) {
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void upTouch() {
            mPath.lineTo(mX, mY);
        }

        private void moveTouch(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(x - mY);

            if (dx >= 5 || dy >= 5) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }
        }

        public void clearCanvas() {
            mPath.reset();
            invalidate();
        }


        public boolean myOnTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startTouch(x, y);
                    invalidate();
                    Log.d(TAG, "down " + x + " " + y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    moveTouch(x, y);
                    invalidate();
                    Log.d(TAG, "move " + x + " " + y);
                    break;
                case MotionEvent.ACTION_UP:
                    upTouch();
                    invalidate();
                    Log.d(TAG, "up " + x + " " + y);
                    break;
            }
            return true;
        }
    }
}
