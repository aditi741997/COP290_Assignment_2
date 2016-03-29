package aditi.ayush.nikhil.complaintmanagement;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewComplaint extends AppCompatActivity {

    MyApp_cookie app_list=new MyApp_cookie();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateSpinner();

    }

    public void populateSpinner()
    {
//        get type - int mapping.
        List<String> type = new ArrayList<String>();
        type.add("Institute");
        type.add("Hostel");
        type.add("Individual");

        ArrayAdapter<String> spin_ada = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,type);
        spin_ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner sp = (Spinner) findViewById(R.id.IHI);
        sp.setAdapter(spin_ada);

        String getmapping = getResources().getString(R.string.IP) + "/complaint/get_desc.json?category_id=-1";
        final long mRequestStartTime = System.currentTimeMillis();

        final List<String> spin_arr = new ArrayList<String>();

        StringRequest spindata = new StringRequest (Request.Method.GET, getmapping,
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

                            String data = json_data.getString("Categories");


                            JSONArray categ = new JSONArray(data);
                            System.out.println("BLAH" + categ);

                            for(int i = 0;i<categ.length();i++)
                            {   JSONObject notification =categ.getJSONObject(i);
                                String cat_description = notification.getString("category_description");
//                                System.out.println("notif json working"+ complaint_id);
                                spin_arr.add(cat_description);
                            }
                            long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;
                            System.out.println("Response time for all complaint categ is==" + totalRequestTime);
                            ArrayAdapter<String> spin_ad = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,spin_arr);
                            spin_ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            Spinner s = (Spinner) findViewById(R.id.TypeSpinner);
                            s.setAdapter(spin_ad);

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
        Volley.newRequestQueue(getApplicationContext()).add(spindata);


    }

    public  void addComplaint(View view)
    {
        Helpers h = new Helpers();
        String addc = getResources().getString(R.string.IP) + "/complaint_data/add_complaint.json?type=";

        Spinner s1 = (Spinner) findViewById(R.id.IHI);
        String type = s1.getSelectedItem().toString();
        if (type.equals("Individual"))
        {
            addc += "i";
        }
        else if (type.equals("Hostel"))
        {
            addc += "h";
        }
        else
        {
            addc += "in";
        }
        addc += "&complaint_type=";

        Spinner s2 = (Spinner) findViewById(R.id.TypeSpinner);
        String ctype = s2.getSelectedItem().toString();
        addc += h.SpaceToScore(ctype) + "&content=";

//        content = , extra_info = , anonymous (1/0)
        final EditText content = (EditText) findViewById(R.id.content);
        addc += h.SpaceToScore(content.getText().toString()) + "&extra_info=";

        final EditText extra = (EditText) findViewById(R.id.extradet);
        addc += h.SpaceToScore(extra.getText().toString()) + "&anonymous=";

        CheckBox anony = (CheckBox) findViewById(R.id.anony);
        if (anony.isChecked())
        {
            addc += "1";
        }
        else
            addc += "0";

//        a post request


        System.out.println(addc);
        StringRequest spindata2 = new StringRequest (Request.Method.GET, addc,
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

                            boolean isdone = json_data.getBoolean("Success");
                            if (isdone)
                            {
                                Toast.makeText(getApplicationContext(),
                                        "Complaint added!",
                                        Toast.LENGTH_LONG).show();

                                content.setText("");
                                extra.setText("");
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),
                                        "Complaint couldn't be added",
                                        Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(getApplicationContext()).add(spindata2);

    }



}
