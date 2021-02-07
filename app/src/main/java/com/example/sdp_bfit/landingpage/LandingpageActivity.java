package com.example.sdp_bfit.landingpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sdp_bfit.R;
import com.example.sdp_bfit.signupandlogin.LoginActivity;
import com.example.sdp_bfit.signupandlogin.SignUpActivity;

public class LandingpageActivity extends AppCompatActivity {
    private Button Login;
    private Button SignUp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage);
        Login = findViewById(R.id.btnLogin);
        SignUp = findViewById(R.id.btnSignUp);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LandingpageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LandingpageActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }


}
