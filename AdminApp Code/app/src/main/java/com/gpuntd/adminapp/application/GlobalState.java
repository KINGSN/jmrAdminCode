package com.gpuntd.adminapp.application;

import android.content.SharedPreferences;

import androidx.multidex.MultiDexApplication;

import com.gpuntd.adminapp.Models.HomeDataDTO;
import com.gpuntd.adminapp.preferences.SharedPrefrence;


public class GlobalState extends MultiDexApplication {

    private static GlobalState mInstance;
    HomeDataDTO homeData;
    SharedPreferences sharedPrefrence;
   // AppEnvironment appEnvironment;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        sharedPrefrence = (SharedPreferences) SharedPrefrence.getInstance(this);
       // appEnvironment = AppEnvironment.PRODUCTION;
    }


    public static synchronized GlobalState getInstance() {
        return mInstance;
    }

    public HomeDataDTO getHomeData() {
        return homeData;
    }

    public void setHomeData(HomeDataDTO homeData) {
        this.homeData = homeData;
    }
}
