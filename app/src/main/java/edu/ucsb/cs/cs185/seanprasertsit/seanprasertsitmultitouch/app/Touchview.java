package edu.ucsb.cs.cs185.seanprasertsit.seanprasertsitmultitouch.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by SilverWolf on 5/20/14.
 */
public class Touchview extends ImageView {

    Bitmap bitmap;
    Paint paint = new Paint();
    Point point = new Point();

    public Touchview(Context context) {
        super(context);
    }

    public Touchview(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public Touchview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Matrix matrix=new Matrix();
        //this.setScaleType(ScaleType.MATRIX);   //required
        //this.setImageMatrix(matrix);
        //canvas.drawBitmap(bitmap, 0, 0, paint);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(point.x, point.y, 20, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                point.x = event.getX();
                point.y = event.getY();

        }
        invalidate();
        return true;
    }

    public Bitmap createBitmap(File f) {
        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
        return bitmap;
    }

    class Point {
        float x, y;
    }
}
