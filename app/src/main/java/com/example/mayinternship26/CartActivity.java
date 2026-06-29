package com.example.mayinternship26;

import static android.graphics.BlurMaskFilter.Blur.INNER;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView recycler;
    TextView totalAmount;
    Button checkout;
    SQLiteDatabase db;
    ArrayList<CartList> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        recycler = findViewById(R.id.cart_recycler);
        totalAmount = findViewById(R.id.cart_total_amount);
        checkout = findViewById(R.id.cart_checkout);

        db = openOrCreateDatabase("MayInternship26.db", MODE_PRIVATE, null);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<>();

        int grandTotal = 0;

        String query = "SELECT * FROM cart " +
                "INNER JOIN product " +
                "ON cart.productId = product.productid " +
                "WHERE cart.orderid = '0'";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {

            do {
                CartList list = new CartList();

                list.setProductid(cursor.getInt(6));
                list.setName(cursor.getString(8));
                list.setImage(cursor.getInt(9));
                list.setQty(cursor.getInt(3));
                list.setPrice(cursor.getInt(4));
                list.setTotalPrice(cursor.getInt(5));

                arrayList.add(list);

                grandTotal = grandTotal + cursor.getInt(5);
            }
            while (cursor.moveToNext());

            CartAdapter adapter = new CartAdapter(this, arrayList);

            recycler.setAdapter(adapter);

            totalAmount.setText("Grand Total : $" + grandTotal);
        }

        checkout.setOnClickListener(view -> {
            Toast.makeText(this, "Proceed To Checkout", Toast.LENGTH_SHORT).show();
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}