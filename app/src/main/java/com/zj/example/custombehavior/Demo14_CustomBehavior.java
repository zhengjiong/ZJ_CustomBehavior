package com.zj.example.custombehavior;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * 效果: 列表滑动的时候是覆盖Header（不是Header缩小，Header没动），然后就是Header有一个alpha 的变化
 * Created by zhengjiong
 * date: 2017/6/11 13:06
 */

public class Demo14_CustomBehavior extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo14_custom_behavior_layout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new TestRecyclerViewAdapter());
    }
}
