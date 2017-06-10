package com.zj.example.custombehavior;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by zj on 2017/6/10.
 */

public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewHolder> {

    @Override
    public TestRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(TestRecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
