package com.example.miniorderingapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniorderingapp.Activities.CartActivity;
import com.example.miniorderingapp.Models.CartProductsModel;
import com.example.miniorderingapp.Models.ProductDataModel;
import com.example.miniorderingapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProdAdapter extends RecyclerView.Adapter<ProdAdapter.ListViewHolder> {

    Context context;
    List<ProductDataModel> all_prod_array;
    ArrayList<CartProductsModel> to_cart_array = new ArrayList<>();
    View addtocart_lay;
    ImageButton addtocart_cancel_btn;
    TextView addtocart_count_items, addtocart_total_price, addtocart_btn;

    int total_price;
    int total_item_count;

    public ProdAdapter(Context context, List<ProductDataModel> all_prod_array, View addtocart_lay, ImageButton addtocart_cancel_btn, TextView addtocart_count_items, TextView addtocart_total_price, TextView addtocart_btn) {
        this.context = context;
        this.all_prod_array = all_prod_array;
        this.addtocart_lay = addtocart_lay;
        this.addtocart_cancel_btn = addtocart_cancel_btn;
        this.addtocart_count_items = addtocart_count_items;
        this.addtocart_total_price = addtocart_total_price;
        this.addtocart_btn = addtocart_btn;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_item, null);
        return new ListViewHolder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        holder.cart_item_lay.setVisibility(View.GONE);
        holder.allproduct_enter_qty.setVisibility(View.VISIBLE);

        if (to_cart_array.size() == 0) {
            holder.allproduct_enter_qty.setBackgroundResource(R.drawable.noadd_stroke_drawable);
            holder.allproduct_enter_qty.setText("");
        }

        addtocartVisibilityFun();
        holder.allproduct_name.setText(all_prod_array.get(position).getProduct_name());
        holder.allproduct_price.setText("₹ " + all_prod_array.get(position).getPrice());
        holder.allproduct_mrp.setText("₹ " + all_prod_array.get(position).getMrp());
        holder.allproduct_mrp.setPaintFlags(holder.allproduct_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.allproduct_enter_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                int get_qnty_val;
                String qnty_txt = holder.allproduct_enter_qty.getText().toString();
                if (qnty_txt != null && !qnty_txt.equals("")) {

                    Log.e("count", "Greater than 0");
                    get_qnty_val = Integer.parseInt(qnty_txt);

                    CartProductsModel cartProductsModel = new CartProductsModel(
                            all_prod_array.get(position).getProduct_id(),
                            all_prod_array.get(position).getProduct_brand_id(),
                            all_prod_array.get(position).getProduct_name(),
                            all_prod_array.get(position).getProduct_code(),
                            all_prod_array.get(position).getMrp(),
                            all_prod_array.get(position).getPrice(),
                            all_prod_array.get(position).getProduct_weight(),
                            all_prod_array.get(position).getProduct_weight_unit(),
                            get_qnty_val
                    );
                    if (get_qnty_val > 0) {
                        Log.e("count", "" + get_qnty_val);
                        holder.allproduct_enter_qty.setBackgroundResource(R.drawable.added_stroke_drawable);
                    } else {
                        Log.e("count", "0");
                        holder.allproduct_enter_qty.setBackgroundResource(R.drawable.noadd_stroke_drawable);
                    }

                    addtocart(cartProductsModel, get_qnty_val);
                } else {
                    Log.e("count", "Empty");
                    get_qnty_val = 0;
                    holder.allproduct_enter_qty.setBackgroundResource(R.drawable.noadd_stroke_drawable);
                    CartProductsModel cartProductsModel = new CartProductsModel(
                            all_prod_array.get(position).getProduct_id(),
                            all_prod_array.get(position).getProduct_brand_id(),
                            all_prod_array.get(position).getProduct_name(),
                            all_prod_array.get(position).getProduct_code(),
                            all_prod_array.get(position).getMrp(),
                            all_prod_array.get(position).getPrice(),
                            all_prod_array.get(position).getProduct_weight(),
                            all_prod_array.get(position).getProduct_weight_unit(),
                            get_qnty_val
                    );
                    addtocart(cartProductsModel, get_qnty_val);
                }
                totalPriceOfProducts();
                addtocartVisibilityFun();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        fromMainWidgetsEvents(holder);
    }

    @SuppressLint("SetTextI18n")
    private void totalPriceOfProducts() {
        total_price = 0;
        total_item_count = 0;
        for (int i = 0; i < to_cart_array.size(); i++) {
            total_price = total_price + (to_cart_array.get(i).getPrice() * to_cart_array.get(i).getProduct_item_count());
            total_item_count = total_item_count + to_cart_array.get(i).getProduct_item_count();
        }

        addtocart_total_price.setText("₹ " + total_price);
        addtocart_count_items.setText(total_item_count + " ITEMS");
    }

    private void fromMainWidgetsEvents(ListViewHolder holder) {

        addtocart_cancel_btn.setOnClickListener(v -> {
            to_cart_array.clear();
            addtocartVisibilityFun();
        });

        addtocart_btn.setOnClickListener(v -> {
            if (to_cart_array.size() > 0) {
                Intent intent = new Intent(context, CartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("all_cart_details", to_cart_array);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Your cart is empty. Please add some products", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addtocartVisibilityFun() {
        if (to_cart_array.size() > 0) {
            addtocart_lay.setVisibility(View.VISIBLE);
        } else {
            addtocart_lay.setVisibility(View.GONE);
        }
    }

    private void addtocart(CartProductsModel cartProductsModel, int get_qnty_val) {
        if (get_qnty_val > 0) {
            if (to_cart_array.size() > 0) {
                for (int i = 0; i < to_cart_array.size(); i++) {
                    if (to_cart_array.get(i).getProduct_id() != cartProductsModel.getProduct_id()) {
                        if (i == to_cart_array.size() - 1) {
                            to_cart_array.add(cartProductsModel);
                        }
                    } else {
                        to_cart_array.remove(i);
                        to_cart_array.add(i, cartProductsModel);
                        break;
                    }
                }
            } else {
                to_cart_array.add(cartProductsModel);
            }
        } else {
            for (int i = 0; i < to_cart_array.size(); i++) {
                if (to_cart_array.get(i).getProduct_id() == cartProductsModel.getProduct_id()) {
                    to_cart_array.remove(i);
                    break;
                }
            }
        }
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
        return all_prod_array.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        TextView allproduct_name, allproduct_price, allproduct_mrp;
        EditText allproduct_enter_qty;
        View cart_item_lay;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            allproduct_name = itemView.findViewById(R.id.allproduct_name);
            allproduct_price = itemView.findViewById(R.id.allproduct_price);
            allproduct_mrp = itemView.findViewById(R.id.allproduct_mrp);
            allproduct_enter_qty = itemView.findViewById(R.id.allproduct_enter_qty);
            cart_item_lay = itemView.findViewById(R.id.cart_item_lay);
        }
    }
}
