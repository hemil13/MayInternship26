package com.example.mayinternship26;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyHolder> {

    Context context;

    ArrayList<CartList> arrayList;

    public  CartAdapter(Context context, ArrayList<CartList> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false);
        return new MyHolder(view);
    }

    public  class MyHolder extends RecyclerView.ViewHolder {
        ImageView image;

        TextView name;
        TextView price;
        TextView qty;
        TextView total;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.cart_item_image);
            name = itemView.findViewById(R.id.cart_item_name);
            price = itemView.findViewById(R.id.cart_item_price);
            qty = itemView.findViewById(R.id.cart_item_qty);
            total = itemView.findViewById(R.id.cart_item_total);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        CartList item = arrayList.get(position);

        holder.image.setImageResource(
                item.getImage());

        holder.name.setText(
                item.getName());

        holder.price.setText(
                "Price : ₹" +
                        item.getPrice());

        holder.qty.setText(
                "Qty : " +
                        item.getQty());

        holder.total.setText(
                "Total : ₹" +
                        item.getTotalPrice());
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
