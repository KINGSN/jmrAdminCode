package com.gpuntd.adminapp.adapter;
/**
 * Created by VARUN on 01/01/19.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.textfield.TextInputEditText;
import com.gpuntd.adminapp.Models.My_ID_Data;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.databinding.AdapterManageCreatedIdBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class ManageIDAdapter extends RecyclerView.Adapter<ManageIDAdapter.MyViewHolder> {
    private static final String TAG ="KINGSN";
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    public ArrayList<My_ID_Data> myId_data;
    public static My_ID_Data myIdData;
    AdapterManageCreatedIdBinding binding;
    LayoutInflater layoutInflater;
    @SuppressLint("StaticFieldLeak")
    public static  Method method;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public static TextInputEditText etEditCreateIdUsername,etEditCreateIdPass,etEditCreatedIdBalance,etEditCreatedIdTotalDepo
            ,etEditCreatedIdWithdrawal
            ,etEditCreatedDate;
    public static String AStatus="1";

    public ManageIDAdapter(List<My_ID_Data> myId_data, Context context) {
        mContext = context;
        this.myId_data = (ArrayList<My_ID_Data>) myId_data;
        CreateDataAdapter.context = context;
    }

    @SuppressLint("CommitPrefEdits")
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_manage_created_id, parent, false);
        method=new Method(parent.getContext());
        preferences = parent.getContext().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
         myIdData = myId_data.get(position);
        Log.d(TAG, "onBindViewHolder: "+myIdData.getIdUsername());
        holder.setIsRecyclable(false);

        holder.binding.tvCreatedIdTitle.setText(myId_data.get(position).getIdName());
        holder.binding.tvCreatedIdWebsite.setText(myId_data.get(position).getIdWebsite());
        holder.binding.orderId.setText(myId_data.get(position).getCorderId());
        holder.binding.tvMobile.setText(myId_data.get(position).getUserMobile());
        holder.binding.appUserName.setText(myId_data.get(position).getIdUsername());
        holder.binding.idPassword.setText(myId_data.get(position).getIdPassword());
        holder.binding.idPassword.setText(myId_data.get(position).getIdPassword());


        try {
            holder.binding.tvCreatedIdTimestamp.setText(myId_data.get(position).getIdCreatedDate());
           // holder.binding.time.setText(method.convertTimestampDateToTime(method.correctTimestamp(Long.parseLong(chatList.get(position).getSend_at()))));


        } catch (Exception e) {
            e.printStackTrace();
        }
        Glide.with(mContext).
                load(myId_data.get(position).getImg())
                .placeholder(R.drawable.dummyuser_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.binding.idImage);

        holder.binding.btnEditCreatedId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myIdData=myId_data.get(position);
                ManageIDAdapter.setupUpdateDialog(v);
            }
        });

        if (myIdData.getIdStatus().equals("1")) {
            if(myIdData.getIdApproval().equals("4")){
                holder.binding.tvCreatedIdUserStatus.setText("NEEDS UPDATION");
                holder.binding.tvCreatedIdTimestamp.setText(myId_data.get(position).getIdCreatedDate());
                holder.binding.tvCreatedIdUserStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.warningColor));
                holder.binding.lverify.setVisibility(View.GONE);
            }else  if(myIdData.getIdApproval().equals("2")){
                holder.binding.tvCreatedIdUserStatus.setText("ID REJECTED");
                holder.binding.tvCreatedIdUserStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.errorColor));
                holder.binding.vorcan.setText("Cancelled Date :");
                holder.binding.lverify.setVisibility(View.VISIBLE);
                holder.binding.tvVerifiedTimestamp.setText(myId_data.get(position).getIdCancelledDate());

            }else  if(myIdData.getIdApproval().equals("1")){
                holder.binding.tvCreatedIdUserStatus.setText("ID CREATED");
                holder.binding.tvCreatedIdUserStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.flipsucc));
                holder.binding.vorcan.setText("Verified Date :");
                holder.binding.lverify.setVisibility(View.VISIBLE);
                holder.binding.tvVerifiedTimestamp.setText(myId_data.get(position).getIdVerifiedDate());
            }
            // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));



        }else  if (myIdData.getIdStatus().equals("0")) {
            // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.binding.tvCreatedIdUserStatus.setText("PENDING");
            holder.binding.tvCreatedIdUserStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.warningColor));

        }else if (myIdData.getIdStatus().equals("2")) {
            // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.binding.tvCreatedIdUserStatus.setText("CANCELLED");
            holder.binding.tvCreatedIdUserStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.errorColor)) ;


        }else if (myIdData.getIdStatus().equals("4")) {
            // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.binding.tvCreatedIdUserStatus.setText("PROCESSING");
            holder.binding.tvCreatedIdUserStatus.setBackgroundColor(ContextCompat.getColor(mContext, R.color.browser_actions_bg_grey)) ;

        }

    }

    @Override
    public int getItemCount() {

        return myId_data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        AdapterManageCreatedIdBinding binding;

        public MyViewHolder(AdapterManageCreatedIdBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static void setupUpdateDialog(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_edit_created_id, v.findViewById(R.id.editcrId));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        etEditCreateIdUsername = view.findViewById(R.id.etEditCreateIdUsername);
        etEditCreateIdPass = view.findViewById(R.id.etEditCreateIdPass);
        etEditCreatedIdBalance = view.findViewById(R.id.etEditCreatedIdBalance);
        etEditCreatedIdBalance = view.findViewById(R.id.etEditCreatedIdBalance);
        etEditCreatedIdTotalDepo = view.findViewById(R.id.etEditCreatedIdTotalDepo);
        etEditCreatedIdWithdrawal = view.findViewById(R.id.etEditCreatedIdWithdrawal);
        etEditCreatedDate = view.findViewById(R.id.etEditCreatedDate);
        Button btnSaveCreatedId=view.findViewById(R.id.btnSaveCreatedId);
        ImageView close=view.findViewById(R.id.btnCloseDialog);
        ImageView idImage=view.findViewById(R.id.idImage);
        Switch sw = view.findViewById(R.id.switch1);


            sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // The toggle is enabled
                        AStatus="1";
                    } else {
                        // The toggle is disabled
                        AStatus="2";
                    }
                }
            });
            Log.d(TAG, "onpopup: "+myIdData.getIdUsername());
            etEditCreateIdUsername.setText(myIdData.getIdUsername());
            etEditCreatedIdBalance.setText(myIdData.getIdBalance());
            etEditCreateIdPass.setText(myIdData.getIdPassword());
            etEditCreatedIdTotalDepo.setText(myIdData.getIdTotalDeposited());
            etEditCreatedIdWithdrawal.setText(myIdData.getIdTotalWithdrawals());
            etEditCreatedDate.setText(myIdData.getIdCreatedDate());

            if(!myIdData.getIdStatus().equals("1")){
                sw.getTextOff();
                sw.setSelected(false);
                sw.setChecked(false);
                AStatus="2";
            } else {
                sw.setSelected(true);
                AStatus="1";
                sw.setChecked(true);
            }







        view.findViewById(R.id.btnSaveCreatedId).setOnClickListener(view1 -> {
            if(etEditCreateIdUsername.getText().toString().equals("")){
                etEditCreateIdUsername.setError("Field Is Required");
            }if(etEditCreatedIdBalance.getText().toString().equals("")){
                etEditCreatedIdBalance.setError("Field Is Required");
            }if(etEditCreateIdPass.getText().toString().equals("")){
                etEditCreateIdPass.setError("Field Is Required");
            }if(etEditCreatedIdTotalDepo.getText().toString().equals("")){
                etEditCreatedIdTotalDepo.setError("Field Is Required");
            }if(etEditCreatedIdWithdrawal.getText().toString().equals("")){
                etEditCreatedIdWithdrawal.setError("Field Is Required");
            }else {
                alertDialog.dismiss();
                    updateonClick();

            }

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
        alertDialog.show();




    }



    private static void updateonClick() {

        if(method.adminDTO.getManageCreateIDList().equals("2")){

            method.params.clear();
            method.params.put("CreatedIDUpdate", "CreatedIDUpdate");
            method.params.put("idStatus",AStatus);
            method.params.put("idBalance",etEditCreatedIdBalance.getText().toString());
            method.params.put("idTotalDeposit",etEditCreatedIdTotalDepo.getText().toString());
            method.params.put("idTotalWithdrwal",etEditCreatedIdWithdrawal.getText().toString());
            method.params.put("idUserName", etEditCreateIdUsername.getText().toString());
            method.params.put("idPassWord",etEditCreateIdPass.getText().toString());
            method.params.put("orderId",(myIdData.getCorderId()));

            method.updateSetting((Activity) mContext, RestAPI.USER_UPDATE);

        }else{
            method.showToasty((Activity) mContext,"2","You Dont Have This Access \n Please" +
                    " Ask Super To Grant Full Access ");
        }


    }

}