package com.example.ayush.complaint_management_system;

import android.app.Application;

import java.net.CookieHandler;
import java.net.CookieManager;

/**
 * Created by Ayush on 24-03-2016.
 */
public class My_App_Cookie extends Application
{

    //TODO: Make a object of what we want to store globally after the login
    //TODO:Also save the object in shared preferences and load them on starting again..try to adjust them
    CookieManager cookieManage;
    public void onCreate()
    {
        cookieManage = new CookieManager();
        CookieHandler.setDefault(cookieManage);
        super.onCreate();
    }
}
