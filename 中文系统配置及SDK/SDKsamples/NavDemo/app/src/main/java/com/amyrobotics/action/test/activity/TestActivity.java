package com.amyrobotics.action.test.activity;

import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.amy.robot.actioncenter.api.robot.RobotActionCenterApiImp;
import com.amy.robot.actioncenter.api.robot.RobotNavActionApiImp;
import com.amy.robot.actioncenter.api.system.SystemActionCenterApiImp;
import com.amy.robot.actioncenter.common.bean.ActionRequest;
import com.amy.robot.actioncenter.lib.ActionNotifyCallBack;
import com.amyrobotics.action.test.R;
import com.amyrobotics.action.test.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {
    @BindView(R.id.btn_start)
    Button btn_start;
    @BindView(R.id.lv_log_list)
    ListView lv_log_list;

    List<Map<String, String>> logData;
    private SimpleAdapter adapter;
    private static final String TAG = "TestActivity";

    private SystemActionCenterApiImp systemActionCenterApiImp;
    private RobotActionCenterApiImp robotActionCenterApiImp;
    //线程池(thread pool)
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    private volatile boolean inTask = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        //机器控制api接口（controll robot api instance）
        robotActionCenterApiImp = new RobotActionCenterApiImp();
        //系统控制api接口（controll robot system api instance）
        systemActionCenterApiImp = new SystemActionCenterApiImp();

        logData = new ArrayList<Map<String, String>>();
        String[] strings = {"log"};
        int[] ids = {R.id.tv_item_log};
        adapter = new SimpleAdapter(this, logData, R.layout.listitem_log, strings, ids);
        lv_log_list.setAdapter(adapter);
    }

    @OnClick({
            R.id.btn_start,
    })
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                //开始任务（start task）
                doTask();
                break;

        }
    }


    /**
     * 开始任务（start task）
     */
    private void doTask() {
        //判断是否正在执行任务（Task is runing, return）
        if (inTask) {
            return;
        }
        inTask = true;
        robotActionCenterApiImp.robotStopDance();//防止正在跳舞的情况（stop dance）

        //新任务开始，清空界面(start new task, clear UI)
        logData.clear();
        adapter.notifyDataSetChanged();


        //任务放在线程里(execute task in thread)
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //后退（back）
                showLog(getString(R.string.back));
                robotActionCenterApiImp.moveBack();
                SystemClock.sleep(3000);

                // 前进（go forward）
                showLog(getString(R.string.go_forward));
                robotActionCenterApiImp.moveForward();
                SystemClock.sleep(3000);

                // 左转(turn left)
                showLog(getString(R.string.turn_left));
                robotActionCenterApiImp.turnRight();
                SystemClock.sleep(3000);

                // 右转(turn right)
                showLog(getString(R.string.trun_right));
                robotActionCenterApiImp.turnLeft();
                SystemClock.sleep(3000);

                // 头部左转（Turn head to the left）
                showLog(getString(R.string.turn_head_left));
                robotActionCenterApiImp.turnHeadLeft();
                SystemClock.sleep(4000);

                // 头部右转（Turn head to the right）
                showLog(getString(R.string.turn_head_right));
                robotActionCenterApiImp.turnHeadRight();
                SystemClock.sleep(8000);

                // 头部复位(head reset)
                showLog(getString(R.string.reset_head));
                robotActionCenterApiImp.turnHeadReset();
                SystemClock.sleep(4000);

                // 头部抬头(Head up)
                showLog(getString(R.string.head_up));
                robotActionCenterApiImp.turnHeadUp();
                SystemClock.sleep(3000);

                // 头部低头(Bow down)
                showLog(getString(R.string.bow_down));
                robotActionCenterApiImp.turnHeadDown();
                SystemClock.sleep(3000);

                // 头部复位(head reset)
                showLog(getString(R.string.reset_head));
                robotActionCenterApiImp.turnHeadReset();
                SystemClock.sleep(2000);

                // 灯环绿色(green lamp)
                showLog(getString(R.string.green_lamp));
                robotActionCenterApiImp.lightListening();
                SystemClock.sleep(2000);

                // 灯环蓝色 (blue lamp)
                showLog(getString(R.string.blue_lamp));
                robotActionCenterApiImp.lightNormal();
                SystemClock.sleep(2000);

                // 灯环彩色变换(Coloured lamp)
                showLog("灯环色彩变幻");
                robotActionCenterApiImp.lightSinging();
                SystemClock.sleep(5000);

                // 灯环紫色(purple lamp)
                showLog("灯环紫色");
                robotActionCenterApiImp.lightTalking();
                SystemClock.sleep(2000);

                // 灯环蓝色 (blue lamp)
                showLog(getString(R.string.blue_lamp));
                robotActionCenterApiImp.lightThinking();
                SystemClock.sleep(2000);

                showLog(getString(R.string.start_speak) + getString(R.string.dancing));
                systemActionCenterApiImp.speakStart(getString(R.string.dancing), 0, new ActionNotifyCallBack() {
                    @Override
                    public void onRecvActionNotify(ActionRequest notifyRequest,
                                                   String srcURI, String notifyAction, final int notifyCode,
                                                   final String notifyInfo, String notifyParams) {
                        switch (notifyInfo) {
                            case "complete":
                                showLog(getString(R.string.speak_done_and_dance));
                                robotActionCenterApiImp.robotDance();
                                showLog(getString(R.string.start_dance_and_click_stop));

                                inTask = false;
                                break;
                            case "timeout":
                                showLog(getString(R.string.init_timeout));
                                break;
                        }
                    }
                });
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止跳舞（stop dancing）
        robotActionCenterApiImp.robotStopDance();
    }

    /**
     * 打印即显示日志（print log）
     */
    private void showLog(final String log) {
        //界面刷新在主线程进行 (refresh ui at main thread)
        TestActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //添加数据 (add data)
                Map<String, String> map = new HashMap<>();
                map.put("log", log);
                logData.add(map);

                //刷新界面(refresh ui)
                adapter.notifyDataSetChanged();

                //打印日志（print log）
                LogUtils.i(TAG, log);
            }
        });
    }
}

