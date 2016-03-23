package com.example.ayush.complaint_management_system;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void login(View view)
    {EditText username=(EditText) findViewById(R.id.Username);
     EditText pass=(EditText) findViewById(R.id.password);
     String url="http://127.0.0.1/default/login.json";
        JsonRequest stringRequest = new JsonRequest(Request.Method.POST,url,
        new Response.Listener<String>() {
        @Override
        public void onResponse(String response)
        {


        }
        },
        new Response.ErrorListener()
        {
        @Override
        public void onErrorResponse(VolleyError error)
        {
        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
        }
        });
    {   @Override
        protected Map<String, String> getParams()
        {
        Map<String, String> params = new HashMap<String, String>();
        params.put("teamname", teamname.getText().toString());
        params.put("entry1", Entry1.getText().toString());
        params.put("name1", Name1.getText().toString());
        params.put("entry2", Entry2.getText().toString());
        params.put("name2", Name2.getText().toString());
        params.put("entry3", Entry3.getText().toString());
        params.put("name3", Name3.getText().toString());
        return params;
        }
    };
    Volley.newRequestQueue(this).add(stringRequest);











     )

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
