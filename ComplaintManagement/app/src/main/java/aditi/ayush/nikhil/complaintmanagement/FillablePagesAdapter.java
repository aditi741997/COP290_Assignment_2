package aditi.ayush.nikhil.complaintmanagement;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by Nikhil on 09/03/16.
 */
public class FillablePagesAdapter extends FragmentStatePagerAdapter {

    private FillableLoaderPage firstPage;
    private FillableLoaderPage fourthPage;

    public FillablePagesAdapter(FragmentManager fm) {
        super(fm);
        firstPage = FillableLoaderPage.newInstance(0);
        fourthPage = FillableLoaderPage.newInstance(1);
    }

    @Override public Fragment getItem(int position) {
        return getFragmentForPosition(position);
    }

    @Override public int getCount() {
        return 2;
    }

    private Fragment getFragmentForPosition(int position) {
        switch (position) {
            case 0:
                return firstPage;
            case 1:
                return fourthPage;
            default:
                return firstPage;
        }
    }

    @Override public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Plain";
            case 1:
                return "Stroke";
            case 2:
                return "Rounded";
            default:
                return "Waves";
        }
    }
}