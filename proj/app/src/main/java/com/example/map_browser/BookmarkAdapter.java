package com.example.map_browser;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookmarkAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private ArrayList<Bookmark> items;

    public BookmarkAdapter(Context context, ArrayList<Bookmark> bookmarks){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items=bookmarks;
    }

    @Override
    public int getCount(){return items.size();}

    @Override
    public Bookmark getItem(int i){return items.get(i);}

    @Override
    public long getItemId(int i){return i;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        if(view==null){
            view=inflater.inflate(R.layout.item_layout,viewGroup,false);
        }

        Bookmark item=items.get(i);

        TextView tv1=view.findViewById(R.id.item_title);
        TextView tv2=view.findViewById(R.id.item_url);

        tv1.setText(item.getName());
        tv2.setText(item.getUrl());


        return view;
    }
}
