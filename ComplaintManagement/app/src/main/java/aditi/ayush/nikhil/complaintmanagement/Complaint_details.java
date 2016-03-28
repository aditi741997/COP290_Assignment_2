package aditi.ayush.nikhil.complaintmanagement;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

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
        c_id = "i_7";

        if (c_id.substring(0,2).equals("i_"))
        {
            TextView up = (TextView) findViewById(R.id.upVote);
            up.setVisibility(View.INVISIBLE);

            TextView down = (TextView) findViewById(R.id.downVote);
            down.setVisibility(View.INVISIBLE);

            TextView neut = (TextView) findViewById(R.id.neutVote);
            neut.setVisibility(View.INVISIBLE);

            TableRow hostel = (TableRow) findViewById(R.id.hostelRow);
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


}
