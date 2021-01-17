package com.example.sdp_bfit.signupandlogin;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdp_bfit.MainActivity;
import com.example.sdp_bfit.R;
import com.example.sdp_bfit.dashboard.DashboardFragment;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel LoginViewModel;
    private TextInputEditText Email, Password;
    private Button btnLogin;
    private TextView Info, SignUpLink;
    Dialog LoginAlertDialog;
    private String String;
    String userEmail, userPassword;


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        Email = findViewById(R.id.editEmail1);
        userEmail = Email.getText().toString();
        Password = findViewById(R.id.editPassword1);
        userPassword = Password.getText().toString();
        btnLogin = findViewById(R.id.btnLogin);
        Info = findViewById(R.id.txtNoAccount);
        SignUpLink = findViewById(R.id.txtClickSignup);

        String txtSignUpLink = "Click here to Sign Up";
        // to change the word color and underlined it
        SpannableString ss = new SpannableString(txtSignUpLink);
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.BLUE);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        // to make the word clickable
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent (LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        };
        // select the specific word according to its string index
        ss.setSpan(fcs, 6, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(underlineSpan, 6, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan, 6, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SignUpLink.setText(ss);
        SignUpLink.setMovementMethod(LinkMovementMethod.getInstance());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if ((userEmail != null) && (userPassword != null)) {
                    Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    openDialog();
                }*/
                if (TextUtils.isEmpty(Email.getText().toString()) && TextUtils.isEmpty(Password.getText().toString())){
                    openDialog();
                }else {
                    Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
    });
    }

    private void openDialog() {
        // login alert message
        LoginAlertDialog = new Dialog (LoginActivity.this);
        LoginAlertDialog.setContentView(R.layout.fragment_login_alertdialog);
        LoginAlertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LoginAlertDialog.setCancelable(false);

        Button login_alertdialog_btnCancel = LoginAlertDialog.findViewById(R.id.login_alertdialog_btnCancel);
        Button login_alertdialog_btnRetry = LoginAlertDialog.findViewById(R.id.login_alertdialog_btnRetry);

        login_alertdialog_btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAlertDialog.dismiss();
            }
        });

        login_alertdialog_btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAlertDialog.dismiss();
            }
        });
        LoginAlertDialog.show();
    }
}
