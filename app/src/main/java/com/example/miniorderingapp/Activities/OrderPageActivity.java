package com.example.miniorderingapp.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniorderingapp.Models.CartProductsModel;
import com.example.miniorderingapp.Models.Functions;
import com.example.miniorderingapp.Models.OrdersModel;
import com.example.miniorderingapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OrderPageActivity extends AppCompatActivity {

    OrdersModel orderdata;
    String ordertitle;
    ImageButton orderpage_back_btn;
    TextView orderpage_title, orderpage_main_price, orderpage_discount_price, orderpage_final_price;
    RecyclerView orderpage_products_rec;
    ArrayList<CartProductsModel> products_arr = new ArrayList<>();
    OrderPageAdapter orderPageAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        new Functions().coloredstatusbardesign(OrderPageActivity.this);
        Intent intent = getIntent();
        orderdata = (OrdersModel) intent.getSerializableExtra("orderdata");
        ordertitle = intent.getStringExtra("ordertitle");

        Gson gson = new Gson();
        products_arr = gson.fromJson(orderdata.getAll_prod_arr(), new TypeToken<ArrayList<CartProductsModel>>() {
        }.getType());

        orderpage_title = findViewById(R.id.orderpage_title);
        orderpage_main_price = findViewById(R.id.orderpage_main_price);
        orderpage_discount_price = findViewById(R.id.orderpage_discount_price);
        orderpage_final_price = findViewById(R.id.orderpage_final_price);
        orderpage_back_btn = findViewById(R.id.orderpage_back_btn);
        orderpage_products_rec = findViewById(R.id.orderpage_products_rec);

        orderpage_title.setText(ordertitle);
        orderpage_main_price.setText("₹ " + orderdata.getMrp_total_price());
        orderpage_discount_price.setText("₹ " + orderdata.getDiscount_total_price());
        orderpage_final_price.setText("₹ " + orderdata.getFinal_total_price());

        orderpage_products_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        orderPageAdapter = new OrderPageAdapter(getApplicationContext(), products_arr);
        orderpage_products_rec.setAdapter(orderPageAdapter);
        orderPageAdapter.notifyDataSetChanged();

        orderpage_back_btn.setOnClickListener(v -> finish());

    }

    public class OrderPageAdapter extends RecyclerView.Adapter<OrderPageAdapter.ListViewHolder> {

        Context context;
        ArrayList<CartProductsModel> products_arr = new ArrayList<>();

        public OrderPageAdapter(Context context, ArrayList<CartProductsModel> products_arr) {
            this.context = context;
            this.products_arr = products_arr;
        }

        @NonNull
        @Override
        public OrderPageAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, null);
            return new ListViewHolder(inflate);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull OrderPageAdapter.ListViewHolder holder, int position) {

            holder.allproduct_item_name.setText(products_arr.get(position).getProduct_name());
            holder.allproduct_item_mrp.setPaintFlags(holder.allproduct_item_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.allproduct_item_mrp.setText("₹ " + products_arr.get(position).getMrp());
            holder.allproduct_item_price.setText("₹ " + products_arr.get(position).getPrice());
            holder.allproduct_item_countitems.setText("" + products_arr.get(position).getProduct_item_count());
            setEachPriceDisplay(holder, position);
        }

        @SuppressLint("SetTextI18n")
        private void setEachPriceDisplay(@NotNull ListViewHolder holder, int position) {
            int each_product_price = 0;
            each_product_price = products_arr.get(position).getPrice() * products_arr.get(position).getProduct_item_count();
            holder.allproduct_item_totalprice.setText("₹ " + each_product_price);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return products_arr.size();
        }

        public class ListViewHolder extends RecyclerView.ViewHolder {

            TextView allproduct_item_name, allproduct_item_price, allproduct_item_mrp, allproduct_item_countitems, allproduct_item_totalprice;

            public ListViewHolder(@NonNull View itemView) {
                super(itemView);

                allproduct_item_name = itemView.findViewById(R.id.allproduct_item_name);
                allproduct_item_price = itemView.findViewById(R.id.allproduct_item_price);
                allproduct_item_mrp = itemView.findViewById(R.id.allproduct_item_mrp);
                allproduct_item_countitems = itemView.findViewById(R.id.allproduct_item_countitems);
                allproduct_item_totalprice = itemView.findViewById(R.id.allproduct_item_totalprice);
            }
        }
    }
}