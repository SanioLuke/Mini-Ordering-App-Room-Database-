package com.example.miniorderingapp.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.miniorderingapp.Models.OrdersModel;
import com.example.miniorderingapp.Models.ProductDataModel;

@Database(entities = {ProductDataModel.class, OrdersModel.class}, version = 2, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static final String DATABASE_NAME = "productsDB";
    private static RoomDB roomDB;

    public synchronized static RoomDB getInstance(Context context) {
        if (roomDB == null) {
            roomDB = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return roomDB;
    }

    public abstract ProductsDao productDao();

    public abstract OrdersDao ordersDao();
}
