package com.example.ayush.complaint_management_system;

/**
 * Created by Ayush on 23-03-2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class notifications extends Fragment{

    public notifications()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.notifications, container, false);
        //TODO: set all the notifications as seen and call the api
        return root;
    }

}