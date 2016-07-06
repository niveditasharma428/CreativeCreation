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
 * Created by preet on 04-04-2016.
 */
public class ItemAdapter extends BaseAdapter {
    Context context;
    ArrayList<Item> itemList = new ArrayList<Item>();
    private static LayoutInflater inflater = null;
    @Override
    public int getCount() {
        return itemList.size();
    }

    public ItemAdapter(Context context, ArrayList<Item> itemList)
    {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.itemList = itemList;
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
            convertView = inflater.inflate(R.layout.item_adapter, null);
        }

            ImageView img = (ImageView)convertView.findViewById(R.id.img_item);
            TextView text_name = (TextView)convertView.findViewById(R.id.tex_name);
            TextView text_price = (TextView)convertView.findViewById(R.id.tex_price);
            TextView text_quan = (TextView)convertView.findViewById(R.id.tex_quan);
            Item i = new Item();
            i = itemList.get(position);
            img.setImageBitmap(i.getImage());
            text_name.setText(i.getName());
            String p = Integer.toString(i.getPrice());
            text_price.setText(p);
            String q = Integer.toString(i.getQuantity());
            text_quan.setText(q);

        return convertView;
    }
}
