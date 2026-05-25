package com.example.mayinternship26;

import android.content.Intent;
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
    String password_pattern = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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

                else if(password.getText().toString().length()<8){
                    password.setError("Minimum 8 characters required");
                }

                else if(cnf_password.getText().toString().equals("")){
                    cnf_password.setError("Confirm Password is Required");
                }

                else if(!cnf_password.getText().toString().matches(password.getText().toString())){
                    cnf_password.setError("Password does not matches");
                }

                else{
                    Toast.makeText(SignupActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}