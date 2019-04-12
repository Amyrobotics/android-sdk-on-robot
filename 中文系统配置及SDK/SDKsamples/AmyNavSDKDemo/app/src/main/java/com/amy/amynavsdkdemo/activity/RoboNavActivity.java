package com.amy.amynavsdkdemo.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amy.amynavsdkdemo.MainActivity;
import com.amy.amynavsdkdemo.R;
import com.amy.amynavsdkdemo.action.ActionCenterCode;
import com.amy.amynavsdkdemo.adapter.MarkPointRecyAdapter;
import com.amy.amynavsdkdemo.base.BaseActivity;
import com.amy.amynavsdkdemo.bean.MarkPointRespBean;
import com.amy.amynavsdkdemo.custom.DividerItemDecorations;
import com.amy.amynavsdkdemo.listener.OnItemClickListener;
import com.amy.amynavsdkdemo.manager.NavigationManager;
import com.amy.robot.actioncenter.api.entity.map.MarkPointEntity;
import com.amy.robot.actioncenter.common.bean.ActionRequest;
import com.amy.robot.actioncenter.common.constant.ActionCode;
import com.amy.robot.actioncenter.lib.ActionNotifyCallBack;
import com.amy.robot.actioncenter.lib.ActionResultCallback;
import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Async_wu
 * @date 2019-3-22 14:10:50
 * 机器人地图、导航
 * Robot map, navigation
 * 注意：由于使用的讯飞、百度语音SDK，因此不支持国外用户体验语音播报
 * Note: this feature does not support foreign user experience
 */
