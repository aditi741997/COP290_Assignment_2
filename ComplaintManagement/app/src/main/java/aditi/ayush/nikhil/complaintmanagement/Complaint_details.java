package aditi.ayush.nikhil.complaintmanagement;

import android.app.Dialog;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.ArrayList;

public class Complaint_details extends AppCompatActivity {
    String c_id;
    String admin_email;
    String admin_no;
    MyApp_cookie cook = new MyApp_cookie();
    boolean up = false;
    boolean down = false;
    String prev_compID;

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

        updateResponse();
//TODO: check box for satisfaction. visibility depends on resolved or not.
    }

    public void updateResponse()
    {
//        get response, change colors, up, down accordingly.
        String getResp = getResources().getString(R.string.IP) + "/complaint/get_user_response.json?complain_id=" + c_id;
        StringRequest compData = new StringRequest (Request.Method.GET, getResp,
                new Response.Listener<String>()
                {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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
//                            save admin no, email (call another api)
                            JSONObject json_data = new JSONObject(response);
                            System.out.println(json_data);

                            int resp = json_data.getInt("resp");

//                                TODO: Color of vote be blue
                            if (resp == 1)
                            {
                                up =true;
                                down = false;
                                ImageButton downB = (ImageButton) findViewById(R.id.imageButton);
                                if (downB != null)
                                    downB.setBackground(getResources().getDrawable(R.drawable.bluedislike));
                            }
                            else if (resp == -1)
                            {
                                down = true;
                                up = false;
                                ImageButton upB = (ImageButton) findViewById(R.id.imageButton2);
                                if (upB != null)
                                    upB.setBackground(getResources().getDrawable(R.drawable.bluelike));
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

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    public  void populateData()
    {
//        c_id = "i_7";
        final TextView up = (TextView) findViewById(R.id.upVote);
        final ImageButton upV = (ImageButton) findViewById(R.id.imageButton2);
        final TextView down = (TextView) findViewById(R.id.downVote);
        final ImageButton downV = (ImageButton) findViewById(R.id.imageButton);
        TableRow hostel = (TableRow) findViewById(R.id.hostelRow);

        if (cook.isAdmin)
        {
            Button x = (Button) findViewById(R.id.addcomment);
            x.setText("Add Admin comment");
//            TODO: checkbox, complainant details section.
            CheckBox cb = (CheckBox) findViewById(R.id.check_comp);
            cb.setText("Mark as resolved");
        }

        if (c_id.substring(0,2).equals("i_"))
        {
            up.setVisibility(View.INVISIBLE);
            upV.setVisibility(View.INVISIBLE);

            down.setVisibility(View.INVISIBLE);
            downV.setVisibility(View.INVISIBLE);



            hostel.setVisibility(View.INVISIBLE);
        }
        else
        {
            Button higher = (Button) findViewById(R.id.takeHigher);
            higher.setVisibility(View.INVISIBLE);
        }
//TODO: visibility of call/mail admin depends upon if user is admin or no.
        String comp_details = getResources().getString(R.string.IP) + "/complaint_data/get_complaint_details.json?complaint_id=" + c_id;

//  area, level, hostel id

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
//                            save admin no, email (call another api)
                            JSONObject json_data = new JSONObject(response);
                            System.out.println(json_data);

                            admin_no = json_data.getString("adminNo");

                            JSONObject details = json_data.getJSONObject("Details");
                            admin_email = details.getString("admin_id") + "@iitd.ac.in";


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
                            {
                                resol.setText("No");
                                CheckBox cb = (CheckBox) findViewById(R.id.check_comp);
                                cb.setVisibility(View.INVISIBLE);
//                                checkbox is shown only if complaint has been marked as resolved.
                            }

                            String adminID = details.getString("admin_id");
                            TextView admin = (TextView) findViewById(R.id.adminID);
                            admin.setText(adminID);

                            String content = details.getString("complaint_content");
                            TextView cont = (TextView) findViewById(R.id.content);
                            cont.setText(content);

                            String extra = details.getString("extra_info");
                            TextView det = (TextView) findViewById(R.id.det_extra);
                            det.setText(extra);

                            String admin_co = details.getString("comment_comp");
                            TextView admincom = (TextView) findViewById(R.id.admin_com);
                            admincom.setText(admin_co);

                            prev_compID = details.getString("prev_id");

                            if (!(c_id.substring(0,2).equals("i_")))
                            {
//                                populate hostel, up,down,neut. TODO
                                int upV = details.getInt("num_up");
                                int downV = details.getInt("num_down");
//                                int neutV = details.getInt("num_neutr");
                                System.out.println("DHINCHAK " + upV + ", down:" + downV);
                                up.setText("Upvotes : " + upV);
                                down.setText("Downvotes : " + downV);

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
        final Helpers hh = new Helpers();

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
                                comment_text.add(hh.ScoreToSpace(comment.getString("description")));
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

    public void dialogbox(View view){
        Dialog dialog = new Dialog(Complaint_details.this);
        dialog.setTitle("Contact Admin");
        dialog.setContentView(R.layout.admin_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public  void addComment(View view)
    {
//        call api
        final EditText comm = (EditText) findViewById(R.id.comment_add);
        String comment = comm.getText().toString();
        Helpers h = new Helpers();
        if (cook.isAdmin)
        {
            if (comment != "" || comment != " ") {
                String addcomm = getResources().getString(R.string.IP) + "/admin_complaint/add_comment.json?complaint_id=" + c_id + "&desc=" + h.SpaceToScore(comment);

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
                            if (success) {
                                Toast.makeText(getApplicationContext(), "Admin Comment posted successfully!", Toast.LENGTH_SHORT).show();
                            }

                            comm.setText("");
                        } catch (JSONException e) {
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
//                populateComments();
                populateData();
            }
        }
        else
        {
            if (comment != "" || comment != " ")
            {
                String addcomm = getResources().getString(R.string.IP) + "/complaint/post_comments.json?complain_id=" + c_id + "&comment=" + h.SpaceToScore(comment);

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
    }

    public void checkClick(View view)
    {
//        admin -> mark for resolved
//        user -> satisfied.
        CheckBox cb = (CheckBox) findViewById(R.id.check_comp);
        if (cook.isAdmin)
        {
//            mark for resolution hai.
            final boolean bool_resp = cb.isChecked();
            String admin_res = getResources().getString(R.string.IP) + "/admin_complaint/mark_resolved.json?complaint_id=" + c_id + "&boolean_resolve=" + bool_resp;

            JsonObjectRequest jsoncomm = new JsonObjectRequest(Request.Method.GET,
                    admin_res, null, new Response.Listener<JSONObject>() {

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
                            if (bool_resp)
                                Toast.makeText(getApplicationContext(),"Complaint marked as resolved successfully!",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(),"Complaint marked as unresolved successfully!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Complaint couldnt be marked.",Toast.LENGTH_SHORT).show();
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
        else
        {
//            mark for satisfaction.
            int resp = 0;
            if (cb.isChecked())
                resp = 1;
            else
                resp = 0;
            String user_sat = getResources().getString(R.string.IP) + "/complaint/put_user_status.json?complaint_id=" + c_id + "&response=" + resp;
//            TODO: request.
        }
    }


    public  void prevThread(View view)
    {
        if (prev_compID == "")
        {
            Toast.makeText(getApplicationContext(),"No previous complaint!", Toast.LENGTH_SHORT).show();
        }
        else

        {
            Intent prev = new Intent(getApplicationContext(),Complaint_details.class);
            prev.putExtra("ID",prev_compID);
            startActivity(prev);
        }
    }


    public  void takeHigher(View view)
    {
        if (c_id.substring(0,2).equals("i_"))
        {
            System.out.println("Taking to higher. DHINCHAK");
            String higher_user = getResources().getString(R.string.IP) + "/complaint/high_auth.json?complaint_id=" + c_id;
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
                            Toast.makeText(getApplicationContext(),response.getString("description"),Toast.LENGTH_SHORT).show();
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


    }

    public  void call_admin(View view)
    {
//TODO
        if (admin_no != "")
        {
            Intent callIntent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + admin_no));
            startActivity(callIntent);
        }
    }

    public  void mail_admin(View view)
    {
//   TODO
        if (admin_email != "")
        {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, admin_email);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Mail regarding Complaint ID: " + c_id);
            startActivity(emailIntent);

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//                finish();
                Log.i("Finished sending email", "");
            }
            catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void upVote(View view)
    {
        if (!up)
        {
            up = true;
            down = false;
//            TODO: Make downVote bttn blue.
//            API
            String upVote = getResources().getString(R.string.IP) + "/complaint/set_user_response.json?complain_id=" + c_id + "&response=" + "1";
            JsonObjectRequest jsoncomm = new JsonObjectRequest(Request.Method.GET,
                    upVote, null, new Response.Listener<JSONObject>() {

                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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
                            ImageButton downB = (ImageButton) findViewById(R.id.imageButton);
                            if (downB != null)
                                downB.setBackground(getResources().getDrawable(R.drawable.bluedislike));

                            ImageButton upB = (ImageButton) findViewById(R.id.imageButton2);
                            if (upB != null)
                                upB.setBackground(getResources().getDrawable(R.drawable.greenlike));                            Toast.makeText(getApplicationContext(),"You upvoted this complaint!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Upvote unsuccessful",Toast.LENGTH_SHORT).show();
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
        else
        {
            Toast.makeText(this, "You have already upvoted this complaint!", Toast.LENGTH_SHORT).show();
        }
    }

    public  void downVote(View view)
    {
        if (!down)
        {
            down = true;
            up = false;
            //            TODO: Make upVote bttn blue.
//            API
            String downvote = getResources().getString(R.string.IP) + "/complaint/set_user_response.json?complain_id=" + c_id + "&response=" + "-1";
            JsonObjectRequest jsoncomm = new JsonObjectRequest(Request.Method.GET,
                    downvote, null, new Response.Listener<JSONObject>() {

                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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
                            ImageButton upB = (ImageButton) findViewById(R.id.imageButton2);
                            if (upB != null)
                                upB.setBackground(getResources().getDrawable(R.drawable.bluelike));

                            ImageButton downB = (ImageButton) findViewById(R.id.imageButton);
                            if (downB != null)
                                downB.setBackground(getResources().getDrawable(R.drawable.reddislike));
                            Toast.makeText(getApplicationContext(),"You downvoted this complaint!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Downvote unsuccessful",Toast.LENGTH_SHORT).show();
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
        else
        {
            Toast.makeText(this, "You have already downvoted this complaint!", Toast.LENGTH_SHORT).show();
        }
    }









}
