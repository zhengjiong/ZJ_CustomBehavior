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
     *
     * 其实Behavior就是一个应用于View的观察者模式，一个View跟随者另一个View的变化而变化，
     * 或者说一个View监听另一个View。
     *
     * 在Behavior中，被观察的View, 也就是事件源被称为denpendcy，而观察的View，则被称为child。
     *
     */


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        //layoutDependsOn() 代表寻找被观察View

        System.out.println("child -> "+ child.toString());
        System.out.println("dependency -> "+ dependency.toString());

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
}
