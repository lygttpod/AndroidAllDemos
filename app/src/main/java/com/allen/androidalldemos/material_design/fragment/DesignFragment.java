package com.allen.androidalldemos.material_design.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allen.androidalldemos.R;
import com.allen.androidalldemos.material_design.adapter.RecyclerViewAdapter;

/**
 * Created by Allen on 2015/12/14.
 */
public class DesignFragment extends BaseFragment {
    private RecyclerView mRecyclerView;

    public static DesignFragment newInstance() {
        DesignFragment designFragment = new DesignFragment();
        return designFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_design, container, false);
        mRecyclerView = ((RecyclerView) view.findViewById(R.id.recyclerview));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getActivity()));
        return view;
    }

    @Override
    protected void lazyLoad() {

    }
}
