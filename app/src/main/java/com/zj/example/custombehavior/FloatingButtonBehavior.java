package com.zj.example.custombehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;

/**
 * Title: FloatingButtonBehavior
 * Description:
 * Copyright:Copyright(c)2016
 * Company: 博智维讯信息技术有限公司
 * CreateTime:17/4/9  23:44
 *
 * @author 郑炯
 * @version 1.0
 */
public class FloatingButtonBehavior extends FloatingActionButton.Behavior {
    private boolean isAnimatingOut = false;

    public FloatingButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyConsumed > 0 && dyUnconsumed == 0) {
            System.out.println("上滑中。。。");
        }
        if (dyConsumed == 0 && dyUnconsumed > 0) {
            System.out.println("到边界了还在上滑。。。");
        }
        if (dyConsumed < 0 && dyUnconsumed == 0) {
            System.out.println("下滑中。。。");
        }
        if (dyConsumed == 0 && dyUnconsumed < 0) {
            System.out.println("到边界了，还在下滑。。。");
        }

        if (((dyConsumed > 0 && dyUnconsumed == 0) || (dyConsumed == 0 && dyUnconsumed > 0))
                && child.getVisibility() != View.VISIBLE) {// 显示
            scaleShow(child, null);
        }

        if (((dyConsumed < 0 && dyUnconsumed == 0) || (dyConsumed == 0 && dyUnconsumed < 0))
                && child.getVisibility() != View.GONE && !isAnimatingOut) {
            scaleHide(child, viewPropertyAnimatorListener);
        }
    }

    public static void scaleShow(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        view.setVisibility(View.VISIBLE);
        ViewCompat.animate(view)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .alpha(1.0f)
                .setDuration(800)
                .setListener(viewPropertyAnimatorListener)
                .setInterpolator(new FastOutSlowInInterpolator())
                .start();
    }

    // 隐藏view
    public static void scaleHide(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        ViewCompat.animate(view)
                .scaleX(0.0f)
                .scaleY(0.0f)
                .alpha(0.0f)
                .setDuration(800)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setListener(viewPropertyAnimatorListener)
                .start();
    }

    ViewPropertyAnimatorListener viewPropertyAnimatorListener = new ViewPropertyAnimatorListener() {

        @Override
        public void onAnimationStart(View view) {
            isAnimatingOut = true;
        }

        @Override
        public void onAnimationEnd(View view) {
            isAnimatingOut = false;
            view.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationCancel(View arg0) {
            isAnimatingOut = false;
        }
    };
}
