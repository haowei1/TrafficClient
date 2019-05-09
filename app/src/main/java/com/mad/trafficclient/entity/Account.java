package com.mad.trafficclient.entity;

public class Account {

    private int id;
    private String carId;
    private String money;
    private String name;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", carId='" + carId + '\'' +
                ", money='" + money + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
