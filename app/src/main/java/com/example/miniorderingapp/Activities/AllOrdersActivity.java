package com.example.miniorderingapp.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import com.example.miniorderingapp.DB.RoomDB;
import com.example.miniorderingapp.Models.Functions;
import com.example.miniorderingapp.Models.OrdersModel;
import com.example.miniorderingapp.R;

import java.util.ArrayList;
import java.util.List;

public class AllOrdersActivity extends AppCompatActivity {

    RecyclerView allorders_rec;
    AllOrdersAdapter allOrdersAdapter;
    ImageButton allorders_back_btn;
    List<OrdersModel> allorders_arr = new ArrayList<>();
    RoomDB roomDB;
    TextView allorders_nodata;
    boolean check_frompage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);

        Intent intent = getIntent();
        check_frompage = intent.getBooleanExtra("from_success_page", false);

        new Functions().lightstatusbardesign(AllOrdersActivity.this);
        allorders_rec = findViewById(R.id.allorders_rec);
        allorders_nodata = findViewById(R.id.allorders_nodata);
        allorders_back_btn = findViewById(R.id.allorders_back_btn);
        roomDB = RoomDB.getInstance(getApplicationContext());
        allorders_arr = roomDB.ordersDao().getAllProducts();

        if (allorders_arr.size() > 0) {
            allorders_rec.setVisibility(View.VISIBLE);
            allorders_nodata.setVisibility(View.GONE);
            allorders_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            allOrdersAdapter = new AllOrdersAdapter(getApplicationContext(), allorders_arr);
            allorders_rec.setAdapter(allOrdersAdapter);
            allOrdersAdapter.notifyDataSetChanged();
        } else {
            allorders_rec.setVisibility(View.GONE);
            allorders_nodata.setVisibility(View.VISIBLE);
        }
        allorders_back_btn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrdersAdapter.ListViewHolder> {

        Context context;
        List<OrdersModel> allorders_arr = new ArrayList<>();

        public AllOrdersAdapter(Context context, List<OrdersModel> allorders_arr) {
            this.context = context;
            this.allorders_arr = allorders_arr;
        }

        @NonNull
        @Override
        public AllOrdersAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_orders_item, null);
            return new ListViewHolder(inflate);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull AllOrdersAdapter.ListViewHolder holder, int position) {
            int pos = position + 1;
            holder.allorders_item_name.setText("Order#" + pos);
            holder.allorders_item_time.setText(allorders_arr.get(position).getOrder_datetime());
            holder.allorders_item_countitems.setText("₹ " + allorders_arr.get(position).getFinal_total_price());

            holder.allorders_item_main_lay.setOnClickListener(v -> {
                Intent intent = new Intent(context, OrderPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("orderdata", allorders_arr.get(position));
                intent.putExtra("ordertitle", "Order#" + pos);
                context.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return allorders_arr.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public class ListViewHolder extends RecyclerView.ViewHolder {

            View allorders_item_main_lay;
            TextView allorders_item_name, allorders_item_time, allorders_item_countitems;

            public ListViewHolder(@NonNull View itemView) {
                super(itemView);

                allorders_item_main_lay = itemView.findViewById(R.id.allorders_item_main_lay);
                allorders_item_name = itemView.findViewById(R.id.allorders_item_name);
                allorders_item_time = itemView.findViewById(R.id.allorders_item_time);
                allorders_item_countitems = itemView.findViewById(R.id.allorders_item_countitems);
            }
        }
    }
}