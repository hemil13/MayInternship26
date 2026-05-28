package com.example.mayinternship26;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ForgetPasswordActivity extends AppCompatActivity {
    Button forget_password;
    EditText email, newPassword, newCnfPassword;

    String emailPattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        db = openOrCreateDatabase("MayInternship26.db", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name VARCHAR(50), email VARCHAR(100), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);

        forget_password = findViewById(R.id.button_forget_password);
        email = findViewById(R.id.fp_email);
        newPassword = findViewById(R.id.fp_new_password);
        newCnfPassword = findViewById(R.id.fp_new_cnfPassword);

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().equals("")){
                    email.setError("Email Required");
                }

                else if(!email.getText().toString().matches(emailPattern)){
                    email.setError("Invalid Email Id");
                }

                else if(newPassword.getText().toString().equals("")){
                    newPassword.setError("Password is required");
                }

                else if(newPassword.getText().toString().length()<6){
                    newPassword.setError("Minimum 8 characters required");
                }

                else if(newCnfPassword.getText().toString().equals("")){
                    newCnfPassword.setError("Confirm Password is Required");
                }

                else if(!newCnfPassword.getText().toString().matches(newPassword.getText().toString())){
                    newCnfPassword.setError("Password does not matches");
                }

                else{

                    String checkUser = "SELECT * FROM user WHERE email = '"+email.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(checkUser, null);

                    if(cursor.getCount()>0){
                        String updateUser = "UPDATE user SET password = '"+newPassword.getText().toString()+"' WHERE email = '"+email.getText().toString()+"'";
                        db.execSQL(updateUser);

                        Toast.makeText(ForgetPasswordActivity.this, "Password Updated Successfully!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                    else{
                        Toast.makeText(ForgetPasswordActivity.this, "Invalid Email Id", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
    }
}