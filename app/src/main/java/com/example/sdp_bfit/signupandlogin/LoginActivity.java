package com.example.sdp_bfit.signupandlogin;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdp_bfit.Database;
import com.example.sdp_bfit.MainActivity;
import com.example.sdp_bfit.R;
import com.example.sdp_bfit.profile.ProfileFragment;
import com.example.sdp_bfit.profile.User;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel LoginViewModel;
    private TextInputEditText Email, Password;
    private Button btnLogin;
    private TextView SignUpLink, ForgotPasswordLink;
    Dialog LoginAlertDialog, LoginWarningDialog;
    public static User user;
    public static User user1;


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        Email = findViewById(R.id.editEmail1);
        Password = findViewById(R.id.editPassword1);
        ForgotPasswordLink = findViewById(R.id.txtForgotPassword);
        btnLogin = findViewById(R.id.btnLogin);
        SignUpLink = findViewById(R.id.txtClickSignup);

        String txtForgotPassLink = "Forgot Password?";
        // to change the word color and underlined it
        SpannableString ss1 = new SpannableString(txtForgotPassLink);
        ForegroundColorSpan fcs1 = new ForegroundColorSpan(Color.BLUE);
        UnderlineSpan underlineSpan1 = new UnderlineSpan();
        // to make the word clickable
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent1 = new Intent(LoginActivity.this, ResetPassActivity.class);
                startActivity(intent1);
            }
        };
        // select the specific word according to its string index
        ss1.setSpan(fcs1, 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(underlineSpan1, 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss1.setSpan(clickableSpan1, 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForgotPasswordLink.setText(ss1);
        ForgotPasswordLink.setMovementMethod(LinkMovementMethod.getInstance());


        String txtSignUpLink = "Click here to Sign Up";
        // to change the word color and underlined it
        SpannableString ss = new SpannableString(txtSignUpLink);
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.BLUE);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        // to make the word clickable
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        };
        // select the specific word according to its string index
        ss.setSpan(fcs, 6, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(underlineSpan, 6, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan, 6, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SignUpLink.setText(ss);
        SignUpLink.setMovementMethod(LinkMovementMethod.getInstance());

        Database db = new Database(this);
        String email1 = Email.getText().toString();
        String uname = db.showUserName(email1);
        String upassword = db.showPassword(email1);
        String ugender = db.showUserGender(email1);
        Double uweight = db.showUserWeight(email1);
        Double uheight = db.showUserHeight(email1);
        user1 = new User(email1, uname, upassword, ugender, uweight, uheight);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Email.getText().toString()) && TextUtils.isEmpty(Password.getText().toString())){
                    openDialog();
                }else {
                    verifyUserDetails();
                }
            }
        });
    }

        //verify user details
    public void verifyUserDetails() {
        Database db = new Database(this);
        String email = Email.getText().toString();
        String uname = db.showUserName(email);
        String upassword = db.showPassword(email);
        String ugender = db.showUserGender(email);
        Double uweight = db.showUserWeight(email);
        Double uheight = db.showUserHeight(email);
        user = new User(email, uname, upassword, ugender, uweight, uheight);
        
        if (db.showPassword(email) != "") {
            Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            openUnexistedEmailDialog();
        }
    }


//        if (c.getCount() == 0) {
//            // no row in the db
//            Toast.makeText(LoginActivity.this, "No data is found", Toast.LENGTH_SHORT).show();
//        } else {
//            if (c.moveToFirst()) {
//                do {
//                    String uemail = c.getColumnName(1);
//                    String upassword = c.getColumnName(3);
//                    if ((Email.getText().toString() == uemail) && (Password.getText().toString() == upassword)) {
//                        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
////                        Bundle bundle = new Bundle();
////                        bundle.putString("UserEmail", email);
////                        //set Profile Fragment Arguements
////                        ProfileFragment pf = new ProfileFragment();
////                        pf.setArguments(bundle);
//                    } else if (Email.getText().toString() != c.getString(1)) {
//                        Toast.makeText(LoginActivity.this, "Your account has not been registered. Please proceed to the Sign Up page.", Toast.LENGTH_SHORT).show();
//                        Email.getText().clear();
//                        Password.getText().clear();
//                    } else if (Email.getText().toString() == c.getString(1) && Password.getText().toString() != c.getString(3)) {
//                        Toast.makeText(LoginActivity.this, "Invalid Password. Please try again.", Toast.LENGTH_SHORT).show();
//                        Password.getText().clear();
//                    }
//                } while (c.moveToNext());
//            }
//        }

    private void openDialog() {
        // login alert message
        LoginAlertDialog = new Dialog(LoginActivity.this);
        LoginAlertDialog.setContentView(R.layout.fragment_login_alertdialog);
        LoginAlertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LoginAlertDialog.setCancelable(false);

        Button login_alertdialog_btnCancel = LoginAlertDialog.findViewById(R.id.alertdialog_btnCancel);
        Button login_alertdialog_btnRetry = LoginAlertDialog.findViewById(R.id.alertdialog_btnRetry);

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

    // alert users the email is not existed
    private void openUnexistedEmailDialog () {
        // unexisted email notification
        LoginWarningDialog = new Dialog(LoginActivity.this);
        LoginWarningDialog.setContentView(R.layout.fragment_login_warning);
        LoginWarningDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LoginWarningDialog.setCancelable(false);

        Button login_warning_btnSignup = LoginWarningDialog.findViewById(R.id.login_warning_btnSignup);
        Button login_warning_btnRetry = LoginWarningDialog.findViewById(R.id.login_warning_btnRetry);

        login_warning_btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        login_warning_btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginWarningDialog.dismiss();
                Email.getText().clear();
                Password.getText().clear();
            }
        });
        LoginWarningDialog.show();
    }

}

