package com.zj.example.custombehavior;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zj on 2017/6/10.
 */

public class Demo12_SwipeDismissBehavior extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_12_layout);

        mTextView = (TextView) findViewById(R.id.textview);

        SwipeDismissBehavior swipeDismissBehavior = new SwipeDismissBehavior();

        /**
         * //设置滑动的方向，有3个值
         *
         * 1，SWIPE_DIRECTION_ANY 表示向左像右滑动都可以，
         * 2，SWIPE_DIRECTION_START_TO_END，只能从左向右滑
         * 3，SWIPE_DIRECTION_END_TO_START，只能从右向左滑
         */
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END);

        swipeDismissBehavior.setDragDismissDistance(0.5F);
        swipeDismissBehavior.setSensitivity(0);

        swipeDismissBehavior.setStartAlphaSwipeDistance(4.5F);
        swipeDismissBehavior.setEndAlphaSwipeDistance(0.5F);

        swipeDismissBehavior.setListener(new SwipeDismissBehavior.OnDismissListener() {
            @Override
            public void onDismiss(View view) {
                Toast.makeText(Demo12_SwipeDismissBehavior.this, "onDismiss", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDragStateChanged(int state) {
                if (state == SwipeDismissBehavior.STATE_IDLE) {
                    System.out.println("onDragStateChanged STATE_IDLE");
                } else if (state == SwipeDismissBehavior.STATE_DRAGGING) {
                    System.out.println("onDragStateChanged STATE_DRAGGING");
                } else if (state == SwipeDismissBehavior.STATE_SETTLING) {
                    System.out.println("onDragStateChanged STATE_SETTLING");
                } else if (state == SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END) {
                    System.out.println("onDragStateChanged SWIPE_DIRECTION_START_TO_END");
                }
            }
        });

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mTextView.getLayoutParams();

        layoutParams.setBehavior(swipeDismissBehavior);
    }
}
