package com.mad.trafficclient.entity;

public class Traffic {

    /**
     * YellowTime : 5
     * GreenTime : 5
     * RedTime : 5
     */

    private int id;
    private int YellowTime;
    private int GreenTime;
    private int RedTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYellowTime() {
        return YellowTime;
    }

    public void setYellowTime(int YellowTime) {
        this.YellowTime = YellowTime;
    }

    public int getGreenTime() {
        return GreenTime;
    }

    public void setGreenTime(int GreenTime) {
        this.GreenTime = GreenTime;
    }

    public int getRedTime() {
        return RedTime;
    }

    public void setRedTime(int RedTime) {
        this.RedTime = RedTime;
    }

    @Override
    public String toString() {
        return "Traffic{" +
                "id=" + id +
                ", YellowTime=" + YellowTime +
                ", GreenTime=" + GreenTime +
                ", RedTime=" + RedTime +
                '}';
    }
}
