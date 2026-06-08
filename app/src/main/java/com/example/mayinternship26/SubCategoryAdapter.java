package com.example.mayinternship26;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyHolder> {
    Context context;
    int[] subIdArray;
    int[] catIdArray;
    String[] nameArray;
    int[] imageArray;

    ArrayList<SubCategoryList> arraylist;

    int count=0;

    SharedPreferences sp;

//    public SubCategoryAdapter(Context context, int[] subIdArray, int[] catIdArray, String[] nameArray, int[] imageArray) {
//        this.context = context;
//        this.subIdArray = subIdArray;
//        this.catIdArray = catIdArray;
//        this.nameArray = nameArray;
//        this.imageArray = imageArray;
//
//        sp = context.getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
//
//    }

    public SubCategoryAdapter(Context context, ArrayList<SubCategoryList> arrayList) {
        this.context = context;
        this.arraylist = arrayList;

    }

    @NonNull
    @Override
    public SubCategoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new SubCategoryAdapter.MyHolder(view);
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
    public void onBindViewHolder(@NonNull SubCategoryAdapter.MyHolder holder, int position) {
//        if(catIdArray[position] == Integer.parseInt(sp.getString(ConstantSp.categoryId,""))){
//            holder.name.setText(nameArray[position]);
//            holder.image.setImageResource(imageArray[position]);
//        }

        holder.image.setImageResource(arraylist.get(position).getImage());
        holder.name.setText(arraylist.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked on "+arraylist.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }


}
