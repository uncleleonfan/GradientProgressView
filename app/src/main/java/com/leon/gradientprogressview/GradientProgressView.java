package com.leon.gradientprogressview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GradientProgressView extends View {
    private static final String TAG = "GradientProgressView";

    private static final int DEFAULT_ANIMATION_DURATION = 3000;

    private int mProgress = 0;
    private int mDrawProgress = 0;
    private int mDuration = DEFAULT_ANIMATION_DURATION;
    private Paint mPaint = new Paint();
    private RectF mRectF = new RectF();


    public GradientProgressView(Context context) {
        this(context, null);
    }

    public GradientProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        initRect(w, h);

        int cx = w / 2;
        int cy = h / 2;
        initPaint(cx, cy);
    }

    private void initRect(int w, int h) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = w - getPaddingRight();
        int bottom = h - getPaddingBottom();
        mRectF.set(left, top, right, bottom);
    }

    private void initPaint(int cx, int cy) {
        int[] colors = {Color.BLUE, Color.GREEN, Color.YELLOW};
        Shader shader = new SweepGradient(cx, cy, colors, null);

        mPaint.setShader(shader);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(30);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: " + mDrawProgress);
        float startAngle = - 90;
        float sweepAngle = mDrawProgress * 1.0f / 100 * 360;
        canvas.drawArc(mRectF, startAngle, sweepAngle, false, mPaint);
    }

    public void setProgress(int progress) {
        mProgress = progress;
    }

    public void startAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mProgress);
        valueAnimator.setDuration(mDuration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDrawProgress = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }


}
