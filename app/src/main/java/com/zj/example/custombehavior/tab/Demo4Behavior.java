package com.zj.example.custombehavior.tab;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.zj.example.custombehavior.R;

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
public class Demo4Behavior extends CoordinatorLayout.Behavior<CircleImageView> {
    private int mStartY;

    public Demo4Behavior() {
    }

    public Demo4Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
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
            mStartY = (int) dependency.getY();
        }
        System.out.println("startY = " + mStartY + " ,getY=" + dependency.getY());
        //计算Textview从开始移动到最后的百分比
        float percent = dependency.getY() / mStartY;    //改变child的坐标(从消失，到可见)
        child.setY(child.getHeight() * (1 - percent) - child.getHeight());//child就是textview
        return true;
    }
}
