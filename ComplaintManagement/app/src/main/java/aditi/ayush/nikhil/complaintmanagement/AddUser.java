package aditi.ayush.nikhil.complaintmanagement;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AddUser extends AppCompatActivity {
    MyApp_cookie cook = new MyApp_cookie();

    NumberPicker myNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        hard code spinner hostel.
        Spinner spin = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> spin_ada = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,cook.Hostels);
        spin_ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //spin.setAdapter(spin_ada);

        myNumber = (NumberPicker) findViewById(R.id.numberPicker);
        myNumber.setMaxValue(10);
        myNumber.setMinValue(0);
        myNumber.setWrapSelectorWheel(false);


    }

    public  void AddUser(View view)
    {
        EditText uName = (EditText) findViewById(R.id.editText);
        String userName = uName.getText().toString();

        EditText pass = (EditText) findViewById(R.id.editText2);
        String pswd = pass.getText().toString();

        EditText pass2 = (EditText) findViewById(R.id.editText3);
        String pswd2 = pass.getText().toString();

        EditText phone = (EditText) findViewById(R.id.Phone);
        String contact_no = pass.getText().toString();

        EditText extra_det = (EditText) findViewById(R.id.Details);
        String details = pass.getText().toString();

        Spinner host = (Spinner) findViewById(R.id.spinner);
        int hostel = host.getSelectedItemPosition() - 1;
//        hostel
//        separate page to add admin.
//        admin or no?
//        if yes, then : 1. category 2. level(-1 denotes special user)
    }


}
