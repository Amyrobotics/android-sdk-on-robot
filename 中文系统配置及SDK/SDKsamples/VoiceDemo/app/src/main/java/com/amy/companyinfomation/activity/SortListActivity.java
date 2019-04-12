package com.amy.companyinfomation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.amy.companyinfomation.R;
import com.amy.companyinfomation.adapter.SortListDividerDecoration;
import com.amy.companyinfomation.adapter.SortListRecycleAdapter;
import com.amy.companyinfomation.entity.IniEntity;
import com.amy.companyinfomation.utils.HanziToPinyin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/21.
 */

public class SortListActivity extends BaseActivity {
    public static final String CODE_CLASS = "code_class";
    public static final String TAG = "SortListActivity";

    private int mClassPosition;
    private List<IniEntity.ClassEntity.ClassBean.DataBean.ItemBean> mList;
    private RecyclerView mSortListRv;
    private SortListRecycleAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_list);

        initData();
        initView();
    }

    private void initView() {
        mSortListRv = findViewById(R.id.rv_sort_list);
        int type = IniEntity.getInstance().getClass_entity().getClassX().get(mClassPosition).getClass_type();
        GridLayoutManager manager;
        if (type == 1) {
            manager = new GridLayoutManager(this, 3);
        } else if (type == 2) {
            manager = new GridLayoutManager(this, 4);
        } else {
            return;
        }
        mSortListRv.addItemDecoration(new SortListDividerDecoration(type));
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mSortListRv.setLayoutManager(manager);
        mAdapter = new SortListRecycleAdapter(this, type, mClassPosition);
        mSortListRv.setAdapter(mAdapter);
        mList = IniEntity.getInstance().getClass_entity().getClassX().get(mClassPosition).getData().getItem();
        mAdapter.setDataList(mList);
        mAdapter.notifyDataSetChanged();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        mClassPosition = intent.getIntExtra(CODE_CLASS, 0);
    }

    @Override
    public synchronized void disposeVoice(String voiceFont) {
        super.disposeVoice(voiceFont);
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getItem_name().equals(voiceFont)) {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra(DetailActivity.CODE_WEB_POSITION, i);
                intent.putExtra(DetailActivity.CODE_CLASS_POSITION, mClassPosition);
                startActivity(intent);
                return;
            }
        }

        //语音识别第一次未匹配，开始执行拼音识别
        List<HanziToPinyin.Token> results = HanziToPinyin.getInstance().get(voiceFont);
        String voiceFontToPinyin = "";
        for (HanziToPinyin.Token token : results) {
            voiceFontToPinyin = voiceFontToPinyin + token.target;
        }
        Log.e(TAG, "voiceFontToPinyin = " + voiceFontToPinyin);
        List<String> itemToPinyin = IniEntity.getInstance().getClass_entity().getClassX().get(mClassPosition).getData().getItemToPinyin();
        for (int i = 0; i < itemToPinyin.size(); i++) {
            if (itemToPinyin.get(i).equals(voiceFontToPinyin)) {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra(DetailActivity.CODE_WEB_POSITION, i);
                intent.putExtra(DetailActivity.CODE_CLASS_POSITION, mClassPosition);
                startActivity(intent);
                return;
            }
        }
    }
}
