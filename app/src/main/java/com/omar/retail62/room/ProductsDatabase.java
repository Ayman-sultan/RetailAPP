package com.omar.retail62.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.omar.retail62.models.ProductModel;

@Database(entities = {ProductModel.class} , version = 2 , exportSchema = false)
public abstract class ProductsDatabase extends RoomDatabase {

    public abstract ProductDAO gerProductDao();
}
