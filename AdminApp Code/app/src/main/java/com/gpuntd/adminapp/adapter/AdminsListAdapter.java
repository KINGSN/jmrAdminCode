package com.gpuntd.adminapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.textfield.TextInputEditText;
import com.gpuntd.adminapp.Models.Profileuser;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.databinding.ItemAdminBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class AdminsListAdapter extends RecyclerView.Adapter<AdminsListAdapter.MyViewHolder> {


    private static final String TAG ="KINGSN" ;
    public ArrayList<Profileuser> topDepositsDTOS;
    public static Profileuser myIdData;
    @SuppressLint("StaticFieldLeak")
    public static  Context context;
     ItemAdminBinding binding;
    public static Method method;
    public static   Profileuser profileuser;
   LayoutInflater layoutInflater;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public static TextInputEditText etEditAdminName,etEditAdminEmail,etEditAdminPhone,etEditAdminUsername,etEditAdminPassword,
            etEditCreateIdPass,etEditDatePassReq;
    public static String AStatus="1";
    public static String AStatus1="0";
    public static String AStatus2="0";
    public static String AStatus3="0";
    public static String AStatus4="0";
    public static String AStatus5="0";
    public static String AStatus6="0";
    public static String AStatus7="0";
    public static String AStatus8="0";
    public static String AStatus9="0";
    public static String id="";
    public AdminsListAdapter(List<Profileuser> topDepositsDTOS, Context context) {
        this.topDepositsDTOS = (ArrayList<Profileuser>) topDepositsDTOS;
        AdminsListAdapter.context = context;
    }

    @SuppressLint("CommitPrefEdits")
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Log.i(TAG, "onCreateViewHolder: " + count++);
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        method=new Method(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_admin, parent, false);
        method=new Method(parent.getContext());
        preferences = parent.getContext().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        return new MyViewHolder(binding);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        profileuser = topDepositsDTOS.get(position);
        holder.setIsRecyclable(false);

        holder.binding.tvAdminName.setText(profileuser.getAdmin_name());
        holder.binding.tvAdminPhone.setText(profileuser.getAdmin_mobile());
        holder.binding.tvAdminUserId.setText(profileuser.getAdmin_user_id());
        holder.binding.tvRegisterdate.setText(profileuser.getAdmin_joining_date());
        holder.binding.tvActivedate.setText(profileuser.getAdmin_login_date());

        if(profileuser.getId().equals("1")){
            holder.binding.disableBtn.setVisibility(View.GONE);
            holder.binding.btnEditUser.setVisibility(View.GONE);
        }


        holder.binding.disableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=topDepositsDTOS.get(position).getId();
                updateonStatusClick();
            }
        });


        switch (profileuser.getAdmin_status()) {
            case "1":
                // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.binding.tvAdminStatus.setText("Verified");
                holder.binding.tvAdminStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.flipsucc));


                break;
            case "0":
                // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.binding.tvAdminStatus.setText("Pending");
                holder.binding.tvAdminStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.warningColor));

                break;
            case "2":
                // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.binding.tvAdminStatus.setText("Disabled");
                holder.binding.tvAdminStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.errorColor));

                break;

        }


            holder.binding.btnEditUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  Log.d(TAG, "onClick: "+topDepositsDTOS.get(position).getPayMentMode()+"\n"+topDepositsDTOS.get(position).getName());
                    myIdData=topDepositsDTOS.get(position);
                    AdminsListAdapter.setupUpdateDialog(v);
                }
            });

        if(!profileuser.getProfileImg().equals("")) {
            Log.d("KINGSN", "onBindViewHolder: "+profileuser.getProfileImg());
            Glide.with(context)
                    .load(profileuser.getProfileImg())
                    .placeholder(R.drawable.round_bg)
                    .dontAnimate()
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.binding.AdminImage);
        }else{
            Glide.with(context)
                    .load(R.mipmap.ic_launcher_round)
                    .placeholder(R.drawable.round_bg)
                    .dontAnimate()
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.binding.AdminImage);

        }


    }



    public void open( String url) {
        Uri uri = Uri.parse("googlechrome://navigate?url=" + url);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        if (i.resolveActivity(context.getPackageManager()) == null) {
            i.setData(Uri.parse(url));
        }
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return topDepositsDTOS.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ItemAdminBinding binding;

        public MyViewHolder(ItemAdminBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    public static void setupUpdateDialog(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_admin, v.findViewById(R.id.editAdminContainer));
        builder.setView(view);
         AlertDialog alertDialog = builder.create();

        etEditAdminName = view.findViewById(R.id.etEditAdminName);
        etEditAdminEmail = view.findViewById(R.id.etEditAdminEmail);
        etEditAdminPhone = view.findViewById(R.id.etEditAdminPhone);
        etEditAdminUsername = view.findViewById(R.id.etEditAdminUsername);
        etEditAdminPassword = view.findViewById(R.id.etEditAdminPassword);
         ImageView close=view.findViewById(R.id.btnCloseDialog);
        Switch sw = view.findViewById(R.id.switch1);
        Spinner spinnerAdminStatus=view.findViewById(R.id.spinnerAdminStatus);
        Spinner spinnerUserDepositAccess=view.findViewById(R.id.spinnerUserDepositAccess);
        Spinner spinnerUserDetailsAccess=view.findViewById(R.id.spinnerUserDetailsAccess);
        Spinner spinnerPasswordRequest=view.findViewById(R.id.spinnerPasswordRequest);
        Spinner spinnerCreateIDAccess=view.findViewById(R.id.spinnerCreateIDAccess);
         Spinner spinnerCloseIDAccess=view.findViewById(R.id.spinnerCloseIDAccess);
        Spinner spinnerWithdrawalAccess=view.findViewById(R.id.spinnerWithdrawalAccess);
        Spinner spinnerSendNotification=view.findViewById(R.id.spinnerSendNotification);
        Spinner spinnerAppSettings=view.findViewById(R.id.spinnerAppSettings);
        Spinner spinnerAdminList=view.findViewById(R.id.spinnerAdminList);


        Log.d(TAG, "onpopup: "+myIdData.getAdmin_name());

        etEditAdminName.setText(myIdData.getAdmin_name());
        etEditAdminEmail.setText(myIdData.getAdmin_email());
        etEditAdminPhone.setText(myIdData.getAdmin_mobile());
        etEditAdminUsername.setText(myIdData.getAdmin_user_id());
        etEditAdminPassword.setText(myIdData.getAdmin_password());
        Log.d(TAG, "onpopup:pass "+myIdData.getAdmin_user_id());

        if(myIdData.getAdmin_status().equals("1")){

            AStatus="1";
            spinnerAdminStatus.setSelection(0);
        }else if(myIdData.getAdmin_status().equals("2")){

            AStatus="2";
            spinnerAdminStatus.setSelection(1);
        }else {

            spinnerAdminStatus.setSelection(1);
        }
        Log.d(TAG, "onpopup:pass2 "+myIdData.getAdmin_user_id());



        if(myIdData.getManageDepositList().equals("0")){

            AStatus1="0";
            spinnerUserDepositAccess.setSelection(0);
        }else if(myIdData.getManageDepositList().equals("2")){

            AStatus1="2";
            spinnerUserDepositAccess.setSelection(2);
        }else {
            spinnerUserDepositAccess.setSelection(0);
            AStatus1="0";
        }

        if(myIdData.getManageUserList().equals("2")){

            AStatus2="2";
            spinnerUserDetailsAccess.setSelection(2);
        }else if(myIdData.getManageUserList().equals("1")){

            AStatus2="1";
            spinnerUserDetailsAccess.setSelection(1);
        }else {
            AStatus2="0";
            spinnerUserDetailsAccess.setSelection(0);
        }

        if(myIdData.getManagePassword().equals("2")){

            AStatus3="2";
            spinnerPasswordRequest.setSelection(2);
        }else if(myIdData.getManagePassword().equals("1")){

            AStatus3="1";
            spinnerPasswordRequest.setSelection(1);
        }else {
            AStatus3="0";
            spinnerPasswordRequest.setSelection(0);
        }

        if(myIdData.getManageCreateIDList().equals("2")){

            AStatus4="2";
            spinnerCreateIDAccess.setSelection(2);
        }else if(myIdData.getManageCreateIDList().equals("1")){

            AStatus4="1";
            spinnerCreateIDAccess.setSelection(1);
        }else {
            AStatus4="0";
            spinnerCreateIDAccess.setSelection(0);
        }

        if(myIdData.getManageCloseID().equals("2")){

            AStatus5="2";
            spinnerCloseIDAccess.setSelection(2);
        }else if(myIdData.getManageCloseID().equals("1")){

            AStatus5="2";
            spinnerCloseIDAccess.setSelection(2);
        }else {
            AStatus5="0";
            spinnerCloseIDAccess.setSelection(0);
        }


        if(myIdData.getManageWithdrawal().equals("2")){
            AStatus6="2";
            spinnerWithdrawalAccess.setSelection(2);
        }else if(myIdData.getManageWithdrawal().equals("1")){
            AStatus6="2";
            spinnerWithdrawalAccess.setSelection(1);
        }else {
            AStatus6="0";
            spinnerWithdrawalAccess.setSelection(0);
        }

        if(myIdData.getManageNotification().equals("2")){
            AStatus7="2";
            spinnerSendNotification.setSelection(2);
        }else if(myIdData.getManageNotification().equals("1")){
            AStatus7="1";
            spinnerSendNotification.setSelection(1);
        }else {
            AStatus7="0";
            spinnerSendNotification.setSelection(0);
        }



        if(myIdData.getManageAppSettings().equals("2")){
            AStatus8="2";
            spinnerAppSettings.setSelection(2);
        }else if(myIdData.getManageAppSettings().equals("1")){
            AStatus8="1";
            spinnerAppSettings.setSelection(1);
        }else {
            AStatus8="1";
            spinnerAppSettings.setSelection(0);
        }

        if(myIdData.getManageAdminList().equals("2")){
            AStatus9="2";
            spinnerAdminList.setSelection(2);
        }else if(myIdData.getManageAdminList().equals("1")){
            AStatus9="1";
            spinnerAdminList.setSelection(1);
        }else {
            AStatus9="0";
            spinnerAdminList.setSelection(0);
        }






        spinnerAdminStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinnerAdminStatus.getSelectedItemPosition()==1){
                    AStatus="1";
                }else if(spinnerAdminStatus.getSelectedItemPosition()==2){
                    AStatus="2";
                }{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AStatus= String.valueOf(0);
            }
        });

        spinnerUserDepositAccess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinnerUserDepositAccess.getSelectedItemPosition()==1){
                    AStatus1= String.valueOf(1);
                }else if(spinnerUserDepositAccess.getSelectedItemPosition()==2){
                    AStatus1= String.valueOf(2);
                }else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AStatus1= String.valueOf(0);
            }
        });

        spinnerUserDetailsAccess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinnerUserDetailsAccess.getSelectedItemPosition()==1){
                    AStatus2= String.valueOf(1);
                }else  if(spinnerUserDetailsAccess.getSelectedItemPosition()==2){
                    AStatus2= String.valueOf(2);
                }{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AStatus2= String.valueOf(0);
            }
        });

        spinnerPasswordRequest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinnerPasswordRequest.getSelectedItemPosition()==1){
                    AStatus3= String.valueOf(1);
                }else if(spinnerPasswordRequest.getSelectedItemPosition()==2){
                    AStatus3= String.valueOf(2);
                }{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AStatus3= String.valueOf(0);
            }
        });

        spinnerCreateIDAccess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinnerCreateIDAccess.getSelectedItemPosition()==1){
                    AStatus4= String.valueOf(1);
                }else if(spinnerCreateIDAccess.getSelectedItemPosition()==2){
                    AStatus4= String.valueOf(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AStatus4= String.valueOf(0);
            }
        });

        spinnerCloseIDAccess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinnerCloseIDAccess.getSelectedItemPosition()==1){
                    AStatus5= String.valueOf(1);
                }else if(spinnerCloseIDAccess.getSelectedItemPosition()==2){
                    AStatus5= String.valueOf(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AStatus5= String.valueOf(0);
            }
        });

        spinnerWithdrawalAccess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinnerWithdrawalAccess.getSelectedItemPosition()==1){
                    AStatus6= String.valueOf(1);
                }else if(spinnerWithdrawalAccess.getSelectedItemPosition()==2){
                    AStatus6= String.valueOf(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AStatus6= String.valueOf(0);
            }
        });

        spinnerSendNotification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinnerSendNotification.getSelectedItemPosition()==1){
                    AStatus7= String.valueOf(1);
                }else
                    if(spinnerSendNotification.getSelectedItemPosition()==2){
                        AStatus7= String.valueOf(2);
                    }else{
                        AStatus7= String.valueOf(0);
                    }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AStatus7= String.valueOf(0);
            }
        });

        spinnerAppSettings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinnerAppSettings.getSelectedItemPosition()==1){
                    AStatus8="1";
                }else if(spinnerAppSettings.getSelectedItemPosition()==2){
                    AStatus8= String.valueOf(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AStatus8= String.valueOf(0);
            }
        });

        spinnerAdminList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinnerAdminList.getSelectedItemPosition()==1){
                    AStatus9="1";
                }else if(spinnerAdminList.getSelectedItemPosition()==2){
                    AStatus9= String.valueOf(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AStatus9= String.valueOf(0);
            }
        });


        Log.d(TAG, "onpopup:pass3 "+myIdData.getAdmin_user_id());

        view.findViewById(R.id.btnSaveAdmin).setOnClickListener(view1 -> {
            if(Objects.requireNonNull(etEditAdminName.getText()).toString().equals("")){
                etEditAdminName.setError("Field Is Required");
            }else if(Objects.requireNonNull(etEditAdminEmail.getText()).toString().equals("")){
                etEditAdminEmail.setError("Field Is Required");
            }else if(Objects.requireNonNull(etEditAdminPhone.getText()).toString().equals("")){
                etEditAdminPhone.setError("Field Is Required");
            }else if(Objects.requireNonNull(etEditAdminUsername.getText()).toString().equals("")){
                etEditAdminUsername.setError("Field Is Required");
            }else if(Objects.requireNonNull(etEditAdminPassword.getText()).toString().equals("")){
                etEditAdminPassword.setError("Field Is Required");
            }else {
                alertDialog.dismiss();
                id=myIdData.getId();
                updateonClick();

            }

            alertDialog.dismiss();

        });



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });



        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        Log.d(TAG, "onpopup:pass4 "+myIdData.getAdmin_user_id());
        alertDialog.show();




    }



    private static void updateonClick() {
             method.params.clear();
          method.params.put("id",id);
           method.params.put("adminRoleUpdate","adminRoleUpdate");
            method.params.put("adminName", Objects.requireNonNull(etEditAdminName.getText()).toString());
            method.params.put("adminEmail",Objects.requireNonNull(etEditAdminEmail.getText()).toString());
            method.params.put("adminMobile",Objects.requireNonNull(etEditAdminPhone.getText()).toString());
           method.params.put("adminUserid", Objects.requireNonNull(etEditAdminUsername.getText()).toString());
           method.params.put("adminPassword", Objects.requireNonNull(etEditAdminPassword.getText()).toString());
          method.params.put("admin_status", AStatus);
          method.params.put("manageDepositList",AStatus1);
        method.params.put("manageUserList",(AStatus2));
        method.params.put("managePassword", AStatus3);
        method.params.put("manageCreateIDList",AStatus4);
        method.params.put("manageCloseID",AStatus5);
        method.params.put("manageWithdrawal",AStatus6);
        method.params.put("manageNotification",AStatus7);
        method.params.put("manageAppSettings", AStatus8);
        method.params.put("manageAdminList", AStatus9);


        method.updateSetting((Activity) context, RestAPI.USER_UPDATE);


    }

    private static void updateonStatusClick() {
        method.params.clear();
        method.params.put("id",id);
        method.params.put("adminRoleUpdate", "adminRoleUpdate");
        method.params.put("admin_status", String.valueOf(2));
        method.params.put("adminName", profileuser.getAdmin_name());
        method.params.put("adminEmail",profileuser.getAdmin_email());
        method.params.put("adminMobile",profileuser.getAdmin_mobile());
        method.params.put("adminUserid", profileuser.getAdmin_user_id());
        method.params.put("adminPassword",  profileuser.getAdmin_password());
        method.params.put("manageDepositList",AStatus1);
        method.params.put("manageUserList",(AStatus2));
        method.params.put("managePassword", AStatus3);
        method.params.put("manageCreateIDList",AStatus4);
        method.params.put("manageCloseID",AStatus5);
        method.params.put("manageWithdrawal",AStatus6);
        method.params.put("manageNotification",AStatus7);
        method.params.put("manageAppSettings", AStatus8);
        method.params.put("manageAdminList", AStatus9);

        method.updateSetting((Activity) context, RestAPI.USER_UPDATE);

    }

}
