package com.example.mayinternship26;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProductDetailActivity extends AppCompatActivity {
    Button BuyNow;
    ImageView image;
    TextView name, originalPrice, discountedPrice, description;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        BuyNow = findViewById(R.id.product_detail_buy_now);
        image = findViewById(R.id.product_detail_image);
        name = findViewById(R.id.product_detail_name);
        originalPrice = findViewById(R.id.product_detail_original_price);
        discountedPrice = findViewById(R.id.product_detail_discounted_price);
        description = findViewById(R.id.product_detail_description);

        originalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        image.setImageResource(sp.getInt(ConstantSp.productImage,0));
        name.setText(sp.getString(ConstantSp.productName,""));
        originalPrice.setText(ConstantSp.symbol+sp.getInt(ConstantSp.productOriginalPrice,0));
        discountedPrice.setText(ConstantSp.symbol+sp.getInt(ConstantSp.productDiscountedPrice,0));
        description.setText(sp.getString(ConstantSp.productDescription,""));


    }
}