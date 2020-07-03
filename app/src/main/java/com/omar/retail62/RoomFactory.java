package com.omar.retail62;

import android.content.Context;

import androidx.room.Room;

import com.omar.retail62.room.ProductsDatabase;

public class RoomFactory {

    private static ProductsDatabase roomDatabase;

    private RoomFactory(){

    }


    public static ProductsDatabase createOrGetRoomObject(Context context){

        if (roomDatabase == null){

            roomDatabase = Room.databaseBuilder(context,ProductsDatabase.class,"products_database")
                    .build();
        }

        return roomDatabase;
    }

}
