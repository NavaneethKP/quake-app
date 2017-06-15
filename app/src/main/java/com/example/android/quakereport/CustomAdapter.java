package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kpn on 12/6/17.
 */

public class CustomAdapter extends ArrayAdapter<earthquakes> {

    private Context context;
    private ArrayList<earthquakes> objects=new ArrayList<>();

    @Override
    public int getCount() {
        return objects.size();
    }

    @Nullable
    @Override
    public earthquakes getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<earthquakes> list) {
        super(context, resource);
        this.objects = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =LayoutInflater.from(getContext());
        View rowview =inflater.inflate(R.layout.mylist,parent,false);
        TextView tv_mag = (TextView) rowview.findViewById(R.id.tv_mag);
        TextView tv_place = (TextView) rowview.findViewById(R.id.tv_place);
        TextView tv_date = (TextView) rowview.findViewById(R.id.tv_date);
        tv_mag.setText(Double.toString(objects.get(position).getMagnitude()));
        tv_place.setText(objects.get(position).getPlace());
        tv_date.setText(Long.toString(objects.get(position).getDate()));
        return rowview;
    }
}
