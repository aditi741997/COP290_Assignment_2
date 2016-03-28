package aditi.ayush.nikhil.complaintmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jagdish on 3/28/2016.
 */
public class CustomListViewAdapter extends BaseAdapter
{
    ArrayList<String> Texts;
    ArrayList<String> Times;
    Context context;
    ArrayList<String> Users;

    private static LayoutInflater inflater = null;

    public CustomListViewAdapter(Context c_Activity, ArrayList<String> textlist, ArrayList<String> timelist, ArrayList<String> userlist)
    {
        /** The constructor takes three arrays as input, one is the list of titles, second is the list of times, and the third is the list of users.
         * They populate the first, second and the third text boxes respectively in the list items. **/
        Texts = textlist;
        Times = timelist;
        Users = userlist;
        context = c_Activity;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount()
    {
        return Texts.size(); // correct.
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
        TextView user;
        TextView text;
        TextView time;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Holder holder = new Holder();
        View onerow;
        onerow = inflater.inflate(R.layout.comment_item, parent, false);
        holder.user = (TextView) onerow.findViewById(R.id.User_comm);
        holder.text = (TextView) onerow.findViewById(R.id.Comment);
        holder.time = (TextView) onerow.findViewById(R.id.CTime);
        holder.text.setText(Texts.get(position));
        holder.time.setText(Times.get(position));
        holder.user.setText(Users.get(position));
        onerow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "You clicked row " + position, Toast.LENGTH_LONG).show();
            }

        });
        return  onerow;

    }

}
