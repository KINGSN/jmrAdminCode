package com.gpuntd.adminapp.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.ui.Fragments.AppBannerScreenFragment;
import com.gpuntd.adminapp.ui.Fragments.AppControlFragment;
import com.gpuntd.adminapp.ui.Fragments.AppCreateIdFragment;
import com.gpuntd.adminapp.ui.Fragments.AppOfferScreenFragment;
import com.gpuntd.adminapp.ui.Fragments.AppPaymentDetailsFragment;
import com.gpuntd.adminapp.ui.Fragments.AppOnesigScreenFragment;
import com.gpuntd.adminapp.ui.Fragments.AppPaymentModeFragment;
import com.gpuntd.adminapp.ui.Fragments.AppSettingsFragment;


public class TabAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public TabAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new AppSettingsFragment();
        }
        else
        if(position==1){
            return new AppControlFragment();

        }else if(position==2){
            return new AppPaymentModeFragment();

        }else if(position==3){
            return new AppPaymentDetailsFragment();

        }else if(position==4){
            return new AppOnesigScreenFragment();

        }else{
            return new AppSettingsFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.app_settings);
            case 1:
                return mContext.getString(R.string.app_control);
            case 2:
                return mContext.getString(R.string.app_payment_mode);
            case 3:
                return mContext.getString(R.string.app_offline_screenshot);
            case 4:
                return mContext.getString(R.string.one_signal_data);

            default:
                return mContext.getString(R.string.app_settings);
        }
    }
}
