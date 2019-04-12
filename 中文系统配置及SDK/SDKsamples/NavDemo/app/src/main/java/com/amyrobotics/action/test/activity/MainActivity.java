package com.amyrobotics.action.test.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.amy.robot.actioncenter.api.entity.map.MapFileInfo;
import com.amy.robot.actioncenter.api.entity.map.MapListEntity;
import com.amy.robot.actioncenter.api.robot.RobotNavActionApiImp;
import com.amy.robot.actioncenter.common.bean.ActionRequest;
import com.amy.robot.actioncenter.common.constant.ActionCode;
import com.amy.robot.actioncenter.lib.ActionNotifyCallBack;
import com.amy.robot.actioncenter.lib.ActionResultCallback;
import com.amyrobotics.action.test.R;
import com.amyrobotics.action.test.data.ViewMapItemData;
import com.amyrobotics.action.test.utils.LogUtils;
import com.amyrobotics.action.test.utils.StringUtils;
import com.amyrobotics.action.test.utils.ViewUtils;
import com.amyrobotics.action.test.view.ListItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by adward on 2018/6/21.
 */

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.btn_start_nav)
    Button btnStartNav;
    @BindView(R.id.btn_goto_voice)
    Button btnGotoVoice;
    @BindView(R.id.btn_stop_nav)
    Button btnStopNav;
    @BindView(R.id.btn_get_nav_state)
    Button btnGetNavState;
    @BindView(R.id.tv_nav_state)
    TextView tvNavState;
    @BindView(R.id.btn_get_map_list)
    Button btnGetMapList;
    @BindView(R.id.listview)
    ListView listview;

    ArrayList<ViewMapItemData> mItems;
    ListViewAdapter listViewAdapter;
    RobotNavActionApiImp robotNavActionApi;
    MapListEntity mMapListEntity;
    ActionNotifyCallBack startNavCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_map_list);
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
                ViewMapItemData markPointViewItemData = mItems.get(position);
                if (markPointViewItemData.mapFileInfo != null) {
                    startNavByMapId(markPointViewItemData.mapFileInfo.id);
                }
            }
        });

        //初始化获取状态
        //get init state
        getNavState();
        //获取地图列表
        //get map list
        getMapList();

    }

    @OnClick({
            R.id.btn_start_nav,
            R.id.btn_stop_nav,
            R.id.btn_get_nav_state,
            R.id.btn_get_map_list,
            R.id.btn_goto_voice,
    })
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_nav:
                startNav();
                break;
            case R.id.btn_stop_nav:
                stopNav();
                break;
            case R.id.btn_get_nav_state:
                getNavState();
                break;
            case R.id.btn_get_map_list:
                getMapList();
                break;
            case R.id.btn_goto_voice:
                goToVoice();
                break;
        }

    }

    private void goToVoice() {
        Intent intent = new Intent(MainActivity.this, VoiceActivity.class);
        startActivity(intent);
    }

    void startNavByMapId(String mapId) {
        int ret = robotNavActionApi.startNavByMapId(mapId, createStartNavCallBack());

        if (ret == ActionCode.ACTION_OK) {
            showLoading(getString(R.string.starting_nav));
        } else {
            showToast(getString(R.string.error) + ret);
        }

    }

    private ActionNotifyCallBack createStartNavCallBack() {
        startNavCallBack = new ActionNotifyCallBack() {
            @Override
            public void onRecvActionNotify(ActionRequest notifyRequest,
                                           String srcURI, String notifyAction, final int notifyCode,
                                           final String notifyInfo, String notifyParams) {
                LogUtils.d(TAG, "onRecvActionNotify srcURI=" + srcURI + ", notifyAction=" + notifyAction
                        + ", notifyCode=" + notifyCode + ", notifyInfo=" + notifyInfo
                        + ", notifyParams=" + notifyParams);

                if (notifyCode == ActionCode.ACTION_OK) {
                    //成功(success)
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            showToast(getString(R.string.start_nav_success));
                            hideLoading();
                            startActivity(new Intent(getActivity(), NavMarkPointListActivity.class));
                        }
                    });

                } else {
                    //错误，错误码：notifyCode, 错误信息：notifyInfo
                    //error, error code: notifyCode, error info: notifyInfo
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            showToast(getString(R.string.start_nav_fail) + notifyCode + ", " + notifyInfo);
                            hideLoading();
                        }
                    });

                }
            }
        };
        return startNavCallBack;
    }

    private void getMapList() {
        robotNavActionApi.getMapList(new ActionResultCallback<MapListEntity>() {
            @Override
            public void onActionSuccess(final MapListEntity mapListEntity) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onGetMapList(mapListEntity);
                    }
                });
            }

            @Override
            public void onActionFailed(int errCode, String errInfo, Throwable throwable) {
                showToast(getString(R.string.get_map_list_error) + errCode + ", " + errInfo);
            }
        });

    }

    private void onGetMapList(MapListEntity mapListEntity) {
        List<ViewMapItemData> itemDataList = new ArrayList<>();

        if (mapListEntity != null) {
            List<MapFileInfo> mapFileInfoList = mapListEntity.mapFileInfoList;
            if (mapFileInfoList != null) {
                for (MapFileInfo mapFileInfo : mapFileInfoList) {
                    ViewMapItemData mapItemData = new ViewMapItemData();
                    mapItemData.mapFileInfo = mapFileInfo;
                    mapItemData.isDefaultNavMap = StringUtils.compare(mapFileInfo.id, mapListEntity.defaultNavMapId);

                    itemDataList.add(mapItemData);
                }
            }
        }

        this.mMapListEntity = mapListEntity;
        mItems.clear();
        mItems.addAll(itemDataList);
        listViewAdapter.notifyDataSetChanged();

        showToast(getString(R.string.get_map_list_success));

    }

    private void getNavState() {
        ViewUtils.setText(tvNavState, getString(R.string.geting_nav_state));

        robotNavActionApi.getNavState(new ActionNotifyCallBack() {
            @Override
            public void onRecvActionNotify(ActionRequest notifyRequest,
                                           String srcURI, String notifyAction, final int notifyCode,
                                           final String notifyInfo, final String notifyParams) {
                if (notifyCode == ActionCode.ACTION_OK) {
                    //成功（success）
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ViewUtils.setText(tvNavState, notifyParams);
                        }
                    });

                } else {
                    //错误，错误码：notifyCode, 错误信息：notifyInfo
                    //error, error code: notifyCode, error info: notifyInfo
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ViewUtils.setText(tvNavState, "err=" + notifyCode + ", " + notifyInfo);
                        }
                    });
                }
            }
        });

    }

    private void startNav() {
        int ret = robotNavActionApi.startNav(createStartNavCallBack());

        if (ret == ActionCode.ACTION_OK) {
            showLoading(getString(R.string.starting_nav));
        } else {
            showToast(getString(R.string.error) + ret);
        }
    }

    private void stopNav() {
        robotNavActionApi.stopNav(new ActionNotifyCallBack() {
            @Override
            public void onRecvActionNotify(ActionRequest notifyRequest,
                                           String srcURI, String notifyAction, final int notifyCode,
                                           final String notifyInfo, String notifyParams) {

                LogUtils.d(TAG, "onRecvActionNotify srcURI=" + srcURI + ", notifyAction=" + notifyAction
                        + ", notifyCode=" + notifyCode + ", notifyInfo=" + notifyInfo
                        + ", notifyParams=" + notifyParams);

                if (notifyCode == ActionCode.ACTION_OK) {
                    //成功（success）
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            hideLoading();
                            showToast(getString(R.string.stop_nav_success));
                        }
                    });

                } else {
                    //错误，错误码：notifyCode, 错误信息：notifyInfo
                    //error, error code: notifyCode, error info: notifyInfo
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            hideLoading();
                            showToast(getString(R.string.stop_nav_fail) + notifyCode + ", " + notifyInfo);
                        }
                    });

                }
            }
        });

        showLoading(getString(R.string.stoping_nav));

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
            ViewMapItemData dataItem = mItems.get(position);

            ListItemView itemView;
            if (convertView == null) {
                itemView = new MapListItemView(getActivity(), viewGroup);
            } else {
                itemView = (ListItemView) convertView.getTag();
            }

            itemView.setViewData(dataItem);

            return itemView.getView();
        }
    }

    public class MapListItemView extends ListItemView {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_map_id)
        TextView tvMapId;
        @BindView(R.id.tv_map_create_time)
        TextView tvMapCreateTime;
        @BindView(R.id.tv_map_size)
        TextView tvMapSize;
        @BindView(R.id.tv_map_is_default)
        TextView tvMapIsDefault;

        public MapListItemView(Context context, ViewGroup parent) {
            super(context, parent);
        }

        public void initView(LayoutInflater mInflater, ViewGroup parent) {
            rootView = mInflater.inflate(R.layout.listitem_map, parent, false);
            ButterKnife.bind(this, rootView);
        }

        @Override
        public void setViewData(Object itemData) {
            ViewMapItemData mViewData = (ViewMapItemData) itemData;
            if (mViewData.mapFileInfo != null) {
                tvName.setText(StringUtils.safe(mViewData.mapFileInfo.name));
                tvMapId.setText(StringUtils.safe(mViewData.mapFileInfo.id));

                //地图尺寸：100 x 100
                //map size: 100 x 100
                String info = mViewData.mapFileInfo.width
                        + "x" + mViewData.mapFileInfo.height
                        + ", ratio=" + mViewData.mapFileInfo.ratio;
                tvMapSize.setText(info);

                tvMapCreateTime.setText(StringUtils.safe(mViewData.mapFileInfo.createTime));

                if (mViewData.isDefaultNavMap) {
                    tvMapIsDefault.setVisibility(View.VISIBLE);
                } else {
                    tvMapIsDefault.setVisibility(View.GONE);
                }
            }
        }
    }
}
