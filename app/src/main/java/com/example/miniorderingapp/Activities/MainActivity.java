package com.example.miniorderingapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.miniorderingapp.Adapters.ProdAdapter;
import com.example.miniorderingapp.DB.RoomDB;
import com.example.miniorderingapp.Models.Functions;
import com.example.miniorderingapp.Models.ProductDataModel;
import com.example.miniorderingapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean isadded;
    RecyclerView all_products_display_rec;
    ProdAdapter prodAdapter;
    View addtocart_lay, home_loading_lay;
    ImageButton addtocart_cancel_btn, homepage_allordersbtn;
    TextView addtocart_count_items, addtocart_total_price, addtocart_btn;
    int page = 1, limit = 10;
    private List<ProductDataModel> all_products = new ArrayList<>();
    private RoomDB roomDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Functions().coloredstatusbardesign(MainActivity.this);
        all_products_display_rec = findViewById(R.id.all_products_display_rec);
        addtocart_lay = findViewById(R.id.add_to_cart_lay);
        addtocart_cancel_btn = findViewById(R.id.addtocart_cancel_btn);
        addtocart_count_items = findViewById(R.id.addtocart_count_items);
        addtocart_total_price = findViewById(R.id.addtocart_total_price);
        homepage_allordersbtn = findViewById(R.id.homepage_allordersbtn);
        home_loading_lay = findViewById(R.id.home_loading_lay);
        addtocart_btn = findViewById(R.id.addtocart_btn);
        SharedPreferences prefs = getSharedPreferences("all_prod_data", MODE_PRIVATE);
        addtocart_lay.setVisibility(View.GONE);
        isadded = prefs.getBoolean("isadded", false);
        roomDB = RoomDB.getInstance(this);

        all_products = roomDB.productDao().getAllProducts();

        if (all_products.size() > 0) {
            home_loading_lay.setVisibility(View.GONE);
        } else {
            home_loading_lay.setVisibility(View.VISIBLE);
        }

        if (!isadded) {
            addAllProductsToDB();
        }

        homerecyclerdata();

        if (all_products.size() <= 0) {
            new Handler().postDelayed(() -> {
                all_products = roomDB.productDao().getAllProducts();

                if (all_products.size() > 0) {
                    home_loading_lay.setVisibility(View.GONE);
                } else {
                    home_loading_lay.setVisibility(View.VISIBLE);
                }

                if (all_products.size() > 0) {
                    homerecyclerdata();
                } else {
                    new Handler().postDelayed(() -> {
                        all_products = roomDB.productDao().getAllProducts();

                        if (all_products.size() > 0) {
                            home_loading_lay.setVisibility(View.GONE);
                        } else {
                            home_loading_lay.setVisibility(View.VISIBLE);
                        }
                        homerecyclerdata();
                    }, 5000);
                }
            }, 5000);
        }

        homepage_allordersbtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AllOrdersActivity.class)));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void homerecyclerdata() {
        Log.e("from_db_size", "From DB products size is : " + roomDB.productDao().getAllProducts().size());
        all_products_display_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        prodAdapter = new ProdAdapter(getApplicationContext(), all_products, addtocart_lay, addtocart_cancel_btn, addtocart_count_items, addtocart_total_price, addtocart_btn);
        all_products_display_rec.setAdapter(prodAdapter);
        prodAdapter.notifyDataSetChanged();
    }

    private void addAllProductsToDB() {

        Log.e("volley_start", "Starting Volley...........");
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://staging.emandi.store/dummy-api/get-products", null,
                response -> {
                    try {
                        for (int i = 0; i < response.getJSONArray("data").length(); i++) {
                            String products_array = response.getJSONArray("data").getString(i);
                            JSONObject prod_obj = new JSONObject(products_array);
                            ProductDataModel productDataModel = new ProductDataModel(
                                    prod_obj.getInt("product_id"),
                                    prod_obj.getInt("product_brand_id"),
                                    prod_obj.getString("product_name"),
                                    prod_obj.getString("product_code"),
                                    prod_obj.getInt("mrp"),
                                    prod_obj.getInt("price"),
                                    prod_obj.getInt("product_weight"),
                                    prod_obj.getString("product_weight_unit"));

                            all_products.add(productDataModel);

                            {
                                Log.e("product_id", "" + prod_obj.getInt("product_id"));
                                Log.e("product_brand_id", "" + prod_obj.getInt("product_brand_id"));
                                Log.e("product_name", "" + prod_obj.getString("product_name"));
                                Log.e("product_code", "" + prod_obj.getString("product_code"));
                                Log.e("mrp", "" + prod_obj.getInt("mrp"));
                                Log.e("price", "" + prod_obj.getInt("price"));
                                Log.e("product_weight", "" + prod_obj.getInt("product_weight"));
                                Log.e("product_weight_unit", "" + prod_obj.getString("product_weight_unit"));
                            } //Logs
                        }

                        if (all_products.size() > 0) {
                            roomDB.productDao().insertAll(all_products);
                            SharedPreferences.Editor pref_edit = getSharedPreferences("all_prod_data", MODE_PRIVATE).edit();
                            pref_edit.putBoolean("isadded", true);
                            pref_edit.apply();
                        }
                    } catch (JSONException e) {
                        Log.e("Volley_data", "JSONException error  : " + e);
                    }
                },
                error -> {
                    Log.e("Volley_data", "JSON Extraction error  : " + error);
                });

        queue.add(jsonObjectRequest);
    }
}