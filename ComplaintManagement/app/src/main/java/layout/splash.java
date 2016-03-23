package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import aditi.ayush.nikhil.complaintmanagement.R;


public class splash extends Fragment {

    private OnFragmentInteractionListener mListener;

    public splash() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.fragment_splash, container, false);
        // Inflate the layout for this fragment

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


        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
