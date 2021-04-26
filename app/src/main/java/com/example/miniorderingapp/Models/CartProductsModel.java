package com.example.miniorderingapp.Models;

import java.io.Serializable;

public class CartProductsModel implements Serializable {

    int product_id;
    int product_brand_id;
    String product_name;
    String product_code;
    int mrp;
    int price;
    int product_weight;
    String product_weight_unit;
    int product_item_count;

    public CartProductsModel(int product_id, int product_brand_id, String product_name, String product_code, int mrp, int price, int product_weight, String product_weight_unit, int product_item_count) {
        this.product_id = product_id;
        this.product_brand_id = product_brand_id;
        this.product_name = product_name;
        this.product_code = product_code;
        this.mrp = mrp;
        this.price = price;
        this.product_weight = product_weight;
        this.product_weight_unit = product_weight_unit;
        this.product_item_count = product_item_count;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_brand_id() {
        return product_brand_id;
    }

    public void setProduct_brand_id(int product_brand_id) {
        this.product_brand_id = product_brand_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public int getMrp() {
        return mrp;
    }

    public void setMrp(int mrp) {
        this.mrp = mrp;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProduct_weight() {
        return product_weight;
    }

    public void setProduct_weight(int product_weight) {
        this.product_weight = product_weight;
    }

    public String getProduct_weight_unit() {
        return product_weight_unit;
    }

    public void setProduct_weight_unit(String product_weight_unit) {
        this.product_weight_unit = product_weight_unit;
    }

    public int getProduct_item_count() {
        return product_item_count;
    }

    public void setProduct_item_count(int product_item_count) {
        this.product_item_count = product_item_count;
    }
}
