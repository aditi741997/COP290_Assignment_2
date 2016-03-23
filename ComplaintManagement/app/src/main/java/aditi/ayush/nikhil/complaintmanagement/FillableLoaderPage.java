package aditi.ayush.nikhil.complaintmanagement;

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
        import android.widget.FrameLayout;
        import android.widget.ImageView;
        import android.widget.TextView;

        import butterknife.Bind;
        import butterknife.ButterKnife;

        import com.github.jorgecastillo.FillableLoader;
        import com.github.jorgecastillo.FillableLoaderBuilder;
        import com.github.jorgecastillo.clippingtransforms.WavesClippingTransform;
        import com.github.jorgecastillo.listener.OnStateChangeListener;

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
            Typeface typeFace = FontLoader.getTypeFace(getContext(), "DroidBold");
            if (typeFace != null) myTextView.setTypeface(typeFace);

        }

        else{
            TextView myTextView = (TextView)myFragmentView.findViewById(R.id.Login);
            Typeface typeFace = FontLoader.getTypeFace(getActivity(), "RECOGNITION");
            if (typeFace != null) myTextView.setTypeface(typeFace);

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
