package com.zj.example.custombehavior;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zj on 2017/6/10.
 */

public class TestRecyclerViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(new TestRecyclerViewAdapter());
        return view;
    }

    public static TestRecyclerViewFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TestRecyclerViewFragment fragment = new TestRecyclerViewFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
