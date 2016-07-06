package com.creation.creative.creativecreation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by preet on 26-03-2016.
 */
public class MyAdapter extends BaseAdapter{

    Context context;
    ArrayList<Item> itemList = new ArrayList<Item>();
    private static LayoutInflater inflater = null;

    public MyAdapter(Context context, ArrayList<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.grid_items, null);
        }
       ImageView grid_image = (ImageView)convertView.findViewById(R.id.grid_image);
        TextView grid_name= (TextView)convertView.findViewById(R.id.grid_name);


        Item e = new Item();
        e = itemList.get(position);
        //.setText(e.getId());

        grid_name.setText(e.getName());
        grid_image.setImageBitmap(e.getImage());


        return convertView;

    }
}
