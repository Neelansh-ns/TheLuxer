package com.e.theluxur;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.e.theluxur.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    List<menuone> listOne;
    RecyclerView recList;
    ProgressDialog pDialog;
    int flag=0;
    JSONParser jsonParser = new JSONParser();
    private static String url = "http://"+IPAddress.IPADDRESS+"/Web/getAllCategories.php";
    private static final String TAG_SUCCESS = "success";
    JSONArray dataa = null;

    Button login,signin;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

       Toolbar toolbar = findViewById(R.id.toolbar);
           setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);





        listOne=new ArrayList<>();
       /* listOne.add(new menuone("Tshirt",R.mipmap.fore_100));
        listOne.add(new menuone("Shirts",R.mipmap.fore_102));
        listOne.add(new menuone("Jeans", R.mipmap.fore_101));
        listOne.add(new menuone("Footwear",R.mipmap.fore_103));
        listOne.add(new menuone("Watches",R.mipmap.men1000));
        listOne.add(new menuone("Belts",R.mipmap.men1001));
        listOne.add(new menuone("Sunglasses",R.mipmap.men1002));
        listOne.add(new menuone("Sportswear",R.mipmap.men1003));*/
       new loginAccess().execute();


        recList = (RecyclerView) findViewById(R.id.rv);
        recList.setHasFixedSize(true);



        GridLayoutManager llm=new GridLayoutManager(this,4);
        llm.setOrientation(RecyclerView.VERTICAL);
        recList.setLayoutManager(llm);







        //Go To Signin.java

        signin=(Button)header.findViewById(R.id.b2);
        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent A = new Intent(getApplicationContext(),Signin.class);
                startActivity(A);

            }
        });
        // Close Signin.java

        //Get all data and log in
        login=(Button)header.findViewById(R.id.b1);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                Intent A = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(A);
                //from login.java

            }



            //Close code that check online details
        });
        //Close log in
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            // Handle the camera action
        } else if (id == R.id.nav_cat) {


        } else if (id == R.id.nav_acc) {


        } else if (id == R.id.nav_fav) {
            Intent A = new Intent(getApplicationContext(),show.class);
            startActivity(A);


        } else if (id == R.id.nav_order) {


        } else if (id == R.id.nav_con) {


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    class loginAccess extends AsyncTask<String, String, String> {

        JSONObject json;

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SecondActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            try {
                json = jsonParser.makeHttpRequest(url, "POST", params);
                Log.d("Create Response", String.valueOf(json));
                if (json == null)
                    SecondActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Oops, API response null", Toast.LENGTH_SHORT).show();
                        }
                    });
                else {
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        flag = 0;
                        dataa = json.getJSONArray("data");
                        int l = dataa.length();
                        for (int i = 0; i < l; i++) {
                            JSONObject c = dataa.getJSONObject(i);

                            //   String category_id = c.getString("category_id");
                            String category_name = c.getString("category_name");
                            String category_id = c.getString("category_id");
                            String category_image = "http://" + IPAddress.IPADDRESS + "/" + c.getString("category_image");
                            listOne.add(new menuone(category_name, category_image));
                        }
                    } else {
                        // failed to login
                        flag = 1;
                    }
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if(flag==1)
                Toast.makeText(SecondActivity.this,"Please Enter Correct information", Toast.LENGTH_LONG).show();
            else {
                Menu_one ca = new Menu_one(getApplicationContext(), listOne);
                recList.setAdapter(ca);
            }


        }

    }

}
