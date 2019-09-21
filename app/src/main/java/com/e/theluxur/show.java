package com.e.theluxur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class show extends AppCompatActivity {
    TextView tv;
    MySQLiteOpenHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        tv = (TextView)findViewById(R.id.tv);
        db = new MySQLiteOpenHelper(getApplicationContext());
        String z = ((MySQLiteOpenHelper) db).showall();
        tv.setText(z);
    }
}
