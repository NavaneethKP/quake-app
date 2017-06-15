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

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        TextView tv_time = (TextView) rowview.findViewById(R.id.tv_time);
        tv_mag.setText(String.valueOf(objects.get(position).getMagnitude()));
        tv_place.setText(objects.get(position).getPlace());
        long date = objects.get(position).getDate();
        String date_format = format_date(date);
        String time_format = format_time(date);
        tv_date.setText(date_format);
        tv_time.setText(time_format);
        return rowview;
    }

    private String format_date(long time)
    {
        Date date_object = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD,yyyy");
        return dateFormat.format(date_object);
    }

    private String format_time(long time)
    {
        Date time_object = new Date(time);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(time_object);
    }


}
