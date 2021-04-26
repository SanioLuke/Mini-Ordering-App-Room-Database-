package com.example.miniorderingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniorderingapp.Adapters.CartAdapter;
import com.example.miniorderingapp.Models.CartProductsModel;
import com.example.miniorderingapp.Models.Functions;
import com.example.miniorderingapp.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ImageButton cart_back_btn;
    ArrayList<CartProductsModel> cart_products_arr = new ArrayList<>();
    RecyclerView cart_items_rec;
    TextView cart_allclear;
    CartAdapter cartAdapter;

    View cart_order_lay, cart_main_lay;
    TextView cart_main_price, cart_discount_price, cart_final_price, cart_count_items, cart_total_price, cart_orderbtn;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        new Functions().lightstatusbardesign(CartActivity.this);
        cart_back_btn = findViewById(R.id.cart_back_btn);
        cart_items_rec = findViewById(R.id.cart_items_rec);
        cart_main_price = findViewById(R.id.cart_main_price);
        cart_discount_price = findViewById(R.id.cart_discount_price);
        cart_final_price = findViewById(R.id.cart_final_price);
        cart_allclear = findViewById(R.id.cart_allclear);
        cart_count_items = findViewById(R.id.cart_count_items);
        cart_total_price = findViewById(R.id.cart_total_price);
        cart_orderbtn = findViewById(R.id.cart_orderbtn);
        cart_order_lay = findViewById(R.id.cart_order_lay);
        cart_main_lay = findViewById(R.id.cart_main_lay);

        Intent intent = getIntent();
        cart_products_arr = (ArrayList<CartProductsModel>) intent.getSerializableExtra("all_cart_details");


        cart_items_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        cartAdapter = new CartAdapter(getApplicationContext(), cart_main_lay, cart_products_arr, cart_order_lay, cart_main_price, cart_discount_price, cart_final_price, cart_count_items, cart_total_price, cart_orderbtn, cart_allclear);
        cart_items_rec.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        cart_back_btn.setOnClickListener(v -> finish());

    }
}