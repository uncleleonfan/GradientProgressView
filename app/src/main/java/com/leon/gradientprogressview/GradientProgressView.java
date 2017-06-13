package com.leon.gradientprogressview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class GradientProgressView extends View {
    private static final String TAG = "GradientProgressView";

    private static final int DEFAULT_ANIMATION_DURATION = 3000;
    private static final int[] DEFAULT_COLORS = {Color.BLUE, Color.GREEN};

    private static int[] mGradientColors = DEFAULT_COLORS;

    private int mProgress = 0;
    private int mDrawProgress = 0;
    private int mDuration = DEFAULT_ANIMATION_DURATION;

    private Paint mGradientCirclePaint = new Paint();
    private Paint mBackgroundCirclePaint = new Paint();
    private Paint mTextPaint = new Paint();

    private RectF mRectF = new RectF();
    private Rect mTextBound = new Rect();
    private int mCx;
    private int mCy;

    public GradientProgressView(Context context) {
        this(context, null);
    }

    public GradientProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTextPaint();
    }

    private void initTextPaint() {
        mTextPaint.setAntiAlias(true);
        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 22, getResources().getDisplayMetrics());
        mTextPaint.setTextSize(textSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        initRect(w, h);
        mCx = w / 2;
        mCy = h / 2;
        initCirclePaint();
    }

    private void initRect(int w, int h) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = w - getPaddingRight();
        int bottom = h - getPaddingBottom();
        mRectF.set(left, top, right, bottom);
    }

    private void initCirclePaint() {
        Shader shader = new SweepGradient(mCx, mCy, mGradientColors, null);
        mGradientCirclePaint.setShader(shader);
        mGradientCirclePaint.setStyle(Paint.Style.STROKE);
        mGradientCirclePaint.setStrokeWidth(30);
        mGradientCirclePaint.setAntiAlias(true);
        mGradientCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        mBackgroundCirclePaint.setStyle(Paint.Style.STROKE);
        mBackgroundCirclePaint.setStrokeWidth(30);
        mBackgroundCirclePaint.setAntiAlias(true);
        mBackgroundCirclePaint.setColor(Color.LTGRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(mRectF, 0, 360, false, mBackgroundCirclePaint);

        float startAngle = - 90;
        //ValueAnimator实现
//        float sweepAngle = mDrawProgress * 1.0f / 100 * 360;
//        String progressString = String.valueOf(mDrawProgress);
        //ObjectAnimator实现
        float sweepAngle = mProgress * 1.0f / 100 * 360;
        String progressString = String.valueOf(mProgress);
        mTextPaint.getTextBounds(progressString, 0, progressString.length(), mTextBound);
        float x = mCx - mTextBound.width() / 2;
        float y = mCy + mTextBound.height() / 2;
        canvas.drawText(progressString, x, y, mTextPaint);
        //绘制进度
        canvas.drawArc(mRectF, startAngle, sweepAngle, false, mGradientCirclePaint);
    }

/*    public void setProgress(int progress) {
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
    }*/

    /**
     * 供属性动画使用
     */
    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    public void startAnimation(int degree) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "progress", 0, degree);
        objectAnimator.setDuration(mDuration);
        objectAnimator.start();
    }



    public static int[] getGradientColors() {
        return mGradientColors;
    }

    public static void setGradientColors(int[] mGradientColors) {
        GradientProgressView.mGradientColors = mGradientColors;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }
}
