package com.zj.example.custombehavior;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by zj on 2017/6/10.
 */

public class Demo10_BottomSheetBehavior extends AppCompatActivity {

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo10_bottom_sheet_behavior_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Demo10");
        toolbar.getMenu().add(0, 0, 0, "展开");
        toolbar.getMenu().add(0, 1, 0, "折叠");
        toolbar.getMenu().add(0, 2, 0, "隐藏");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == 0) {
                    /**
                     * 1, STATE_EXPANDED 展开状态，显示完整布局。
                     * 2，STATE_COLLAPSED 折叠状态，显示peekHeigth 的高度，如果peekHeight为0，则全部隐藏,与STATE_HIDDEN效果一样。
                     * 3，STATE_DRAGGING 拖拽时的状态
                     * 4，STATE_HIDDEN 隐藏时的状态
                     * 5，STATE_SETTLING 释放时的状态
                     */
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                    //通过方法 sheetBehavior.setState（）来改变状态

                    return true;
                } else if (item.getItemId() == 1) {
                    //设置为展开状态
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (item.getItemId() == 2) {
                    /**
                     * 必须设置了setHideable(true),才可以设置setState(BottomSheetBehavior.STATE_HIDDEN)
                     */
                    if (bottomSheetBehavior.isHideable()) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                }
                return false;
            }
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_holder, TestRecyclerViewFragment.newInstance(), TestRecyclerViewFragment.class.getSimpleName())
                .commitAllowingStateLoss();


        LinearLayout bottomSheetLayout = (LinearLayout) findViewById(R.id.bottom_sheet_layout);

        //BottomSheetBehavior有一个静态方法BottomSheetBehavior.from(View)，会返回这个View引用的BottomSheetBehavior
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);

        //设置折叠时的高度
        bottomSheetBehavior.setPeekHeight(150);

        //下滑的时候是否可以隐藏
        bottomSheetBehavior.setHideable(true);

        //监听BottomSheetBehavior 状态的变化
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    Toast.makeText(Demo10_BottomSheetBehavior.this, "折叠了", Toast.LENGTH_SHORT).show();
                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    Toast.makeText(Demo10_BottomSheetBehavior.this, "展开了", Toast.LENGTH_SHORT).show();
                } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    Toast.makeText(Demo10_BottomSheetBehavior.this, "隐藏了", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        bottomSheetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Demo10_BottomSheetBehavior.this, "click", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
