package com.amy.amynavsdkdemo.manager;

import com.amy.robot.actioncenter.api.robot.RobotActionCenterApi;
import com.amy.robot.actioncenter.api.robot.RobotActionCenterApiImp;

/**
 * @author Async_wu
 * @date 2019-3-20 16:30:39
 * 机器人控制
 * Robot Control
 */
public class RobotControlManager {
    private static RobotControlManager instance;
    private RobotControlManager(){}
    private static RobotActionCenterApi robotActionCenterApi;

    public static RobotControlManager getInstance() {
        if(instance==null){
            synchronized (RobotControlManager.class){
                if(instance==null){
                    instance = new RobotControlManager();
                    robotActionCenterApi = new RobotActionCenterApiImp();
                }
            }
        }
        return instance;
    }

    /**
     * 往前移动
     *  One Forwards
     */
    public void moveForward(){
        robotActionCenterApi.moveForward();
    }

    /**
     * 往后移动
     * move backward
     */
    public void moveBack(){
        robotActionCenterApi.moveBack();
    }

    /**
     * 停止移动
     *  come to rest
     */
    public void moveStop(){
        robotActionCenterApi.stopWalking();
    }

    /**
     * 向左转
     * turn left
     */
    public void turnLeft(){
        robotActionCenterApi.turnLeft();
    }

    /**
     * 向右转
     * turn right
     */
    public void turnRight(){
        robotActionCenterApi.turnRight();
    }

    /**
     * 停止转向
     * Stop turning
     */
    public void turnRound(){
        robotActionCenterApi.turnRound();
    }

    /**
     * 开始跳舞
     * Start Dancing
     */
    public void startDance(){
        robotActionCenterApi.robotDance();
    }

    /**
     * 停止跳舞
     * Stop dancing
     */
    public void stopDance(){
        robotActionCenterApi.robotStopDance();
    }

    /**
     * 开启跟随
     * Open the following
     */
    public void startFollow(){
        robotActionCenterApi.robotFollow();
    }

    /**
     * 结束跟随
     * End to follow
     */
    public void stopFollow(){
        robotActionCenterApi.stopFollow();
    }

    /**
     * 结束所有任务
     * End all missions
     */
    public void stopAll(){
        robotActionCenterApi.stopAll();
    }

    /**
     * 头部向左转
     * Head left
     */
    public void headLeft(){
        robotActionCenterApi.turnHeadLeft();
    }
    /**
     * 头部向右转
     * Head right
     */
    public void headRight(){
        robotActionCenterApi.turnHeadRight();
    }
    /**
     * 头部向左右转
     * The head turns left and right
     */
    public void headLeftRight(){
        robotActionCenterApi.headLeftRight();
    }
    /**
     * 头部向上转
     * Head up
     */
    public void headUp(){
        robotActionCenterApi.turnHeadUp();
    }
    /**
     * 头部向下转
     * Head down
     */
    public void headDown(){
        robotActionCenterApi.turnHeadDown();
    }
    /**
     * 头部向上下转
     * Head up and down
     */
    public void headUpDown(){
        robotActionCenterApi.headUpDown();
    }

    /**
     * 头部重置
     * Head to reset
     */
    public void headReset(){
        robotActionCenterApi.turnHeadReset();
    }


    /**
     * 正常状态下灯环
     * Lamp rings in normal condition
     */
    public void lightNormal(){
        robotActionCenterApi.lightNormal();
    }
    /**
     * 说话状态下灯环
     * Lamp rings while speaking
     */
    public void lightTalk(){
        robotActionCenterApi.lightTalking();
    }
    /**
     * 监听状态下灯环
     * Lamp rings while listening
     */
    public void lightListen(){
        robotActionCenterApi.lightListening();
    }
    /**
     * 思考状态下灯环
     * Lamp rings while thinking
     */
    public void lightThink(){
        robotActionCenterApi.lightThinking();
    }
    /**
     * 唱歌状态下灯环
     * Lamp rings while singing
     */
    public void lightSing(){
        robotActionCenterApi.lightSinging();
    }

}
