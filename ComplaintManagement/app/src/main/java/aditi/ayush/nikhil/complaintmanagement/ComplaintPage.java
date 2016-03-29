package aditi.ayush.nikhil.complaintmanagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ComplaintPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    home home = new home();
    MyApp_cookie cook = new MyApp_cookie();
    notifications notifications = new notifications();
    complaint_list complaint_list = new complaint_list();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle registrationData = getIntent().getExtras();
        if(registrationData == null){
            return;
        }

        String UserType = registrationData.getString("UserType");

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager, UserType);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        boolean Active=true;
        String activetab="0";
        if (Active) {
            viewPager.setCurrentItem(Integer.parseInt(activetab));
        }
//TODO: set visibilities of user depending on user type

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //TODO: Call the api's to get concerned complaint list and notifications and populate in this only
//        pop_complaint_list();//These function can be shifted to their respective fragments as well
//        pop_noti_list();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    private void setupViewPager(ViewPager viewPager, String User)
    {/** Adding Fragments  to the Tab View **/
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(User.equals("Special")){
        adapter.addFragment(home, "Home");}
        adapter.addFragment(complaint_list, "Complaints");
        adapter.addFragment(notifications, "Notifications");
        viewPager.setAdapter(adapter);
    }

//    public void Reg_comp(View view)
//    {
//        TextView txt=(TextView) findViewById(R.id.Reg);
//        txt.setText("Hello clickable");
//        //TODO: Add an intent to open a new Activity to  Register a Complaint
//
//
//    }
//    public void My_Complaints(View view)
//    {
//        TextView txt=(TextView) findViewById(R.id.My_comp);
//        txt.setText("Hello clickable");
//        String url="http://127.0.0.1/admin_complaint/get_all_complaints.json";
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response)
//                    {
//                        //TODO: Add an intent to open a new Activity showing self registered complaints or add it as a tab
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(HomePage.this, error.toString(), Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        Volley.newRequestQueue(this).add(stringRequest);
//
//    }
//    public void Add_User(View view)
//    {
//        TextView txt=(TextView) findViewById(R.id.Add_User);
//        txt.setText("Hello clickable");
//        Intent I=new Intent(HomePage.this,addUser.class);
//        startActivity(I);
//    }
//    public void pop_complaint_list()
//    {String url= "http://127.0.0.1/complaint_data/get_all.json";
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response)
//                    {//TODO
//                        //We receive a set of complaints..sort them on the basis of level
//                        // make array on the basis of response array and populate the list view
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ComplaintPage.this, error.toString(), Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        Volley.newRequestQueue(this).add(stringRequest);
//    }
//    public void pop_noti_list()
//    {String url= "http://10.192.38.23:8000/notification/get_noti.json";
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response)
//                    {//TODO
//                        //We receive a set of complaints..sort them on the basis of level
//                        // make array on the basis of response array and populate the list view
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ComplaintPage.this, error.toString(), Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        Volley.newRequestQueue(this).add(stringRequest);
//    }

    public void address_comp(View view)
    {
        Intent i=new Intent(ComplaintPage.this,Admin_complaints.class);
        startActivity(i);
    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "HomePage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://aditi.ayush.nikhil.complaintmanagement/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "HomePage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://aditi.ayush.nikhil.complaintmanagement/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        /**
         * A class to populate the Tab Views
         **/
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    ///////////////////////////////Navigation Drawer Related stuff
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.complaint_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout)
        {   String url= getResources().getString(R.string.IP) + "/default/logout.json";
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response)
                        {Intent i=new Intent(ComplaintPage.this,MainActivity.class);
                            Toast.makeText(ComplaintPage.this,"",Toast.LENGTH_SHORT).show();
                            startActivity(i);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ComplaintPage.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            Volley.newRequestQueue(this).add(stringRequest);

            return true;
        }
        else if (id == R.id.action_Change_pass)
        {//TODO: direct to a new activity or we can use popup dialog box
            Intent i=new Intent(ComplaintPage.this,ChangePassword.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.complaints) {
            // Handle the camera action
        } else if (id == R.id.Notifications)
        {

        }
        else if (id == R.id.action_Change_pass)
        { Intent i=new Intent(ComplaintPage.this,ChangePassword.class);
            startActivity(i);
        } else if (id == R.id.Set_Preferences)
        {Intent i =new Intent(ComplaintPage.this,Preferences.class);
            startActivity(i);
        } else if (id == R.id.action_logout)
        { String url= getResources().getString(R.string.IP) + "/default/logout.json";
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response)
                        {Intent i=new Intent(ComplaintPage.this,MainActivity.class);
                            Toast.makeText(ComplaintPage.this,"",Toast.LENGTH_SHORT).show();
                            startActivity(i);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ComplaintPage.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            Volley.newRequestQueue(this).add(stringRequest);


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
