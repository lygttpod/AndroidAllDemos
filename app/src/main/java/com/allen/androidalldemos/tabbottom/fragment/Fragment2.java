package com.allen.androidalldemos.tabbottom.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allen.androidalldemos.R;

/**
 * Created by Allen on 2015/12/25.
 */
public class Fragment2 extends Fragment {
    private TextView textView;
    private String title;
    private static final String ARG = "title";

    public static Fragment2 newInstance(String title) {
        Fragment2 fragment = new Fragment2();
        Bundle bundle = new Bundle();
        bundle.putString(ARG, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        title = getArguments().getString(ARG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        textView = ((TextView) view.findViewById(R.id.title_fragment));
        textView.setText(title);
        return view;
    }
}
