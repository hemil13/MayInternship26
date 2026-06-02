package com.example.mayinternship26;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    Button delete, logout, profile, category;

    TextView welcome;

    String email;

    SharedPreferences sp;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        db = openOrCreateDatabase("MayInternship26.db", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name VARCHAR(50), email VARCHAR(100), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);


        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        email = sp.getString(ConstantSp.email, "");

        delete = findViewById(R.id.dashboard_delete);
        logout = findViewById(R.id.dashboard_logout);
        welcome = findViewById(R.id.dashboard_welcome);
        profile = findViewById(R.id.dashboard_profile);
        category = findViewById(R.id.dashboard_category);

        welcome.setText("Welcome "+sp.getString(ConstantSp.name, ""));





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().clear().commit();

                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);

                Toast.makeText(DashboardActivity.this, "User Logged Out", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteUser = "DELETE FROM user WHERE email='"+email+"'";
                db.execSQL(deleteUser);

                sp.edit().clear().commit();

                Toast.makeText(DashboardActivity.this, "Profile Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}