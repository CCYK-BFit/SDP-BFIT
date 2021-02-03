package com.example.sdp_bfit.signupandlogin;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdp_bfit.MainActivity;
import com.example.sdp_bfit.R;
import com.example.sdp_bfit.profile.ProfileFragment;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel SignUpViewModel;
    EditText Email, FullName, Password, ConfirmationPassword, Weight, Height;
    Button btnSignUp;
    RadioButton radMale, radFemale;
    TextView LoginLink;
    Dialog InvalidEmailDialog, PasswordUnmatchedDialog, SignUpNoticeDialog;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        SignUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        Email = findViewById(R.id.editEmail);
        FullName = findViewById(R.id.editFName);
        Password = findViewById(R.id.editPassword);
        ConfirmationPassword = findViewById(R.id.editConfirmationPassword);
        radMale = findViewById(R.id.radMale);
        radFemale = findViewById(R.id.radFemale);
        Weight = findViewById(R.id.editWeight);
        Height = findViewById(R.id.editHeight);
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
//                if(TextUtils.isEmpty(Email.getText().toString()) && (TextUtils.isEmpty(FullName.getText().toString())) && (TextUtils.isEmpty(Password.getText().toString())) &&
//                        (TextUtils.isEmpty(ConfirmationPassword.getText().toString())) && (TextUtils.isEmpty(radMale.getText().toString())) &&
//                        (TextUtils.isEmpty(radFemale.getText().toString())) && (TextUtils.isEmpty(Weight.getText().toString())) &&
//                        (TextUtils.isEmpty(Height.getText().toString()))){
//                    new AlertDialog.Builder(SignUpActivity.this)
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .setTitle("Null Value")
//                            .setMessage("Please fill in all your details.")
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            })
//                            .setNegativeButton("Cancel", null)
//                            .show();
//                } else
                if (!EMAIL_ADDRESS_PATTERN.matcher(Email.getText().toString()).matches()) {
                    openInvalidEmailDialog();
                } else if (! Password.getText().toString().equals(ConfirmationPassword.getText().toString())){
                    openPasswordUnmatchedDialog();
                } else if (FullName.getText().toString().isEmpty()) {
                    FullName.setError("Must contain at least 5 characters");
                } else {
                    Toast.makeText(SignUpActivity.this, "Sign Up Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    // invalid email dialog
    private void openInvalidEmailDialog() {
        InvalidEmailDialog = new Dialog(SignUpActivity.this);
        InvalidEmailDialog.setContentView(R.layout.fragment_invalidemail);
        InvalidEmailDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        InvalidEmailDialog.setCancelable(false);

        Button InvalidEmailDialogbtnCancel = InvalidEmailDialog.findViewById(R.id.btnCancel);
        Button InvalidEmailDialogbtnRetry = InvalidEmailDialog.findViewById(R.id.btnRetry);

        InvalidEmailDialogbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InvalidEmailDialog.dismiss();
            }
        });

        InvalidEmailDialogbtnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InvalidEmailDialog.dismiss();
                Email.getText().clear();
            }
        });
        InvalidEmailDialog.show();
    }
    // password unmatched dialog
    private void openPasswordUnmatchedDialog() {
        PasswordUnmatchedDialog = new Dialog(SignUpActivity.this);
        PasswordUnmatchedDialog.setContentView(R.layout.fragment_passwordunmatched);
        PasswordUnmatchedDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        PasswordUnmatchedDialog.setCancelable(false);

        Button PasswordUnmatchedDialogbtnCancel = PasswordUnmatchedDialog.findViewById(R.id.btnCancel);
        Button PasswordUnmatchedDialogbtnRetry = PasswordUnmatchedDialog.findViewById(R.id.btnRetry);

        PasswordUnmatchedDialogbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordUnmatchedDialog.dismiss();
            }
        });

        PasswordUnmatchedDialogbtnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordUnmatchedDialog.dismiss();
                Password.getText().clear();
                ConfirmationPassword.getText().clear();
            }
        });
        PasswordUnmatchedDialog.show();
    }
    //        // alert users the email is existed
//        private void openDialog () {
//            // existed email notification
//            SignUpNoticeDialog = new Dialog(SignUpActivity.this);
//            SignUpNoticeDialog.setContentView(R.layout.fragment_signup_notice);
//            SignUpNoticeDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            SignUpNoticeDialog.setCancelable(false);
//
//            Button signup_notice_btnLogin = SignUpNoticeDialog.findViewById(R.id.signup_notice_btnLogin);
//            Button signup_notice_btnRetry = SignUpNoticeDialog.findViewById(R.id.signup_notice_btnRetry);
//
//            signup_notice_btnLogin.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                }
//            });
//
//            signup_notice_btnRetry.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    SignUpNoticeDialog.dismiss();
//                    Email.getText().clear();
//                }
//            });
//            SignUpNoticeDialog.show();
//        }
    // email format
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );


}
