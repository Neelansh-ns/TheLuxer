package com.e.theluxur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class List2 extends AppCompatActivity {
    List<menuone1> listtwo;
    RecyclerView recList;
    ProgressDialog pDialog;
    int flag=0;
    JSONParser jsonParser = new JSONParser();
    private static String url = "http://" + IPAddress.IPADDRESS+"/Web/getAllProducts.php";
    private static final String TAG_SUCCESS = "success";
    JSONArray dataa = null;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);

        Bundle b = getIntent().getExtras();
       String category = b.getString("Category");
        Log.d("Category",category);

        listtwo = new ArrayList<>();

        recList = (RecyclerView)findViewById(R.id.rv);
        recList.setHasFixedSize(true);

        GridLayoutManager llm=new GridLayoutManager(this,2);
        llm.setOrientation(RecyclerView.VERTICAL);
        recList.setLayoutManager(llm);

        new loginAccess().execute(category);

    /*  if(category .equals("Tshirt"))
       {
           listtwo=new ArrayList<>();
           listtwo.add(new menuone1("Price:500Rs",R.mipmap.fore_100));

           RecyclerView recList1 = (RecyclerView) findViewById(R.id.rv);
           recList1.setHasFixedSize(true);

           Menu_one1 ca1 = new Menu_one1(this,listtwo);

           GridLayoutManager llm1=new GridLayoutManager(this,2);
           llm1.setOrientation(RecyclerView.VERTICAL);
           recList1.setLayoutManager(llm1);
           recList1.setAdapter(ca1);
       }


       // listtwo=new ArrayList<>();
        //listtwo.add(new menuone1("Price:500Rs",R.mipmap.fore_100));
        //listtwo.add(new menuone1("Price:1000Rs",R.mipmap.fore_102));
*/

    }

    class loginAccess extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(List2.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String category = arg0[0];
            params.add(new BasicNameValuePair("category", category));
            JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
            Log.d("Create Response", json.toString());

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    flag=0;
                    dataa = json.getJSONArray("data");
                    int l = dataa.length();
                    for (int i = 0; i < l; i++) {
                        JSONObject c = dataa.getJSONObject(i);

                        //   String category_id = c.getString("category_id");
                        String product_name = c.getString("product_name");
                        String product_price = c.getString("product_price");
                        String product_image = "http://"+ IPAddress.IPADDRESS+"/"+c.getString("product_image");
                        listtwo.add(new menuone1(product_name,product_image,product_price));
                    }
                }
                else
                {
                    // failed to login
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
                Toast.makeText(List2.this,"No Products Available", Toast.LENGTH_LONG).show();
            else {
                Menu_one1 ca = new Menu_one1(getApplicationContext(), listtwo);
                recList.setAdapter(ca);
            }


        }

    }

}
