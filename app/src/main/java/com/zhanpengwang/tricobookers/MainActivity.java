package com.zhanpengwang.tricobookers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private DBhelper mDBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize a DBhelper
        mDBhelper = new DBhelper(getBaseContext());

    }
}
