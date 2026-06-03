package com.example.mayinternship26;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class CategoryActivity extends AppCompatActivity {

    int[] idArray = {1,2,3,4,5};
    String[] nameArray = {"Electronics", "Books", "Clothes", "Games", "KitchenWare"};
    int[] imageArray = {R.drawable.electronics, R.drawable.books, R.drawable.clothes, R.drawable.electronics, R.drawable.books};

    RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recycler = findViewById(R.id.category_recycler);

//        recycler.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
//        recycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this,idArray, nameArray, imageArray);
        recycler.setAdapter(adapter);

    }
}