package aditi.ayush.nikhil.complaintmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class resolved extends Fragment{

    ExpandableListAdapter listAdapter;
    public static ExpandableListView expListView;
    public static List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String,List<String>> compID;
    MyApp_cookie app_list=new MyApp_cookie();
    private long mRequestStartTime;
     ArrayList<String> res_content=new ArrayList<String>();
     ArrayList<String> res_id=new ArrayList<String>();
     ArrayList<String> res_type=new ArrayList<String>();
    HashMap<Integer,String> ind=new HashMap<Integer, String>();
    HashMap<Integer,String> hostel=new HashMap<Integer, String>();
    HashMap<Integer,String> insti=new HashMap<Integer, String>();


    public resolved()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //@Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
////    }

 private void prepareListData()
    {/** A method to populate the Expandable List view for the Grades Tab"**/
        final int[] no_assign = {0};
        final JSONArray noti = new JSONArray();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        mRequestStartTime = System.currentTimeMillis();
        listDataHeader.add("Indiviudal Complaints");
        listDataHeader.add("Hostel Complaints");
        listDataHeader.add("Institute Complaints");
        List<String> expand1 = new ArrayList<String>();
        List<String> expand2 = new ArrayList<String>();
        List<String> expand3 = new ArrayList<String>();
        for(int i=0;i<res_content.size();i++)
        {if(res_type.get(i)=="1") {
            expand1.add(res_content.get(i));
            ind.put(ind.size(),res_id.get(i));
        }
        else if(res_type.get(i)=="2")
        {
            expand2.add(res_content.get(i));
            hostel.put(hostel.size(),res_id.get(i));
        }
        else
        {
            expand3.add(res_content.get(i));
            insti.put(insti.size(),res_id.get(i));
        }

        }
        listDataChild.put("Individual Complaints",expand1);
        listDataChild.put("Hostel Complaints",expand2);
        listDataChild.put("Institute Complaints",expand3);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/** Inflate the layout for grades tab **/
        View rootView = inflater.inflate(R.layout.resolved, null);

        expListView = (ExpandableListView) rootView.findViewById(R.id.cmpExp);

//        Button addnew = (Button) rootView.findViewById(R.id.addnew);
//
//        addnew.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v)
//            {
//                Intent add_comp = new Intent(getActivity(), NewComplaint.class);
//                getActivity().startActivity(add_comp);
//            }
//
//        });
        Bundle bund=this.getArguments();
        if(bund == null ) return null;
System.out.println(bund);
        res_content=bund.getStringArrayList("content");
        res_id=bund.getStringArrayList("id");
        res_type=bund.getStringArrayList("type");
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        Log.i("groups", listDataHeader.toString());
        Log.i("details", listDataChild.toString());

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition)
            {
                //    Toast.makeText(getActivity().getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition)
            {
                //    Toast.makeText(getActivity().getApplicationContext(), listDataHeader.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                //        Toast.makeText(getActivity().getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
///todo: add the below code again
//                new page -> complaint details.
//                String head = listDataHeader.get(groupPosition);
//                List<String> IDs = compID.get(head);
//                Intent add_comp = new Intent(getActivity(), Complaint_details.class);
//                add_comp.putExtra("ID",IDs.get(childPosition));
//                getActivity().startActivity(add_comp);
                return false;
//                TODO: open complaint details page. Intent+ putExtra = ID.
            }
        });
        return rootView;

    }
}