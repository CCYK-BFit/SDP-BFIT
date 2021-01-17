package com.example.sdp_bfit.signupandlogin;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.example.sdp_bfit.R;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel SignUpViewModel;
    EditText Email, FullName, Gender, Password, RePassword;
    Button btnSignUp;
    TextView LoginLink;
    Dialog SignUpNoticeDialog;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        SignUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        Email = findViewById(R.id.editEmail);
        FullName = findViewById(R.id.editFName);
        Gender = findViewById(R.id.editGender);
        Password = findViewById(R.id.editPassword);
        RePassword = findViewById(R.id.editRePassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        LoginLink = findViewById(R.id.txtClickLogin);

        String txtLoginLink = "Click here to login";

        SpannableString ss = new SpannableString(txtLoginLink);
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.BLUE);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };

        ss.setSpan(fcs, 6, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(underlineSpan, 6, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan, 6, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        LoginLink.setText(ss);
        LoginLink.setMovementMethod(LinkMovementMethod.getInstance());

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



        private void openDialog() {
            // existed email notification
            SignUpNoticeDialog = new Dialog(SignUpActivity.this);
            SignUpNoticeDialog.setContentView(R.layout.fragment_signup_notice);
            SignUpNoticeDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            SignUpNoticeDialog.setCancelable(false);

            Button signup_notice_btnLogin = SignUpNoticeDialog.findViewById(R.id.signup_notice_btnLogin);
            Button signup_notice_btnRetry = SignUpNoticeDialog.findViewById(R.id.signup_notice_btnRetry);

            signup_notice_btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });

            signup_notice_btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SignUpNoticeDialog.dismiss();
                    Email.getText().clear();
                }
            });
            SignUpNoticeDialog.show();
        }

}
