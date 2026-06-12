package com.example.mayinternship26;

import android.app.Activity;
import android.content.SharedPreferences;
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




        BuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startpayment();
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