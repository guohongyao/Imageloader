package com.example.guohongyao.imageloader.LOD;

import java.util.List;

/**
 * Created by GuoHongyao on 2016/6/20.
 */
public class Tenant {
    public float roomArea;
    public float roomPrice;
    public static final float diffPrice=100.0f;
    public static final float diffArea=0.0f;

    public void rentRoom(Mediator mediator){
        List<Room> rooms=mediator.getAllRooms();
        for(Room room:rooms){
            if(isSuitable(room)){
                System.out.println("OK_"+room);
                break;
            }
        }
    }

    private boolean isSuitable(Room room) {
        return Math.abs(room.price-roomPrice)<diffPrice
                &&Math.abs(room.area-roomArea)<diffArea;
    }
}
