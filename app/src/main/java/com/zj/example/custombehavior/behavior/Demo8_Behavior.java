package com.zj.example.custombehavior.behavior;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * CreateTime: 17/6/16  10:18
 *
 * @author 郑炯
 */
public class Demo8_Behavior extends CoordinatorLayout.Behavior {
    private IAnimateListener mListener;

    public interface IAnimateListener{
        void show();
        void hide();
    }

    public Demo8_Behavior() {
    }

    public Demo8_Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return ViewCompat.SCROLL_AXIS_VERTICAL == nestedScrollAxes;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target,
                               int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        if (dyConsumed > 0 || dyUnconsumed > 0) {
            //上滑
            System.out.println("------------->上滑");
            ViewCompat
                    .animate(child)
                    .scaleX(0)
                    .scaleY(0)
                    .setDuration(300)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .start();

            if (mListener != null) {
                mListener.hide();
            }

        } else if (dyConsumed < 0 || dyConsumed < 0) {
            //下滑
            System.out.println("------------->下滑");
            ViewCompat
                    .animate(child)
                    .scaleX(1)
                    .scaleY(1)
                    .setDuration(300)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .start();

            if (mListener != null) {
                mListener.show();
            }
        }
    }

    public void setListener(IAnimateListener listener) {
        this.mListener = listener;
    }

    public static Demo8_Behavior from(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params)
                .getBehavior();
        if (!(behavior instanceof Demo8_Behavior)) {
            throw new IllegalArgumentException(
                    "The view is not associated with Demo8_Behavior");
        }
        return (Demo8_Behavior) behavior;
    }
}
