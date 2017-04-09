package com.zj.example.custombehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
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
public class Demo3Behavior extends CoordinatorLayout.Behavior<TextView> {
    private int mStartY;

    public Demo3Behavior() {
    }

    public Demo3Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        if (dependency.getId() == R.id.title && dependency instanceof TextView) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        //记录开始的Y坐标  也就是toolbar起始Y坐标
        if (mStartY == 0) {
            mStartY = (int) dependency.getY();
        }
        System.out.println("startY = " + mStartY + " ,getY=" + dependency.getY());
        //计算Textview从开始移动到最后的百分比
        float percent = dependency.getY() / mStartY;    //改变child的坐标(从消失，到可见)
        child.setY(child.getHeight() * (1 - percent) - child.getHeight());//child就是textview
        return true;
    }
}
