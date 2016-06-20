package com.example.guohongyao.imageloader.LOD;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GuoHongyao on 2016/6/20.
 */
public class Mediator {
    List<Room> rooms=new ArrayList<Room>();

    public Mediator() {
        for (int i=0;i<5;i++){
            rooms.add(new Room(14+i,(14+i)*150));
        }
    }
    public List<Room> getAllRooms(){
        return rooms;
    }
    public Room rentRoom(float area,float price){
        List<Room> rooms=getAllRooms();
        for(Room room:rooms){
            if(isSuitable(area,price,room)){
                return room;
            }
        }
        return null;
    }

    private boolean isSuitable(float area,float price,Room room) {
        return Math.abs(room.price-price)<Tenant.diffPrice
                &&Math.abs(room.area-area)<Tenant.diffArea;
    }
}
