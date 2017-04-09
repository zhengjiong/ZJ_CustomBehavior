package com.zj.example.custombehavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Title: Demo2Behavior
 * Description:
 * Copyright:Copyright(c)2016
 * CreateTime:17/4/8  13:10
 *
 * @author 郑炯
 * @version 1.0
 */
public class Demo5Behavior extends CoordinatorLayout.Behavior<CircleImageView> {
    private int mMinSize;
    private int mStartX;
    private int mStartY;
    private LinearInterpolator mInterpolator = new LinearInterpolator();

    public Demo5Behavior() {
    }

    public Demo5Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMinSize = TypedValue.complexToDimensionPixelSize(30, context.getResources().getDisplayMetrics());
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        if (dependency.getId() == R.id.title && dependency instanceof TextView) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {
        //记录开始的Y坐标  也就是toolbar起始Y坐标
        if (mStartY == 0) {
            mStartX = dependency.getMeasuredWidth();
            mStartY = (int) dependency.getY();
        }
        System.out.println("startY = " + mStartY + " ,getY=" + dependency.getY());
        //计算Textview从开始移动到最后的百分比
        float percent = dependency.getY() / mStartY;

        child.setX((mStartX - child.getMeasuredWidth()) - ((mStartX - child.getMeasuredWidth()) / 2 * (1 - percent)));
        child.setY(dependency.getY());
        return true;
    }
}
