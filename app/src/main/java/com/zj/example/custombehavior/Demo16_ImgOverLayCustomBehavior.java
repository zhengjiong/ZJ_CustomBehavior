package com.zj.example.custombehavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.zj.example.custombehavior.util.ColorUtils;

/**
 * Created by zhengjiong
 * date: 2018/8/12 22:27
 */
public class Demo16_ImgOverLayCustomBehavior extends CoordinatorLayout.Behavior<View> {

    public Demo16_ImgOverLayCustomBehavior() {
    }

    public Demo16_ImgOverLayCustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (dependency.getId() == R.id.appbar) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        AppBarLayout appBar = (AppBarLayout) dependency;
        TextView title1 = appBar.findViewById(R.id.title1);
        TextView title2 = appBar.findViewById(R.id.title2);
        int offset = Math.abs(getAppBarLayoutOffset(appBar));
        int totalScrollRange = appBar.getTotalScrollRange();


        float alpha = offset * 1F / totalScrollRange;
        System.out.println("offset=" + offset + " | getTotalScrollRange=" + totalScrollRange + " | alpha=" + alpha);
        child.setAlpha(alpha);

        int textColor = ColorUtils.caculateColor(
                ContextCompat.getColor(parent.getContext(), android.R.color.white),
                ContextCompat.getColor(parent.getContext(), android.R.color.black),
                alpha);
        title1.setTextColor(textColor);
        title2.setTextColor(textColor);
        return true;
    }

    private static int getAppBarLayoutOffset(AppBarLayout abl) {
        final CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) abl.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            return ((AppBarLayout.Behavior) behavior).getTopAndBottomOffset();
        }
        return 0;
    }
}
