package com.example.ayush.complaint_management_system;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddComplaint extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);
    }
    private void Reg_comp(View view)
    {String url="http://127.0.0.1:8000/complaint_data/add_complaint.json";
        JsonObjectRequest Complaint = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // JsonObject x= response.g
                            boolean success = response.getBoolean("success");
                            if (success)
                            {
// String name=response.getString("Name")
                                String id= response.getString("Unique_Id");
                                // Sending only id as we can get all the information via database

                                //TODO: Add an intent to start the main page activity
                                //TODO: Also set the **** here
                            }

                        } catch (JSONException e) {
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
                        Toast.makeText(AddComplaint.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {   //@Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                //TODO: Add complaint parameters like this
               // params.put("username", username.getText().toString());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(Complaint);

    }
    private void back(View view)
    {//Going back to main page as we have stored all the required things into the global class
        // We will add the dynamic parts such as complaints in the oncreate method of home page
        Intent intent =new Intent(AddComplaint.this,HomePage.class);
        startActivity(intent);
    }

}
