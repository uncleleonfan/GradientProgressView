package com.leon.gradientprogressview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class GradientProgressView extends View {

    private int mProgress = 0;
    private Paint mPaint = new Paint();
    private RectF mRectF = new RectF();


    public GradientProgressView(Context context) {
        this(context, null);
    }

    public GradientProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        int cx = w / 2;
        int cy = h / 2;

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = w - getPaddingRight();
        int bottom = h - getPaddingBottom();
        mRectF.set(left, top, right, bottom);

        int[] colors = {Color.BLUE, Color.GREEN};
        Shader shader = new SweepGradient(cx, cy, colors, null);
        mPaint.setShader(shader);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int startAngle = - 90;
        int sweepAngle = 360;
        canvas.drawArc(mRectF, startAngle, sweepAngle, false, mPaint);
    }
}
