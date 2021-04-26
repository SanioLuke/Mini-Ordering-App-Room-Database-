package com.example.miniorderingapp.DB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.miniorderingapp.Models.OrdersModel;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface OrdersDao {

    @Insert(onConflict = REPLACE)
    Long insert(OrdersModel order);

    @Query("SELECT * FROM orders_table")
    List<OrdersModel> getAllProducts();
}
