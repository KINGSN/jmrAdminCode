package com.gpuntd.adminapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gpuntd.adminapp.Models.HomeGraphDTO;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.databinding.ActivityCloseIdBinding;
import com.gpuntd.adminapp.databinding.ActivityReportBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ru.slybeaver.slycalendarview.SlyCalendarDialog;
import ru.slybeaver.slycalendarview.listeners.DateSelectListener;

public class ReportActivity extends AppCompatActivity implements SlyCalendarDialog.Callback {
    private static final String TAG ="KINGSN" ;
    ActivityReportBinding binding;
    Bitmap bitmap;
    Toolbar toolbar;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public static Method method;
    Map<String, String> params = new HashMap<>();
    //private SlyCalendarData slyCalendarData;
    String pls="";
    public int d,w,p;
    int pageHeight = 1120;
    int pagewidth = 792;

    // creating a bitmap variable
    // for storing our images
    Bitmap bmp, scaledbmp;

    private static final int PERMISSION_REQUEST_CODE = 200;
    public int pl=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.toolbarReport);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        method = new Method(this);
        params.put("type", "1");
        getData();

      // Spinner spin = (Spinner) findViewById(R.id.selPeriod);
       // binding.selPeriod.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) ReportActivity.this);

     binding.btnGenerateReport.setVisibility(View.GONE);
        binding.btnGenerateReport.setOnClickListener(view -> {

            PdfGenerator.getBuilder().setContext(this)
                    .fromViewIDSource()
                    .fromViewID(R.layout.activity_report, this, R.id.reportLayout)
                    .setFileName("Admin Report")
                    .setFolderName("Gpuntd Document")
                    .openPDFafterGeneration(true)
                    .build(new PdfGeneratorListener() {

                        @Override
                        public void onStartPDFGeneration() {
                            binding.pb.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFinishPDFGeneration() {
                            binding.pb.setVisibility(View.GONE);

                        }
                    });

        });

       // setdata();
        //getData();

    }

    private void getData() {

            method.loadingDialogg(ReportActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_GET_REPORTDATA,
                    response -> {
                        // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                        try {
                            System.out.println(response);
                            // Log.d(TAG, "getHomeData: "+response);
                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject jsonObject1 = jsonObject.getJSONObject(GlobalVariables.AppSid);

                            String success = jsonObject1.getString("success");

                            if (success.equals("1")) {
                                JSONObject jsonArray2 = jsonObject1.getJSONObject("Results");
                                JSONArray jsonArray_homeGraph = jsonArray2.getJSONArray("homeGraph");

                                for (int i = 0; i < jsonArray_homeGraph.length(); i++) {

                                    JSONObject object = jsonArray_homeGraph.getJSONObject(i);
                                    String  totalActiveUser=object.getString("totalActiveUser");
                                    String  totaldeActiveUser=object.getString("totaldeActiveUser");
                                    String  totalBlockedUser=object.getString("totalBlockedUser");
                                    String  totalUser=object.getString("totalUser");
                                    String  registeruser=object.getString("registeruser");
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


                                    method.preferences.setValue("registeruser",registeruser);
                                    GlobalVariables.homeGraph = new HomeGraphDTO( totalActiveUser, totaldeActiveUser, totalBlockedUser,  totalUser
                                            ,totalD,totalDPending,  totalDVerified,
                                            totalDRejected,totalcID,  totalcIDPending,  totalcIDVerified,  totalcIDRejected,
                                            totalw,totalwPending,  totalwVerified,  totalwRejected,totalClose,
                                            totalclosePending,  totalcloseVerified,  totalcloseRejected,totalADeposited,totalAWithdrawals);
                                    editor.apply();
                                    Log.d(TAG, "getHomeData: "+GlobalVariables.homeGraph.getTotalAWithdrawals()+
                                            "totalPending  "+GlobalVariables.homeGraph.getTotalcIDPending());

                                    method.loadingDialog.dismiss();

                            }
                                setdata();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    method.loadingDialog.dismiss();
                                }
                            }, GlobalVariables.dismissAfter);
                        }else{
                            method.loadingDialog.dismiss();

                            Method.alertBox("2","Something Went Wrong","Close & Restart", ReportActivity.this,"Go to Home");

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

            return params;
    }
    };

    RequestQueue requestQueue= Volley.newRequestQueue(ReportActivity.this);
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);

  }


    @SuppressLint("SetTextI18n")
    private void setdata() {
       // Log.d(TAG, "setdata: "+Integer.parseInt(GlobalVariables.homeGraph.getTotalcIDVerified()));
        binding.tvTotalUser.setText(method.preferences.getValue("registeruser"));
      /*  binding.tvTotalDeactiveUser.setText((Integer.parseInt(GlobalVariables.homeGraph.getTotalUser()))
                -(Integer.parseInt(GlobalVariables.homeGraph.getTotalActiveUser())));
*/
        binding.tvTotalDeactiveUser.setText(GlobalVariables.homeGraph.getTotaldeActiveUser());

        binding.tvTotalBlokedUser.setText(GlobalVariables.homeGraph.getTotalBlockedUser());
        binding.tvtotalActiveUser.setText(GlobalVariables.homeGraph.getTotalActiveUser());

      //  Log.d(TAG, "setdata: "+Integer.parseInt(GlobalVariables.homeGraph.getTotalcIDVerified()));

      /*  binding.tvTotalCid.setText((Integer.parseInt(GlobalVariables.homeGraph.getTotalcIDPending()))+
                ( Integer.parseInt(GlobalVariables.homeGraph.getTotalcIDVerified()))+
                (Integer.parseInt(GlobalVariables.homeGraph.getTotalcIDRejected())));*/
        Log.d(TAG, "setdata: "+GlobalVariables.homeGraph.getTotalADeposited());
      if(GlobalVariables.homeGraph.getTotalAWithdrawals().equals("0")&&(GlobalVariables.homeGraph.getTotalADeposited().equals("0"))){
          pl=0;
      }else{
           pl=Integer.parseInt(GlobalVariables.homeGraph.getTotalADeposited())-Integer.parseInt(GlobalVariables.homeGraph.getTotalAWithdrawals());

        }

      if(pl>0){
          pls="Profit of Rs "+(pl);
          binding.tvTotalpl.setTextColor(getResources().getColor(R.color.successColor));
      }else if(pl<0){
           pls="Loss of Rs "+(pl);
          binding.tvTotalpl.setTextColor(getResources().getColor(R.color.errorColor));
      }else{
           pls="No Profit No Loss ";
          binding.tvTotalpl.setTextColor(getResources().getColor(R.color.black_overlay));
      }

        if(pl>0){
            p = (pl / Integer.parseInt(GlobalVariables.homeGraph.getTotalADeposited())) * 100;
        }else p=0;

        Log.d(TAG, "setdata: "+p);
      binding.totalDeposit.setText("Rs "+GlobalVariables.homeGraph.getTotalADeposited());
      binding.totalWithdrawalsp.setText("Rs "+GlobalVariables.homeGraph.getTotalAWithdrawals());
      binding.tvTotalpl.setText(pls);
      binding.tvplPercent.setText(""+p +" %");
       /* myTextView.setText(Html.fromHtml(text + "<font color=white>" + CepVizyon.getPhoneCode() + "</font><br><br>"
                + getText(R.string.currentversion) + CepVizyon.getLicenseText()));
*/    binding.tuser.setText("Total Users : "+GlobalVariables.homeGraph.getTotalUser());

        binding.tvTotalCid.setText(GlobalVariables.homeGraph.getTotalcID());

        binding.tvActiveCid.setText(GlobalVariables.homeGraph.getTotalcIDPending());
        binding.tvVerifiedCid.setText(GlobalVariables.homeGraph.getTotalcIDVerified());
        binding.tvRejCid.setText(GlobalVariables.homeGraph.getTotalcIDRejected());


       /* binding.tvTotalWid.setText((Integer.parseInt(GlobalVariables.homeGraph.getTotalwPending()))+
                (Integer.parseInt(GlobalVariables.homeGraph.getTotalwVerified()))+
                (Integer.parseInt(GlobalVariables.homeGraph.getTotalwRejected())));
*/


        binding.tvTotalWid.setText(GlobalVariables.homeGraph.getTotalw());

        binding.tvTotalPaidWid.setText(GlobalVariables.homeGraph.getTotalwVerified());
        binding.tvTotalPendingWid.setText(GlobalVariables.homeGraph.getTotalwPending());
        binding.tvTotalRejWid.setText(GlobalVariables.homeGraph.getTotalwRejected());


        /*binding.tvTotalChange.setText((Integer.parseInt(GlobalVariables.homeGraph.getTotalclosePending()))+
                (Integer.parseInt(GlobalVariables.homeGraph.getTotalcloseVerified()))+
                (Integer.parseInt(GlobalVariables.homeGraph.getTotalcloseRejected())));*/

        binding.tvTotalChange.setText(GlobalVariables.homeGraph.getTotalClose());

        binding.tvTotalChangePend.setText(GlobalVariables.homeGraph.getTotalclosePending());
        binding.totalChangeApp.setText(GlobalVariables.homeGraph.getTotalcloseVerified());
        binding.tvTotalRej.setText(GlobalVariables.homeGraph.getTotalcloseRejected());







        binding.selPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (binding.selPeriod.getSelectedItemPosition()==0){
                    // Your code here
                    params.put("type", "1");
                    params.put("startDate", "");
                    params.put("endDate", "");
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -1);
                    dateFormat.format(cal.getTime());

                    binding.repPeriod.setText("Report Data On:"+method.ReturnDate(0));
                }else  if (binding.selPeriod.getSelectedItemPosition()==1){
                    // Your code here
                    params.put("type", "2");
                    params.put("startDate", "");
                    params.put("endDate", "");
                    binding.repPeriod.setText("Report Data From:"+method.ReturnDate(1));
                }else  if (binding.selPeriod.getSelectedItemPosition()==2){
                    // Your code here
                    params.put("type", "3");
                    params.put("startDate", "");
                    params.put("endDate", "");
                    binding.repPeriod.setText("Report Data From:"+method.ReturnDate(2));
                }else  if (binding.selPeriod.getSelectedItemPosition()==3){
                    // Your code here
                    params.put("type", "4");
                    params.put("startDate", "");
                    params.put("endDate", "");
                    binding.repPeriod.setText("Report Data From:"+method.ReturnDate(3));
                }else  if (binding.selPeriod.getSelectedItemPosition()==4){
                    // Your code here
                    params.put("type", "5");
                    params.put("startDate", "");
                    params.put("endDate", "");
                    binding.repPeriod.setText("Report Data Up To:"+method.ReturnDate(4));
                }else {

                    new SlyCalendarDialog()
                            .setSingle(false)
                            .setFirstMonday(false)
                            .setCallback(new SlyCalendarDialog.Callback() {
                                @Override
                                public void onCancelled() {

                                }

                                @Override
                                public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
                                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    dateFormat.format(firstDate.getTime());

                                    String simpleStartDate = new SimpleDateFormat(getString(R.string.timeFormat2), Locale.getDefault()).format(firstDate.getTime());
                                    String simpleEndDate = new SimpleDateFormat(getString(R.string.timeFormat2), Locale.getDefault()).format(secondDate.getTime());

                                    try {
                                        Log.d("shubhcode", "timestamp: "+ method.convertDateToTimestamp(simpleStartDate)+"\n"+method.correctTimestamp(method.convertDateToTimestamp(simpleStartDate)));
                                        params.put("type", "6");
                                        params.put("startDate", method.convertDateToTimestamp(simpleStartDate));
                                        params.put("endDate", method.convertDateToTimestamp(simpleEndDate));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                }
                            })
                            .show(getSupportFragmentManager(), "TAG_SLYCALENDAR");


                }
                method.loadingDialog.dismiss();

                getData();

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                method.loadingDialog.dismiss();
                binding.selPeriod.setSelection(0);
                return;
            }
        });
    }


    @Override
    public void onCancelled() {

    }

    @SuppressLint({"StringFormatInvalid", "ShowToast"})
    @Override
    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
        if (firstDate != null) {
            if (secondDate == null) {
                firstDate.set(Calendar.HOUR_OF_DAY, hours);
                firstDate.set(Calendar.MINUTE, minutes);
                Toast.makeText(
                        ReportActivity.this,
                        new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(firstDate.getTime()),
                        Toast.LENGTH_LONG
                   ).show();

                Log.d(TAG, "onDataSelected: "+firstDate);

            } else {
                Toast.makeText(
                        ReportActivity.this,
                        getString(
                                R.string.period,
                                new SimpleDateFormat(getString(R.string.dateFormat), Locale.getDefault()).format(firstDate.getTime()),
                                new SimpleDateFormat(getString(R.string.dateFormat), Locale.getDefault()).format(secondDate.getTime())
                        ),
                        Toast.LENGTH_LONG

                ).show();
                Log.d(TAG, "onDataSelected:KINGSON "+firstDate+"\n  sec "+secondDate);

                Toast.makeText(
                        ReportActivity.this,
                        getString(
                                R.string.period,
                                new SimpleDateFormat(getString(R.string.timeFormat1), Locale.getDefault()).format(firstDate.getTime()),
                                new SimpleDateFormat(getString(R.string.timeFormat1), Locale.getDefault()).format(secondDate.getTime())
                        ),
                        Toast.LENGTH_LONG  ).show();
                String Date1= new SimpleDateFormat(getString(R.string.timeFormat2), Locale.getDefault()).format(firstDate.getTime());
                String Date2= new SimpleDateFormat(getString(R.string.timeFormat2), Locale.getDefault()).format(firstDate.getTime());
                Log.d(TAG, "times1: "+Date1);
                Log.d(TAG, "times2: "+Date2);

                try {
                    Log.d(TAG, "onDataSelected:KINGSON "+ Method.convertDateToTimestamp(Date2)+"\n"+ Method.convertDateToTimestamp(Date2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}