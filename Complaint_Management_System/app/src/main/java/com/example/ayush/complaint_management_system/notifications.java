package com.example.ayush.complaint_management_system;

/**
 * Created by Ayush on 23-03-2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.text.Html;
import android.text.Spanned;

import java.util.ArrayList;




public class notifications extends Fragment{

    public TextView msg ;


    public notifications()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /** This function receives the bundle containing arrays of title and time of notifications, and passes them to the CustomAdapter_Thread object,which populates the ListView.**/
        View ret_view = inflater.inflate(R.layout.notifications, container, false);
        msg=(TextView) ret_view.findViewById(R.id.three);
        Bundle bundle = this.getArguments();

        //TextView contentView = (TextView)view.findViewById(R.id.contentPreview);
//        String htmlEncodedString = Html.toHtml(contentText);
//        String decodedString = StringEscapeUtils.unescapeHtml4(htmlEncodedString);
        // myTextView.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p>"));
        TextView notific=(TextView) ret_view.findViewById(R.id.three);
        ArrayList<String > noti_user =bundle.getStringArrayList("noti_user");
        ArrayList<String > noti_text =bundle.getStringArrayList("noti_text");

        System.out.println(noti_text.size()+"fdafsdfasdfasfadsadsfasfasdfaf");
        ArrayList<String> noti_time = bundle.getStringArrayList("noti_time");
        ArrayList<String> noti_html=new ArrayList<String>();
        for(int i=0;i<noti_text.size();i++)
        {  String str=noti_text.get(i);
            Spanned spannedText=Html.fromHtml(str);
            notific.setText(spannedText);
            String htmlString = notific.getText().toString();
            // htmlString=Html.fromHtml(Html.fromHtml(str).toString());
            noti_html.add(htmlString);
        }
        if(noti_text.isEmpty())
        {
            msg.setText("No New Notifications");
            TextView Notes = (TextView) ret_view.findViewById(R.id.No_Notification);
            Notes.setVisibility(View.VISIBLE);
            ListView Lister = (ListView) ret_view.findViewById(R.id.notif_list);
            Lister.setVisibility(View.INVISIBLE);
        }
//        list view needed.
        else
        {

            ListView notif_list = (ListView) ret_view.findViewById(R.id.notif_list);
            NotificationAdapter adap = new NotificationAdapter(getActivity().getApplicationContext(), noti_html, noti_time);
            notif_list.setAdapter(adap);
        }
        return ret_view;


    }

}