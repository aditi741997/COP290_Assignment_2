package aditi.ayush.nikhil.complaintmanagement;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView myTextView = (TextView) findViewById(R.id.Login);
        Typeface typeFace = FontLoader.getTypeFace(this, "RECOGNITION");
        if (typeFace != null) myTextView.setTypeface(typeFace);
    }

}
