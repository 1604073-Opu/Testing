package com.ourcuet.opu.diary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    EditText name, mail, pass, dob;
    TextView status;
    Button signup, male, female;
    String gender, Name, Pass, Email, Dob;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = (EditText) findViewById(R.id.name);
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        dob = (EditText) findViewById(R.id.dob);
        signup = (Button) findViewById(R.id.signup);
        male = (CheckBox) findViewById(R.id.gender1);
        female = (CheckBox) findViewById(R.id.gender);
        status = (TextView) findViewById(R.id.note);
        EditText pass2 = (EditText) findViewById(R.id.password2);
        male.setOnClickListener((View e) -> {
            gender = "Male";
            if (((CheckBox) female).isChecked()) ((CheckBox) female).setChecked(false);

        });
        female.setOnClickListener(e -> {
            gender = "Female";
            if (((CheckBox) male).isChecked()) ((CheckBox) male).setChecked(false);
        });
        signup.setOnClickListener(e ->
        {
            Name = name.getText().toString();
            Pass = pass.getText().toString();
            Email = mail.getText().toString();
            Dob = dob.getText().toString();
            if (Name.length() != 0 && Pass.length() != 0 && Email.length() != 0 && Dob.length() != 0 && (gender == "Male" || gender == "Female") && Pass.equals(pass2.getText().toString())) {
                pd = ProgressDialog.show(this, "", "Loading... Please Wait");
                register();
            } else if (Pass.equals(pass2.getText().toString()) == false)
                status.setText("Password should be same");
            else status.setText("Please fill out all the fields");
        });

        dob.setInputType(InputType.TYPE_NULL);
        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(Signup.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox

                                if (year < mYear)
                                    view.updateDate(mYear, mMonth, mDay);

                                if (monthOfYear < mMonth && year == mYear)
                                    view.updateDate(mYear, mMonth, mDay);

                                if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                                    view.updateDate(mYear, mMonth, mDay);

                                dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();

            }
        });
    }

    private void register() {
        String url = "http://zero.ourcuet.com/signup.php";
        Map<String, String> map = new HashMap<>();
        map.put("email", Email);
        map.put("pass", Pass);
        map.put("name", Name);
        map.put("dob", Dob);
        map.put("gender", gender);
        CustomRequest customRequest = new CustomRequest(Request.Method.POST, url, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pd.cancel();
                System.out.println(response.toString());
                String st=null;
                try {
                    st = response.getString("status");
                } catch (JSONException e) {
                    status.setText("Network Error");
                    e.printStackTrace();
                    System.out.println(e);
                }
                if (st.equals("ok"))
                    setContentView(R.layout.activity_welcome);
                else if (st.equals("exists"))
                    status.setText("Email already exists");
                else status.setText("Network Error");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                error.printStackTrace();
                System.out.println(error);
                status.setText("Internel Error");
            }
        });
        SingletonRequestQueue.getInstance(this).addToRequestQueue(customRequest);
    }
}