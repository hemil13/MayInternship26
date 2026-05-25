package com.example.mayinternship26;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

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

                else if(newPassword.getText().toString().length()<8){
                    newPassword.setError("Minimum 8 characters required");
                }

                else if(newCnfPassword.getText().toString().equals("")){
                    newCnfPassword.setError("Confirm Password is Required");
                }

                else if(!newCnfPassword.getText().toString().matches(newPassword.getText().toString())){
                    newCnfPassword.setError("Password does not matches");
                }

                else{
                    Toast.makeText(ForgetPasswordActivity.this, "Password Updated Successfully!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

            }
        });
    }
}