package org.tensorflow.demo.env;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import org.tensorflow.demo.OverlayView;

import java.util.LinkedList;
import java.util.List;

public class RippleProgress extends View {

    public static final int BUBBLE_MAX_RADIUS_IN_DP = 32;
    public static final int BUBBLE_MIN_RADIUS_IN_DP = 4;
    public static final int ANIMATION_TIME = 800; // in milliseconds
    private static final int REPEAT_COUNT = 1800;
    public static final String color = "#1976D2";
    public static final String fadedColor = "#001976D2";
    private Resources resources;
    private float mBubbleMaxRadiusInPx;
    private float mBubbleMinRadiusInPx;
    private Paint mVariablePaint;
    private Paint mPaint;

    private float mFadingCircleRadius;
    private float mColoredCircleRadius;


    public RippleProgress(Context context) {
        super(context);
        init(context);
    }

    public RippleProgress(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RippleProgress(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        resources = context.getResources();
        mBubbleMaxRadiusInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BUBBLE_MAX_RADIUS_IN_DP, resources.getDisplayMetrics());
        mBubbleMinRadiusInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BUBBLE_MIN_RADIUS_IN_DP, resources.getDisplayMetrics());

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor(color));
        mColoredCircleRadius = mBubbleMinRadiusInPx;

        mVariablePaint = new Paint();
        mVariablePaint.setStyle(Paint.Style.FILL);
        mVariablePaint.setColor(Color.parseColor(color));
        mFadingCircleRadius = mBubbleMinRadiusInPx;
        startAnimating();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //fading circle
        canvas.drawCircle(mBubbleMaxRadiusInPx,mBubbleMaxRadiusInPx,mFadingCircleRadius,mVariablePaint);
        //draw colored circle
        canvas.drawCircle(mBubbleMaxRadiusInPx,mBubbleMaxRadiusInPx,mColoredCircleRadius,mPaint);
    }

    private void startAnimating(){

        final ValueAnimator colorAnimator = ValueAnimator.ofArgb(Color.parseColor(color),Color.parseColor(fadedColor));
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mVariablePaint.setColor((Integer) valueAnimator.getAnimatedValue());
            }
        });

        colorAnimator.setRepeatCount(REPEAT_COUNT);
        colorAnimator.setRepeatMode(ValueAnimator.RESTART);
        colorAnimator.setDuration(ANIMATION_TIME);
        colorAnimator.setInterpolator(new DecelerateInterpolator());
        colorAnimator.start();


        final ValueAnimator radiusAnimator = ValueAnimator.ofFloat(mBubbleMinRadiusInPx, mBubbleMaxRadiusInPx);
        radiusAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFadingCircleRadius = (float) valueAnimator.getAnimatedValue();
                mColoredCircleRadius = mFadingCircleRadius/3;
                invalidate();
            }
        });
        radiusAnimator.setRepeatMode(ValueAnimator.RESTART);
        radiusAnimator.setRepeatCount(REPEAT_COUNT);
        radiusAnimator.setDuration(ANIMATION_TIME);
        radiusAnimator.setInterpolator(new DecelerateInterpolator());
        radiusAnimator.start();

    }
}
