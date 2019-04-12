package com.amy.amynavsdkdemo.manager;

/**
 * Created by adward on 2017/11/23.
 */
public interface RobotNavActionNames {
    //robot action uri
    String REMOTE_SERVER_ACTION_URI = "content://com.amy.remote.server.actionprovider";

    //================ 运动 ==================
    //================ sports  ==================
    //移动指令
    //move instruction
    String MOVE_FORWARD = "robot.goForward";
    String TURN_LEFT = "robot.turnLeft";
    String TURN_RIGHT = "robot.turnRight";
    String MOVE_BACK = "robot.moveBack";

    //转圈
    //Turn around
    String TURN_ROUND = "robot.turnRound";

    //停止移动
    //come to rest
    String STOP_WALKING = "robot.stopWalking";

    //跟随
    //follow
    String FOLLOW = "robot.follow";
    //停止跟随
    //Stop to follow
    String STOP_FOLLOW = "robot.stopFollow";

    //停止全部（停止移动，停止跟随，停止导航，停止唱歌，停止跳舞，停止漫游）
    //Stop all (stop moving, stop following, stop navigating, stop singing, stop dancing, stop roaming)
    String STOP_ALL = "robot.stopAll";

    //================ 跳舞 ==================
    //================ dance ==================
    //跳舞
    //dance
    String ROBOT_DANCE = "robot.dance";
    //停止跳舞
    //stop dance
    String ROBOT_STOP_DANCE = "robot.stopDance";

    //=============== 头部 ===================
    //=============== head ===================
    //head turn
    String TURN_HEAD_RIGHT = "robot.turnHeadRight";
    String TURN_HEAD_LEFT = "robot.turnHeadLeft";
    String TURN_HEAD_UP = "robot.turnHeadUp";
    String TURN_HEAD_DOWN = "robot.turnHeadDown";

    //头部复位
    //The head is reset
    String TURN_HEAD_RESET = "robot.turnHeadReset";
    //点头
    //nod one's head
    String HEAD_UP_DOWN = "robot.headUpDown";
    //摇头
    //shake one's head
    String HEAD_LEFT_RIGHT = "robot.headLeftRight";

    //============== 灯环 ==================
    //============== The lamp ring ==================
    //灯环控制
    //Light loop control
    String LIGHT_NORMAL = "robot.lightNormal";

    String LIGHT_TALKING = "robot.lightTalking";

    String LIGHT_THINKING = "robot.lightThinking";

    String LIGHT_LISTENING = "robot.lightListening";

    String LIGHT_SINGING = "robot.lightSinging";

    //============== 导航 ==================
    //============== navigation ==================
    //开始导航，使用默认导航地图进行导航
    //Start navigation using the default navigation map
    String START_NAV = "robot.startNav";
    //使用地图id 开启导航
    //Use the map id to open the navigation
    String START_NAV_BY_MAP_ID = "robot.startNavByMapId";
    //导航去某点
    //Navigate to a point
    String NAV_TO_POINT= "robot.navToPoint";
    //根据名称导航到某个点
    //Navigate to a point by name
    String NAV_TO_POINT_BY_NAME = "robot.navToPointByName";
    //获取导航状态
    //Get navigation status
    String GET_NAV_STATE = "robot.getNavState";

    //停止导航
    //Stop navigation
    String STOP_NAV = "robot.stopNav";
    //取消导航
    //Cancel navigation
    String CANCEL_NAV = "robot.cancelNav";

    //回充
    //to recharge
    String BACK_DOCK = "robot.backDock";
    //取消回冲
    //Cancel the backout
    String CANCEL_BACK_DOCK = "robot.cancelBackDock";

    //add 2018-06-21
    //获取地图列表
    //Get map list
    String GET_MAP_LIST = "robot.getMapList";
    //获取标记列表
    //Get tag list
    String GET_MARK_POINT_LIST = "robot.getMarkPointList";

    //============== 任务 ====================
    //============== task ====================
    //任务开始
    //mission start
    String ROBOT_TASK_START = "robot.taskStart";
    //任务停止
    //task quit
    String ROBOT_TASK_STOP = "robot.taskStop";
    //任务暂停
    //task suspension
    String ROBOT_TASK_PAUSE = "robot.taskPause";
    //获取任务进度
    //Get task progress
    String ROBOT_TASK_GET_STATUS = "robot.getTaskStatus";

    String ROBOT_TASK_START_BY_NAME = "robot.taskStartByName";

    //============== 人体感应 ====================
    //============== body induction ====================
    //人体感应
    //body induction
    String ROBOT_HUMAN_INDUCTION_START = "robot.humanInductionStart";

    String ROBOT_HUMAN_INDUCTION_STOP = "robot.humanInductionStop";


}
