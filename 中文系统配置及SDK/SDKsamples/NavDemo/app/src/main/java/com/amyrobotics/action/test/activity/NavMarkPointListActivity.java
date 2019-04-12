package com.amyrobotics.action.test.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amy.robot.actioncenter.api.entity.map.MarkPointEntity;
import com.amy.robot.actioncenter.api.robot.RobotNavActionApi;
import com.amy.robot.actioncenter.api.robot.RobotNavActionApiImp;
import com.amy.robot.actioncenter.common.bean.ActionRequest;
import com.amy.robot.actioncenter.common.constant.ActionCode;
import com.amy.robot.actioncenter.lib.ActionNotifyCallBack;
import com.amy.robot.actioncenter.lib.ActionResultCallback;
import com.amyrobotics.action.test.R;
import com.amyrobotics.action.test.data.MarkPointViewItemData;
import com.amyrobotics.action.test.utils.LogUtils;
import com.amyrobotics.action.test.utils.StringUtils;
import com.amyrobotics.action.test.view.ListItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adward on 2018/6/21.
 */

public class NavMarkPointListActivity extends BaseActivity {
    private static final String TAG = NavMarkPointListActivity.class.getSimpleName();

    @BindView(R.id.btn_get_mark_point_list)
    Button btnGetMarkPointList;
    @BindView(R.id.btn_stop_nav)
    Button btnStopNav;
    @BindView(R.id.btn_cancel_nav)
    Button btnCancelNav;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.rb_nav_by_pos)
    RadioButton rbNavByPos;
    @BindView(R.id.rb_nav_by_name)
    RadioButton rbNavByName;

    private ArrayList<MarkPointViewItemData> mItems;
    ListViewAdapter listViewAdapter;
    RobotNavActionApi robotNavActionApi;
    boolean isNavPointByPos;
    ActionNotifyCallBack navToPointCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_point);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        robotNavActionApi = new RobotNavActionApiImp();

        mItems = new ArrayList<>();
        listViewAdapter = new ListViewAdapter();
        listview.setAdapter(listViewAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                MarkPointViewItemData markPointViewItemData = mItems.get(position);
                if (markPointViewItemData.markPointEntity != null) {
                    navToPoint(markPointViewItemData.markPointEntity);
                }
            }
        });
        rbNavByPos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                LogUtils.d(TAG, "rbNavByPos isChecked=" + isChecked);
                if (isChecked) {
                    isNavPointByPos = true;
                }
            }
        });

        rbNavByName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                LogUtils.d(TAG, "rbNavByName isChecked=" + isChecked);
                if (isChecked) {
                    isNavPointByPos = false;
                }
            }
        });

        if (isNavPointByPos) {
            rbNavByPos.setChecked(true);
            rbNavByName.setChecked(false);
        } else {
            rbNavByPos.setChecked(false);
            rbNavByName.setChecked(true);
        }

        getMarkPointList();

    }

    private void navToPoint(final MarkPointEntity markPointEntity) {

        tvState.setText(getText(R.string.navigating_to) + markPointEntity.text);

        if (navToPointCallBack != null) {
            //取消前次监听
            //cancel current listen
            navToPointCallBack.cancel();
        }

        navToPointCallBack = new ActionNotifyCallBack() {
            @Override
            public void onRecvActionNotify(ActionRequest notifyRequest,
                                           String srcURI, String notifyAction, final int notifyCode,
                                           final String notifyInfo, final String notifyParams) {

                if (notifyCode == ActionCode.ACTION_OK) {
                    //成功, notifyParams
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            String info = getString(R.string.arrived) + markPointEntity.text;
                            showToast(info);
                            tvState.setText(info);
                        }
                    });

                } else {
                    //错误，错误码：notifyCode, 错误信息：notifyInfo
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            String info = getString(R.string.nav_error) + notifyCode + ", " + notifyInfo;
                            showToast(info);
                            tvState.setText(info);
                        }
                    });
                }
            }
        };

        LogUtils.d(TAG, "navToPoint isNavPointByPos =" + isNavPointByPos + ", markPointEntity=" + JSON.toJSONString(markPointEntity));


        if (isNavPointByPos) {
            robotNavActionApi.navToPointByPostion(markPointEntity.realX, markPointEntity.realY, markPointEntity.realAngle, navToPointCallBack);
        } else {
            robotNavActionApi.navToPointByName(markPointEntity.text, navToPointCallBack);
        }

    }

    @OnClick({
            R.id.btn_stop_nav,
            R.id.btn_get_mark_point_list,
            R.id.btn_cancel_nav,
    })
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.btn_stop_nav:
                stopNav();
                break;
            case R.id.btn_get_mark_point_list:
                getMarkPointList();
                break;
            case R.id.btn_cancel_nav:
                cancelNav();
                break;
        }

    }

    private void stopNav() {
        robotNavActionApi.stopNav(new ActionNotifyCallBack() {
            @Override
            public void onRecvActionNotify(ActionRequest notifyRequest,
                                           String srcURI, String notifyAction, final int notifyCode,
                                           final String notifyInfo, String notifyParams) {

                if (notifyCode == ActionCode.ACTION_OK) {
                    //成功(success)
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            showToast(getString(R.string.stop_nav_success));
                            onBackPressed();
                        }
                    });

                } else {
                    //错误，错误码：notifyCode, 错误信息：notifyInfo
                    //error, error code: notifyCode, error info: notifyInfo
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            showToast(getString(R.string.start_nav_fail) + notifyCode + ", " + notifyInfo);
                        }
                    });

                }
            }
        });

    }

    private void cancelNav() {
        robotNavActionApi.cancelNav(new ActionNotifyCallBack() {
            @Override
            public void onRecvActionNotify(ActionRequest notifyRequest,
                                           String srcURI, String notifyAction, final int notifyCode,
                                           final String notifyInfo, String notifyParams) {
                if (notifyCode == ActionCode.ACTION_OK) {
                    //成功(success)
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            showToast(getString(R.string.cancel_nav_success));
                        }
                    });

                } else {
                    //错误，错误码：notifyCode, 错误信息：notifyInfo
                    //error, error code: notifyCode, error info: notifyInfo
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            showToast(getString(R.string.cancel_nav_fail) + notifyCode + ", " + notifyInfo);
                        }
                    });

                }
            }
        });


    }

    private void getMarkPointList() {
        robotNavActionApi.getMarkPointList(null, new ActionResultCallback<List<MarkPointEntity>>() {
            @Override
            public void onActionSuccess(final List<MarkPointEntity> markPointEntities) {
                LogUtils.e(TAG, "getMarkPointList ok");

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isFinishing()) {
                            return;
                        }

                        showToast(getString(R.string.get_map_list_success));
                        onGetMarkPointList(markPointEntities);
                    }
                });

            }

            @Override
            public void onActionFailed(int errCode, String errInfo, Throwable throwable) {
                LogUtils.e(TAG, "getMarkPointList failed errCode=" + errCode + ", errInfo=" + errInfo + ", " + throwable);

                showToast(getString(R.string.get_map_list_error) + errCode + ", " + errInfo);
            }
        });

    }

    private void onGetMarkPointList(List<MarkPointEntity> markPointEntities) {
        ArrayList<MarkPointViewItemData> items = new ArrayList<>();
        if (markPointEntities != null) {
            for (MarkPointEntity markPointEntity : markPointEntities) {
                MarkPointViewItemData itemData = new MarkPointViewItemData();
                itemData.markPointEntity = markPointEntity;
                items.add(itemData);
            }
        }

        mItems.clear();
        mItems.addAll(items);
        listViewAdapter.notifyDataSetChanged();

    }

    private class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int i) {
            return mItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            MarkPointViewItemData dataItem = mItems.get(position);

            ListItemView itemView;
            if (convertView == null) {
                itemView = new MarkPtListItemView(getActivity(), viewGroup);
            } else {
                itemView = (ListItemView) convertView.getTag();
            }

            itemView.setViewData(dataItem);

            return itemView.getView();
        }
    }

    public class MarkPtListItemView extends ListItemView {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_map_pos)
        TextView tvMapPos;
        @BindView(R.id.tv_real_pos)
        TextView tvRealPos;
        @BindView(R.id.tv_direction_angle)
        TextView tvDirectionAngle;
        @BindView(R.id.tv_direction_rad)
        TextView tvDirectionRad;

        public MarkPtListItemView(Context context, ViewGroup parent) {
            super(context, parent);
        }

        public void initView(LayoutInflater mInflater, ViewGroup parent) {
            rootView = mInflater.inflate(R.layout.listitem_mark_point, parent, false);
            ButterKnife.bind(this, rootView);
        }

        @Override
        public void setViewData(Object itemData) {
            MarkPointViewItemData mViewData = (MarkPointViewItemData) itemData;
            if (mViewData.markPointEntity != null) {
                tvName.setText(StringUtils.safe(mViewData.markPointEntity.text));

                if (!StringUtils.isEmpty(mViewData.markPointEntity.desc)) {
                    tvDesc.setText(StringUtils.safe(mViewData.markPointEntity.desc));
                    tvDesc.setVisibility(View.VISIBLE);
                } else {
                    tvDesc.setVisibility(View.GONE);
                }

                tvMapPos.setText(mContext.getString(R.string.map_xy) + mViewData.markPointEntity.x + ", " + mViewData.markPointEntity.y);
                tvRealPos.setText(mContext.getString(R.string.real_xy) + mViewData.markPointEntity.realX + ", " + mViewData.markPointEntity.realY);
                tvDirectionAngle.setText(mContext.getString(R.string.text_angle) + mViewData.markPointEntity.realAngle);
//                tvDirectionRad.setText(mContext.getString(R.string.text_radian) + mViewData.markPointEntity.realX);
            }
        }
    }
}
