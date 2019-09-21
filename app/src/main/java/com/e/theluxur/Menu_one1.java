package com.e.theluxur;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.theluxur.Menu_one;
import com.e.theluxur.R;
import com.e.theluxur.menuone1;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Menu_one1 extends RecyclerView.Adapter<Menu_one1.MenuoneViewHolder>
{
    private Context mcontext;
    private List<menuone1> mdata;


    public Menu_one1(Context mcontext, List<menuone1> mdata)
    {
        this.mcontext = mcontext;
        this.mdata = mdata;

    }

    @NonNull
    @Override
    public Menu_one1.MenuoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.list_two, parent, false);
        return new Menu_one1.MenuoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuoneViewHolder holder, final int position)
    {
        holder.tv_title.setText(mdata.get(position).getTitle());
        holder.tv_price.setText(mdata.get(position).getPrice());

        URL newurl = null;
        Bitmap mIcon_val;
        try {
            newurl = new URL(mdata.get(position).getImage());
            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
            holder.img_icon.setImageBitmap(mIcon_val);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent B = new Intent(mcontext,List3.class);
                B.putExtra("Name",mdata.get(position).getTitle());
                B.putExtra("Price",mdata.get(position).getPrice());
                B.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(B);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mdata.size();
    }

    public static class MenuoneViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_title,tv_price;
        ImageView img_icon;
        LinearLayout ll1;

        public MenuoneViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv7);
            img_icon = (ImageView) view.findViewById(R.id.listtwo_imgIcon);
            ll1 = (LinearLayout)view.findViewById(R.id.ll1);
            tv_price = (TextView)view.findViewById(R.id.listtwo_text);

        }
    }
}


