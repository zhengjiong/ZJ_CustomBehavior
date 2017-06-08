package com.zj.example.custombehavior.tab;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Title: Demo1Behavior
 * Description:
 * Copyright:Copyright(c)2016
 * CreateTime:17/4/8  12:44
 *
 * @author 郑炯
 * @version 1.0
 */
public class Demo1Behavior extends CoordinatorLayout.Behavior {
    public Demo1Behavior() {
    }

    //必须重写带双参的构造器，因为从xml反射需要调用。
    public Demo1Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * behavior基础概念
     * <p>
     * 其实Behavior就是一个应用于View的观察者模式，一个View跟随者另一个View的变化而变化，
     * 或者说一个View监听另一个View。
     * <p>
     * 在Behavior中，被观察的View, 也就是事件源被称为denpendcy，而观察的View，则被称为child。
     */


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        //layoutDependsOn() 代表寻找被观察View

        System.out.println("child -> " + child.toString());
        System.out.println("dependency -> " + dependency.toString());

        //告知监听的事件源是Button
        return dependency instanceof Button;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //当 dependency(Button)变化的时候，可以对child(TextView)进行操作

        System.out.println("onDependentViewChanged x=" + dependency.getX() + " ,y=" + dependency.getY());
        child.setX(dependency.getX());
        child.setY(dependency.getY() + 200);
        return true;
    }

    /**
     * 当coordinatorLayout 的子View试图开始嵌套滑动的时候被调用。当返回值为true的时候表明
     * coordinatorLayout 充当nested scroll parent 处理这次滑动，需要注意的是只有当返回值为true
     * 的时候，Behavior 才能收到后面的一些nested scroll 事件回调（如：onNestedPreScroll、onNestedScroll等）
     * 这个方法有个重要的参数nestedScrollAxes，表明处理的滑动的方向。
     *
     * @param coordinatorLayout 和Behavior 绑定的View的父CoordinatorLayout
     * @param child             和Behavior 绑定的View
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes  嵌套滑动 应用的滑动方向，看 {@link ViewCompat#SCROLL_AXIS_HORIZONTAL},
     *                          {@link ViewCompat#SCROLL_AXIS_VERTICAL}
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        System.out.println("onStartNestedScroll nestedScrollAxes=" + nestedScrollAxes
                + " ,child=" + child + " ,directTargetChild="
                + directTargetChild + " ,target=" + target);
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }
}
