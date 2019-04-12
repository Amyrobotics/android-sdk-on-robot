package com.amy.amynavsdkdemo.manager;

import com.amy.robot.actioncenter.api.entity.map.MapListEntity;
import com.amy.robot.actioncenter.api.entity.map.MarkPointEntity;
import com.amy.robot.actioncenter.api.robot.RobotNavActionApiImp;
import com.amy.robot.actioncenter.lib.ActionNotifyCallBack;
import com.amy.robot.actioncenter.lib.ActionResultCallback;

import java.util.List;

/**
 * @author Async_wu
 * @date 2019年3月22日18:40:14
 * Navigation manager
 */
public class NavigationManager {
    private static NavigationManager instance;
    private static RobotNavActionApiImp robotNavActionApiImp;
    private NavigationManager() {}
    public static NavigationManager getInstance(){
        if(instance==null){
            synchronized (NavigationManager.class){
                if(instance==null){
                    instance = new NavigationManager();
                    robotNavActionApiImp = new RobotNavActionApiImp();
                }
            }
        }
        return instance;
    }


    /**
     * 启动导航
     * Start the navigation
     * @param callBack 回调
     */
    public void startNav(ActionNotifyCallBack callBack){
        robotNavActionApiImp.startNav(callBack);
    }

    /**
     * 关闭导航
     * Close the navigation
     * @param callBack 回调
     */
    public void stopNav(ActionNotifyCallBack callBack){
        robotNavActionApiImp.stopNav(callBack);
    }

    /**
     * 取消本次导航
     * Cancel this navigation
     * @param callBack 回调
     */
    public void cancelNav(ActionNotifyCallBack callBack){
        robotNavActionApiImp.cancelNav( callBack);
    }


    /**
     * 获取地图列表
     * Get map list
     * @param callBack  回调
     */
    public void getMapList(ActionNotifyCallBack callBack) {
        LocalActionCenterManager.getInstance().sendAsynAction(RobotNavActionNames.REMOTE_SERVER_ACTION_URI, RobotNavActionNames.GET_MAP_LIST,
                -1, null, null, callBack);
    }

    /**
     * 获取导航状态
     * Get navigation status
     * @param callBack 回调
     */
    public void getNavStatus(ActionNotifyCallBack callBack){
        robotNavActionApiImp.getNavState(callBack);
    }


    /**
     * 获取标记点列表
     * Gets a list of tag points
     * @param mapId 地图ID
     *                 map Id
     * @param callBack 回调
     */
    public void getMarkPoint(String mapId,ActionNotifyCallBack callBack){
        LocalActionCenterManager.getInstance().sendAsynAction(RobotNavActionNames.REMOTE_SERVER_ACTION_URI, RobotNavActionNames.GET_MARK_POINT_LIST,
                -1, mapId, null, callBack);
    }

    /**
     * 根据标记点名 导航
     * Navigate by name according to the mark
     * @param name 标记点text
     *              Marker name
     * @param callBack 回调
     */
    public void navToPointByName(String name,ActionNotifyCallBack callBack){
        robotNavActionApiImp.navToPointByName(name,callBack);
    }


}
