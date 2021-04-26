package com.example.miniorderingapp.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.miniorderingapp.Models.ProductDataModel;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProductsDao {

    @Insert(onConflict = REPLACE)
    void insertAll(List<ProductDataModel> all_products);

    @Delete
    void delete(ProductDataModel productDataModel);

    @Delete
    void reset(List<ProductDataModel> all_products);

    @Query("SELECT * FROM products_details")
    List<ProductDataModel> getAllProducts();
}
