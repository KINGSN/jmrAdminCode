package com.gpuntd.adminapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.gpuntd.adminapp.Models.AdminDTO;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.application.GlobalState;
import com.gpuntd.adminapp.databinding.ActivityMainBinding;
import com.gpuntd.adminapp.preferences.SharedPrefrence;
import com.gpuntd.adminapp.ui.Fragments.UsersChatFragment;
import com.gpuntd.adminapp.ui.deposits.DepositsFragment;
import com.gpuntd.adminapp.ui.home.HomeFragment;

import java.util.HashMap;
import java.util.Objects;

/*public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemReselectedListener{*/

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "KINGSN";
    public static  ActivityMainBinding binding;
    public static ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    public static  NavigationView navViewSide;
    public static  DrawerLayout drawerLayout;
    public static   NavController navController;
    public static Method method;
    public static TextView adminName,adminType;
    public TextView walletbal;
    public static  GlobalState globalState;
    public static   SharedPrefrence preferences;
    public static  AdminDTO adminDTO;
    HashMap<String, String> params = new HashMap<>();

    String type = "";
    public static final String TAG_MAIN = "main";
    public static final String TAG_CHAT = "chat";
    public static final String TAG_HOME = "home";
    public static final String TAG_BOOKING = "booking";
    public static final String TAG_NOTIFICATION = "notification";
    public static final String TAG_SETTING = "setting";
    public static final String TAG_DISCOUNT = "discount";
    public static final String TAG_HISTORY = "history";
    public static final String TAG_PROFILE_SETINGS = "profile_settings";
    public static final String TAG_TICKETS = "tickets";
    public static final String TAG_LOG_OUT = "signOut";
    public static final String TAG_APPOINTMENT = "appointment";
    public static final String TAG_JOBS = "jobs";
    public static final String TAG_WALLET = "wallet";
    public static String CURRENT_TAG = TAG_MAIN;
    public static int navItemIndex = 0;


    @SuppressLint("CommitPrefEdits")
    @Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.toolbarMain);
        drawerLayout = findViewById(R.id.drawer_layout);
        navViewSide = findViewById(R.id.sideNav_view);

       // preferences= SharedPrefrence.getInstance(MainActivity.this);
        method=new Method(this);
        method.preferences.setBooleanValue("MainActivityLoaded",false);
      //  adminDTO = preferences.getParentUser2(GlobalVariables.ADMIN_DTO);
       /* Log.d(TAG, "HELLO ON: "+preferences.getValue(adminDTO.getManageUserList()).equals("0"));
        Log.d(TAG, "HELLO ON: "+adminDTO.getManageUserList());*/
        /*globalState = GlobalState.getInstance();*/

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_1);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        if(!method.adminDTO.getManageUserList().equals("4")){
                            navController.navigate(R.id.navigation_home);
                        }

                        break;
                    case R.id.navigation_users:
                        if(!method.adminDTO.getManageUserList().equals("0")){
                            navController.navigate(R.id.navigation_users);
                        }else{
                            method.showToasty(MainActivity.this,"2","You Dont Have This Access \n Please" +
                                    " Ask Super To Grant Full Access ");
                        }

                        break;
                    case R.id.navigation_deposits:
                        if(!method.adminDTO.getManageDepositList().equals("0")){
                            navController.navigate(R.id.navigation_deposits);
                        }else{
                            method.showToasty(MainActivity.this,"2","You Dont Have This Access \n Please" +
                                    " Ask Super To Grant Full Access ");
                        }
                        break;
                    case R.id.navigation_settings:
                        if(!method.adminDTO.getManageDepositList().equals("0")){
                            navController.navigate(R.id.navigation_settings);
                        }else{
                            method.showToasty(MainActivity.this,"2","You Dont Have This Access \n Please" +
                                    " Ask Super To Grant Full Access ");
                        }

                        break;
                }
                return true;
            }
        });



        setupNavigationDrawer();



        if (getIntent().hasExtra(GlobalVariables.SCREEN_TAG)) {
            type = getIntent().getStringExtra(GlobalVariables.SCREEN_TAG);
            method.getHomeData2(MainActivity.this);
        }else{
            method.getHomeData2(MainActivity.this);
          //  method.showToasty(MainActivity.this,"2",adminDTO.getAdminName()+" \n "+adminDTO.getAdminPassword());

        }
        if (savedInstanceState == null) {
            if (type != null) {

                if (type.equalsIgnoreCase(GlobalVariables.CHAT_NOTIFICATION)) {

                 //   navController.navigate(R.id.navigation_chat);

                    Intent in = new Intent(MainActivity.this, OneTwoOneChat.class);
                    in.putExtra(GlobalVariables.userMobile,getIntent().getStringExtra("sendBy") );
                    in.putExtra(GlobalVariables.userMobile1,getIntent().getStringExtra("sendBy"));
                    in.putExtra(GlobalVariables.userName, getIntent().getStringExtra("userName"));
                    startActivity(in);
                } else if (type.equalsIgnoreCase(GlobalVariables.ADMIN_NOTIFICATION)) {
                    /*header.setVisibility(View.VISIBLE);
                    navItemIndex = 11;*/
                  /*  CURRENT_TAG = TAG_TICKETS;
                    navController.navigate(R.id.navigation_home);*/
                   // startActivity( new Intent(MainActivity.this, MainActivity.class));
                } else if (type.equalsIgnoreCase(GlobalVariables.ALL_DEPOSIT)) {
                    /*header.setVisibility(View.VISIBLE);
                    navItemIndex = 11;*/
                    CURRENT_TAG = TAG_TICKETS;
                   // navController.navigate(R.id.navigation_deposits);
                    startActivity( new Intent(MainActivity.this, MainActivity.class));
                    loadHomeFragment(new DepositsFragment(), "All Deposit ");
                } else if (type.equalsIgnoreCase(GlobalVariables.USER_WITHDRAWALS)) {
                    /*header.setVisibility(View.VISIBLE);
                    navItemIndex = 11;*/
                    CURRENT_TAG = TAG_TICKETS;
                    startActivity(new Intent(MainActivity.this, WithdrawRequestActivity.class));
                } else if (type.equalsIgnoreCase(GlobalVariables.USER_PASSID)) {
                    /*header.setVisibility(View.VISIBLE);
                    navItemIndex = 11;*/
                    CURRENT_TAG = TAG_TICKETS;
                    startActivity(new Intent(MainActivity.this, PasswordRequestActivity.class));
                } else {
                    CURRENT_TAG = TAG_HOME;
                    loadHomeFragment(new HomeFragment(), CURRENT_TAG);
                }
            }else{

                CURRENT_TAG = TAG_HOME;
                loadHomeFragment(new HomeFragment(), CURRENT_TAG);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        adminName = findViewById(R.id.adminName);
        adminType = findViewById(R.id.adminType);


        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_profile:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
            case R.id.action_admins:
                if(!method.adminDTO.getManageAdminList().equals("0")){
                 startActivity(new Intent(MainActivity.this, AdminActivity.class));
                }else{
                    binding.toolbarMain.getMenu().findItem(R.id.action_admins).setVisible(false);
                    method.showToasty(MainActivity.this,"2","You Dont Have This Access \n Please" +
                            " Ask Super To Grant Full Access ");
                }


                break;
            case R.id.action_logout:
              //  Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                method.setupLogoutDialog(MainActivity.this);
                break;

            case R.id.action_chat:

                /*final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_activity_main, new UsersChatFragment(), "Deposit");
                ft.commit();*/

                if(method.adminDTO.getId().equals("1")){
                   // navController.navigate(R.id.navigation_chat);


                }else{
                    binding.toolbarMain.getMenu().findItem(R.id.action_chat).setVisible(false);
                    method.showToasty(MainActivity.this,"2","You Dont Have This Access \n Please" +
                            " Ask Super To Grant Full Access ");
                }



                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    private void setupNavigationDrawer() {
        navViewSide.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint({"WrongConstant", "NonConstantResourceId"})
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_home:
                        drawerLayout.closeDrawer(Gravity.START, true);
                        navController.navigate(R.id.navigation_home);
                        break;
                    case R.id.drawer_user_lists:
                        drawerLayout.closeDrawer(Gravity.START, true);
                        //binding.toolbarMain.setTitle("All User List");
                        if(!method.adminDTO.getManageUserList().equals("0")){
                        navController.navigate(R.id.navigation_users);
                    }else{
                        method.showToasty(MainActivity.this,"2","You Dont Have This Access \n Please" +
                                " Ask Super To Grant Full Access ");
                    }

                    break;
                    case R.id.drawer_manage_deposits:
                        drawerLayout.closeDrawer(Gravity.START, true);
                        //binding.toolbarMain.setTitle("All Deposit List");

                        if(!method.adminDTO.getManageDepositList().equals("0")){
                        navController.navigate(R.id.navigation_deposits);
                    }else{
                        method.showToasty(MainActivity.this,"2","You Dont Have This Access \n Please" +
                                " Ask Super To Grant Full Access ");
                    }
                    break;
                    case R.id.drawer_manage_created_id:
                        drawerLayout.closeDrawer(Gravity.START, true);

                        // Toast.makeText(MainActivity.this, "manage created id req", Toast.LENGTH_SHORT).show();
                        if(!method.adminDTO.getManageCreateIDList().equals("0")){
                        startActivity(new Intent(MainActivity.this, ManageCreatedIdActivity.class));
                    }else{
                        method.showToasty(MainActivity.this,"2","You Dont Have This Access \n Please" +
                                " Ask Super To Grant Full Access ");
                    }
                    break;
                    case R.id.drawer_withdraw_request:
                        drawerLayout.closeDrawer(Gravity.START, true);

                        if(!method.adminDTO.getManageWithdrawal().equals("0")){
                        startActivity(new Intent(MainActivity.this, WithdrawRequestActivity.class));
                    }else{
                        method.showToasty(MainActivity.this,"2","You Dont Have This Access \n Please" +
                                " Ask Super To Grant Full Access ");
                    }
                    break;
                    case R.id.drawer_pass_request:
                        drawerLayout.closeDrawer(Gravity.START, true);

                        if(!method.adminDTO.getManagePassword().equals("0")){
                        startActivity(new Intent(MainActivity.this, PasswordRequestActivity.class));
                    }else{
                        method.showToasty(MainActivity.this,"2","You Dont Have This Access \n Please" +
                                " Ask Super To Grant Full Access ");
                    }
                    break;
                    case R.id.drawer_close_id_req:
                        drawerLayout.closeDrawer(Gravity.START, true);

                        if(!method.adminDTO.getManageCloseID().equals("0")){
                        startActivity(new Intent(MainActivity.this, CloseIdActivity.class));

                    }else{
                        method.showToasty(MainActivity.this,"2","You Dont Have This Access \n Please" +
                                " Ask Super To Grant Full Access ");
                    }

                    break;
                    case R.id.drawer_create_id_mode:
                        drawerLayout.closeDrawer(Gravity.START, true);

                        if(!method.adminDTO.getManageCreateIDList().equals("0")){
                        startActivity(new Intent(MainActivity.this, CreateIdModeActivity.class));
                    }else{
                        method.showToasty(MainActivity.this,"2","You Dont Have This Access \n Please" +
                                " Ask Super To Grant Full Access ");
                    }
                    break;
                    case R.id.drawer_reports:
                        drawerLayout.closeDrawer(Gravity.START, true);
                        startActivity(new Intent(MainActivity.this, ReportActivity.class));
                        break;
                    case R.id.drawer_notification:
                        drawerLayout.closeDrawer(Gravity.START, true);
                        if(!method.adminDTO.getManageNotification().equals("0")){
                        startActivity(new Intent(MainActivity.this, NotificationsActivity.class));
                    }else{
                        method.showToasty(MainActivity.this,"2","You Dont Have This Access \n Please" +
                                " Ask Super To Grant Full Access ");
                    }

                    break;
                    case R.id.drawer_app_banner:
                        drawerLayout.closeDrawer(Gravity.START, true);

                        if(!method.adminDTO.getManageNotification().equals("0")){
                        startActivity(new Intent(MainActivity.this, AppBannerActivity.class));
                    }else{
                        method.showToasty(MainActivity.this,"2","You Dont Have This Access \n Please" +
                                " Ask Super To Grant Full Access ");
                    }
                    break;
                    case R.id.drawer_create_app_offer:
                        if(!method.adminDTO.getManageNotification().equals("0")){
                        drawerLayout.closeDrawer(Gravity.START, true);
                        startActivity(new Intent(MainActivity.this, CreateAppOfferActivity2.class));
                    }else{
                        method.showToasty(MainActivity.this,"2","You Dont Have This Access \n Please" +
                                " Ask Super To Grant Full Access ");
                    }

                    break;

                    case R.id.drawer_logout:
                        drawerLayout.closeDrawer(Gravity.START, true);
                        // Toast.makeText(MainActivity.this, "logging out", Toast.LENGTH_SHORT).show();
                        method.setupLogoutDialog(MainActivity.this);

                        break;

                }
                return false;
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    public void loadHomeFragment(final Fragment fragment, final String TAG) {

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment, TAG);
                fragmentTransaction.commitAllowingStateLoss();
                //  ivFilter.setVisibility(View.GONE);

            }
        };

       /* if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);

        }
*/

        // drawer.closeDrawers();

        invalidateOptionsMenu();
    }


    @SuppressLint("SetTextI18n")
    public static void setData2(Activity activity) {

        Log.d(TAG, "setData: " + method.adminDTO.getId());
      // adminName.setText(GlobalVariables.profileuser.getAdmin_name());
        adminName.setText(method.adminDTO.getAdminName());
        if (!GlobalVariables.profileuser.getId().equals("1")) {
            adminType.setText("Sub Admin");
        } else {
            adminType.setText("Super Admin");
        }




        if (!GlobalVariables.profileuser.getProfileImg().equals("")) {
            Glide.with(activity)
                    .asBitmap()
                    .load(method.adminDTO.getProfileImg())
                    .placeholder(R.drawable.dummyuser_image)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((ImageView) binding.sideNavView.findViewById(R.id.profileImageView));

        } else {
            Glide.with(activity)
                    .asBitmap()
                    .load(activity.getResources().getDrawable(R.drawable.ic_profile))
                    .placeholder(R.drawable.dummyuser_image)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((ImageView) binding.sideNavView.findViewById(R.id.profileImageView));
        }

        if (!GlobalVariables.profileuser.getDeviceToken().equals(method.preferences.getValue(GlobalVariables.DEVICE_TOKEN))) {

            method.params.clear();
            method.params.put("deviceIdUpdate", "deviceIdUpdate");
            method.params.put("adminMobile", GlobalVariables.profileuser.getAdmin_mobile());
            method.params.put("device_token", method.preferences.getValue(GlobalVariables.DEVICE_TOKEN));
            Log.d(TAG, "setData: " + method.params);
            method.updateSetting(activity, RestAPI.USER_UPDATE);
        } else {
            Log.d(TAG, "setData: " + method.params);
        }


           method.preferences.setBooleanValue("MainActivityLoaded",true);

    }


    public void setupLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_logout, findViewById(R.id.logoutView));
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btnLogoutDialog).setOnClickListener(view1 -> {
            Toast.makeText(MainActivity.this, "Logging out!", Toast.LENGTH_SHORT).show();
           // preferences.edit().clear().apply();
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        });
        view.findViewById(R.id.btnCancelDialog).setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    /*@Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {


    }*/
}