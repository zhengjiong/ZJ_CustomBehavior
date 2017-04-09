package com.zj.example.custombehavior;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Title: Demo1Activity
 * Description:
 * Copyright:Copyright(c)2016
 * CreateTime:17/4/8  12:25
 *
 * @author 郑炯
 * @version 1.0
 */
public class Demo1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo1_layout);

        Button button = (Button) findViewById(R.id.btn);

        button.setOnTouchListener(new View.OnTouchListener() {
            private int lastX;
            private int lastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                /**
                 * 当前触摸点坐标
                 */
                int currentX = (int) event.getRawX();
                int currentY = (int) event.getRawY();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = currentX;
                        lastY = currentY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        /**
                         * 计算偏移量
                         */
                        int offsetX = currentX - lastX;
                        int offsetY = currentY - lastY;

                        //设置当前view的位置, layout内部会调用onLayout方法
                        /*v.layout(
                                v.getLeft() + offsetX,
                                v.getTop() + offsetY,
                                v.getRight() + offsetX,
                                v.getBottom() + offsetY
                        );*/
                        v.offsetLeftAndRight(offsetX);
                        v.offsetTopAndBottom(offsetY);

                        //重新设置初始坐标
                        lastX = currentX;
                        lastY = currentY;
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }
                return true;
            }
        });
    }
}
