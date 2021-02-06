package com.example.sdp_bfit.signupandlogin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sdp_bfit.MainActivity;
import com.example.sdp_bfit.R;
import static com.example.sdp_bfit.signupandlogin.LoginActivity.user1;

public class ResetPassActivity extends AppCompatActivity {
    private TextView lblEmail;
    private EditText txtEmail, txtNewPass, txtCNewPass;
    private Button btnResetBack, btnReset;
    private Dialog LoginAlertDialog, PasswordUnmatchedDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

//        lblEmail = findViewById(R.id.lblEmail);
        txtEmail = findViewById(R.id.editResetEmail1);
        txtNewPass = findViewById(R.id.editNewPassword1);
        txtCNewPass = findViewById(R.id.editCNewPassword1);
        btnResetBack = findViewById(R.id.btnResetBack);
        btnReset = findViewById(R.id.btnReset);

//        String uemail = user1.getUserEmail();
//        lblEmail.setText(uemail.toString());

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtEmail.getText().toString()) && TextUtils.isEmpty(txtNewPass.getText().toString()) &&
                        TextUtils.isEmpty(txtCNewPass.getText().toString())){
                    openDialog();
                } else if (!txtNewPass.getText().toString().equals(txtCNewPass.getText().toString())) {
                    openPasswordUnmatchedDialog();
                } else {
                    Toast.makeText(ResetPassActivity.this, "Password reset successfully!", Toast.LENGTH_SHORT).show();
                    txtEmail.getText().clear();
                    txtNewPass.getText().clear();
                    txtCNewPass.getText().clear();
                }
            }
        });

        btnResetBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPassActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openDialog() {
        // login alert message
        LoginAlertDialog = new Dialog(ResetPassActivity.this);
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

    // password unmatched dialog
    private void openPasswordUnmatchedDialog () {
        PasswordUnmatchedDialog = new Dialog(ResetPassActivity.this);
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
                txtNewPass.getText().clear();
                txtCNewPass.getText().clear();
            }
        });
        PasswordUnmatchedDialog.show();
    }
}
