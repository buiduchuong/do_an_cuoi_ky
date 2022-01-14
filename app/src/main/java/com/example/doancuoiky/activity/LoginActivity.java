package com.example.doancuoiky.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.doancuoiky.R;
import com.example.doancuoiky.model.Book;
import com.example.doancuoiky.other.Check;
import com.example.doancuoiky.other.ConnectWithFirebase;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {

    EditText editText_user;
    TextView textView_forgotPass;
    TextInputEditText editText_pass;
    CheckBox checkBox_saveAcc;
    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        anhxa();
        setSaveAccount();
    }



    private void anhxa() {

        textView_forgotPass = findViewById(R.id.textView_forGetPass);
        checkBox_saveAcc = findViewById(R.id.checkBox_saveAcc);
        editText_pass = findViewById(R.id.edit_pass);
        editText_user = findViewById(R.id.edit_user);
    }


    private void login() {

        ConnectWithFirebase.getInstance(LoginActivity.this).signInwithEmail(editText_user.getText().toString().trim(),
                editText_pass.getText().toString().trim());

    }

    public void click(View view) {
        if (!Check.haveNetworkConnection(getApplicationContext())) {
            Check.creatToat("Bạn chưa kết nối internet!", getApplicationContext());
        } else {
            switch (view.getId()) {
                case R.id.button_login:
                    if (Check.checkNull(editText_user, "email") && Check.checkNull(editText_pass, getResources().getString(R.string.password)) &&
                            Check.checkKhoangTrang(editText_user, "email") && Check.checkKhoangTrang(editText_pass, getResources().getString(R.string.password))){
                        login();
                        saveAccount();
                    }
                        break;
                case R.id.textView_signUp:
                    startActivity(new Intent(this, SignUpActivity.class));
                    break;
                case R.id.textView_forGetPass:
                    startActivity(new Intent(this, ForgotPasswordActivity.class));
                    break;
            }
        }
    }


    private void setSaveAccount() {
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        editText_user.setText(sharedPreferences.getString("user", ""));
        editText_pass.setText(sharedPreferences.getString("pass", ""));
        checkBox_saveAcc.setChecked(sharedPreferences.getBoolean("checkbox", false));
    }

    private void saveAccount() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", editText_user.getText().toString());
        if (checkBox_saveAcc.isChecked()) {
            editor.putString("pass", editText_pass.getText().toString());
            editor.putBoolean("checkbox", true);
        } else {
            editor.remove("pass");
            editor.remove("checkbox");
        }
        editor.commit();
    }


}
