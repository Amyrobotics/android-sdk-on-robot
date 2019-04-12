package com.amy.amynavsdkdemo.bean;

/**
 * @author Async_wu
 * @date 2019-3-22 15:08:01
 * 标记点列表
 * Marker entity
 */
public class MarkPointRespBean {

    /**
     * angle : 353.625
     * isStartPoint : true
     * radian : 6.171921
     * realAngle : 6.375
     * realX : 1.0851471
     * realY : -0.003567505
     * text : StartPoint
     * type : 0
     * x : 531.4059
     * y : 536.1427
     * desc :
     * name2 :
     */
    /** 角度 */
    /** angle */
    private double angle;
    /** 是否为起始点 */
    /** Is the starting point*/
    private boolean isStartPoint;
    /** 方位角度 */
    /** compass bearing */
    private double radian;
    /** 实际角度 */
    /**  Real Angle */
    private double realAngle;
    /** 地图实际x轴坐标 */
    /** The actual X-axis coordinates of the map  */
    private double realX;
    /** 地图实际y轴坐标 */
    /** The actual Y-axis coordinates of the map  */
    private double realY;
    /** 地图名称 */
    /** Map Name */
    private String text;
    /** 地图 类型 */
    /** MapType */
    private int type;
    /** 地图x轴坐标 */
    /** Map x coordinates */
    private double x;
    /** 地图y轴坐标 */
    /** Map y coordinates */
    private double y;
    /** 描述 */
    /** description  */
    private String desc;
    /** 别名 */
    /** alias */
    private String name2;

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public boolean isIsStartPoint() {
        return isStartPoint;
    }

    public void setIsStartPoint(boolean isStartPoint) {
        this.isStartPoint = isStartPoint;
    }

    public double getRadian() {
        return radian;
    }

    public void setRadian(double radian) {
        this.radian = radian;
    }

    public double getRealAngle() {
        return realAngle;
    }

    public void setRealAngle(double realAngle) {
        this.realAngle = realAngle;
    }

    public double getRealX() {
        return realX;
    }

    public void setRealX(double realX) {
        this.realX = realX;
    }

    public double getRealY() {
        return realY;
    }

    public void setRealY(double realY) {
        this.realY = realY;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
}
