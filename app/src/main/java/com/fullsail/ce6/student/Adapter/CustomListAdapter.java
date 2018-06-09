package com.fullsail.ce6.student.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fullsail.ce6.student.Model.Book;
import com.fullsail.ce6.student.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    private Context context; //context
    private ArrayList<Book> items; //data source of the list adapter

    //public constructor 
    public CustomListAdapter(Context context, ArrayList<Book> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }
	
        // get current item to be displayed
        Book currentItem = (Book) getItem(position);
  
        // get the TextView for item name and item description
        TextView textViewItemName = (TextView) convertView.findViewById(R.id.textView1);
        TextView textViewItemDescription = (TextView) convertView.findViewById(R.id.textView2);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        //sets the text for item name and item description from the current item object
        textViewItemName.setText(currentItem.getTitle());
        textViewItemDescription.setText(currentItem.getDescription());
        if (currentItem.getThumbnail()!= null && !currentItem.getThumbnail().isEmpty()) {
            Picasso.with(context).load(currentItem.getThumbnail()).resize(150, 150).onlyScaleDown().into(imageView);
        }

        // returns the view for the current row
        return convertView;
    }
}