public class RoboNavActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.btn_getMarkList)
    Button getMarkListButton;
    @BindView(R.id.btn_cancelMarkNav)
    Button cacelMarkNavButton;
    @BindView(R.id.btn_stopNav)
    Button stopNavButton;
    @BindView(R.id.btn_startNav)
    Button startNavButton;
    @BindView(R.id.tv_navStatus)
    TextView navStatusTextView;
    @BindView(R.id.recy_markList)
    RecyclerView markListRecyclerView;
    @BindView(R.id.srl_markPointList)
    SwipeRefreshLayout srl_markPoint;

    ActionBar actionBar;
    private String mapId;
    private String bean;
    private Handler handler = new Handler();
    private MarkPointRecyAdapter markPointRecyAdapter;
    private LinkedList<MarkPointRespBean> markPointRespBean;
    private static final String TAG = MainActivity.class.getSimpleName();
    /** 导航状态 */
    /** Navigational state */
    private String navStatus = "NAVI_STOP";
    ProgressDialog startNavDialog;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_robotnav;
    }

    @Override
    protected void initView() {
        actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.title_mapNav);
        actionBar.setDisplayHomeAsUpEnabled(true);

        markPointRecyAdapter = new MarkPointRecyAdapter(this);
        markListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        markListRecyclerView.addItemDecoration(new DividerItemDecorations());
        markPointRecyAdapter.setOnItemClickListener(this);
        markListRecyclerView.setAdapter(markPointRecyAdapter);

        startNavDialog = new ProgressDialog(this);
        startNavDialog.setCanceledOnTouchOutside(false);
        startNavDialog.setMessage(getResources().getString(R.string.toast_startNav));

    }

    @Override
    protected void initData() {
        mapId =  getIntent().getStringExtra("defaultMapId");
        bean = getIntent().getStringExtra("bean");

        getNavStatus();
        getMarkList();
    }

    @Override
    protected void initEvent() {
        srl_markPoint.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMarkList();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.btn_getMarkList,R.id.btn_cancelMarkNav,R.id.btn_stopNav,R.id.btn_startNav})
    public void markClick(View view){
        switch (view.getId()){
            case R.id.btn_getMarkList:
                getMarkList();
                break;
            case R.id.btn_cancelMarkNav:
                cancelNav();
                break;
            case R.id.btn_stopNav:
                stopNav();
                break;
            case R.id.btn_startNav:
                startNav();
                break;
            default:
                break;
        }
    }

    /**
     * 取消导航
     * Cancel the navigation
     */
    public void cancelNav(){
        NavigationManager.getInstance().cancelNav(new ActionNotifyCallBack() {
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
//                LogUtils.d(TAG,"取消导航 "+s+" i"+i+" s1 "+s1+" s2 "+s2 +" s3 "+s3);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(i== ActionCenterCode.ACTION_OK){
                            Toast.makeText(RoboNavActivity.this,getResources().getString(R.string.toast_navCancelSuccess),Toast.LENGTH_SHORT).show();
                            getNavStatus();
                        }else {
                            Toast.makeText(RoboNavActivity.this,getResources().getString(R.string.toast_navCancelFail),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
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
                            Toast.makeText(RoboNavActivity.this,getResources().getString(R.string.toast_navStartSuccess),Toast.LENGTH_SHORT).show();
                            getNavStatus();
                        }else {
                            Toast.makeText(RoboNavActivity.this,getResources().getString(R.string.toast_navStartFail),Toast.LENGTH_SHORT).show();
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
            @Override
            public void onRecvActionNotify(ActionRequest actionRequest, String s, String s1, final int i, String s2, String s3) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(i== ActionCenterCode.ACTION_OK){
                            Toast.makeText(RoboNavActivity.this,getResources().getString(R.string.toast_navStopSuccess),Toast.LENGTH_SHORT).show();
                            getNavStatus();
                        }else {
                            Toast.makeText(RoboNavActivity.this,getResources().getString(R.string.toast_navStopFail),Toast.LENGTH_SHORT).show();
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
            @Override
            public void onRecvActionNotify(ActionRequest actionRequest, String s, String s1, int i, String s2, final String s3) {
//                LogUtils.e("导航状态"+i+" s2 "+s2+" s3  "+s3);
                navStatus = s3;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        navStatusTextView.setText(s3);
                    }
                });

            }
        });
    }


    /**
     * 获取标记点列表
     * Gets a list of mark points
     */
    public void getMarkList(){
        NavigationManager.getInstance().getMarkPoint(mapId,new ActionNotifyCallBack() {
            /**
             * 返回
             * return
             * @param actionRequest all json
             * @param s uri
             * @param s1 action Name
             * @param i 执行状态码：0为成功
             *           Execution status code: 0 indicates success
             * @param s2 null
             * @param s3 标记点数组json
             *           Marker cluster json
             */
            @Override
            public void onRecvActionNotify(ActionRequest actionRequest, String s, String s1, int i, String s2, final String s3) {
//                LogUtils.e(TAG,"标记点 "+s+" s1 "+s1+" i "+i+" s2 "+s2+" s3 "+s3);
                if(i== ActionCode.ACTION_OK){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(srl_markPoint.isRefreshing()){
                                srl_markPoint.setRefreshing(false);
                            }
                            Toast.makeText(RoboNavActivity.this,R.string.toast_loadMapSuccess,Toast.LENGTH_SHORT).show();
                            Type listType = new TypeToken<LinkedList<MarkPointRespBean>>(){}.getType();
                            markPointRespBean = new Gson().fromJson(s3, listType);
                            markPointRecyAdapter.getMarkList(markPointRespBean);
                            markPointRecyAdapter.notifyDataSetChanged();
                        }
                    });
                }else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(srl_markPoint.isRefreshing()){
                                srl_markPoint.setRefreshing(false);
                            }
                            Toast.makeText(RoboNavActivity.this,R.string.toast_loadMapFail,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    /**
     * 标记点导航
     * Marker navigation
     * @param view button
     * @param postion 列表位置
     *                  Position in the list of markers
     *  注意：导航前，请确保急停开关已经打开
     *  Note: before navigation, make sure the emergency stop switch is on
     *  注意：导航到标记点，请先确保导航模式因为开启（NAVI_START）
     *  Note: navigate to the marker, please make sure the navigation mode is turned on first
     *  注意：如果多次执行导航到标记点方法，系统会依次执行（2019-3-22 17:32:39 工控版本：1.5.7）
     *  Note: if the navigation to the marker method is executed multiple times, the system will execute in sequence (2019-3-22 17:32:39 industrial control version: 1.5.7)
     */
    @Override
    public void onItemClick(View view, final int postion) {
        if(!navStatus.equals("NAVI_START")){
            AlertDialog.Builder builder = new AlertDialog.Builder(RoboNavActivity.this);
            builder.setTitle(R.string.title_warning)
                    .setMessage(R.string.toast_pleStartNav)
                    .setPositiveButton(R.string.btn_sure,null);
            builder.show();
            return;
        }
        String msg = getResources().getString(R.string.toast_navTo) +  markPointRespBean.get(postion).getText();
        Toast.makeText(RoboNavActivity.this,msg,Toast.LENGTH_SHORT).show();
        NavigationManager.getInstance().navToPointByName(markPointRespBean.get(postion).getText(), new ActionNotifyCallBack() {
            /**
             * 返回
             *  return
             * @param actionRequest all json
             * @param s uri
             * @param s1 Action Name
             * @param i 执行状态码：0为成功，其他为失败
             *           Execution status code: 0 for success, others for failure
             * @param s2 null
             * @param s3 json
             */
            @Override
            public void onRecvActionNotify(ActionRequest actionRequest, String s, String s1, int i, String s2, String s3) {
//                LogUtils.e(TAG,"导航结果 "+i+" s "+s+" s1 "+s1+" s2 "+s2 +" s3 "+s3);
                //导航成功
                //Navigation is successful
                if(i==ActionCode.ACTION_OK){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String msg = getResources().getString(R.string.toast_navToSuccess)+markPointRespBean.get(postion).getText();
                            Toast.makeText(RoboNavActivity.this,msg,Toast.LENGTH_SHORT).show();
                        }
                    });
                //导航失败 请参考SDK文档或者本Demo中的ActionCenterCode简介类 排查原因
                //Please refer to SDK document or ActionCenterCode introduction class in this Demo for reasons of navigation failure
                }else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String msg = getResources().getString(R.string.toast_navToFail)+markPointRespBean.get(postion).getText();
                            Toast.makeText(RoboNavActivity.this,msg,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(startNavDialog!=null){
            startNavDialog.dismiss();
        }
        if(srl_markPoint.isRefreshing()){
            srl_markPoint.setRefreshing(false);
        }
    }
}
