package com.example.mayinternship26;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class SubCategoryActivity extends AppCompatActivity {

    int[] subIdArray = {1,2,3,4,5,6,7,8,9};
    int[] catIdArray = {1,1,1,2,2,2,3,3,3};
    String[] nameArray = {"Headphones", "Mobile", "Earbuds",
            "Novel", "Fiction", "Horror",
            "Jeans", "Shirts", "T-Shirts"};
    int[] imageArray = {R.drawable.headphone, R.drawable.mobile, R.drawable.earbuds,
            R.drawable.novel, R.drawable.fiction, R.drawable.horror,
            R.drawable.jeans, R.drawable.shirt, R.drawable.thsirt};

    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        recycler = findViewById(R.id.subcategory_recycler);
        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        SubCategoryAdapter adapter = new SubCategoryAdapter(SubCategoryActivity.this, subIdArray, catIdArray, nameArray, imageArray);
        recycler.setAdapter(adapter);


    }
}