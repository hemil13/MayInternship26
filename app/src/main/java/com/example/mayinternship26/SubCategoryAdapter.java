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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyHolder> {
    Context context;
    int[] subIdArray;
    int[] catIdArray;
    String[] nameArray;
    int[] imageArray;

    int count=0;

    SharedPreferences sp;

    public SubCategoryAdapter(Context context, int[] subIdArray, int[] catIdArray, String[] nameArray, int[] imageArray) {
        this.context = context;
        this.subIdArray = subIdArray;
        this.catIdArray = catIdArray;
        this.nameArray = nameArray;
        this.imageArray = imageArray;

        sp = context.getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
        for(int i=0; i<subIdArray.length; i++){
            if(subIdArray[i] == Integer.parseInt(sp.getString(ConstantSp.categoryId,""))){
                count++;
            }
        }
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
        if(catIdArray[position] == Integer.parseInt(sp.getString(ConstantSp.categoryId,""))){
            holder.name.setText(nameArray[position]);
            holder.image.setImageResource(imageArray[position]);
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }


}
