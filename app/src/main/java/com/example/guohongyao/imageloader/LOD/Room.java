package com.example.guohongyao.imageloader.LOD;

/**
 * Created by GuoHongyao on 2016/6/20.
 */
public class Room {
    public float area;
    public float price;

    public Room(float area, float price) {
        this.area = area;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room [area="+area+",price="+price+"]";
    }
}
