package com.amy.amynavsdkdemo.bean;

import java.util.List;

/**
 * @author Async_wu
 * @date 2019-3-22 10:41:54
 * 地图列表
 * maps entity
 */
public class MapListRespBean {

    /**
     * defaultNavMapId : 180eb0db-0651-4179-89a1-a7866227ec24
     * mapFileInfoList : [{"createTime":"2019-03-14_14:37:45","height":512,"id":"180eb0db-0651-4179-89a1-a7866227ec24","name":"测试","ratio":0.05,"width":544,"x":-12.2,"y":-12.2,"z":0}]
     */
    /** 默认地图 id*/
    // Default map id
    private String defaultNavMapId;
    /** 地图列表 */
    // Map list
    private List<MapFileInfoListBean> mapFileInfoList;

    public String getDefaultNavMapId() {
        return defaultNavMapId;
    }

    public void setDefaultNavMapId(String defaultNavMapId) {
        this.defaultNavMapId = defaultNavMapId;
    }

    public List<MapFileInfoListBean> getMapFileInfoList() {
        return mapFileInfoList;
    }

    public void setMapFileInfoList(List<MapFileInfoListBean> mapFileInfoList) {
        this.mapFileInfoList = mapFileInfoList;
    }

    public static class MapFileInfoListBean {
        /**
         * createTime : 2019-03-14_14:37:45
         * height : 512
         * id : 180eb0db-0651-4179-89a1-a7866227ec24
         * name : 测试
         * ratio : 0.05
         * width : 544
         * x : -12.2
         * y : -12.2
         * z : 0.0
         */
        /** 创建时间 */
        /** Creation Time */
        private String createTime;
        /** 地图长度 */
        /** The length of the map */
        private int height;
        /** 地图ID */
        /** map ID */
        private String id;
        /** 地图名称 */
        /** Map Name */
        private String name;
        /** 地图比例尺 */
        /** map ratio */
        private double ratio;
        /** 地图宽度 */
        /** The width of the map */
        private int width;
        /** 三维系数 */
        /** The three dimensional coefficients */
        private double x;
        private double y;
        private double z;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getRatio() {
            return ratio;
        }

        public void setRatio(double ratio) {
            this.ratio = ratio;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getZ() {
            return z;
        }

        public void setZ(double z) {
            this.z = z;
        }
    }
}
