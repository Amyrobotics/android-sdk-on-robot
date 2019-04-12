package com.amyrobotics.action.test.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amyrobotics.action.test.data.MarkPointViewItemData;

/**
 * Created by adward on 2018/6/21.
 */
public class ListItemView {
    public View rootView;
    public Context mContext;

    public ListItemView(Context context, ViewGroup parent) {
        mContext = context;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initView(mInflater, parent);
        rootView.setTag(this);
    }

    public void initView(LayoutInflater mInflater, ViewGroup parent) {
    }

    public void setViewData(Object itemData) {
    }

    public View getView() {
        return rootView;
    }

}
