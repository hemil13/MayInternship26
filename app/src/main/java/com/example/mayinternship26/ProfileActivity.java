package com.example.mayinternship26;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    EditText name, email, contact, password, cnf_password;
    Button edit_profile, update_profile;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        name = findViewById(R.id.profile_name);
        email = findViewById(R.id.profile_email);
        contact = findViewById(R.id.profile_contact);
        password = findViewById(R.id.profile_password);
        cnf_password = findViewById(R.id.profile_confirm_password);
        edit_profile = findViewById(R.id.button_edit_profile);
        update_profile = findViewById(R.id.button_update_profile);


        name.setEnabled(false);
        email.setEnabled(false);
        contact.setEnabled(false);

        name.setText(sp.getString(ConstantSp.name, ""));
        email.setText(sp.getString(ConstantSp.email, ""));
        contact.setText(sp.getString(ConstantSp.conatct, ""));

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(true);
                email.setEnabled(true);
                contact.setEnabled(true);

                cnf_password.setVisibility(View.VISIBLE);
                edit_profile.setVisibility(View.GONE);
                update_profile.setVisibility(View.VISIBLE);
            }
        });

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(false);
                email.setEnabled(false);
                contact.setEnabled(false);

                cnf_password.setVisibility(View.GONE);
                edit_profile.setVisibility(View.VISIBLE);
                update_profile.setVisibility(View.GONE);
            }
        });



    }
}