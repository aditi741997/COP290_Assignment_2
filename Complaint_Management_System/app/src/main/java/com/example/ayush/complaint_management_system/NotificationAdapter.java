package com.example.ayush.complaint_management_system;

/**
 * Created by Nikhil on 25/03/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter{

    /** This class is a custom adapter to populate a ListView with user-defined layout of a list item.
     **/
    ArrayList<String> Titles;
    ArrayList<String> Times;
    Context context;
    ArrayList<Integer> Serial;

    private static LayoutInflater inflater = null;

    public NotificationAdapter(Context c_Activity, ArrayList<String> titlelist, ArrayList<String> timelist)
    {
        /** The constructor takes two arrays as input, one is the list of titles, and the other is the list of times.
         * They populate the second and the third text boxes respectively in the list items. **/
        Titles = titlelist;
        context = c_Activity;
        Times = timelist;
        Serial = new ArrayList<Integer>();
        for (int i = 0; i < titlelist.size(); i ++)
        {
            Serial.add(i, i+1);
        }
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount()
    {
        return Titles.size(); // correct.
    }

    public Object getItem(int position)
    {
        return  position; // or return the tuple (titles[posn],time[posn],serial[posn].
    }

    public long getItemId(int position)
    {
        return position; // correct.
    }

    public class Holder
    {
        /** This class is just to hold all the data corresponding to one list item together. **/
        TextView sno;
        TextView title;
        TextView time;
    }


    public  View getView(final int position, View convertView, ViewGroup parent)
    {
        /** This method actually sets the text of the TextViews in the layout of list item, as defined in the file customlistitem.xml **/
        Holder holder = new Holder();
        View onerow;
        onerow = inflater.inflate(R.layout.customlistitem, parent, false);
        holder.sno = (TextView) onerow.findViewById(R.id.SerialNo);
        holder.title = (TextView) onerow.findViewById(R.id.ATitle);
        holder.time = (TextView) onerow.findViewById(R.id.Time);
        holder.sno.setText(String.valueOf(Serial.get(position)));
        holder.time.setText(Times.get(position));
        holder.title.setText(Titles.get(position));
//        onclick listener to be set in fragment code.
        return  onerow;

    }

}
