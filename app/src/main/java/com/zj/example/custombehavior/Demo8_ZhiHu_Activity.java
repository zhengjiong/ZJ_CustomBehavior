package com.zj.example.custombehavior;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

import com.zj.example.custombehavior.behavior.Demo8_Behavior;

/**
 * Title: Demo8_ZhiHu_Activity
 * CreateTime: 17/6/16  09:44
 *
 * @author 郑炯
 * @version 1.0
 */
public class Demo8_ZhiHu_Activity extends AppCompatActivity {

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo8_zhihu_layout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TestRecyclerViewAdapter());


        TextView tvBottom = (TextView) findViewById(R.id.tv_bottom);

        bottomSheetBehavior = BottomSheetBehavior.from(tvBottom);
        bottomSheetBehavior.setHideable(true);

        /*DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, displayMetrics);
        bottomSheetBehavior.setPeekHeight((int) value);*/


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Demo8_Behavior behavior = Demo8_Behavior.from(fab);
        behavior.setListener(new Demo8_Behavior.IAnimateListener() {
            @Override
            public void show() {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

            @Override
            public void hide() {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
}
