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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Menu_one extends RecyclerView.Adapter<Menu_one.MenuoneViewHolder>
{
    private Context mcontext;
    private List<menuone> mdata;


    public Menu_one(Context mcontext, List<menuone> mdata)
    {
        this.mcontext = mcontext;
        this.mdata = mdata;

    }

    @NonNull
    @Override
    public MenuoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.list_one, parent, false);
        return new MenuoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuoneViewHolder holder, final int position)
    {
        holder.tv_title.setText(mdata.get(position).getTitle());
        //holder.img_icon.setImageResource(mdata.get(position).getImages());

        URL newurl = null;
        Bitmap mIcon_val;
        try {
            newurl = new URL(mdata.get(position).getImages());
            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
            holder.img_icon.setImageBitmap(mIcon_val);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent A = new Intent(mcontext,List2.class);
                A.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                A.putExtra("Category",mdata.get(position).getTitle());
                mcontext.startActivity(A);
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
        TextView tv_title;
        de.hdodenhof.circleimageview.CircleImageView img_icon;
        LinearLayout ll;

        public MenuoneViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.listOne_text);
            img_icon = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.listOne_imgIcon);
            ll = (LinearLayout)view.findViewById(R.id.ll);
        }
    }
}
