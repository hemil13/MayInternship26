package com.example.mayinternship26;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

    Context context;
    ArrayList<ProductList> arrayList;
    SharedPreferences sp;

    public ProductAdapter(Context context, ArrayList<ProductList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sp = context.getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ProductAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, originalPrice, discountedPrice;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_product_image);
            name = itemView.findViewById(R.id.item_product_name);
            originalPrice = itemView.findViewById(R.id.item_product_original_price);
            discountedPrice = itemView.findViewById(R.id.item_product_discounted_price);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyHolder holder, int position) {

        holder.image.setImageResource(arrayList.get(position).getImage());
        holder.name.setText(arrayList.get(position).getName());
        holder.originalPrice.setText(ConstantSp.symbol+arrayList.get(position).getOriginalPrice());
        holder.discountedPrice.setText(ConstantSp.symbol+arrayList.get(position).getDiscountedPrice());

        holder.originalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString(ConstantSp.productId, String.valueOf(arrayList.get(position).getProductid())).commit();
                sp.edit().putString(ConstantSp.productName, arrayList.get(position).getName()).commit();
                sp.edit().putInt(ConstantSp.productImage, arrayList.get(position).getImage()).commit();
                sp.edit().putInt(ConstantSp.productOriginalPrice, arrayList.get(position).getOriginalPrice()).commit();
                sp.edit().putInt(ConstantSp.productDiscountedPrice, arrayList.get(position).getDiscountedPrice()).commit();
                sp.edit().putString(ConstantSp.productDescription, arrayList.get(position).getDescription()).commit();

                Intent intent = new Intent(context, ProductDetailActivity.class);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
