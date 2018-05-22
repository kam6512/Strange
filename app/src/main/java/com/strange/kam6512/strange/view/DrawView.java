package com.strange.kam6512.strange.view;

import android.content.Context;
import android.graphics.*;
import android.graphics.Path.Direction;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

//https://stackoverflow.com/questions/16650419/draw-in-canvas-by-finger-android
public class DrawView extends View {

    private boolean isDrawed = false;

    private static final float TOUCH_TOLERANCE = 4;

    private static int SIZE = 0;
    private float x, y;

    private Canvas canvas;
    private Bitmap paper;
    private Paint paint;

    private Brush brush;
    private Cursor cursor;

    private Brush brush1;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public DrawView(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        paint = new Paint(Paint.DITHER_FLAG);
        brush = new Brush();
        cursor = new Cursor();
        brush1 = new Brush();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        paper = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(paper);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size, size);
        SIZE = size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(paper, 0, 0, paint);
        brush.drawPathOnCanvas(canvas);
        cursor.drawPathOnCanvas(canvas);
        brush1.drawPathOnCanvas(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isDrawed) return true;
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                endTouch();
                invalidate();
                isDrawed = true;
                break;
        }
        return true;
    }


    private void startTouch(float x, float y) {
        brush.start(x, y);
        movePosition(x, y);
        brush1.start(SIZE - x, SIZE - y);
    }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - this.x);
        float dy = Math.abs(y - this.y);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            brush.move(this.x, this.y, (x + this.x) / 2, (y + this.y) / 2);
            cursor.moveCursor(x, y);
            movePosition(x, y);
        }
    }

    private void endTouch() {
        cursor.resetCursor();
        brush.end(this.x, this.y);
        brush.drawPathOnCanvas(canvas);
        brush.reset();
    }

    private void movePosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    void reset() {
        isDrawed = false;
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
    }

    private class Brush {
        private Paint brushPaint;
        private Path brushPath;

        Brush() {

            brushPath = new Path();
            initializeBrushPaint();
        }

        private void initializeBrushPaint() {
            brushPaint = new Paint();
            brushPaint.setAntiAlias(true);
            brushPaint.setDither(true);
            brushPaint.setColor(Color.GREEN);
            brushPaint.setStyle(Paint.Style.STROKE);
            brushPaint.setStrokeJoin(Paint.Join.ROUND);
            brushPaint.setStrokeCap(Paint.Cap.ROUND);
            brushPaint.setStrokeWidth(12);
        }

        void drawPathOnCanvas(Canvas canvas) {
            canvas.drawPath(brushPath, brushPaint);
        }

        void start(float x, float y) {
            brushPath.reset();
            brushPath.moveTo(x, y);
        }

        void move(float x1, float y1, float x2, float y2) {
            brushPath.quadTo(x1, y1, x2, y2);
        }

        void end(float x, float y) {
            brushPath.lineTo(x, y);
        }

        void reset() {
            brushPath.reset();
        }
    }

    private class Cursor {
        private int radius;
        private Direction direction;
        private Path circlePath;
        private Paint circlePaint;

        Cursor() {
            radius = 30;
            direction = Direction.CW;
            circlePath = new Path();
            initializeCursorPaint();
        }

        private void initializeCursorPaint() {
            circlePaint = new Paint();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLACK);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);
        }

        void drawPathOnCanvas(Canvas canvas) {
            canvas.drawPath(circlePath, circlePaint);
        }


        void moveCursor(float x, float y) {
            resetCursor();
            circlePath.addCircle(x, y, radius, direction);
        }

        void resetCursor() {
            circlePath.reset();
        }
    }
}
