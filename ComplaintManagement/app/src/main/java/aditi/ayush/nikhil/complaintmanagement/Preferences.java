package aditi.ayush.nikhil.complaintmanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Preferences extends AppCompatActivity {
    public  String hostel_preferences="",insti_preferences="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Check_set();
        //setSupportActionBar(toolbar);
        //get here and set checked unchecked

    }
public void Check_set()
{

    String url=getResources().getString(R.string.IP) +"/user/preferences.json";
    JsonObjectRequest cpass = new JsonObjectRequest (Request.Method.GET, url,null,
            new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    try {
                        hostel_preferences=response.getString("hostel_prefs");
                        insti_preferences=response.getString("insti_prefs");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //,insti_prefs

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Log.i("yo", "why this not working");
                    //  Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            });
    Volley.newRequestQueue(getApplicationContext()).add(cpass);



    CheckBox checkBox1=(CheckBox) findViewById(R.id.checkBox1);
    CheckBox checkBox2=(CheckBox) findViewById(R.id.checkBox2);
    CheckBox checkBox3=(CheckBox) findViewById(R.id.checkBox3);
    CheckBox checkBox4=(CheckBox) findViewById(R.id.checkBox4);
    CheckBox checkBox5=(CheckBox) findViewById(R.id.checkBox5);
    CheckBox checkBox6=(CheckBox) findViewById(R.id.checkBox6);
    CheckBox checkBox7=(CheckBox) findViewById(R.id.checkBox7);
    CheckBox checkBox8=(CheckBox) findViewById(R.id.checkBox8);
    CheckBox checkBox9=(CheckBox) findViewById(R.id.checkBox9);
    CheckBox checkBox10=(CheckBox) findViewById(R.id.checkBox10);
    if(hostel_preferences.charAt(0)=='1') checkBox1.setChecked(true); else checkBox1.setChecked(false) ;
    if(hostel_preferences.charAt(1)=='1') checkBox2.setChecked(true); else checkBox2.setChecked(false) ;
    if(hostel_preferences.charAt(2)=='1') checkBox3.setChecked(true); else checkBox3.setChecked(false) ;
    if(hostel_preferences.charAt(3)=='1') checkBox4.setChecked(true); else checkBox4.setChecked(false) ;
    if(hostel_preferences.charAt(4)=='1') checkBox5.setChecked(true); else checkBox5.setChecked(false) ;
    if(hostel_preferences.charAt(5)=='1') checkBox6.setChecked(true); else checkBox6.setChecked(false) ;
    if(hostel_preferences.charAt(6)=='1') checkBox7.setChecked(true); else checkBox7.setChecked(false) ;
    if(hostel_preferences.charAt(7)=='1') checkBox8.setChecked(true); else checkBox8.setChecked(false) ;
    if(hostel_preferences.charAt(8)=='1') checkBox9.setChecked(true); else checkBox9.setChecked(false) ;
    if(hostel_preferences.charAt(9)=='1') checkBox10.setChecked(true); else checkBox10.setChecked(false) ;

    CheckBox checkBox11=(CheckBox) findViewById(R.id.checkBox11);
    CheckBox checkBox12=(CheckBox) findViewById(R.id.checkBox12);
    CheckBox checkBox13=(CheckBox) findViewById(R.id.checkBox13);
    CheckBox checkBox14=(CheckBox) findViewById(R.id.checkBox14);
    CheckBox checkBox15=(CheckBox) findViewById(R.id.checkBox15);
    CheckBox checkBox16=(CheckBox) findViewById(R.id.checkBox16);
    CheckBox checkBox17=(CheckBox) findViewById(R.id.checkBox17);
    CheckBox checkBox18=(CheckBox) findViewById(R.id.checkBox18);
    CheckBox checkBox19=(CheckBox) findViewById(R.id.checkBox19);
    CheckBox checkBox20=(CheckBox) findViewById(R.id.checkBox20);
    if(insti_preferences.charAt(0)=='1')     checkBox11.setChecked(true); else checkBox11.setChecked(false) ;
    if(insti_preferences.charAt(1)=='1') checkBox12.setChecked(true); else checkBox12.setChecked(false) ;
    if(insti_preferences.charAt(2)=='1') checkBox13.setChecked(true); else checkBox13.setChecked(false) ;
    if(insti_preferences.charAt(3)=='1') checkBox14.setChecked(true); else checkBox14.setChecked(false) ;
    if(insti_preferences.charAt(4)=='1') checkBox15.setChecked(true); else checkBox15.setChecked(false) ;
    if(insti_preferences.charAt(5)=='1') checkBox16.setChecked(true); else checkBox16.setChecked(false) ;
    if(insti_preferences.charAt(6)=='1') checkBox17.setChecked(true); else checkBox17.setChecked(false) ;
    if(insti_preferences.charAt(7)=='1') checkBox18.setChecked(true); else checkBox18.setChecked(false) ;
    if(insti_preferences.charAt(8)=='1') checkBox19.setChecked(true); else checkBox19.setChecked(false) ;
    if(insti_preferences.charAt(9)=='1') checkBox20.setChecked(true); else checkBox20.setChecked(false) ;


}
public void save(View view)
{
//    for(int i=1; i<=10;i++)
//    {int k=i,j=i+10;
//    String id_1="@+id/checkBox1",id_2="";
//        CheckBox checkBox=(CheckBox) findViewById(R.id.checkBox1);
//    }
    CheckBox checkBox1=(CheckBox) findViewById(R.id.checkBox1);
    CheckBox checkBox2=(CheckBox) findViewById(R.id.checkBox2);
    CheckBox checkBox3=(CheckBox) findViewById(R.id.checkBox3);
    CheckBox checkBox4=(CheckBox) findViewById(R.id.checkBox4);
    CheckBox checkBox5=(CheckBox) findViewById(R.id.checkBox5);
    CheckBox checkBox6=(CheckBox) findViewById(R.id.checkBox6);
    CheckBox checkBox7=(CheckBox) findViewById(R.id.checkBox7);
    CheckBox checkBox8=(CheckBox) findViewById(R.id.checkBox8);
    CheckBox checkBox9=(CheckBox) findViewById(R.id.checkBox9);
    CheckBox checkBox10=(CheckBox) findViewById(R.id.checkBox10);
String hostel_pre="";
    if(checkBox1.isChecked()) hostel_pre+="1"; else hostel_pre+="0";
    if(checkBox2.isChecked()) hostel_pre+="1"; else hostel_pre+="0";
    if(checkBox3.isChecked()) hostel_pre+="1"; else hostel_pre+="0";
    if(checkBox4.isChecked()) hostel_pre+="1"; else hostel_pre+="0";
    if(checkBox5.isChecked()) hostel_pre+="1"; else hostel_pre+="0";
    if(checkBox6.isChecked()) hostel_pre+="1"; else hostel_pre+="0";
    if(checkBox7.isChecked()) hostel_pre+="1"; else hostel_pre+="0";
    if(checkBox8.isChecked()) hostel_pre+="1"; else hostel_pre+="0";
    if(checkBox9.isChecked()) hostel_pre+="1"; else hostel_pre+="0";
    if(checkBox10.isChecked()) hostel_pre+="1"; else hostel_pre+="0";



    CheckBox checkBox11=(CheckBox) findViewById(R.id.checkBox11);
    CheckBox checkBox12=(CheckBox) findViewById(R.id.checkBox12);
    CheckBox checkBox13=(CheckBox) findViewById(R.id.checkBox13);
    CheckBox checkBox14=(CheckBox) findViewById(R.id.checkBox14);
    CheckBox checkBox15=(CheckBox) findViewById(R.id.checkBox15);
    CheckBox checkBox16=(CheckBox) findViewById(R.id.checkBox16);
    CheckBox checkBox17=(CheckBox) findViewById(R.id.checkBox17);
    CheckBox checkBox18=(CheckBox) findViewById(R.id.checkBox18);
    CheckBox checkBox19=(CheckBox) findViewById(R.id.checkBox19);
    CheckBox checkBox20=(CheckBox) findViewById(R.id.checkBox20);

    String insti_pref="";
    if(checkBox11.isChecked()) insti_pref+="1"; else insti_pref+="0";
    if(checkBox12.isChecked()) insti_pref+="1"; else insti_pref+="0";
    if(checkBox13.isChecked()) insti_pref+="1"; else insti_pref+="0";
    if(checkBox14.isChecked()) insti_pref+="1"; else insti_pref+="0";
    if(checkBox15.isChecked()) insti_pref+="1"; else insti_pref+="0";
    if(checkBox16.isChecked()) insti_pref+="1"; else insti_pref+="0";
    if(checkBox17.isChecked()) insti_pref+="1"; else insti_pref+="0";
    if(checkBox18.isChecked()) insti_pref+="1"; else insti_pref+="0";
    if(checkBox19.isChecked()) insti_pref+="1"; else insti_pref+="0";
    if(checkBox20.isChecked()) insti_pref+="1"; else insti_pref+="0";

    System.out.println("Hostel= "+hostel_pre+"insti= "+insti_pref);
    String url=getResources().getString(R.string.IP) + "/user/update_preferences.json?hostel="+hostel_pre+"&institute=" +
            insti_pref+"&extra="+"1111111111" ;
    StringRequest cpass = new StringRequest (Request.Method.GET, url,
        new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.i("yo", "why this .set pref.. working" + response);
///                        System.out.println(response.toString());
//                        Toast.makeText(getActivity().getApplicationContext(),
//                                "Logout ",
//                                Toast.LENGTH_LONG).show();

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
}
