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
}
