package com.gpuntd.adminapp.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gpuntd.adminapp.CloseIdActivity;
import com.gpuntd.adminapp.Interface.Helper;
import com.gpuntd.adminapp.LoginActivity;
import com.gpuntd.adminapp.MainActivity;
import com.gpuntd.adminapp.ManageCreatedIdActivity;
import com.gpuntd.adminapp.Models.AdminDTO;
import com.gpuntd.adminapp.Models.HomeDataDTO;
import com.gpuntd.adminapp.Models.HomeGraphDTO;
import com.gpuntd.adminapp.Models.HomeTopDepositsDTO;
import com.gpuntd.adminapp.Models.HomeTopUserDTO;
import com.gpuntd.adminapp.Models.My_ID_Data;
import com.gpuntd.adminapp.Models.Profileuser;
import com.gpuntd.adminapp.Models.User1DTO;
import com.gpuntd.adminapp.Models.UserDTO;
import com.gpuntd.adminapp.Models.Settings;
import com.gpuntd.adminapp.Models.SliderData;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.Ex;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.PrefManager;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.WithdrawRequestActivity;
import com.gpuntd.adminapp.adapter.TopDepositDataAdapter;
import com.gpuntd.adminapp.adapter.TopUserDataAdapter;

import com.gpuntd.adminapp.application.GlobalState;
import com.gpuntd.adminapp.databinding.FragmentHomeBinding;
import com.gpuntd.adminapp.https.HttpsRequest;
import com.gpuntd.adminapp.preferences.SharedPrefrence;
import com.gpuntd.adminapp.ui.deposits.DepositsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class HomeFragment extends Fragment {

    private static final String TAG = "KINGSN";
    private static final int MAX_X_VALUE = 6;
    private static final int MAX_Y_VALUE = 500;
    private static final int MIN_Y_VALUE = 5;
    private static final int GROUPS = 3;
    private static final String GROUP_1_LABEL = "Success/Active";
    private static final String GROUP_2_LABEL = "Pending";
    private static final String GROUP_3_LABEL = "Blocked/Rejected";
    private static final float BAR_SPACE = 0.02f;
    private static final float BAR_WIDTH = 0.2f;
    private static final String[] DAYS = {"Users", "Deposit", "Create ID", "Withdrawals", "Close ID"};
    // private SliderLayout sliderLayout;
   public RecyclerView CreaterecyclerView, recyclerView2, MyrecyclerView;
    ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
    private FragmentHomeBinding binding;
    private List<My_ID_Data> my_id_data;
    private BarChart chart;
     HomeDataDTO homeDataDTO;
    public Method method;
    public GlobalState globalState;
    public HomeGraphDTO homegrph;
    public  SharedPrefrence preferences;
    AdminDTO adminDTO;
    MainActivity mainActivity;
   // private User1DTO user1DTO,usermdto;
    HashMap<String, String> params = new HashMap<>();


    ArrayList<HomeGraphDTO> homeGraphDTOArrayList = new ArrayList<HomeGraphDTO>() ;
    ArrayList<HomeTopUserDTO> homeTopUserDTOArrayList = new ArrayList<>();
    ArrayList<HomeTopDepositsDTO> homeTopDepositsDTOArrayList = new ArrayList<>();
    TopUserDataAdapter topUserDataAdapter;
    TopDepositDataAdapter topDepositDataAdapter;
    @SuppressLint("CommitPrefEdits")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        preferences= SharedPrefrence.getInstance(requireActivity());
        Log.d(TAG, "onCreateView: " + preferences.getValue("OnesignalappKey"));
     mainActivity=new MainActivity();

        method=new Method(requireActivity());

        adminDTO = preferences.getParentUser2(GlobalVariables.ADMIN_DTO);
       // method.showToasty(requireActivity(),"1",adminDTO.getAdminName()+" \n "+adminDTO.getAdminPassword());



         binding.mainl.setVisibility(View.GONE);
        globalState = GlobalState.getInstance();
        params.put("adminUserId", "super_admin");

        getHomeData();


        chart = root.findViewById(R.id.fragment_groupedbarchart_chart);



        return root;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("SetTextI18n")
    public void setData() {

        method.getHomeData2(requireActivity());

        BarData data = createChartData();
        configureChartAppearance();
        prepareChartData(data);
      /*  binding.pCreatedId.setText(preferences.getString(GlobalVariables.homeGraph.getTotalcIDPending(),""));
        binding.pcardPasswordRequest.setText(preferences.getString(GlobalVariables.homeGraph.getTotalDPending(),""));
        binding.pcardCloseIdRequest.setText(preferences.getString(GlobalVariables.homeGraph.getTotalclosePending(),""));
        binding.pcardWithdrawRequest.setText(preferences.getString(GlobalVariables.homeGraph.getTotalwPending(),""));*/

        binding.pCreatedId.setText(GlobalVariables.homeGraph.getTotalcIDPending());
        binding.pcardPasswordRequest.setText(GlobalVariables.homeGraph.getTotalDPending());
        binding.pcardCloseIdRequest.setText(GlobalVariables.homeGraph.getTotalclosePending());
        binding.pcardWithdrawRequest.setText(GlobalVariables.homeGraph.getTotalwPending());
        binding.mainl.setVisibility(View.VISIBLE);
        if(method.loadingDialog.isShowing()){
            method.loadingDialog.dismiss();
        }
        binding.cardManageCreatedId.setOnClickListener(view -> {


            if(!adminDTO.getManageCreateIDList().equals("0")){
                requireActivity().startActivity(new Intent(getActivity(), ManageCreatedIdActivity.class));
            }else{
                method.showToasty(requireActivity(),"2","You Dont Have This Access \n Please" +
                        " Ask Super To Grant Full Access ");
            }
        });
        binding.cardWithdrawRequest.setOnClickListener(view -> {
            if(!adminDTO.getManageWithdrawal().equals("0")){
            requireActivity().startActivity(new Intent(getActivity(), WithdrawRequestActivity.class));
        }else{
                method.showToasty(requireActivity(),"2","You Dont Have This Access \n Please" +
                        " Ask Super To Grant Full Access ");
            }
        });

        binding.cardPasswordRequest.setOnClickListener(view -> {
            if(!adminDTO.getManageDepositList().equals("0")){
             //  binding.navController.navigate(R.id.navigation_deposits);
                final FragmentTransaction ft = requireFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_activity_main, new DepositsFragment(), "Deposit");
                ft.commit();
            }else{
                method.showToasty(requireActivity(),"2","You Dont Have This Access \n Please" +
                        " Ask Super To Grant Full Access ");
            }
        });
        binding.cardCloseIdRequest.setOnClickListener(view -> {
            if(!adminDTO.getManageDepositList().equals("0")){
                //  binding.navController.navigate(R.id.navigation_deposits);
                requireActivity().startActivity(new Intent(getActivity(), CloseIdActivity.class));
            }else{
                method.showToasty(requireActivity(),"2","You Dont Have This Access \n Please" +
                        " Ask Super To Grant Full Access ");
            }
        });

        MainActivity.setData2(requireActivity());

        if(method.preferences.getBooleanValue("MainActivityLoaded")){
            method.loadingDialog.dismiss();

        }else{
            method.loadingDialog.dismiss();
           // Method.alertBox("2","Something Went Wrong","Close & Restart", requireActivity(),"Go to Home");

        }
    }


    private void configureChartAppearance() {

        chart.getDescription().setEnabled(false);
        // chart.setBackgroundColor(Color.WHITE);

        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        // l.setTypeface(tfLight);
        l.setYOffset(0f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setXEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xAxis = chart.getXAxis();
        // xAxis.setTypeface(tfLight);
        xAxis.setGranularity(1f);


        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return DAYS[(int) value];
            }
        });

        YAxis leftAxis = chart.getAxisLeft();
        //leftAxis.setTypeface(tfLight);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(55f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        chart.getAxisRight().setEnabled(false);
        chart.setFitBars(true);
    }

    private BarData createChartData() {
        Util u = new Util();

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();

        for (int i = 0; i < DAYS.length; i++) {
            chart.getXAxis().getTextColor();

           /* values1.add(new BarEntry(i, u.randomFloatBetween(MIN_Y_VALUE, MAX_Y_VALUE)));
            values2.add(new BarEntry(i, u.randomFloatBetween(MIN_Y_VALUE, MAX_Y_VALUE)));
            values3.add(new BarEntry(i, u.randomFloatBetween(MIN_Y_VALUE, MAX_Y_VALUE)));*/
            if(i == 0){
                values1.add(new BarEntry(i, Integer.parseInt(GlobalVariables.homeGraph.getTotalActiveUser())));
                values2.add(new BarEntry(i, Integer.parseInt(GlobalVariables.homeGraph.getTotalUser())));
                values3.add(new BarEntry(i, Float.parseFloat(GlobalVariables.homeGraph.getTotalBlockedUser())));
            } if(i == 1){
                values1.add(new BarEntry(i, Float.parseFloat(GlobalVariables.homeGraph.getTotalDVerified())));
                values2.add(new BarEntry(i, Float.parseFloat(GlobalVariables.homeGraph.getTotalDPending())));
                values3.add(new BarEntry(i, Float.parseFloat(GlobalVariables.homeGraph.getTotalDRejected())));
            } if(i == 2){
                values1.add(new BarEntry(i, Float.parseFloat(GlobalVariables.homeGraph.getTotalcIDVerified())));
                values2.add(new BarEntry(i, Float.parseFloat(GlobalVariables.homeGraph.getTotalcIDPending())));
                values3.add(new BarEntry(i, Float.parseFloat(GlobalVariables.homeGraph.getTotalDRejected())));
            } if(i == 3){
                values1.add(new BarEntry(i, Float.parseFloat(GlobalVariables.homeGraph.getTotalwVerified())));
                values2.add(new BarEntry(i, Float.parseFloat(GlobalVariables.homeGraph.getTotalwPending())));
                values3.add(new BarEntry(i, Float.parseFloat(GlobalVariables.homeGraph.getTotalwRejected())));
            } if(i == 4){
                values1.add(new BarEntry(i, Float.parseFloat(GlobalVariables.homeGraph.getTotalcloseVerified())));
                values2.add(new BarEntry(i, Float.parseFloat(GlobalVariables.homeGraph.getTotalclosePending())));
                values3.add(new BarEntry(i, Float.parseFloat(GlobalVariables.homeGraph.getTotalcloseRejected())));
            }
        }

        BarDataSet set1 = new BarDataSet(values1, GROUP_1_LABEL);
        BarDataSet set2 = new BarDataSet(values2, GROUP_2_LABEL);
        BarDataSet set3 = new BarDataSet(values3, GROUP_3_LABEL);

        set1.setColor(ColorTemplate.MATERIAL_COLORS[0]);
        set2.setColor(ColorTemplate.MATERIAL_COLORS[1]);
        set3.setColor(ColorTemplate.MATERIAL_COLORS[2]);
        set1.getValueTextColor(R.color.white);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);

        BarData data = new BarData(dataSets);

        return data;
    }

    private void prepareChartData(BarData data) {
        chart.setData(data);

        chart.getBarData().setBarWidth(BAR_WIDTH);

        float groupSpace = 1f - ((BAR_SPACE + BAR_WIDTH) * GROUPS);
        chart.groupBars(0, groupSpace, BAR_SPACE);

        chart.invalidate();

    }


    public static class Util {
        public float randomFloatBetween(float min, float max) {
            Random r = new Random();
            float random = min + r.nextFloat() * (max - min);
            return random;
        }
    }

    public void getHomeData(){

        method.loadingDialogg(requireActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_GET_HOMEDATA,
            response -> {
                // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject(GlobalVariables.AppSid);


                    String success = jsonObject1.getString("success");

                    if (success.equals("1")) {
                        JSONObject jsonArray2 = jsonObject1.getJSONObject("Results");


                        JSONArray jsonArray_homeGraph = jsonArray2.getJSONArray("homeGraph");

                        Log.d(TAG, "getHomeData: hbvh"+homeDataDTO);
                      //  globalState.setHomeData(homeDataDTO);

                        for (int i = 0; i < jsonArray_homeGraph.length(); i++) {

                            JSONObject object = jsonArray_homeGraph.getJSONObject(i);

                            String  totalActiveUser=object.getString("totalActiveUser");
                            String  totaldeActiveUser=object.getString("totaldeActiveUser");
                            String  totalBlockedUser=object.getString("totalBlockedUser");
                            String  totalUser=object.getString("totalUser");
                            String  totalD=object.getString("totalD");
                            String  totalDPending=object.getString("totalDPending");
                            String  totalDVerified=object.getString("totalDVerified");
                            String  totalDRejected=object.getString("totalDRejected");
                            String totalcID=object.getString("totalcID");
                            String  totalcIDPending=object.getString("totalcIDPending");
                            String  totalcIDVerified=object.getString("totalcIDVerified");
                            String  totalcIDRejected=object.getString("totalcIDRejected");
                            String totalw=object.getString("totalw");
                            String  totalwPending=object.getString("totalwPending");
                            String  totalwVerified=object.getString("totalwVerified");
                            String  totalwRejected=object.getString("totalwRejected");
                            String totalClose=object.getString("totalClose");
                            String  totalclosePending=object.getString("totalclosePending");
                            String  totalcloseVerified=object.getString("totalcloseVerified");
                            String  totalcloseRejected=object.getString("totalcloseRejected");
                            String  totalADeposited=object.getString("totalADeposited");
                            String  totalAWithdrawals=object.getString("totalAWithdrawals");




                            HomeGraphDTO homegrph = new HomeGraphDTO( totalActiveUser, totaldeActiveUser, totalBlockedUser,  totalUser
                                    ,totalD,totalDPending,  totalDVerified,
                                    totalDRejected,totalcID,  totalcIDPending,  totalcIDVerified,  totalcIDRejected,
                                    totalw,totalwPending,  totalwVerified,  totalwRejected,totalClose,
                                    totalclosePending,  totalcloseVerified,  totalcloseRejected,totalADeposited,totalAWithdrawals);

                            GlobalVariables.homeGraph = homegrph;

                        }

                       // prefrence.putListString("homeGraph", object);
                        JSONArray jsonArray_adminData = jsonArray2.getJSONArray("adMinProf");


                        for (int i = 0; i < jsonArray_adminData.length(); i++) {
                            JSONObject object = jsonArray_adminData.getJSONObject(i);


                                String id = object.getString("id");
                                String admin_user_id = object.getString("admin_user_id");
                                String admin_refer_code = object.getString("admin_refer_code");
                                String admin_password = object.getString("admin_password");
                                String admin_name = object.getString("admin_name");
                                String admin_email = object.getString("admin_email");
                                String admin_mobile = object.getString("admin_mobile");
                                String profileImg = object.getString("profileImg");
                                String deviceToken = object.getString("device_token");
                                String admin_status = object.getString("admin_status");
                                String manageUserList = object.getString("manageUserList");
                                String manageDepositList = object.getString("manageDepositList");
                                String manageCreateIDList = object.getString("manageCreateIDList");
                                String manageWithdrawal = object.getString("manageWithdrawal");
                                String managePassword = object.getString("managePassword");
                                String manageCloseID = object.getString("manageCloseID");
                                String manageNotification = object.getString("manageNotification");
                                String manageAppSettings = object.getString("manageAppSettings");
                                String manageAdminList = object.getString("manageAdminList");
                                // String admin_login_date = object.getString("admin_login_date");
                                String admin_login_date = (Method.convertTimestampDateToTime(object.getString("admin_login_date")));
                                // String admin_joining_date = object.getString("admin_joining_date");
                                String admin_joining_date = (Method.convertTimestampDateToTime(object.getString("admin_joining_date")));


                                //constant.sharedEditor.putBoolean(constant.isLogin, true);

                                Profileuser profileuser = new Profileuser(id, admin_user_id, admin_refer_code, admin_password, admin_name, admin_email, admin_mobile, profileImg,
                                        deviceToken, admin_status,
                                        manageUserList, manageDepositList, manageCreateIDList, manageWithdrawal, managePassword, manageCloseID,
                                        manageNotification, manageAppSettings, manageAdminList, admin_login_date, admin_joining_date);

                                String appName = object.getString("app_name");
                                String appLogo = object.getString("app_logo");
                                String appDescription = object.getString("app_description");
                                String appVersion = object.getString("app_version");
                                String appAuthor = object.getString("app_author");
                                String appContact = object.getString("app_contact");
                                String appEmail = object.getString("app_email");
                                String appWebsite = object.getString("app_website");
                                String appDevelopedBy = object.getString("app_developed_by");
                                String redeemCurrency = object.getString("redeem_currency");
                                String homeBannerimg1Enabled = object.getString("bannerimg1_enabled");
                                String homeBannerimg1 = object.getString("home_bannerimg1");
                                String homeBannerimg2Enabled = object.getString("home_bannerimg2_enabled");
                                String homeBannerimg2 = object.getString("home_bannerimg2");
                                String homeBannerimg3 = object.getString("home_bannerimg3");
                                String onesignalappId = object.getString("onesignalapp_id");
                                String onesignalappKey = object.getString("onesignalapp_key");
                                String referTxt = object.getString("refer_txt");
                                String adminUpiName = object.getString("adminUpiName");
                                String upiMobileNo = object.getString("upiMobileNo");
                                String adminUpi = object.getString("adminUpi");
                                String adminPaytmName = object.getString("adminPaytmName");
                                String adminPaytmNo = object.getString("adminPaytmNo");
                                String adminGpayName = object.getString("adminGpayName");
                                String adminGpaymobileNo = object.getString("adminGpaymobileNo");
                                String adminGpay = object.getString("adminGpay");
                                String adminAccountName = object.getString("adminAccountName");
                                String adminAccountNo = object.getString("adminAccountNo");
                                String adminAccountIfsc = object.getString("adminAccountIfsc");
                                String adminAccountType = object.getString("adminAccountType");
                                String image = object.getString("image");
                                String joiningBonus = object.getString("joining_bonus");
                                String perRefer = object.getString("per_refer");
                                String minDepositcoin = object.getString("minDepositcoin");
                                String hourlyQuizCoin = object.getString("hourly_quiz_coin");
                                String mathsQuizCoin = object.getString("maths_quiz_coin");
                                String maxmMathsQuestn = object.getString("maxm_maths_questn");
                                String hourlySpinLimit = object.getString("hourly_spin_limit");
                                String hourlyMathsquizLimit = object.getString("hourly_mathsquiz_limit");
                                String mathsQuizUnlockMin = object.getString("mathsQuiz_unlockMin");
                                String perNewsCoin = object.getString("per_news_coin");
                                String minimumWidthrawal = object.getString("minimum_widthrawal");
                                String minRedeemAmount = object.getString("min_redeem_amount");
                                String telegramlink = object.getString("telegramlink");
                                String youtubeLink = object.getString("youtube_link");
                                String facebookPage = object.getString("facebook_page");
                                String newVersion = object.getString("new_version");
                                String updateLink = object.getString("update_link");
                                String adminMsg = object.getString("admin_msg");
                                String joinGroup = object.getString("join_group");
                                String appPromo1 = object.getString("app_promo1");
                                String appPromo2 = object.getString("app_promo2");
                                // String date = object.getString("date");
                                String date = (Method.convertTimestampDateToTime(object.getString("date")));
                                String payment_gateway = object.getString("payment_gateway");
                                String offline_paymentGateway = object.getString("offline_paymentGateway");
                                String paytm_mid = object.getString("paytm_mid");
                                String paytm_Key = object.getString("paytm_key");
                                String razorpay_mid = object.getString("razorpay_mid");
                                String razorpay_key = object.getString("razorpay_key");
                                String payumoney_mid = object.getString("payumoney_mid");
                                String payumoney_key = object.getString("payumoney_key");
                                String payumoney_salt = object.getString("payumoney_salt");


                                Settings settings = new Settings(appName, appLogo, appDescription, appVersion, appAuthor, appContact,
                                        appEmail, appWebsite, appDevelopedBy, redeemCurrency, homeBannerimg1Enabled, homeBannerimg1,
                                        homeBannerimg2Enabled, homeBannerimg2, homeBannerimg3, onesignalappId, onesignalappKey, referTxt,
                                        adminUpiName, upiMobileNo, adminUpi, adminPaytmName, adminPaytmNo, adminGpayName, adminGpaymobileNo,
                                        adminGpay, adminAccountName, adminAccountNo, adminAccountIfsc, adminAccountType,
                                        image, joiningBonus, perRefer, minDepositcoin, hourlyQuizCoin, mathsQuizCoin, maxmMathsQuestn,
                                        hourlySpinLimit, hourlyMathsquizLimit, mathsQuizUnlockMin, perNewsCoin, minimumWidthrawal,
                                        minRedeemAmount, telegramlink, youtubeLink, facebookPage, newVersion, updateLink, adminMsg,
                                        joinGroup, appPromo1, appPromo2, date, payment_gateway, offline_paymentGateway, paytm_mid, paytm_Key,
                                        razorpay_mid, razorpay_key, payumoney_mid, payumoney_key, payumoney_salt);

                                GlobalVariables.profileuser = profileuser;
                                GlobalVariables.settings = settings;


                            }


                        JSONArray jsonArray_hometopUser = jsonArray2.getJSONArray("topUsers");
                        for (int i = 0; i < jsonArray_hometopUser.length(); i++) {

                            JSONObject object = jsonArray_hometopUser.getJSONObject(i);
                            String id =object.getString("id");
                            String mobile =object.getString("mobile");
                            String password =object.getString("password");
                            String name =object.getString("name");
                            String city =object.getString("city");
                            String email =object.getString("email");
                            String wallet =object.getString("wallet");
                            String totalPaid =object.getString("total_paid");
                            String totalRedeemed =object.getString("total_redeemed");
                            String userReferalCode =object.getString("user_referal_code");
                            String refferedBy =object.getString("reffered_by");
                            String refferedPaid =object.getString("reffered_paid");
                            String totalReferals =object.getString("total_referals");
                            String allow =object.getString("allow");
                            String deviceId =object.getString("device_id");
                            String deviceToken =object.getString("device_token");
                            String profilePic =object.getString("profile_pic");
                            // String activeDate =object.getString("active_date");

                            String activeDate = Method.convertTimestampDateToTime(Method.correctTimestamp(object.getString("active_date")));
                           // String activeDate = Method.convertTimestampDateToTime(object.getString("active_date"));
                          /*  Log.d(TAG, "getHomeData:activeDate "+(object.getString("active_date"))+"\n"+activeDate);
                            Log.d(TAG, "getHomeData:activeDate "+Method.convertTimestampDateToTime(Method.correctTimestamp(object.getString("joining_time"))));*/


                            String onesignalPlayerid =object.getString("onesignal_playerid");
                            String onesignalPushtoken =object.getString("onesignal_pushtoken");
                            // String joiningTime =object.getString("joining_time");
                            String joiningTime=  Method.convertTimestampDateToTime(Method.correctTimestamp(object.getString("joining_time")));



                            HomeTopUserDTO homeTopUserDTO = new HomeTopUserDTO( id,  mobile,  password,  name,  city,  email,  wallet,  totalPaid,  totalRedeemed,
                                    userReferalCode,  refferedBy,  refferedPaid,  totalReferals,  allow,  deviceId,  deviceToken,  profilePic,  activeDate,
                                    onesignalPlayerid,  onesignalPushtoken,  joiningTime);
                           // editor.apply();
                            homeTopUserDTOArrayList.add(homeTopUserDTO);
                            MyrecyclerView = binding.rvTopUser;
                            MyrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                            MyrecyclerView.setLayoutManager(linearLayoutManager);
                            topUserDataAdapter= new TopUserDataAdapter(homeTopUserDTOArrayList,getContext());
                            MyrecyclerView.setAdapter(topUserDataAdapter);


                        }
                        JSONArray jsonArray_hometopDeposit = jsonArray2.getJSONArray("topDeposit");
                        for (int i = 0; i < jsonArray_hometopDeposit.length(); i++) {

                            JSONObject object2 = jsonArray_hometopDeposit.getJSONObject(i);
                            String id =object2.getString("id");
                            String mobile =object2.getString("mobile");
                            String password =object2.getString("password");
                            String name =object2.getString("name");
                            String city =object2.getString("city");
                            String email =object2.getString("email");
                            String wallet =object2.getString("wallet");
                            String totalPaid =object2.getString("total_paid");
                            String totalRedeemed =object2.getString("total_redeemed");
                            String userReferalCode =object2.getString("user_referal_code");
                            String refferedBy =object2.getString("reffered_by");
                            String refferedPaid =object2.getString("reffered_paid");
                            String totalReferals =object2.getString("total_referals");
                            String allow =object2.getString("allow");
                            String deviceId =object2.getString("device_id");
                            String deviceToken =object2.getString("device_token");
                            String profilePic =object2.getString("profile_pic");
                            //  String activeDate =object2.getString("active_date");
                            String activeDate = (Method.convertTimestampDateToTime(object2.getString("active_date")));

                            String onesignalPlayerid =object2.getString("onesignal_playerid");
                            String onesignalPushtoken =object2.getString("onesignal_pushtoken");
                            // String joiningTime =object2.getString("joining_time");
                            String joiningTime = (Method.convertTimestampDateToTime(object2.getString("joining_time")));

                            String userMobile =object2.getString("userMobile");
                            String txnType =object2.getString("txnType");
                            String orderId =object2.getString("orderId");
                            String amount =object2.getString("amount");
                            String createdId =object2.getString("createdId");
                            String idcreated =object2.getString("Idcreated");
                            String payMentMode =object2.getString("payMentMode");
                            String payMode =object2.getString("payMode");
                            String txnStatus =object2.getString("txnStatus");
                            String idUsername =object2.getString("idUsername");
                            String idPassword =object2.getString("idPassword");
                            String adminComment =object2.getString("adminComment");
                            // String txnDate =object2.getString("txnDate"); Method.convertTimestampDateToTime(method.correctTimestamp(
                            String txnDate = (Method.convertTimestampDateToTime(Method.correctTimestamp(object2.getString("txnDate"))));
                            // String cancelledDate =object2.getString("cancelledDate");
                            String cancelledDate = (Method.convertTimestampDateToTime(Method.correctTimestamp(object2.getString("cancelledDate"))));
                            // String verifiedDate =object2.getString("verifiedDate");
                            String verifiedDate = (Method.convertTimestampDateToTime(Method.correctTimestamp(object2.getString("verifiedDate"))));
                            String img =object2.getString("img");
                            String idWebsite =object2.getString("idWebsite");
                            String idName =object2.getString("idName");
                            String wDetails =object2.getString("wDetails");

                          HomeTopDepositsDTO homeTopDeposits = new HomeTopDepositsDTO( id,  mobile,  password,  name,  city,  email,  wallet,
                          totalPaid,  totalRedeemed,  userReferalCode,  refferedBy,  refferedPaid,  totalReferals,  allow,  deviceId,
                          deviceToken,  profilePic,  activeDate,  onesignalPlayerid,  onesignalPushtoken,  joiningTime,  userMobile,
                          txnType,  orderId,  amount,  createdId,  idcreated,  payMentMode,  payMode,  txnStatus,  idUsername,
                          idPassword,  adminComment,  txnDate,  cancelledDate,  verifiedDate,img,idWebsite,idName,wDetails);

                            homeTopDepositsDTOArrayList.add(homeTopDeposits);
                            recyclerView2 = binding.rvTopDeposit;
                            recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                            recyclerView2.setLayoutManager(linearLayoutManager);
                            topDepositDataAdapter= new TopDepositDataAdapter(homeTopDepositsDTOArrayList,getContext());
                            recyclerView2.setAdapter(topDepositDataAdapter);



                        }
                        setData();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                               // method.loadingDialog.dismiss();
                               // setData();
                            }
                        }, GlobalVariables.dismissAfter);
                    }else{
                        method.loadingDialog.dismiss();

                        Method.alertBox("2","Something Went Wrong","Close & Restart", requireActivity(),"Go to Home");

                    }


                    //loadingDialog.dismiss();

                }catch (JSONException e){
                    e.printStackTrace();
                    //  Toast.makeText(getActivity(), "hello"+e, Toast.LENGTH_LONG).show();
                }
            },
            new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    // Do something when error occurred
                    //Toast.makeText(WalletActivity.this,"Error...!",Toast.LENGTH_SHORT).show();
                    method.loadingDialog.dismiss();
                }
            })
        {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("users_login", "KINGSN");
              //  params.put("mobile",preferences.getString(GlobalVariables.profileuser.getAdmin_mobile(),"") );
                params.put(GlobalVariables.adminUserId, preferences.getValue(GlobalVariables.adminUserId));
                params.put("device_id",method.getDeviceId(requireActivity()));
                Log.d(TAG, "getParams: "+params);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);
        Log.d(TAG, "getHomeData: "+stringRequest);

    }





}