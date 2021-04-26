package com.example.miniorderingapp.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "orders_table")
public class OrdersModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int order_id;

    @ColumnInfo(name = "mrp_total_price")
    int mrp_total_price;

    @ColumnInfo(name = "discount_total_price")
    int discount_total_price;

    @ColumnInfo(name = "final_total_price")
    int final_total_price;

    @ColumnInfo(name = "all_prod_arr")
    String all_prod_arr;

    @ColumnInfo(name = "order_datetime")
    String order_datetime;

    public OrdersModel(int mrp_total_price, int discount_total_price, int final_total_price, String all_prod_arr, String order_datetime) {
        this.mrp_total_price = mrp_total_price;
        this.discount_total_price = discount_total_price;
        this.final_total_price = final_total_price;
        this.all_prod_arr = all_prod_arr;
        this.order_datetime = order_datetime;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getMrp_total_price() {
        return mrp_total_price;
    }

    public void setMrp_total_price(int mrp_total_price) {
        this.mrp_total_price = mrp_total_price;
    }

    public int getDiscount_total_price() {
        return discount_total_price;
    }

    public void setDiscount_total_price(int discount_total_price) {
        this.discount_total_price = discount_total_price;
    }

    public int getFinal_total_price() {
        return final_total_price;
    }

    public void setFinal_total_price(int final_total_price) {
        this.final_total_price = final_total_price;
    }

    public String getAll_prod_arr() {
        return all_prod_arr;
    }

    public void setAll_prod_arr(String all_prod_arr) {
        this.all_prod_arr = all_prod_arr;
    }

    public String getOrder_datetime() {
        return order_datetime;
    }

    public void setOrder_datetime(String order_datetime) {
        this.order_datetime = order_datetime;
    }
}
