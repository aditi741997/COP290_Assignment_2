package aditi.ayush.nikhil.complaintmanagement;

/**
 * Created by Nikhil on 26/03/16.
 */

import android.app.Application;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.HashMap;

public class MyApp_cookie extends Application
{/** Extends throughout the Application,Manages Cookies, Stores the Required codes and names of courses**/

CookieManager cookieManage;
//    public static HashMap<Integer,String> course_list=new HashMap<Integer,String>();
//    public static HashMap<Integer,String> course_code=new HashMap<Integer,String>();
    public static  String Username="";
    public  static  String Pswd="";
    public static boolean isSpecial;
    public  static  boolean isAdmin;

    public void onCreate() {
        cookieManage= new CookieManager();
        CookieHandler.setDefault(cookieManage);
        super.onCreate();
//        courses=new ArrayList<>();
        //urls=new String[10];


    }





}