package com.example.mayinternship26;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {

    int[] subIdArray = {1,2,3,4,5,6,7,8,9};
    int[] catIdArray = {1,1,1,2,2,2,3,3,3};
    String[] nameArray = {"Headphones", "Mobile", "Earbuds",
            "Novel", "Fiction", "Horror",
            "Jeans", "Shirts", "T-Shirts"};
    int[] imageArray = {R.drawable.headphone, R.drawable.mobile, R.drawable.earbuds,
            R.drawable.novel, R.drawable.fiction, R.drawable.horror,
            R.drawable.jeans, R.drawable.shirt, R.drawable.thsirt};

    ArrayList<SubCategoryList> arrayList;

    RecyclerView recycler;

    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
        db = openOrCreateDatabase("MayInternship26.db", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name VARCHAR(50), email VARCHAR(100), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);

        String categoryTable = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), image VARCHAR)";
        db.execSQL(categoryTable);

        String subcategoryTable = "CREATE TABLE IF NOT EXISTS subcategory(subcategoryid INTEGER PRIMARY KEY AUTOINCREMENT, categoryid INTEGER(10),name VARCHAR(50), image VARCHAR)";
        db.execSQL(subcategoryTable);


        recycler = findViewById(R.id.subcategory_recycler);


        for (int i = 0; i<subIdArray.length; i++){
            String checkSubCategory = "SELECT * FROM subcategory WHERE name = '"+nameArray[i]+"'";
            Cursor cursor = db.rawQuery(checkSubCategory, null);
            if(cursor.getCount() == 0){
                String insertSubCategory = "INSERT INTO subcategory VALUES (null, '"+catIdArray[i]+"','"+nameArray[i]+"', '"+imageArray[i]+"')";
                db.execSQL(insertSubCategory);
            }
        }

        String getSubCategory = "SELECT * FROM subcategory WHERE categoryid = '"+sp.getString(ConstantSp.categoryId, "")+"'";
        Cursor cursor = db.rawQuery(getSubCategory, null);
        arrayList = new ArrayList<>();
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                SubCategoryList list = new SubCategoryList();
                list.setSubcategoryid(cursor.getInt(0));
                list.setCategoryid(cursor.getInt(1));
                list.setName(cursor.getString(2));
                list.setImage(cursor.getInt(3));
                arrayList.add(list);
            }
            SubCategoryAdapter adapter = new SubCategoryAdapter(SubCategoryActivity.this,arrayList);
            recycler.setAdapter(adapter);
        }




        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

//        SubCategoryAdapter adapter = new SubCategoryAdapter(SubCategoryActivity.this, subIdArray, catIdArray, nameArray, imageArray);
//        recycler.setAdapter(adapter);


    }
}