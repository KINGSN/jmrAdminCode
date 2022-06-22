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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.textfield.TextInputEditText;
import com.gpuntd.adminapp.Models.HomeTopDepositsDTO;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.databinding.ItemPasswordRequestBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class PasswordDataAdapter extends RecyclerView.Adapter<PasswordDataAdapter.MyViewHolder> {


    private static final String TAG ="KINGSN" ;
    public ArrayList<HomeTopDepositsDTO> topDepositsDTOS;
    public static HomeTopDepositsDTO myIdData;
    @SuppressLint("StaticFieldLeak")
    public static  Context context;
     ItemPasswordRequestBinding binding;
    public static Method method;
    public static   HomeTopDepositsDTO topDepositsDTO;
   LayoutInflater layoutInflater;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public static TextInputEditText etEditUsernamePassReq,etEditCreateIdPass,etEditDatePassReq;
    public static String AStatus="1";
    public PasswordDataAdapter(List<HomeTopDepositsDTO> topDepositsDTOS, Context context) {
        this.topDepositsDTOS = (ArrayList<HomeTopDepositsDTO>) topDepositsDTOS;
        PasswordDataAdapter.context = context;
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
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_password_request, parent, false);
        method=new Method(parent.getContext());
        preferences = parent.getContext().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();

        return new MyViewHolder(binding);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        topDepositsDTO = topDepositsDTOS.get(position);
        holder.setIsRecyclable(false);

        holder.binding.tvIdPassRequest.setText(topDepositsDTO.getIdName());
        holder.binding.tvMobilePassRequest.setText(topDepositsDTO.getMobile());
        holder.binding.tvUsernamePassRequest.setText(topDepositsDTO.getIdUsername());
        holder.binding.tvPasswordPassRequest.setText(topDepositsDTO.getIdPassword());
        holder.binding.tvTimestampPassRequest.setText(topDepositsDTO.getTxnDate());
        holder.binding.orderId.setText(topDepositsDTO.getOrderId());
        holder.binding.appUserName.setText(topDepositsDTO.getName());


        holder.binding.btnDeletePassRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateonClick();
            }
        });


        switch (topDepositsDTO.getTxnStatus()) {
            case "1":
                // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.binding.tvStatusPassRequest.setText("Verified");
                holder.binding.tvStatusPassRequest.setBackgroundColor(ContextCompat.getColor(context, R.color.flipsucc));


                break;
            case "0":
                // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.binding.tvStatusPassRequest.setText("Pending");
                holder.binding.tvStatusPassRequest.setBackgroundColor(ContextCompat.getColor(context, R.color.warningColor));

                break;
            case "2":
                // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.binding.tvStatusPassRequest.setText("Cancelled");
                holder.binding.tvStatusPassRequest.setBackgroundColor(ContextCompat.getColor(context, R.color.errorColor));

                break;
            case "4":
                // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.binding.tvStatusPassRequest.setText("Processing");
                holder.binding.tvStatusPassRequest.setBackgroundColor(ContextCompat.getColor(context, R.color.browser_actions_bg_grey));
                break;
        }


            holder.binding.btnEditPassRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: "+topDepositsDTOS.get(position).getPayMentMode()+"\n"+topDepositsDTOS.get(position).getName());
                    myIdData=topDepositsDTOS.get(position);
                    if(method.adminDTO.getManagePassword().equals("2")){
                        PasswordDataAdapter.setupUpdateDialog(v);
                    }else{
                        method.showToasty((Activity) context,"2","You Dont Have This Access \n Please" +
                                " Ask Super To Grant Full Access ");
                    }

                }
            });


        if(!topDepositsDTO.getImg().equals("")) {
            Log.d("KINGSN", "onBindViewHolder: "+topDepositsDTO.getImg());
            Glide.with(context)
                    .load(topDepositsDTO.getImg())
                    .placeholder(R.drawable.round_bg)
                    .dontAnimate()
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.binding.idImage);
        }else{
            Glide.with(context)
                    .load(R.mipmap.ic_launcher_round)
                    .placeholder(R.drawable.round_bg)
                    .dontAnimate()
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.binding.idImage);

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

        ItemPasswordRequestBinding binding;

        public MyViewHolder(ItemPasswordRequestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    public static void setupUpdateDialog(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_password_request, v.findViewById(R.id.editcPass));
        builder.setView(view);
         AlertDialog alertDialog = builder.create();

        etEditUsernamePassReq = view.findViewById(R.id.etEditUsernamePassReq);
        etEditCreateIdPass = view.findViewById(R.id.etEditCreateIdPass);
        etEditDatePassReq = view.findViewById(R.id.etEditDatePassReq);


        ImageView close=view.findViewById(R.id.btnCloseDialogPassReq);
        Switch sw = view.findViewById(R.id.switch1);




        Log.d(TAG, "onpopup: "+myIdData.getIdUsername());

        etEditUsernamePassReq.setText(myIdData.getIdUsername());
        etEditCreateIdPass.setText(myIdData.getIdPassword());
        etEditDatePassReq.setText(myIdData.getVerifiedDate());

        Log.d(TAG, "onpopup:pass "+myIdData.getIdUsername());

        if(myIdData.getTxnStatus().equals("0")){
            etEditDatePassReq.setText(myIdData.getTxnDate());
            sw.getTextOff();
            sw.setSelected(false);
            sw.setChecked(false);
            AStatus="0";
        } if(myIdData.getTxnStatus().equals("2")){
            etEditDatePassReq.setText(myIdData.getCancelledDate());
            sw.getTextOff();
            sw.setSelected(false);
            sw.setChecked(false);
            AStatus="2";
        }/*else if(myIdData.getTxnStatus().equals("1")){
            etEditDatePassReq.setText(myIdData.getV());
            sw.getTextOff();
            sw.setSelected(false);
            sw.setChecked(false);
            AStatus="0";
        }*/ else {
            sw.setSelected(true);
            AStatus="1";
            sw.setChecked(true);
            etEditDatePassReq.setText(myIdData.getVerifiedDate());
            AStatus="1";
        }
        Log.d(TAG, "onpopup:pass2 "+myIdData.getIdUsername());
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




        Log.d(TAG, "onpopup:pass3 "+myIdData.getIdUsername());

        view.findViewById(R.id.btnSavePassReq).setOnClickListener(view1 -> {
            if(Objects.requireNonNull(etEditUsernamePassReq.getText()).toString().equals("")){
                etEditUsernamePassReq.setError("Field Is Required");
            }else if(Objects.requireNonNull(etEditCreateIdPass.getText()).toString().equals("")){
                etEditCreateIdPass.setError("Field Is Required");
            }else {
                alertDialog.dismiss();
                updateonStatusClick();

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

        Log.d(TAG, "onpopup:pass4 "+myIdData.getIdUsername());
        alertDialog.show();




    }


    private static void updateonClick() {
             method.params.clear();
            method.params.put("PasswordUpdate", "PasswordUpdate");
           method.params.put("userMobile", (topDepositsDTO.getUserMobile()));
            method.params.put("txnStatus",AStatus);
            method.params.put("orderId",(myIdData.getOrderId()));
           method.params.put("idUserName", Objects.requireNonNull(etEditUsernamePassReq.getText()).toString());
           method.params.put("idPassWord", Objects.requireNonNull(etEditCreateIdPass.getText()).toString());

            method.updateSetting((Activity) context, RestAPI.USER_UPDATE);


    }

    private static void updateonStatusClick() {
        method.params.clear();
        method.params.put("PasswordUpdate", "PasswordUpdate");
        method.params.put("userMobile", (topDepositsDTO.getUserMobile()));
        method.params.put("txnStatus", AStatus);
        method.params.put("orderId",(myIdData.getOrderId()));
        method.params.put("idUserName",myIdData.getIdUsername());
        method.params.put("idPassWord", myIdData.getPassword());

        method.updateSetting((Activity) context, RestAPI.USER_UPDATE);

    }

}
