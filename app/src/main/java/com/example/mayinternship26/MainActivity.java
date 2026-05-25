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

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    TextView create_new_account, forget_password;
    Button button_sign_in;
    String emailPattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    EditText login_email, login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create_new_account = findViewById(R.id.create_new_account);
        forget_password = findViewById(R.id.forget_password);
        button_sign_in = findViewById(R.id.button_sign_in);

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);




        create_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });


        button_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(login_email.getText().toString().trim().equals("")){
                    login_email.setError("Email Required");
                }

                else if(!login_email.getText().toString().matches(emailPattern)){
                    login_email.setError("Invalid Email Address");
                }
                else if(login_password.getText().toString().trim().equals("")){
                    login_password.setError("Password Required");
                }

                else if(login_password.getText().toString().trim().length()<8){
                    login_password.setError("Minimum 8 Characters Required");
                }

                else{
                    Toast.makeText(MainActivity.this, "Login Succesfully", Toast.LENGTH_LONG).show();
                }




                //Toast (Short Duration, Long Duration)

//               Toast.makeText(MainActivity.this, "Login Succesfully", Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, "Login Succesfully", Toast.LENGTH_LONG).show();



                //Snackbar (Short Duration, Long Duration)
//                Snackbar.make(view, "Login Successfully!!", Snackbar.LENGTH_SHORT).show();

            }
        });
    }
}




