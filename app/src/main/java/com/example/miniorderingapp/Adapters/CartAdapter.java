package com.example.miniorderingapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniorderingapp.Activities.MainActivity;
import com.example.miniorderingapp.Activities.OrderSuccessActivity;
import com.example.miniorderingapp.DB.RoomDB;
import com.example.miniorderingapp.Models.CartProductsModel;
import com.example.miniorderingapp.Models.OrdersModel;
import com.example.miniorderingapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ListViewHolder> {

    Context context;
    ArrayList<CartProductsModel> cart_products_arr = new ArrayList<>();
    View cart_order_lay, cart_main_lay;
    TextView cart_main_price, cart_discount_price, cart_final_price, cart_count_items, cart_total_price, cart_orderbtn, cart_allclear;

    private int total_price;
    private int total_item_count;
    private int total_mrp_price;
    private int total_discount_price;

    public CartAdapter(Context context, View cart_main_lay, ArrayList<CartProductsModel> cart_products_arr, View cart_order_lay, TextView cart_main_price, TextView cart_discount_price, TextView cart_final_price, TextView cart_count_items, TextView cart_total_price, TextView cart_orderbtn, TextView cart_allclear) {
        this.context = context;
        this.cart_main_lay = cart_main_lay;
        this.cart_products_arr = cart_products_arr;
        this.cart_order_lay = cart_order_lay;
        this.cart_main_price = cart_main_price;
        this.cart_discount_price = cart_discount_price;
        this.cart_final_price = cart_final_price;
        this.cart_count_items = cart_count_items;
        this.cart_total_price = cart_total_price;
        this.cart_orderbtn = cart_orderbtn;
        this.cart_allclear = cart_allclear;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_item, null);
        return new ListViewHolder(inflate);
    }

    private void deleteItem(int position) {
        cart_products_arr.remove(position);
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        RoomDB roomDB = RoomDB.getInstance(context);
        holder.cart_item_lay.setVisibility(View.VISIBLE);
        holder.allcart_enter_qty.setVisibility(View.GONE);

        holder.allcart_mrp.setPaintFlags(holder.allcart_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.cart_item_countitems.setText("" + cart_products_arr.get(position).getProduct_item_count());
        holder.allcart_name.setText(cart_products_arr.get(position).getProduct_name());
        holder.allcart_price.setText("₹ " + cart_products_arr.get(position).getPrice());
        holder.allcart_mrp.setText("₹ " + cart_products_arr.get(position).getMrp());

        setValuesToWidgets();
        holder.cart_item_deletebtn.setOnClickListener(v -> {
            deleteItem(position);
            setValuesToWidgets();
            if (cart_products_arr.size() == 0) {
                Snackbar.make(cart_main_lay, "Your cart is empty. Please add some products.", Snackbar.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }, 1000);
            }
        });

        cart_orderbtn.setOnClickListener(v -> {

            Gson gson = new Gson();
            String cart_prod_arr = gson.toJson(cart_products_arr);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd'th' MMM, yy, h:mm a");
            String order_datetime = simpleDateFormat.format(new Date());

            OrdersModel ordersModel = new OrdersModel(total_mrp_price, total_discount_price, total_price, cart_prod_arr, order_datetime);
            roomDB.ordersDao().insert(ordersModel);

            Intent intent = new Intent(context, OrderSuccessActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        });

        cart_allclear.setOnClickListener(v -> {
            cart_products_arr.clear();
            notifyDataSetChanged();
            setValuesToWidgets();
            if (cart_products_arr.size() == 0) {
                Snackbar.make(cart_main_lay, "Your cart is empty. Please add some products.", Snackbar.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }, 1000);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void setValuesToWidgets() {
        total_price = 0;
        total_item_count = 0;
        total_mrp_price = 0;
        total_discount_price = 0;

        if (cart_products_arr.size() > 0) {
            for (int i = 0; i < cart_products_arr.size(); i++) {
                total_price = total_price + (cart_products_arr.get(i).getPrice() * cart_products_arr.get(i).getProduct_item_count());
                total_item_count = total_item_count + cart_products_arr.get(i).getProduct_item_count();
            }

            for (int i = 0; i < cart_products_arr.size(); i++) {
                total_mrp_price = total_mrp_price + (cart_products_arr.get(i).getMrp() * cart_products_arr.get(i).getProduct_item_count());
                total_discount_price = total_discount_price + (Math.abs(cart_products_arr.get(i).getMrp() - cart_products_arr.get(i).getPrice()) * cart_products_arr.get(i).getProduct_item_count());
            }
        }

        cart_final_price.setText("₹ " + total_price);
        cart_total_price.setText("₹ " + total_price);
        cart_count_items.setText(total_item_count + " ITEMS");
        cart_main_price.setText("₹ " + total_mrp_price);
        cart_discount_price.setText("₹ " + total_discount_price);
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
        return cart_products_arr.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        TextView allcart_name, allcart_price, allcart_mrp, cart_item_countitems;
        EditText allcart_enter_qty;
        ImageButton cart_item_deletebtn;
        View cart_item_lay;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            allcart_name = itemView.findViewById(R.id.allproduct_name);
            allcart_price = itemView.findViewById(R.id.allproduct_price);
            allcart_mrp = itemView.findViewById(R.id.allproduct_mrp);
            allcart_enter_qty = itemView.findViewById(R.id.allproduct_enter_qty);
            cart_item_lay = itemView.findViewById(R.id.cart_item_lay);
            cart_item_countitems = itemView.findViewById(R.id.cart_item_countitems);
            cart_item_deletebtn = itemView.findViewById(R.id.cart_item_deletebtn);

        }
    }
}
