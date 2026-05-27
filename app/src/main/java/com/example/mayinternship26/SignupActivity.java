package com.example.mayinternship26;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {
    TextView already_account;
    EditText name, email, contact, password, cnf_password;
    Button button_sign_up;

    String EmailPattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = openOrCreateDatabase("MayInternship26", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name VARCHAR(50), email VARCHAR(100), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);

        already_account = findViewById(R.id.already_account);
        button_sign_up = findViewById(R.id.button_sign_up);


        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        contact = findViewById(R.id.signup_contact);
        password = findViewById(R.id.signup_password);
        cnf_password = findViewById(R.id.signup_confirm_password);

        already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        button_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().equals("")){
                    name.setError("Name Required");
                }

                else if(email.getText().toString().equals("")){
                    email.setError("Email Required");
                }

                else if(!email.getText().toString().matches(EmailPattern)){
                    email.setError("Email Not Valid");
                }

                else if(contact.getText().toString().equals("")){
                    contact.setError("Contact is required");
                }

                else if(contact.getText().toString().length()<10){
                    contact.setError("Invalid Contact number");
                }

                else if(password.getText().toString().equals("")){
                    password.setError("Password is Required");
                }

                else if(password.getText().toString().length()<6){
                    password.setError("Minimum 8 characters required");
                }

                else if(cnf_password.getText().toString().equals("")){
                    cnf_password.setError("Confirm Password is Required");
                }

                else if(!cnf_password.getText().toString().matches(password.getText().toString())){
                    cnf_password.setError("Password does not matches");
                }

                else{

                    String insertUser = "INSERT INTO user VALUES (null, '"+name.getText().toString()+"', " +
                            "'"+email.getText().toString()+"' , '"+contact.getText().toString()+"', " +
                            "'"+password.getText().toString()+"' )";
                    db.execSQL(insertUser);



                    Toast.makeText(SignupActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}