package com.e.theluxur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;




public class About extends Activity {
    TextView tv;

    public void onCreate(Bundle ins)
    {
        super.onCreate(ins);
        setContentView(R.layout.about);
        tv=(TextView)findViewById(R.id.a_tv);
        Intent i=getIntent();
        String abt=i.getStringExtra("about");
        System.out.println(abt);
        tv.setText(abt);

    }

}


