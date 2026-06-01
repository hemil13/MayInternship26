package com.example.mayinternship26;

import android.content.SharedPreferences;
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

public class ProfileActivity extends AppCompatActivity {

    EditText name, email, contact, password, cnf_password;
    Button edit_profile, update_profile;

    SharedPreferences sp;

    SQLiteDatabase db;

    String EmailPattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
        db = openOrCreateDatabase("MayInternship26.db", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name VARCHAR(50), email VARCHAR(100), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);


        name = findViewById(R.id.profile_name);
        email = findViewById(R.id.profile_email);
        contact = findViewById(R.id.profile_contact);
        password = findViewById(R.id.profile_password);
        cnf_password = findViewById(R.id.profile_confirm_password);
        edit_profile = findViewById(R.id.button_edit_profile);
        update_profile = findViewById(R.id.button_update_profile);

        setData(false);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(true);

                cnf_password.setVisibility(View.VISIBLE);
                edit_profile.setVisibility(View.GONE);
                update_profile.setVisibility(View.VISIBLE);
            }
        });

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(name.getText().toString().trim().equals("")){
                    name.setError("Name Required");
                }

                else if(email.getText().toString().trim().equals("")){
                    email.setError("Email Required");
                }

                else if(!email.getText().toString().trim().matches(EmailPattern)){
                    email.setError("Email Not Valid");
                }

                else if(contact.getText().toString().trim().equals("")){
                    contact.setError("Contact is required");
                }

                else if(contact.getText().toString().trim().length()<10){
                    contact.setError("Invalid Contact number");
                }

                else if(password.getText().toString().trim().equals("")){
                    password.setError("Password is Required");
                }

                else if(password.getText().toString().trim().length()<6){
                    password.setError("Minimum 8 characters required");
                }

                else if(cnf_password.getText().toString().trim().equals("")){
                    cnf_password.setError("Confirm Password is Required");
                }

                else if(!cnf_password.getText().toString().trim().matches(password.getText().toString())){
                    cnf_password.setError("Password does not matches");
                }

                else{
                    String updateUser = "UPDATE user SET name = '"+name.getText().toString()+"'," +
                            " email = '"+email.getText().toString()+"', contact = '"+contact.getText().toString()+"' ," +
                            " password = '"+password.getText().toString()+"' " +
                            "WHERE userid = '"+sp.getString(ConstantSp.userid,"")+"'";
                    db.execSQL(updateUser);

                    sp.edit().putString(ConstantSp.name, name.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.email, email.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.conatct, contact.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.password, password.getText().toString()).commit();

                    Toast.makeText(ProfileActivity.this, "User Updated Successfully", Toast.LENGTH_SHORT).show();

                    setData(false);

                    cnf_password.setVisibility(View.GONE);
                    edit_profile.setVisibility(View.VISIBLE);
                    update_profile.setVisibility(View.GONE);
                }
            }
        });
    }
    void setData(Boolean b){
        name.setEnabled(b);
        email.setEnabled(b);
        contact.setEnabled(b);
        password.setEnabled(b);

        name.setText(sp.getString(ConstantSp.name, ""));
        email.setText(sp.getString(ConstantSp.email, ""));
        contact.setText(sp.getString(ConstantSp.conatct, ""));
        password.setText(sp.getString(ConstantSp.password, ""));
    }
}