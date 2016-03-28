package aditi.ayush.nikhil.complaintmanagement;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class Complaint_details extends AppCompatActivity {
    String c_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details);

        Bundle data=getIntent().getExtras();
        if(data == null)
        {
            return;
        }
//        need thread id to get details.
        c_id = data.getString("ID");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateData();

        populateComments();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public  void populateData()
    {
//        c_id = "i_7";
        final TextView up = (TextView) findViewById(R.id.upVote);
        final TextView down = (TextView) findViewById(R.id.downVote);
        final TextView neut = (TextView) findViewById(R.id.neutVote);
        TableRow hostel = (TableRow) findViewById(R.id.hostelRow);

        if (c_id.substring(0,2).equals("i_"))
        {
            up.setVisibility(View.INVISIBLE);

            down.setVisibility(View.INVISIBLE);

            neut.setVisibility(View.INVISIBLE);

            hostel.setVisibility(View.INVISIBLE);
        }

        String comp_details = getResources().getString(R.string.IP) + "/complaint_data/get_complaint_details.json?complaint_id=" + c_id;

        StringRequest compData = new StringRequest (Request.Method.GET, comp_details,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.i("yo", "why this ... working" + response);
///                        System.out.println(response.toString());
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                "Logout ",
//                                Toast.LENGTH_LONG).show();
                        try
                        {
                            JSONObject json_data = new JSONObject(response);
                            System.out.println(json_data);

                            JSONObject details = json_data.getJSONObject("Details");

                            String comptype = json_data.getString("category");
                            TextView comp_Type = (TextView) findViewById(R.id.comp_type);
                            comp_Type.setText(comptype);

                            String time = json_data.getString("timest");
                            TextView time_elap = (TextView) findViewById(R.id.time_elap);
                            time_elap.setText(time);

                            int resolved = details.getInt("resolved");
                            TextView resol = (TextView) findViewById(R.id.resolved);
                            if (resolved == 1)
                            {
                                resol.setText("Yes");
                            }
                            else
                                resol.setText("No");

                            String adminID = details.getString("admin_id");
                            TextView admin = (TextView) findViewById(R.id.adminID);
                            admin.setText(adminID);

                            String content = details.getString("complaint_content");
                            TextView cont = (TextView) findViewById(R.id.content);
                            cont.setText(content);

                            String extra = details.getString("");
                            TextView det = (TextView) findViewById(R.id.det_extra);
                            det.setText(extra);

                            String admin_co = details.getString("comment_comp");
                            TextView admincom = (TextView) findViewById(R.id.admin_com);
                            admincom.setText(admin_co);

                            if (!(c_id.substring(0,2).equals("i_")))
                            {
//                                populate hostel, up,down,neut. TODO
                                int upV = details.getInt("num_up");
                                int downV = details.getInt("num_down");
                                int neutV = details.getInt("num_neutr");

                                up.setText("Upvotes : " + upV);
                                down.setText("Downvotes : " + downV);
                                neut.setText("Neutral : " + neutV);
//                                hostel : get string from server directly.
                                String hostelname = json_data.getString("hostelname");
                                TextView h_name = (TextView) findViewById(R.id.Hostel);
                                h_name.setText(hostelname);

                            }


                        } catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("yo", "why this not working");
                        //  Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(getApplicationContext()).add(compData);
    }

    public  void populateComments()
    {
        String comments = getResources().getString(R.string.IP) + "/complaint/get_comments.json?complain_id=" + c_id;

        JsonObjectRequest comment_json = new JsonObjectRequest (Request.Method.GET, comments,null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("yo", "why this ... working");
//                     System.out.println(response.toString());

                        try
                        {
                            JSONArray comments = response.getJSONArray("Comments");

                            ArrayList<String> comment_text = new ArrayList<>();
                            ArrayList<String> comm_user = new ArrayList<>();
                            ArrayList<String> comm_time = new ArrayList<>();
//                            System.out.println(x);


                            for(int i=0;i<comments.length();i++)
                            {    JSONObject comment= comments.getJSONObject(i);
                                comment_text.add(comment.getString("description"));
                                comm_time.add(comment.getString("time_stamp"));
                                int anony = comment.getInt("anonymous");
                                if (anony == 1)
                                    comm_user.add("Anonymous");
                                else
                                    comm_user.add(comment.getString("user_id"));


                            }
                            ListView comment_List = (ListView) findViewById(R.id.commentList);
                            Context c = getApplicationContext();
                            CustomListViewAdapter adap = new CustomListViewAdapter(c ,comment_text, comm_time, comm_user);
                            comment_List.setAdapter(adap);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("thread", "thread details request error");
                    }
                });

        Volley.newRequestQueue(this).add(comment_json);

    }

    public  void addComment(View view)
    {
//        call api
        final EditText comm = (EditText) findViewById(R.id.comment_add);
        String comment = comm.getText().toString();
        if (comment != "" || comment != " ")
        {
            String addcomm = getResources().getString(R.string.IP) + "/complaint/post_comments.json?complain_id=" + c_id + "&comment=" + comment;

            JsonObjectRequest jsoncomm = new JsonObjectRequest(Request.Method.GET,
                    addcomm, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //Log.d(TAG, response.toString());

                    try {
                        // Parsing json object response
                        // response will be a json object
                        boolean success = response.getBoolean("Success");
                        //  String email = response.getString("email");
                        if(success)
                        {
                            Toast.makeText(getApplicationContext(),"Comment posted successfully!",Toast.LENGTH_SHORT).show();
                        }

                        comm.setText("");
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Tag", "Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            Volley.newRequestQueue(getApplicationContext()).add(jsoncomm);
            populateComments();
        }
    }

    public  void takeHigher(View view)
    {
        String higher_user = getResources().getString(R.string.IP);
        JsonObjectRequest jsoncomm = new JsonObjectRequest(Request.Method.GET,
                higher_user, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    boolean success = response.getBoolean("Success");
                    //  String email = response.getString("email");
                    if(success)
                    {
                        Toast.makeText(getApplicationContext(),"Complaint taken to higher authority successfully!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Complaint couldnt be forwarded.",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Tag", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(jsoncomm);


    }

    public  void call_admin(View view)
    {
//TODO
    }

    public  void mail_admin(View view)
    {
//   TODO
    }







}