package com.example.mayinternship26;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    Button BuyNow;
    ImageView image, wishlist;
    TextView name, originalPrice, discountedPrice, description;

    SharedPreferences sp;

    Boolean isWishlist = false;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

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


        BuyNow = findViewById(R.id.product_detail_buy_now);
        image = findViewById(R.id.product_detail_image);
        name = findViewById(R.id.product_detail_name);
        originalPrice = findViewById(R.id.product_detail_original_price);
        discountedPrice = findViewById(R.id.product_detail_discounted_price);
        description = findViewById(R.id.product_detail_description);
        wishlist = findViewById(R.id.product_detail_wishlist_empty);

        originalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        image.setImageResource(sp.getInt(ConstantSp.productImage,0));
        name.setText(sp.getString(ConstantSp.productName,""));
        originalPrice.setText(ConstantSp.symbol+sp.getInt(ConstantSp.productOriginalPrice,0));
        discountedPrice.setText(ConstantSp.symbol+sp.getInt(ConstantSp.productDiscountedPrice,0));
        description.setText(sp.getString(ConstantSp.productDescription,""));


        String checkWishlist = "SELECT * FROM wishlist WHERE productId = '"+sp.getString(ConstantSp.productId,"")+"'";
        Cursor wishlistCursor = db.rawQuery(checkWishlist, null);
        if(wishlistCursor.getCount()>0){
            isWishlist = true;
            wishlist.setImageResource(R.drawable.wishlist_fill);
        }



        BuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startpayment();
            }
        });



        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isWishlist){
                    String insertWishlist = "INSERT INTO wishlist VALUES (null, '"+sp.getString(ConstantSp.productId,"")+"')";
                    db.execSQL(insertWishlist);

                    isWishlist = true;
                    Toast.makeText(ProductDetailActivity.this, "Added to wishlist", Toast.LENGTH_SHORT).show();
                    wishlist.setImageResource(R.drawable.wishlist_fill);
                }
                else{
                    String deleteWishlist = "DELETE FROM wishlist WHERE productId = '"+sp.getString(ConstantSp.productId,"")+"'";
                    db.execSQL(deleteWishlist);


                    isWishlist = false;
                    Toast.makeText(ProductDetailActivity.this, "Removed From wishlist", Toast.LENGTH_SHORT).show();
                    wishlist.setImageResource(R.drawable.wishlist_empty);
                }


            }
        });


    }



    private void startpayment() {
        final Activity activity = this;
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_SmqOXAexWBNDbY");

        try {
            JSONObject options = new JSONObject();
            options.put("name", getResources().getString(R.string.app_name));
            options.put("description", "Purchase Deal From " + getResources().getString(R.string.app_name));
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", R.mipmap.ic_launcher);
            options.put("currency", "INR");
            options.put("amount", String.valueOf(sp.getInt(ConstantSp.productDiscountedPrice,0) * 100));

            JSONObject preFill = new JSONObject();
            preFill.put("email", "hemigarala@gmail.com");
            preFill.put("contact", "9638221084");
            options.put("prefill", preFill);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("RESPONSE", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Toast.makeText(this, "Payment Sucessfull: "+ s, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(this, "Payment Failed: "+ s, Toast.LENGTH_SHORT).show();
    }
}