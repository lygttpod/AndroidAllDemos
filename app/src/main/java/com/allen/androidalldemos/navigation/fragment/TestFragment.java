package com.allen.androidalldemos.navigation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allen.androidalldemos.R;

/**
 * Created by hardy on 2015/10/23.
 */
public class TestFragment extends Fragment {
    private String title;

    private TextView textView;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        title = bundle != null ? bundle.getString("title") : "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, null);
        textView = (TextView) view.findViewById(R.id.fragment_test_text);
        textView.setText(title);
        return view;
    }
}
