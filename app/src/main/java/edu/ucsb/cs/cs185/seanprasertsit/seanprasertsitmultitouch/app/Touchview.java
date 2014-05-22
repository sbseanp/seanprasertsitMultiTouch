package edu.ucsb.cs.cs185.seanprasertsit.seanprasertsitmultitouch.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.widget.ImageView;

public class Touchview extends ImageView {

    // Before and after matrices
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();

    // 3 motion options. Found on StackOverflow
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;

    // Starting and mid points. Also rotation variable. Also found on Stackoverflow
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private float oldRot = 0f;
    private float newRot = 0f;
    private float[] lastEvent = null;

    // Small point class for drawing
    class Point {
        float x, y;
    }

    // Initiate variables for canvas drawing
    Paint paint = new Paint();
    Point point = new Point();

    // Constructors that don't do anything
    public Touchview(Context context) {
        super(context);
    }

    public Touchview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Touchview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // onDraw function for the Touchview subclass
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(point.x, point.y, 30, paint);
        this.setImageMatrix(matrix);
    }

    // Simple reset matrix function
    public void resetMatrix() {
        matrix.reset();
    }

    // All touch handlers
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                lastEvent = null;
                point.x = event.getX();
                point.y = event.getY();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                oldRot = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    matrix.postTranslate(dx, dy);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = (newDist / oldDist);
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                    if (lastEvent != null && event.getPointerCount() == 2) {//was 3
                        newRot = rotation(event);
                        float r = newRot - oldRot;
                        float[] values = new float[9];
                        matrix.getValues(values);
                        matrix.postRotate(r, mid.x, mid.y);
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    // Calculate space between two fingers
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    // Calculate midpoint between two fingers
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    // Calculate the degree to be rotated by.
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }
}
