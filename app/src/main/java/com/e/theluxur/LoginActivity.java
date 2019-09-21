package com.e.theluxur;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText mobile_number,password;
    Button login;
    ProgressDialog pDialog;
    int flag=0;
    JSONParser jsonParser = new JSONParser();
    private static String url = "http://" + IPAddress.IPADDRESS + "/Web/login.php";
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mobile_number=(EditText)findViewById(R.id.mobile_number);
        password=(EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {


                String number = mobile_number.getText().toString();
                String pwd = password.getText().toString();

                //Check all fields
                if(number.length()<10)
                {
                    Toast.makeText(LoginActivity.this,"Please Enter correct mobile number", Toast.LENGTH_LONG).show();
                    return;
                }
                if(pwd.length()<4)
                {
                    Toast.makeText(LoginActivity.this,"Please Enter correct password", Toast.LENGTH_LONG).show();
                    return;
                }
                //check connectivity
                if(!isOnline(LoginActivity.this))
                {
                    Toast.makeText(LoginActivity.this,"No network connection", Toast.LENGTH_LONG).show();
                    return;
                }


                new loginAccess().execute(number,pwd);

            }



            //Close code that check online details
        });
        //Close log in

    }

    //code to check online details
    private boolean isOnline(Activity mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }

    class loginAccess extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String number,pwd;
            number = arg0[0];
            pwd = arg0[1];
            params.add(new BasicNameValuePair("mobile_number", number));
            params.add(new BasicNameValuePair("password", pwd));
            JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
            Log.d("Create Response", String.valueOf(json));
            if (json == null)
                LoginActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Oops, API response null", Toast.LENGTH_SHORT).show();
                    }
                });
            else {
                try {
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        flag = 0;
                        Intent i = new Intent(getApplicationContext(), Welcome.class);
                        i.putExtra("mobile_number", number);
                        i.putExtra("password", pwd);
                        startActivity(i);
                        finish();
                    } else {
                        // failed to login
                        flag = 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if(flag==1)
                Toast.makeText(LoginActivity.this,"Please Enter Correct information", Toast.LENGTH_LONG).show();

        }

    }
}
