package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.example.assignment.PrefManager.SharedPrefManager;
import com.google.android.material.snackbar.Snackbar;

public class Activity_login extends AppCompatActivity {
    private EditText name,mobile;
    private AppCompatButton btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //init
        name = findViewById(R.id.edt_name);
        mobile = findViewById(R.id.edt_mobile);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (name.getText().toString().length() == 0) {
                        Snackbar.make(v, "Please enter Name", Snackbar.LENGTH_SHORT).show();
                    } else if (mobile.getText().toString().length() == 0) {
                        Snackbar.make(v, "Please enter Mobile No.", Snackbar.LENGTH_SHORT).show();
                    }else{
                        SharedPrefManager.getInstance(Activity_login.this).userLogin(name.getText().toString(),mobile.getText().toString());
                        Intent intent = new Intent(Activity_login.this,Activity_main.class);
                        startActivity(intent);
                        finish();
                    }
                }
        });
    }
}
