package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<earthquakes> {

    private Context context;
    private List<earthquakes> objects = new ArrayList<>();

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

    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, List<earthquakes> list) {
        super(context, resource);
        this.objects = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //finding obj at a particular position
        earthquakes obj = objects.get(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowview = inflater.inflate(R.layout.mylist, parent, false);
        TextView tv_mag = (TextView) rowview.findViewById(R.id.tv_mag);
        TextView tv_place = (TextView) rowview.findViewById(R.id.tv_place);
        TextView tv_date = (TextView) rowview.findViewById(R.id.tv_date);
        TextView tv_time = (TextView) rowview.findViewById(R.id.tv_time);
        TextView tv_loc = (TextView) rowview.findViewById(R.id.tv_loc);

        //Calling function format_magnitude to format the decimal number
        tv_mag.setText(format_magnitude(obj.getMagnitude()));

        //Calling format_place to split the place string into two parts
        String[] location = format_place(obj.getPlace());
        tv_place.setText(location[0]);
        tv_loc.setText(location[1]);
        long date = obj.getDate();

        //Calling format_date & format_time to convert time in milliseconds to date & time
        String date_format = format_date(date);
        String time_format = format_time(date);
        tv_date.setText(date_format);
        tv_time.setText(time_format);

        //Using gradient drawable to get background for the magnitude textView
        GradientDrawable magnitude_circle = (GradientDrawable) tv_mag.getBackground();

        //Calling set_color to set color based on magnitude of earthquake
        int magnitude_color = set_color(obj.getMagnitude());
        magnitude_circle.setColor(magnitude_color);
        return rowview;
    }

    //Method to return date in readable format
    private String format_date(long time) {
        Date date_object = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy");
        return dateFormat.format(date_object);
    }

    //Method to return time in readable format
    private String format_time(long time) {
        Date time_object = new Date(time);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(time_object);
    }

    //Method to split the string
    private String[] format_place(String place) {
        String[] location;
        boolean check = place.contains("of");
        if (check) {
            location = place.split("of ");
            location[0] = location[0] + "of";
            return location;
        } else {
            return new String[]{"Near the", place};
        }
    }

    //Method to display magnitude upto 1 decimal
    private String format_magnitude(double mag) {
        DecimalFormat formatter = new DecimalFormat("0.0");
        return formatter.format(mag);
    }

    //Method to set color to circle background depending on intensity of earthquake
    private int set_color(double mag) {
        int color, color_id = 0;
        switch ((int) mag) {
            case 0:
            case 1:
                color_id = R.color.magnitude1;
                break;
            case 2:
                color_id = R.color.magnitude2;
                break;
            case 3:
                color_id = R.color.magnitude3;
                break;
            case 4:
                color_id = R.color.magnitude4;
                break;
            case 5:
                color_id = R.color.magnitude5;
                break;
            case 6:
                color_id = R.color.magnitude6;
                break;
            case 7:
                color_id = R.color.magnitude7;
                break;
            case 8:
                color_id = R.color.magnitude8;
                break;
            case 9:
                color_id = R.color.magnitude9;
                break;
            case 10:
                color_id = R.color.magnitude10plus;
                break;
        }
        color = ContextCompat.getColor(getContext(), color_id);
        return color;
    }
}