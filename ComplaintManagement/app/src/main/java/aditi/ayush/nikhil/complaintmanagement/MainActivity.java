package aditi.ayush.nikhil.complaintmanagement;

import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import android.support.v4.view.GestureDetectorCompat;



public class MainActivity extends AppCompatActivity {

    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.clippingTransformMode)
    TextView clippingModeText;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupPagination();
        setupDisableTraceButton();
//        TextView myTextView = (TextView) findViewById(R.id.complaint);
//        Typeface typeFace = FontLoader.getTypeFace(this, "DroidBold");
//        if (typeFace != null) myTextView.setTypeface(typeFace);
    }

    private void setupPagination() {
        pager = (ViewPager) findViewById(R.id.pager);
        final FillablePagesAdapter adapter = new FillablePagesAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ((ResettableView) adapter.getItem(position)).reset();
                clippingModeText.setText(adapter.getPageTitle(position));
            }
        });

        pager.post(new Runnable() {
            @Override
            public void run() {
                ((ResettableView) adapter.getItem(0)).reset();
                clippingModeText.setText(adapter.getPageTitle(0));
            }
        });
    }

    private void setupDisableTraceButton() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void showStateHint(int state) {
       // Snackbar.make(fab, "State changed callback: " + state, Snackbar.LENGTH_SHORT).show();
    }




}
