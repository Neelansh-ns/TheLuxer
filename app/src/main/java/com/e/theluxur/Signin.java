package com.e.theluxur;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
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



public class Signin extends Activity {
    Button login , signin;
    EditText mobile_number,password,email_id,hint;
    ProgressDialog pDialog;
    int flag=0;
    JSONParser jsonParser = new JSONParser();
    private static String url = "http://192.168.1.104/Web/register.php";
    private static final String TAG_SUCCESS = "success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        //Go To Login.java
        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(i);

            }
        });
        // Close Login.java

        //Get all data and log in
        signin=(Button)findViewById(R.id.signin);
        mobile_number=(EditText)findViewById(R.id.mobile_number);
        password=(EditText)findViewById(R.id.password);
        hint=(EditText)findViewById(R.id.hint);
        email_id=(EditText)findViewById(R.id.email_id);



        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                String number=mobile_number.getText().toString();
                String pwd=password.getText().toString();
                String h=hint.getText().toString();
                String id=email_id.getText().toString();

                //Check all fields
                if(number.length()<10)
                {
                    Toast.makeText(Signin.this,"Please Enter correct mobile number", Toast.LENGTH_LONG).show();
                    return;
                }
                if(pwd.length()<4)
                {
                    Toast.makeText(Signin.this,"Please Enter minimum 4 letters in password", Toast.LENGTH_LONG).show();
                    return;
                }
                if(h.length()<4)
                {
                    Toast.makeText(Signin.this,"Please Enter minimum 4 leters in hint", Toast.LENGTH_LONG).show();
                    return;
                }
                if(id.length()<11)
                {
                    Toast.makeText(Signin.this,"Please Enter correct email id", Toast.LENGTH_LONG).show();
                    return;
                }
                //check connectivity
                if(!isOnline(Signin.this))
                {
                    Toast.makeText(Signin.this,"No network connection", Toast.LENGTH_LONG).show();
                    return;
                }

                //from login.java
                new loginAccess().execute(number,pwd,h,id);
            }



            //code to check online details
            private boolean isOnline(Context mContext) {
                ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnectedOrConnecting())
                {
                    return true;
                }
                return false;
            }
            //Close code that check online details
        });
        //Close log in
    }


    class loginAccess extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Signin.this);
            pDialog.setMessage("Sign in...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String number,pwd,h,id;
            number = arg0[0];
            pwd = arg0[1];
            h = arg0[2];
            id = arg0[3];


            params.add(new BasicNameValuePair("mobile_number", number));
            params.add(new BasicNameValuePair("password", pwd));
            params.add(new BasicNameValuePair("hint", h));
            params.add(new BasicNameValuePair("email_id", id));

            JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);

            Log.d("Create Response", json.toString());

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    flag=0;
                    Intent i = new Intent(getApplicationContext(),Welcome.class);
                    i.putExtra("mobile_number",number);
                    i.putExtra("password",pwd);
                    startActivity(i);
                    finish();
                }
                else
                {
                    // failed to Sign in
                    flag=1;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if(flag==1)
                Toast.makeText(Signin.this,"Please Enter Correct information", Toast.LENGTH_LONG).show();

        }

    }

}


