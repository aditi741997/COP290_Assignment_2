package aditi.ayush.nikhil.complaintmanagement;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.FrameLayout;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import butterknife.Bind;
        import butterknife.ButterKnife;

        import com.github.jorgecastillo.FillableLoader;
        import com.github.jorgecastillo.FillableLoaderBuilder;
        import com.github.jorgecastillo.clippingtransforms.WavesClippingTransform;
        import com.github.jorgecastillo.listener.OnStateChangeListener;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.net.CookieHandler;
        import java.net.CookieManager;
        import java.util.HashMap;
        import java.util.Map;

        import com.android.volley.Request;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;
        import com.google.android.gms.appindexing.Action;
        import com.google.android.gms.appindexing.AppIndex;
        import com.google.android.gms.common.api.GoogleApiClient;

public class FillableLoaderPage extends Fragment implements OnStateChangeListener, ResettableView {

    @Bind(R.id.fillableLoader) @Nullable
    FillableLoader fillableLoader;
    private View rootView;
    private int pageNum;

    public static FillableLoaderPage newInstance(int pageNum) {
        FillableLoaderPage page = new FillableLoaderPage();
        Bundle bundle = new Bundle();
        bundle.putInt("pageNum", pageNum);
        page.setArguments(bundle);

        return page;
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                                 Bundle savedInstanceState) {
        pageNum = getArguments().getInt("pageNum", 0);

        switch (pageNum) {
            case 0:
                rootView = inflater.inflate(R.layout.fragment_first_page, container, false);
                break;
            case 1:
                rootView =
                        inflater.inflate(R.layout.fragment_fourth_page, container, false);
                break;
            default:
                rootView = inflater.inflate(R.layout.fragment_first_page, container, false);
        }

        whattodo(pageNum, rootView);

        return rootView;

    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, rootView);
        setupFillableLoader(pageNum);

    }

    public void whattodo(int pageNum, View myFragmentView) {
        if( pageNum == 0 )
        {

            ImageView text1 = (ImageView) myFragmentView.findViewById(R.id.Arrow);
            Animation animation1 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.arrow);
            text1.startAnimation(animation1);

            TextView text2 = (TextView) myFragmentView.findViewById(R.id.swipe);
            Animation animation2 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.pulse);
            text2.startAnimation(animation2);

            ImageView text3 = (ImageView) myFragmentView.findViewById(R.id.Logo);
            Animation animation3 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.showlogo);
            text3.startAnimation(animation3);

            TextView text4 = (TextView)myFragmentView.findViewById(R.id.complaint);
            Animation animation4 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.show5_5);
            text4.startAnimation(animation4);

            TextView text5 = (TextView)myFragmentView.findViewById(R.id.cover);
            Animation animation5 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.block5);
            text5.startAnimation(animation5);

            TextView myTextView = (TextView) myFragmentView.findViewById(R.id.complaint);
            Typeface typeFace = FontLoader.getTypeFace(getActivity().getApplicationContext(), "DroidBold");
            if (typeFace != null) myTextView.setTypeface(typeFace);

        }

        else{
            TextView myTextView = (TextView)myFragmentView.findViewById(R.id.Login);
            Typeface typeFace = FontLoader.getTypeFace(getActivity(), "RECOGNITION");
            if (typeFace != null) myTextView.setTypeface(typeFace);

            final EditText username = (EditText) myFragmentView.findViewById(R.id.Username);
            final EditText password = (EditText) myFragmentView.findViewById(R.id.password);


            Button button = (Button) myFragmentView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View V)
