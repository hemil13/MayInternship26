package com.example.mayinternship26;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    Button BuyNow;
    FloatingActionButton fabCart;
    ImageView image, wishlist, minus, plus, cart;
    TextView name, originalPrice, discountedPrice, description, qty, totalAmount;

    LinearLayout cart_layout;

    SharedPreferences sp;

    Boolean isWishlist = false;

    SQLiteDatabase db;

    int cart_qty = 0;

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

        String cartTable = "CREATE TABLE IF NOT EXISTS cart(" +"cartid INTEGER PRIMARY KEY AUTOINCREMENT," +"orderid INTEGER(10)," +"productid VARCHAR(10)," +" qty INTEGER(3)," +"price INTEGER(10)," +" totalPrice INTEGER(10))";
        db.execSQL(cartTable);

        fabCart = findViewById(R.id.fab_cart);
        BuyNow = findViewById(R.id.product_detail_buy_now);
        image = findViewById(R.id.product_detail_image);
        name = findViewById(R.id.product_detail_name);
        originalPrice = findViewById(R.id.product_detail_original_price);
        discountedPrice = findViewById(R.id.product_detail_discounted_price);
        description = findViewById(R.id.product_detail_description);
        wishlist = findViewById(R.id.product_detail_wishlist_empty);

        cart_layout = findViewById(R.id.product_detail_cart_layout);
        minus = findViewById(R.id.product_detail_cart_minus);
        plus = findViewById(R.id.product_detail_cart_add);
        qty = findViewById(R.id.product_detail_cart_qty);
        cart = findViewById(R.id.product_detail_cart);
        totalAmount = findViewById(R.id.product_detail_total_amount);

        originalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        image.setImageResource(sp.getInt(ConstantSp.productImage,0));
        name.setText(sp.getString(ConstantSp.productName,""));
        originalPrice.setText(ConstantSp.symbol+sp.getInt(ConstantSp.productOriginalPrice,0));
        discountedPrice.setText(ConstantSp.symbol+sp.getInt(ConstantSp.productDiscountedPrice,0));
        description.setText(sp.getString(ConstantSp.productDescription,""));


        //Ccheck
        String checkCart = "SELECT * FROM cart WHERE productId='" + sp.getString(ConstantSp.productId,"") + "' AND orderid='0'";

        Cursor cartCursor = db.rawQuery(checkCart,null);

        if(cartCursor.moveToFirst())
        {
            cart_qty = cartCursor.getInt(3);

            qty.setText(String.valueOf(cart_qty));

            int total = cartCursor.getInt(5);

            totalAmount.setText("Total: $" + total);

            cart.setVisibility(View.GONE);
            cart_layout.setVisibility(View.VISIBLE);
        }

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

        fabCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                startActivity(intent);
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

//        cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cart_layout.setVisibility(View.VISIBLE);
//                cart.setVisibility(View.GONE);
//                Toast.makeText(ProductDetailActivity.this, "Item Added to Cart", Toast.LENGTH_SHORT).show();
//                cart_qty = 1;
//            }
//        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart_qty = 1;

                int price = sp.getInt(ConstantSp.productDiscountedPrice,0);

                int totalPrice = cart_qty * price;

                String insertCart = "INSERT INTO cart VALUES(NULL,'0','"+ sp.getString(ConstantSp.productId,"")+ "','" + cart_qty+ "','" + price+ "','" + totalPrice + "')";

                db.execSQL(insertCart);

                qty.setText(String.valueOf(cart_qty));

                totalAmount.setText("Total: $" + totalPrice);

                cart_layout.setVisibility(View.VISIBLE);
                cart.setVisibility(View.GONE);

                Toast.makeText(ProductDetailActivity.this, "Item Added to cart", Toast.LENGTH_SHORT).show();
            }
        });



        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart_qty++;
               updateCart(cart_qty,"update");
            }
        });

//        minus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cart_qty--;
//                if(cart_qty>0){
//                    updateCart(cart_qty,"update");
//                }
//                else{
//                    updateCart(0,"delete");
//                    Toast.makeText(ProductDetailActivity.this, "Item Removed From Cart", Toast.LENGTH_SHORT).show();
//                    cart_layout.setVisibility(View.GONE);
//                    cart.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cart_qty--;

                if(cart_qty > 0)
                {
                    updateCart(cart_qty,"update");
                }
                else
                {
                    // Remove item completely
                    updateCart(0,"delete");

                    Toast.makeText(ProductDetailActivity.this,
                            "Item Removed From Cart",
                            Toast.LENGTH_SHORT).show();
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
//            options.put("amount", String.valueOf(sp.getInt(ConstantSp.productDiscountedPrice,0) * 100));


            int quantity = cart_qty;
            if (quantity == 0){
                quantity = 1;
            }
            int finalAmount = quantity * sp.getInt(ConstantSp.productDiscountedPrice,0);
            options.put("amount", String.valueOf(finalAmount * 100));

            JSONObject preFill = new JSONObject();
            preFill.put("email", "hemigarala@gmail.com");
            preFill.put("contact", "9638221084");
            options.put("prefill", preFill);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("RESPONSE", "Error in starting Razorpay Checkout", e);
        }
    }


    private void updateCart(int qtyValue, String action){
        int price = sp.getInt(ConstantSp.productDiscountedPrice,0);
        int totalPrice = qtyValue * price;
        if (action.equalsIgnoreCase("update"))
        {
            String updateQuery =
                    "UPDATE cart SET qty='" + qtyValue +"', totalPrice='" + totalPrice + "' WHERE productId='" +sp.getString(ConstantSp.productId,"") +"' AND orderid='0'";

            db.execSQL(updateQuery);
            qty.setText(String.valueOf(qtyValue));
            totalAmount.setText("Total: $"+ totalPrice);
        }
        else {
            String deleteQuery = "DELETE FROM cart WHERE productid='" + sp.getString(ConstantSp.productId,"") + "' AND orderid='0'";
            db.execSQL(deleteQuery);
            totalAmount.setText("Total: $0");

            cart_layout.setVisibility(View.GONE);
            cart.setVisibility(View.VISIBLE);
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