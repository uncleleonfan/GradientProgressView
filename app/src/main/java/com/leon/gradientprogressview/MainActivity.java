package com.leon.gradientprogressview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GradientProgressView mProgress =(GradientProgressView) findViewById(R.id.progress);
        mProgress.setProgress(100);
        mProgress.startAnimation();
    }
}
