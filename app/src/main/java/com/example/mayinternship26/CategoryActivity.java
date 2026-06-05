package com.example.mayinternship26;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.service.chooser.AdditionalContentContract;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    int[] idArray = {1,2,3,4,5};
    String[] nameArray = {"Electronics", "Books", "Clothes", "Games", "KitchenWare"};
    int[] imageArray = {R.drawable.electronics, R.drawable.books, R.drawable.clothes, R.drawable.electronics, R.drawable.books};


    ArrayList<CategoryList> arrayList;

    RecyclerView recycler;

    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        db = openOrCreateDatabase("MayInternship26.db", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name VARCHAR(50), email VARCHAR(100), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);

        String categoryTable = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), image VARCHAR)";
        db.execSQL(categoryTable);


        recycler = findViewById(R.id.category_recycler);

        for (int i = 0; i<idArray.length; i++){
            String checkCategory = "SELECT * FROM category WHERE name = '"+nameArray[i]+"'";
            Cursor cursor = db.rawQuery(checkCategory, null);
            if(cursor.getCount() == 0){
                String insertCategory = "INSERT INTO category VALUES (null, '"+nameArray[i]+"', '"+imageArray[i]+"')";
                db.execSQL(insertCategory);
            }
        }

        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        String getCategory = "SELECT * FROM category";
        Cursor cursor = db.rawQuery(getCategory, null);
        arrayList = new ArrayList<>();
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
               CategoryList list = new CategoryList();
               list.setId(cursor.getInt(0));
               list.setName(cursor.getString(1));
               list.setImage(cursor.getInt(2));
               arrayList.add(list);
            }
            CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this,arrayList);
            recycler.setAdapter(adapter);
        }




//        recycler.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
//        recycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));


//        CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this,idArray, nameArray, imageArray);
//        recycler.setAdapter(adapter);

    }
}