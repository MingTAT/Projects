package com.healthy.healthyhelper.base;

/**
 * Created by Ming on 2016/4/3.
 */
public class Config {
    //URL to login.php file
    public static final String LOGIN_URL = "http://proj-309-05.cs.iastate.edu/login.php";


    //Keys for username & password as defined in $_POST['key'] in login.php
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    //If server response is equal to this taht means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //KEYS for Shared preferences

    //Name of shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //To store the username of current logged in user
    public static final String USERNAME_SHARED_PREF = "username";

    //To store the boolean in shared preference to track user is logged in or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    //Testing activity

    public static final String TEST_URL = "http://proj-309-05.cs.iastate.edu/bodyTestUpdate.php";

    public static final String Food_URL = "http://proj-309-05.cs.iastate.edu/getPlanD.php";
    public static final String Train_URL = "http://proj-309-05.cs.iastate.edu/getPlanT.php";


}
