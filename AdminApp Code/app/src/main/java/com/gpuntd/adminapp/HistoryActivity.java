package com.gpuntd.adminapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gpuntd.adminapp.Models.HomeTopDepositsDTO;
import com.gpuntd.adminapp.Models.HomeTopUserDTO;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.adapter.HistoryTabAdapter;
import com.gpuntd.adminapp.adapter.TopDepositDataAdapter;
import com.gpuntd.adminapp.adapter.TopUserDataAdapter;
import com.gpuntd.adminapp.databinding.ActivityHistoryBinding;
import com.gpuntd.adminapp.preferences.SharedPrefrence;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private static final String TAG = "KINGSN";
    public ActivityHistoryBinding binding1;
    RecyclerView MyrecyclerView;
    Toolbar toolbar;
    private Method method;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static Uri selectedImage;
    ArrayList<HomeTopUserDTO> homeTopUserDTOArrayList = new ArrayList<>();
    ArrayList<HomeTopDepositsDTO> homeTopDepositsDTOArrayList = new ArrayList<>();
    TopUserDataAdapter topUserDataAdapter;
    TopDepositDataAdapter topDepositDataAdapter;
    private ViewPager viewPager;
    private SharedPrefrence prefrence;
    String  pls="0";
    int pl,t1,t2;
    public TextView tvUserName;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        binding1 = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding1.getRoot());
        toolbar = findViewById(R.id.toolbarHistory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       tvUserName=binding1.tvUserName;
        preferences = HistoryActivity.this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        method=new Method(this);




        // preferences = this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);

        method = new Method(this);
        HistoryTabAdapter walletAdapter=new HistoryTabAdapter(HistoryActivity.this,getSupportFragmentManager());
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPagerHistory);
        viewPager.setAdapter(walletAdapter);

        tabLayout.setupWithViewPager(viewPager);

      /*  method.preferences.setValue("userTotalDeposit",topUserDTO.getTotalPaid());
        method.preferences.setValue("userTotalRedeemed",topUserDTO.getTotalRedeemed());
        method.preferences.setValue("totalreffered",topUserDTO.getTotalReferals());*/

         binding1.tvUserName.setText(preferences.getString(GlobalVariables.SELUSER_NAME,""));
         binding1.tvUserPhone.setText(preferences.getString(GlobalVariables.SELUSER_MOBILE,""));
        binding1.tvActivedate.setText(preferences.getString(GlobalVariables.SELUSER_ACTIVE,""));
       binding1.tvRegisterdate.setText(preferences.getString(GlobalVariables.SELUSER_REGDATE,""));
         binding1.tvWalletbal.setText(preferences.getString(GlobalVariables.SELUSER_WALLETBAL,""));
        binding1.tvUserReferCode.setText(preferences.getString(GlobalVariables.SELUSER_REFERCODE,""));
        binding1.totaldp.setText("D/A : "+method.preferences.getValue("userTotalDeposit"));
        binding1.totaldwl.setText("W/A : "+method.preferences.getValue("userTotalRedeemed"));
         t1= Integer.parseInt(method.preferences.getValue("userTotalDeposit"));
         t2= Integer.parseInt(method.preferences.getValue("userTotalRedeemed"));
       // Log.d(TAG, "onCreate: "+t1+"hgchgch"+t2);
         pl=t1-t2;

       // Log.d(TAG, "onCreate: "+pl);
        if(pl>0){
          pls="PL : "+(pl);
            binding1.totalpl.setTextColor(getResources().getColor(R.color.successColor));
        }else if(pl<0){
            pls="PL : "+(pl);
            binding1.totalpl.setTextColor(getResources().getColor(R.color.errorColor));
        }else{
            pls="PL : No P/L ";
            binding1.totalpl.setTextColor(getResources().getColor(R.color.black_overlay));
        }
        binding1.totalpl.setText(pls);
      //  binding1.totalpl.setText(t1-t2);

    }




}