package com.zj.example.custombehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Title: Demo2Behavior
 * Description:
 * Copyright:Copyright(c)2016
 * CreateTime:17/4/8  13:10
 *
 * @author 郑炯
 * @version 1.0
 */
public class Demo2Behavior extends CoordinatorLayout.Behavior<TextView> {
    private int mFrameMaxHeight = 100;
    private int mStartY;

    public Demo2Behavior() {
    }

    public Demo2Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        //记录开始的Y坐标  也就是toolbar起始Y坐标
        if (mStartY == 0) {
            mStartY = (int) dependency.getY();
        }
        //计算toolbar从开始移动到最后的百分比
        float percent = dependency.getY() / mStartY;    //改变child的坐标(从消失，到可见)
        child.setY(child.getHeight() * (1 - percent) - child.getHeight());//child就是textview
        return true;
    }
}
