package com.gpuntd.adminapp.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.ui.Fragments.AppSettingsFragment;
import com.gpuntd.adminapp.ui.Fragments.UserAllDepositFragment;
import com.gpuntd.adminapp.ui.Fragments.UserAllWithdrawFragment;
import com.gpuntd.adminapp.ui.Fragments.UserCreateIDFragment;


public class HistoryTabAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public HistoryTabAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new UserAllDepositFragment();
        }
        else
        if(position==1){
            return new UserCreateIDFragment();

        }else if(position==2){
            return new UserAllWithdrawFragment();

        }else{
            return new UserAllDepositFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.allDeposit);
            case 1:
                return mContext.getString(R.string.allCreateID);
            case 2:
                return mContext.getString(R.string.allWithdrawals);
            default:
                return mContext.getString(R.string.allDeposit);
        }
    }
}
