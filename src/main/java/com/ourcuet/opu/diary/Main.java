package com.ourcuet.opu.diary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Main extends AppCompatActivity {

    private String status = " ";
    private EditText editMail, editPass;
    TextView show;
    private Button signin, signup;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editMail = (EditText) findViewById(R.id.editMail);
        editPass = (EditText) findViewById(R.id.editPass);
        show = (TextView) findViewById(R.id.note);
        show.setText(status);
        signin = (Button) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        signin.setOnClickListener(e -> {
            String email, password;
            email = editMail.getText().toString();
            password = editPass.getText().toString();
            if (email.length() != 0 && password.length() != 0 && email.contains("@")) {
                dialog = ProgressDialog.show(Main.this, "",
                        "Loading. Please wait...", true);
                LogIn(email, password);
            } else {
                show.setText("Please input valid email and password");
            }
        });
        signup.setOnClickListener(e ->

        {
            finish();
            startActivity(new Intent(this, Signup.class));
        });
    }

    public void LogIn(String email, String pass) {
        String url = "http://zero.ourcuet.com/login.php";
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("pass", pass);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    System.out.println(response.getBoolean("status"));
                    if (response.getBoolean("status") == true) {
                        dialog.cancel();
                        setContentView(R.layout.activity_welcome);
                    } else {
                        dialog.cancel();
                        status = "Wrong email or password";
                        show.setText(status);
                    }
                } catch (JSONException e) {
                    dialog.cancel();
                    show.setText("Network Error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                show.setText("Network Error");
            }
        });
        SingletonRequestQueue.getInstance(this).addToRequestQueue(jsObjRequest);
    }
}

