package com.amy.amynavsdkdemo;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amy.amynavsdkdemo.action.ActionCenterCode;
import com.amy.amynavsdkdemo.activity.RobotActionActivity;
import com.amy.amynavsdkdemo.activity.RobotControlActivity;
import com.amy.amynavsdkdemo.adapter.MapRecyAdapter;
import com.amy.amynavsdkdemo.base.BaseActivity;
import com.amy.amynavsdkdemo.bean.MapListRespBean;
import com.amy.amynavsdkdemo.custom.DividerItemDecorations;
import com.amy.amynavsdkdemo.manager.NavigationManager;
import com.amy.robot.actioncenter.api.robot.RobotNavActionApiImp;
import com.amy.robot.actioncenter.common.bean.ActionRequest;
import com.amy.robot.actioncenter.common.constant.ActionCode;
import com.amy.robot.actioncenter.lib.ActionNotifyCallBack;
import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Async_wu
 * @date 2019-3-19 15:02:28
 * 本demo基于AmyOS2.1.37_190314  工控版本1.5.3
 * This demo is based on AmyOS2.1.37 190314 industrial version 1.5.3
 * 注意：如需使用导航功能，首先需要在libs包下引入相应的arr包（请使用\\192.168.1.217\Android_team\public 目录下的最新版本）
 * Note: to use navigation, you first need to introduce the appropriate arr package under the libs package（Please check the sdk package version）
 * 注意：机器人的远程控制版本不能小于1.10.2
 * Note: the remote control version of the robot cannot be less than 1.10.2
 * 注意：导航开启失败请根据sdk文档或者ActionCenterCode类进行错位定位
 * Note: the navigation fails to open. Please locate it in a wrong position according to SDK document or ActionCenterCode class
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.btn_refreshMapList)
    Button refreshMapButton;
    @BindView(R.id.btn_startNav)
    Button startNavButton;
    @BindView(R.id.btn_navStatus)
    Button navStatusButton;
    @BindView(R.id.tv_navStatus)
    TextView navStatusTextView;
    @BindView(R.id.btn_stopNav)
    Button stopNavButton;
    @BindView(R.id.btn_robotControl)
    Button robotControlButton;
    @BindView(R.id.btn_robotAction)
    Button robotActionButton;
    @BindView(R.id.recy_mapList)
    RecyclerView mapListRecy;
    @BindView(R.id.srl_maplsit)
    SwipeRefreshLayout srl_mapList;

    private Handler handler = new Handler();
    private static final String TAG = MainActivity.class.getSimpleName();
    private MapListRespBean mapListRespBean;
    private MapRecyAdapter mapRecyAdapter;
    ProgressDialog startNavDialog;

    RobotNavActionApiImp robotNavActionApi;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mapRecyAdapter = new MapRecyAdapter(this);
        mapListRecy.setLayoutManager(new LinearLayoutManager(this));
        mapListRecy.addItemDecoration(new DividerItemDecorations());
        mapListRecy.setAdapter(mapRecyAdapter);

        startNavDialog = new ProgressDialog(this);
        startNavDialog.setCanceledOnTouchOutside(false);
        startNavDialog.setMessage(getResources().getString(R.string.toast_startNav));

        robotNavActionApi = new RobotNavActionApiImp();


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        getNavStatus();
        getMapList();
        srl_mapList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMapList();
            }
        });
    }

    @OnClick({R.id.btn_refreshMapList,R.id.btn_startNav,R.id.btn_navStatus,R.id.btn_stopNav,R.id.btn_robotControl,R.id.btn_robotAction})
    public void btnClick(View view){
        switch (view.getId()){
            case R.id.btn_refreshMapList:
                getMapList();
                break;
            case R.id.btn_startNav:
                startNav();
                break;
            case R.id.btn_navStatus:
                getNavStatus();
                break;
            case R.id.btn_stopNav:
                stopNav();
                break;
            case R.id.btn_robotControl:
                robotControl();
                break;
            case R.id.btn_robotAction:
                robotAction();
                break;
                default:
                    break;
        }
    }

    /**
     * 获取地图列表
     * Get map list
     */
    public void getMapList(){

        NavigationManager.getInstance().getMapList(new ActionNotifyCallBack() {
            /**
             * 返回
             * return
             * @param actionRequest all json
             * @param s uri
             * @param s1 action Name
             * @param i 执行状态码 0：执行成功
             *           Execution status code 0: successful
             * @param s2 null
             * @param s3 地图列表json
             *            maps json
             */
            @Override
            public void onRecvActionNotify(ActionRequest actionRequest, String s, String s1, int i, String s2, final String s3) {
//                LogUtils.d("获取地图 "+s+" s1 "+s1+" i "+i+" s2 "+s2+" s3 "+s3 );
                if(i==ActionCode.ACTION_OK){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,R.string.toast_loadMapSuccess,Toast.LENGTH_SHORT).show();
                            if(s3!=null){
                                if(srl_mapList.isRefreshing()){
                                    srl_mapList.setRefreshing(false);
                                }
                                mapListRespBean = new Gson().fromJson(s3,MapListRespBean.class);
                                mapRecyAdapter.getNewList(mapListRespBean.getDefaultNavMapId(),mapListRespBean.getMapFileInfoList());
                                mapRecyAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(srl_mapList.isRefreshing()){
                                srl_mapList.setRefreshing(false);
                            }
                            Toast.makeText(MainActivity.this,R.string.toast_loadMapFail,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
//        NavigationManager.getInstance().getMapList(new ActionResultCallback<MapListEntity>() {
//            @Override
//            public void onActionSuccess(MapListEntity mapListEntity) {
//                LogUtils.d(TAG,"获取成功 "+new Gson().toJson(mapListEntity));
//            }
//
//            @Override
//            public void onActionFailed(int i, String s, Throwable throwable) {
//                LogUtils.d(TAG,"获取失败 "+i);
//            }
//        });

    }

    /**
     * 开启导航
     * Open the navigation
     */
    public void startNav(){
        if(startNavDialog!=null){
            startNavDialog.show();
        }
        NavigationManager.getInstance().startNav(new ActionNotifyCallBack() {
            /**
             * 返回
             * return
             * @param actionRequest all json
             * @param s uri
             * @param s1 action Name
             * @param i 执行状态码 0：执行成功
             *           Execution status code 0: successful
             * @param s2 null
             * @param s3 导航状态
             *            Navigational state
             */
            @Override
            public void onRecvActionNotify(ActionRequest actionRequest, String s, String s1, final int i, String s2, String s3) {
//                LogUtils.d("开启导航 "+s+" s1 "+s1+" i "+i+" s2 "+s2+" s3 "+s3 );
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(startNavDialog!=null){
                            startNavDialog.dismiss();
                        }
                        if(i== ActionCenterCode.ACTION_OK){
                            Toast.makeText(MainActivity.this,getResources().getString(R.string.toast_navStartSuccess),Toast.LENGTH_SHORT).show();
                            getNavStatus();
                        }else {
                            Toast.makeText(MainActivity.this,getResources().getString(R.string.toast_navStartFail),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /**
     * 停止导航
     * Stop navigation
     */
    public void stopNav(){
        NavigationManager.getInstance().stopNav(new ActionNotifyCallBack() {
            /**
             * 返回
             * return
             * @param actionRequest all json
             * @param s uri
             * @param s1 action Name
             * @param i 执行状态码 0：执行成功
             *           Execution status code 0: successful
             * @param s2 null
             * @param s3 执行结果
             *            result of enforcement
             */
            @Override
            public void onRecvActionNotify(ActionRequest actionRequest, String s, String s1, final int i, String s2, String s3) {
//                LogUtils.d(TAG,"停止导航 "+s+" i"+i+" s1 "+s1+" s2 "+s2 +" s3 "+s3);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(i==ActionCenterCode.ACTION_OK){
                            Toast.makeText(MainActivity.this,getResources().getString(R.string.toast_navStopSuccess),Toast.LENGTH_SHORT).show();
                            getNavStatus();
                        }else {
                            Toast.makeText(MainActivity.this,getResources().getString(R.string.toast_navStopFail),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /**
     * 获取导航状态
     * Get navigation status
     */
    public void getNavStatus(){
        NavigationManager.getInstance().getNavStatus(new ActionNotifyCallBack() {
            /**
             * 返回
             * return
             * @param actionRequest all json
             * @param s uri
             * @param s1 action Name
             * @param i 执行状态码 0：执行成功
             *           Execution status code 0: successful
             * @param s2 null
             * @param s3 导航状态
             *            Navigational state
             */
            @Override
            public void onRecvActionNotify(ActionRequest actionRequest, String s, String s1, int i, String s2, final String s3) {
//                LogUtils.d(TAG,"导航状态 "+s+" i"+i+" s1 "+s1+" s2 "+s2 +" s3 "+s3);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        navStatusTextView.setText(s3);
                    }
                });

            }
        });
    }

    public void robotControl(){
        Intent intent = new Intent(MainActivity.this, RobotControlActivity.class);
        startActivity(intent);
    }

    /**Note: this feature does not support foreign user experience*/
    public void robotAction(){
        Intent intent = new Intent(MainActivity.this, RobotActionActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(srl_mapList.isRefreshing()){
            srl_mapList.setRefreshing(false);
        }
        if(startNavDialog!=null){
            startNavDialog.dismiss();
        }
    }


}
