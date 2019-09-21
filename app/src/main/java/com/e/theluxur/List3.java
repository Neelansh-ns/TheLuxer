package com.e.theluxur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class List3 extends AppCompatActivity {

    TextView p,title;
    Button b1,b2;
    MySQLiteOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list3);

        Bundle b = getIntent().getExtras();
        final String name  = b.getString("Name");
        final String price = b.getString("Price");

        p = (TextView)findViewById(R.id.price);
        title  = (TextView)findViewById(R.id.title);
        p.setText(price);
        title.setText(name);

        db = new MySQLiteOpenHelper(getApplicationContext());

        b1 = (Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long y = db.insert(name,price);
                if(y>0)
                {
                    Toast.makeText(getApplicationContext(),"Added to cart",Toast.LENGTH_SHORT).show();
                    Intent A= new Intent(getApplicationContext(),SecondActivity.class);
                    startActivity(A);

                }else{
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

   /*     b2 = (Button)findViewById(R.id.nav_fav);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == b2.getId())
                {
                    Intent A = new Intent(getApplicationContext(),show.class);
                    startActivity(A);
                }
            }
        });*/







    }
}
