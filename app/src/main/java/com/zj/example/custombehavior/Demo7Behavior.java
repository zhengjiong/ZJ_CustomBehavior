package com.zj.example.custombehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zj on 2017/4/24.
 */

public class Demo7Behavior extends CoordinatorLayout.Behavior {
    int offsetTotal = 0;
    boolean scrolling = false;


    public Demo7Behavior(Context context, AttributeSet attrs) {
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
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        //进行滑动事件处理
        System.out.println("dyConsumed = " + dyConsumed + "  ,dyUnconsumed = " + dyUnconsumed + " ," + target.getY() + " ," + target.getHeight());
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

    /**
     * 你可以捕获对内部View的fling事件，如果return true则表示拦截掉内部View的事件。
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX
     * @param velocityY
     * @param consumed 你可以捕获对内部View的fling事件，如果return true则表示拦截掉内部View的事件。
     * @return
     */
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        //当进行快速滑动
        //return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);

        offset(child, (int) velocityY);

        return true;
    }
}
