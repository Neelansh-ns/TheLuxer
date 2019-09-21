package com.e.theluxur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public  static final String DB_NAME = "luxur";
    public  static final String TB_NAME = "cart";
    public  static final int DB_VERSION =1;
    SQLiteDatabase db;

    public  static final String KEY_ID = "id";
    public  static final String KEY_TITLE = "title";
    public  static final String KEY_PRICE = "price";
    public  static final String KEY_QTY = "qty";

    public MySQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TB_NAME+
                " ( "+
                KEY_ID+" integer primary key autoincrement,"+
                KEY_TITLE+" text ," +
                KEY_PRICE+" text,"+
                KEY_QTY+" text);"
            );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public long insert(String title,String price){
        ContentValues val = new ContentValues();
        String cols[] = {KEY_TITLE,KEY_PRICE,KEY_QTY};
        Cursor c = db.query(TB_NAME,cols,KEY_TITLE+"=?",new String[]{title},null,null,null,null);
        if(c.getCount()>0){
            c.moveToFirst();
            int qty = Integer.parseInt(c.getString(2));
            qty++;
            val.put(KEY_QTY,qty);
            long y = db.update(TB_NAME,val,KEY_TITLE+"=?",new String[]{title});
            return  y;
        }
        else {
            val.put(KEY_TITLE, title);
            val.put(KEY_PRICE, price);
            val.put(KEY_QTY, "1");
            long y = db.insert(TB_NAME, null, val);
            return  y;
        }

    }

    public String showall()
    {
        String cols[] = {KEY_ID,KEY_TITLE,KEY_PRICE,KEY_QTY};
        String z = "ID\t\t\t\t\t\t\t\tName\t\t\t\t\t\t\t\tPrice\t\t\t\t\t\t\tQty\n";
        Cursor c = db.query(TB_NAME,cols,null,null,null,null,null);
        if(c != null)
        {
            for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
            {
                z = z+c.getString(0)+"\t\t\t\t\t\t\t\t";
                z = z+c.getString(1)+"\t\t\t\t\t\t\t\t";
                z = z+c.getString(2)+"\t\t\t\t\t\t\t\t";
                z = z+c.getString(3)+"\n";
            }
            return z;
        }
        else
        {
            return z;
        }
    }
}
