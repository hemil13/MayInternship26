package com.example.mayinternship26;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    int[] productIdArray = {1,2,3,4,5,6,7,8,9};
    int[] subIdArray = {1,1,1,2,2,2,3,3,3};
    String[] nameArray = {"Noise","Airpods Max","Sony Headphones",
            "OnePlus","Redmi","Sony",
            "Noise Aura","Boat 120","Airpods Pro 2"};
    int[] imageArray = {R.drawable.noise, R.drawable.airpods_max, R.drawable.sony,
            R.drawable.oneplus, R.drawable.redmi, R.drawable.sony,
            R.drawable.noiseaura, R.drawable.boat120, R.drawable.airpodspro2};
    int[] originalPrice= {1000,2000,3000,4000,5000,6000,7000,8000,9000};
    int[] discountedPrice= {900,1900,2900,3900,4900,5900,6900,7900,8900};
    String[] prodDescription = {"Noise Desc","Airpods Max Desc","Sony Headphones Desc",
            "OnePlus Desc","Redmi Desc","Sony Desc",
            "Noise Aura Desc","Boat 120 Desc","Airpods Pro 2 Desc"};

    ArrayList<ProductList> arrayList;

    SQLiteDatabase db;
    SharedPreferences sp;

    RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
        db = openOrCreateDatabase("MayInternship26.db", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name VARCHAR(50), email VARCHAR(100), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);

        String categoryTable = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), image VARCHAR)";
        db.execSQL(categoryTable);

        String subcategoryTable = "CREATE TABLE IF NOT EXISTS subcategory(subcategoryid INTEGER PRIMARY KEY AUTOINCREMENT, categoryid INTEGER(10),name VARCHAR(50), image VARCHAR)";
        db.execSQL(subcategoryTable);

        String productTable = "CREATE TABLE IF NOT EXISTS product(productid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "subcategoryid INTEGER(10), name VARCHAR(50), image VARCHAR, " +
                "originalPrice INTEGER(10), discountedPrice INTEGER(10), description VARCHAR(100))";
        db.execSQL(productTable);

        String wishlistTable = "CREATE TABLE IF NOT EXISTS wishlist(wishlistId INTEGER PRIMARY KEY AUTOINCREMENT, productId VARCHAR(10))";
        db.execSQL(wishlistTable);


        recycler = findViewById(R.id.product_recycler);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        for (int i = 0; i<subIdArray.length; i++){
            String checkProduct = "SELECT * FROM product WHERE name = '"+nameArray[i]+"' AND subcategoryid = '"+subIdArray[i]+"'";
            Cursor cursor = db.rawQuery(checkProduct, null);
            if(cursor.getCount() == 0){
                String insertProduct = "INSERT INTO product VALUES (null, '"+subIdArray[i]+"','"+nameArray[i]+"'," +
                        " '"+imageArray[i]+"', '"+originalPrice[i]+"', '"+discountedPrice[i]+"', " +
                        "'"+prodDescription[i]+"')";
                db.execSQL(insertProduct);
            }
        }


        String getProduct = "SELECT * FROM product WHERE subcategoryid = '"+sp.getString(ConstantSp.subcategoryId,"")+"'";
        Cursor cursor = db.rawQuery(getProduct, null);
        arrayList = new ArrayList<>();
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                ProductList list = new ProductList();
                list.setProductid(cursor.getInt(0));
                list.setSubId(cursor.getInt(1));
                list.setName(cursor.getString(2));
                list.setImage(cursor.getInt(3));
                list.setOriginalPrice(cursor.getInt(4));
                list.setDiscountedPrice(cursor.getInt(5));
                list.setDescription(cursor.getString(6));

                arrayList.add(list);

            }

            ProductAdapter adapter = new ProductAdapter(ProductActivity.this,arrayList, db);
            recycler.setAdapter(adapter);
        }

    }
}