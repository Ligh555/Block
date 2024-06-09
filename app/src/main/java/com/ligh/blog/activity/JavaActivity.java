package com.ligh.blog.activity;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;


import com.ligh.blog.R;
import com.ligh.blog.databinding.ActivityMain2Binding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class JavaActivity extends BaseActivity {


    ActivityMain2Binding activityMain2Binding = null;

    @NonNull
    @Override
    public ViewBinding getViewBinding() {
        if (activityMain2Binding == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.activity_main2, null);
            activityMain2Binding = ActivityMain2Binding.bind(view);
        }
        return activityMain2Binding;
    }

    @Override
    public void initViewBinding() {

    }
}