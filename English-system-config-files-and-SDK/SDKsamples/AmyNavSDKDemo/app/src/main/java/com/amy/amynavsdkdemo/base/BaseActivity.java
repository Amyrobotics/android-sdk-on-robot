package com.amy.amynavsdkdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Async_wu
 * @date 2019-3-19 15:05:40
 * BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder =  ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    protected abstract int getLayoutId();

    protected void initView(){}
    protected void initData(){}
    protected void initEvent(){}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder!=null){
            unbinder.unbind();
        }
    }
}