//                {   final String Username = username.getText().toString();
//                    final String Password = password.getText().toString();
//
//                    Toast.makeText(getActivity().getApplicationContext(),
//                                                    "Click Success\t" + Username +"\t" + Password,
//                                                    Toast.LENGTH_LONG).show();
//                }
                {
                    CookieManager manager = new CookieManager();
                    CookieHandler.setDefault(manager);
                    final String Username = username.getText().toString();
                    final String Password = password.getText().toString();
//                    String url = "http://10.192.38.23:8000/default/login.json";
                    String url1 = getResources().getString(R.string.IP) + "/default/login.json?userid="+Username+"&password="+Password;

                    JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url1, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        // JsonObject x= response.g
                                        boolean success = response.getBoolean("success");
//                                        boolean user = response.getBoolean("UserType");
                                        String s1 = response.getString("userid");
                                        String s2 = response.getString("passwd");

                                        if (success)
                                        {
// String name=response.getString("Name")
                                            String id= response.getString("Unique_Id");
                                            // Sending only id as we can get all the information via database
                                            //TODO: Add an intent to start the main page activity
                                            //TODO: Also set the **** here
                                            Toast.makeText(getActivity().getApplicationContext(),
                                                    "Login Success" + "\t" + s1 + "\t" + s2,
                                                    Toast.LENGTH_LONG).show();
                                            Intent i=new Intent(getActivity().getApplicationContext(),ComplaintPage.class);
                                            Toast.makeText(getActivity().getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                                            i.putExtra("UserType", Username);
                                            startActivity(i);
                                        }
                                        else
                                        {
                                            Toast.makeText(getActivity().getApplicationContext(),
                                                    "Login not success" + success + "\t" +s1 + "\t" +s2,
                                                    Toast.LENGTH_LONG).show();
                                            Intent i=new Intent(getActivity().getApplicationContext(),ComplaintPage.class);
                                            Toast.makeText(getActivity().getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                                            i.putExtra("UserType", Username);
                                            startActivity(i);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                "Error: " + e.getMessage(),
                                                Toast.LENGTH_LONG).show();
                                        Intent i=new Intent(getActivity().getApplicationContext(),ComplaintPage.class);
                                        Toast.makeText(getActivity().getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                                        i.putExtra("UserType", Username);
                                        startActivity(i);
                                    }


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                                    Intent i=new Intent(getActivity().getApplicationContext(),ComplaintPage.class);
                                    Toast.makeText(getActivity().getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                                    i.putExtra("UserType", Username);
                                    startActivity(i);
                                }
                            }) {   //@Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("userid", username.getText().toString());
                            params.put("password", password.getText().toString());
                            return params;
                        }
                    };

                    Volley.newRequestQueue(getActivity().getApplicationContext()).add(stringRequest);

                }
            });

        }


    }

    private void setupFillableLoader(int pageNum) {
        if (pageNum == 1) {
            int viewSize = getResources().getDimensionPixelSize(R.dimen.fourthSampleViewSize);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(viewSize, viewSize);
            params.gravity = Gravity.CENTER;

            FillableLoaderBuilder loaderBuilder = new FillableLoaderBuilder();
            fillableLoader = loaderBuilder.parentView((FrameLayout) rootView)
                    .svgPath(Paths.LOGIN)
                    .layoutParams(params)
                    .originalDimensions(970, 970)
                    .strokeColor(Color.parseColor("#000000"))
                    .fillColor(Color.parseColor("#125688"))
                    .strokeDrawingDuration(2000)
                    .strokeWidth(1)
                    .clippingTransform(new WavesClippingTransform())
                    .fillDuration(10000)
                    .build();


        } else {
            fillableLoader.setSvgPath(pageNum == 0 ? Paths.COMPLAIN : Paths.COMPLAIN);

        }

        fillableLoader.setOnStateChangeListener(this);
    }



    @Override public void onStateChange(int state) {
        ((MainActivity) getActivity()).showStateHint(state);
    }

    @Override public void reset() {
        fillableLoader.reset();

        //We wait a little bit to start the animation, to not contaminate the drawing effect
        //by the activity creation animation.
        fillableLoader.postDelayed(new Runnable() {
            @Override public void run() {
                fillableLoader.start();
            }
        }, 250);
    }
}
