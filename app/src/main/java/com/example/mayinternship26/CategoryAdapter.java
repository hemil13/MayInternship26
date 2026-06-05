package com.example.mayinternship26;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {
    Context context;
    int[] idArray;
    String[] nameArray;
    int[] imageArray;

    ArrayList<CategoryList> arrayList;

    SharedPreferences sp;

//    public CategoryAdapter(Context context, int[] idArray, String[] nameArray, int[] imageArray) {
//        this.context = context;
//        this.idArray = idArray;
//        this.nameArray = nameArray;
//        this.imageArray = imageArray;
//
//        sp = context.getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
//    }


    public CategoryAdapter(Context context, ArrayList<CategoryList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

        sp = context.getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
    }

    @NonNull
    @Override
    public CategoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.item_category_image);
            name = itemView.findViewById(R.id.item_category_name);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyHolder holder, int position) {
//        holder.name.setText(nameArray[position]);
//        holder.image.setImageResource(imageArray[position]);

        holder.image.setImageResource(arrayList.get(position).getImage());
        holder.name.setText(arrayList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "You clicked on "+nameArray[position], Toast.LENGTH_SHORT).show();

                sp.edit().putString(ConstantSp.categoryId, String.valueOf(arrayList.get(position).getId())).commit();

                Intent intent = new Intent(context, SubCategoryActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
