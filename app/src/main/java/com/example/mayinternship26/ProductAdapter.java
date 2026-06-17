package com.example.mayinternship26;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

    Context context;
    ArrayList<ProductList> arrayList;
    SharedPreferences sp;

    SQLiteDatabase db;

//    public ProductAdapter(Context context, ArrayList<ProductList> arrayList) {
//        this.context = context;
//        this.arrayList = arrayList;
//        sp = context.getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
//    }


    public ProductAdapter(Context context, ArrayList<ProductList> arrayList, SQLiteDatabase db) {
        this.context = context;
        this.arrayList = arrayList;
        this.db = db;
        sp = context.getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ProductAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView image, wishlist;
        TextView name, originalPrice, discountedPrice;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_product_image);
            name = itemView.findViewById(R.id.item_product_name);
            originalPrice = itemView.findViewById(R.id.item_product_original_price);
            discountedPrice = itemView.findViewById(R.id.item_product_discounted_price);
            wishlist = itemView.findViewById(R.id.item_product_wishlist);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyHolder holder, int position) {

        holder.image.setImageResource(arrayList.get(position).getImage());
        holder.name.setText(arrayList.get(position).getName());
        holder.originalPrice.setText(ConstantSp.symbol+arrayList.get(position).getOriginalPrice());
        holder.discountedPrice.setText(ConstantSp.symbol+arrayList.get(position).getDiscountedPrice());

        holder.originalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        if(arrayList.get(position).isWishlist()){
            holder.wishlist.setImageResource(R.drawable.wishlist_fill);
        }
        else{
            holder.wishlist.setImageResource(R.drawable.wishlist_empty);
        }

        holder.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayList.get(position).isWishlist()){
                    String deleteWishlist = "DELETE FROM wishlist WHERE productId = '"+arrayList.get(position).getProductid()+"'";
                    db.execSQL(deleteWishlist);
                    arrayList.get(position).setWishlist(false);
                    holder.wishlist.setImageResource(R.drawable.wishlist_empty);
                    Toast.makeText(context, "Removed from wishlist", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
                else{
                    String insertWishlist = "INSERT INTO wishlist VALUES (null, '"+arrayList.get(position).getProductid()+"')";
                    db.execSQL(insertWishlist);
                    arrayList.get(position).setWishlist(true);
                    holder.wishlist.setImageResource(R.drawable.wishlist_fill);
                    Toast.makeText(context, "Added to wishlist", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            }
        });


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
