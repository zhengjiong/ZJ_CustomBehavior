package com.zj.example.custombehavior;

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

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        System.out.println("child -> "+ child.toString());
        System.out.println("dependency -> "+ dependency.toString());
        return dependency instanceof Button;
    }



    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        System.out.println("onDependentViewChanged x=" + dependency.getX() + " ,y=" + dependency.getY());
        child.setX(dependency.getX());
        child.setY(dependency.getY() + 200);
        return true;
    }
}
