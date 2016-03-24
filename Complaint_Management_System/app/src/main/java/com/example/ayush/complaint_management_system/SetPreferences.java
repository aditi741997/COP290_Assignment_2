package com.example.ayush.complaint_management_system;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SetPreferences extends AppCompatActivity {
     String Hostel_Preferences="";
    String Insti_Preferences="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_preferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String url="http://127.0.0.1/user/preferences.json";
        //TODO: To tick the preferences already set depending on response of following api
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                        Hostel_Preferences=response.getString("hostel_preferences");
                        Insti_Preferences=response.getString("insti_preferences");
                        } catch (JSONException e)
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
                        Toast.makeText(SetPreferences.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void Save_Preferences(View view)
   {//TODO: Check the list and set the preferences
    Hostel_Preferences="Set by checked list";
    Insti_Preferences="Set by checked list";
    String extra="";
    String url1="http://127.0.0.1/user/update_preferences.json?hostel="+Hostel_Preferences+"&institute=" +
            Insti_Preferences+"&extra="+ extra;
       JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url1, null,
               new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(JSONObject response)
                   {Toast.makeText(SetPreferences.this,"Saved Preference List",Toast.LENGTH_SHORT).show();
                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(SetPreferences.this, error.toString(), Toast.LENGTH_LONG).show();
                   }
               });

       Volley.newRequestQueue(this).add(stringRequest);
   }
    private void back(View view)
    {//Going back to main page as we have stored all the required things into the global class
     // We will add the dynamic parts such as complaints in the oncreate method of home page
        Intent intent =new Intent(SetPreferences.this,HomePage.class);
    }

}
