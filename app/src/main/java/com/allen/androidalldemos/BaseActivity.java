package com.allen.androidalldemos;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.allen.androidalldemos.applaction.MyApplaction;

/**
 * Created by hardy on 2015/10/23.
 */
public class BaseActivity extends AppCompatActivity {

    private MyApplaction myApplaction;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        myApplaction = (MyApplaction) getApplication();
    }
}
