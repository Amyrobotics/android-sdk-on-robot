package com.amy.amynavsdkdemo.activity;



import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.amy.amynavsdkdemo.R;
import com.amy.amynavsdkdemo.base.BaseActivity;
import com.amy.amynavsdkdemo.manager.RobotControlManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Async_wu
 * @date sj
 * 机器人运动、跳舞、跟随、头部控制、灯环控制
 * Robot movement, dance, follow, head control, lamp control
 */
public class RobotControlActivity extends BaseActivity {
    ActionBar actionBar;
    @BindView(R.id.btn_moveForward)
    Button forwardButton;
    @BindView(R.id.btn_moveBack)
    Button backButton;
    @BindView(R.id.btn_moveStop)
    Button stopButton;
    @BindView(R.id.btn_turnLeft)
    Button turnLeftButton;
    @BindView(R.id.btn_turnRight)
    Button turnRightButton;
    @BindView(R.id.btn_turnStop)
    Button turnstopButton;
    @BindView(R.id.btn_startDance)
    Button startDance;
    @BindView(R.id.btn_stopDance)
    Button stopDance;
    @BindView(R.id.btn_startFollow)
    Button startFollowButton;
    @BindView(R.id.btn_stopFollow)
    Button stopFollowButton;
    @BindView(R.id.btn_stopAll)
    Button stopAllButton;
    @BindView(R.id.btn_headLeft)
    Button headLeftButton;
    @BindView(R.id.btn_headRight)
    Button headRightButton;
    @BindView(R.id.btn_headLeftRight)
    Button headLeftRightButton;
    @BindView(R.id.btn_headUp)
    Button headUpButton;
    @BindView(R.id.btn_headDown)
    Button headDownButton;
    @BindView(R.id.btn_headUpDown)
    Button headStopButton;
    @BindView(R.id.btn_headReset)
    Button headResetButton;
    @BindView(R.id.btn_lightNormal)
    Button lightNormalButton;
    @BindView(R.id.btn_lightTalk)
    Button lightTalkButton;
    @BindView(R.id.btn_lightListen)
    Button lightListenButton;
    @BindView(R.id.btn_lightThink)
    Button lightThinkButton;
    @BindView(R.id.btn_lightSing)
    Button lightSingButton;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_robotcontrol;
    }

    @Override
    protected void initView() {
        actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.btn_robotControl);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 机器人移动
     * Robot movement
     * @param view view
     */
    @OnClick({R.id.btn_moveForward,R.id.btn_moveBack,R.id.btn_moveStop,R.id.btn_turnLeft,R.id.btn_turnRight,R.id.btn_turnStop})
    public void moveClick(View view){
        switch (view.getId()){
            case R.id.btn_moveForward:
                RobotControlManager.getInstance().moveForward();
                break;
            case R.id.btn_moveBack:
                RobotControlManager.getInstance().moveBack();
                break;
            case R.id.btn_moveStop:
                RobotControlManager.getInstance().moveStop();
                break;
            case R.id.btn_turnLeft:
                RobotControlManager.getInstance().turnLeft();
                break;
            case R.id.btn_turnRight:
                RobotControlManager.getInstance().turnRight();
                break;
            case R.id.btn_turnStop:
                RobotControlManager.getInstance().turnRound();
                break;
                default:
                    break;
        }
    }

    /**
     * 机器人跳舞
     * Robot dance
     * @param view view
     */
    @OnClick({R.id.btn_startDance,R.id.btn_stopDance})
    public void danceClick(View view){
        switch (view.getId()){
            case R.id.btn_startDance:
                RobotControlManager.getInstance().startDance();
                break;
            case R.id.btn_stopDance:
                RobotControlManager.getInstance().stopDance();
                break;
                default:
                    break;
        }
    }

    /**
     * 机器人跟随
     * Robot following
     * @param view view
     */
    @OnClick({R.id.btn_startFollow,R.id.btn_stopFollow})
    public void followClick(View view){
        switch (view.getId()){
            case R.id.btn_startFollow:
                RobotControlManager.getInstance().startFollow();
                break;
            case R.id.btn_stopFollow:
                RobotControlManager.getInstance().stopFollow();
                break;
                default:
                    break;
        }
    }

    /**
     * 机器人停止 导航 唱歌 跳舞等动作
     * The robot stops the navigation, singing, dancing and other actions
     */
    @OnClick(R.id.btn_stopAll)
    public void stopAll(){
        RobotControlManager.getInstance().stopAll();
    }

    /**
     * 机器人头部转动
     * Robot head rotation
     * @param view view
     */
    @OnClick({R.id.btn_headUp,R.id.btn_headDown,R.id.btn_headUpDown,R.id.btn_headLeft,R.id.btn_headRight,R.id.btn_headLeftRight,R.id.btn_headReset})
    public void headClick(View view){
        switch (view.getId()){
            case R.id.btn_headLeft:
                RobotControlManager.getInstance().headLeft();
                break;
            case R.id.btn_headRight:
                RobotControlManager.getInstance().headRight();
                break;
            case R.id.btn_headLeftRight:
                RobotControlManager.getInstance().headLeftRight();
                break;
            case R.id.btn_headUp:
                RobotControlManager.getInstance().headUp();
                break;
            case R.id.btn_headDown:
                RobotControlManager.getInstance().headDown();
                break;
            case R.id.btn_headUpDown:
                RobotControlManager.getInstance().headUpDown();
                break;
            case R.id.btn_headReset:
                RobotControlManager.getInstance().headReset();
                break;
                default:
                    break;
        }
    }

    /**
     * 灯环状态
     * The lamp ring state
     * @param view
     */
    @OnClick({R.id.btn_lightNormal,R.id.btn_lightSing,R.id.btn_lightThink,R.id.btn_lightListen,R.id.btn_lightTalk})
    public void lightClick(View view){
        switch (view.getId()){
            case R.id.btn_lightNormal:
                RobotControlManager.getInstance().lightNormal();
                break;
            case R.id.btn_lightListen:
                RobotControlManager.getInstance().lightListen();
                break;
            case R.id.btn_lightTalk:
                RobotControlManager.getInstance().lightTalk();
                break;
            case R.id.btn_lightSing:
                RobotControlManager.getInstance().lightSing();
                break;
            case R.id.btn_lightThink:
                RobotControlManager.getInstance().lightThink();
                break;
                default:
                    break;
        }
    }

}
