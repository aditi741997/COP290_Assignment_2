package aditi.ayush.nikhil.complaintmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class ChangePassword extends AppCompatActivity {
    MyApp_cookie cook = new MyApp_cookie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public  void BackHome(View view)
    {
        Intent home = new Intent(this,ComplaintPage.class);
        if (cook.isSpecial)
            home.putExtra("UserType","Special");
        else
            home.putExtra("UserType",cook.Username);

        startActivity(home);
        finish();

    }

    public  void ChangePass(View view)
    {
        final TextView stat = (TextView) findViewById(R.id.status);
        EditText old = (EditText) findViewById(R.id.oldpass);
        String oldpass = old.getText().toString();
        if (oldpass.equals(cook.Pswd))
        {
            EditText new1 = (EditText) findViewById(R.id.new1);
            String newp1 = new1.getText().toString();

            EditText new2 = (EditText) findViewById(R.id.new2);
            String newp2 = new2.getText().toString();

            if (newp1.equals(newp2) && !newp1.equals("") && !newp1.equals(" "))
            {
//                call API.
                String change_pass = getResources().getString(R.string.IP) + "/default/change_pass.json?newpwd=" + newp1;

                StringRequest cpass = new StringRequest (Request.Method.GET, change_pass,
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
                                    boolean succ = json_data.getBoolean("success");
                                    if (succ)
                                    {
                                        stat.setText("Your password has been changed successfully!");
                                        String newp = json_data.getString("newpwd");
                                        cook.Pswd = newp;
                                    }
                                    else
                                    {
                                        stat.setText("Your password could not be changed.");
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
                Volley.newRequestQueue(getApplicationContext()).add(cpass);
            }
            else
            {
                stat.setText("The two password fields did not match!");
            }
        }
        else
        {
            stat.setText("Incorrect old password");
            Toast.makeText(ChangePassword.this, "Incorrect old password", Toast.LENGTH_LONG).show();
        }
    }



}
