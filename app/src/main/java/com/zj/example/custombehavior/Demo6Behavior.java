package com.zj.example.custombehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zj on 2017/4/24.
 */

public class Demo6Behavior extends CoordinatorLayout.Behavior {
    int offsetTotal = 0;
    boolean scrolling = false;


    public Demo6Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        System.out.println(dependency.getY());

        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        //这里返回true，才会接受到后续滑动事件。

        //return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);

        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        //进行滑动事件处理
        System.out.println("dyConsumed = " + dyConsumed + "  ,dyUnconsumed = " + dyUnconsumed + " ," + target.getY() + " ," + target.getHeight());
        //super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        offset(child, dyConsumed);
    }

    public void offset(View child,int dy){
        int old = offsetTotal;
        int top = offsetTotal - dy;
        top = Math.max(top, -child.getHeight());
        top = Math.min(top, 0);
        offsetTotal = top;
        if (old == offsetTotal){
            scrolling = false;
            return;
        }
        int delta = offsetTotal-old;
        child.offsetTopAndBottom(delta);
        scrolling = true;
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        //当进行快速滑动
        //return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);

        offset(child, (int) velocityY);

        return true;
    }
}
