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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.gpuntd.adminapp.HistoryActivity;
import com.gpuntd.adminapp.MainActivity;
import com.gpuntd.adminapp.Models.HomeTopDepositsDTO;
import com.gpuntd.adminapp.Models.HomeTopUserDTO;
import com.gpuntd.adminapp.NotificationsActivity;
import com.gpuntd.adminapp.OneTwoOneChat;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;


public class UserListDataAdapter extends RecyclerView.Adapter<UserListDataAdapter.MyViewHolder> {


    private static final String TAG ="KINGSN" ;
    public ArrayList<HomeTopUserDTO> topUserDTOS;
    public static Context context;
    UserListDataAdapter binding;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    LayoutInflater layoutInflater;
    public static  HomeTopUserDTO topUserDTO;
    public static TextInputEditText etEditCreateIdUsername,etEditCreateIdPass,UserNumber,etEditCreatedIdBalance,etDeviceID
            ,etDeviceToken,etuserReferredcode,etuserReferalcode
            ,etEditCreatedDate;
    public static String AStatus="1";
    public static  Method method;
    private ArrayList<HomeTopUserDTO> userBookingsList;

    // int count=0;
    public UserListDataAdapter(ArrayList<HomeTopUserDTO> topUserDTOS, Context context) {
        this.topUserDTOS = topUserDTOS;
        this.context = context;
        preferences = context.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        userBookingsList = new ArrayList<>();
        userBookingsList.addAll(topUserDTOS);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Log.i(TAG, "onCreateViewHolder: " + count++);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_user_list, parent, false);
        method=new Method(parent.getContext());

        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        topUserDTO = topUserDTOS.get(position);
        holder.setIsRecyclable(false);

        holder.idName.setText(topUserDTO.getName());
        holder.idMobile.setText(topUserDTO.getMobile());
        holder.tvTopTimestamp.setText(topUserDTO.getWallet());
        holder.tvRegisterdate.setText(topUserDTO.getJoiningTime());
        holder.tvUserReferCode.setText(topUserDTO.getUserReferalCode());
        holder.tvActivedate.setText(topUserDTO.getActiveDate());


        switch (topUserDTO.getAllow()) {
            case "1":
                // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.tvUserStatus.setText("Enabled");
                holder.tvUserStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.flipsucc));


                break;
            case "0":
                // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.tvUserStatus.setText("Pending");
                holder.tvUserStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.warningColor));

                break;
            case "2":
                // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.tvUserStatus.setText("Cancelled");
                holder.tvUserStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.errorColor));

                break;
            case "4":
                // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.tvUserStatus.setText("Processing");
                holder.tvUserStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.browser_actions_bg_grey));
                break;
        }

        holder.enableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topUserDTO = topUserDTOS.get(position);
                if(method.adminDTO.getManageUserList().equals("2")){
                    if(!topUserDTO.getAllow().equals("1")){
                        updateonStatusClick(1);
                    }else {
                        Toasty.error(context, "You Cant Verify A Verified Details Again", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    method.showToasty((Activity) context,"2","You Dont Have This Access \n Please" +
                            " Ask Super To Grant Full Access ");
                }


            }
        });

        holder.disableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topUserDTO = topUserDTOS.get(position);
                if(method.adminDTO.getManageUserList().equals("2")){
                    if(!topUserDTO.getAllow().equals("2")){
                        updateonStatusClick(2);
                    }else {
                        Toasty.error(context, "You Cant Verify A Verified Details Again", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    method.showToasty((Activity) context,"2","You Dont Have This Access \n Please" +
                            " Ask Super To Grant Full Access ");
                }
            }
        });

        holder.historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topUserDTO = topUserDTOS.get(position);
               // topUserDTO.setMobile(topUserDTOS.get(position).getMobile());
                GlobalVariables.SELUSER_MOBILE=topUserDTO.getMobile();
                editor.putString(GlobalVariables.SELUSER_MOBILE,topUserDTO.getMobile());
                editor.putString(GlobalVariables.SELUSER_NAME,topUserDTO.getName());
                editor.putString(GlobalVariables.SELUSER_REGDATE,topUserDTO.getJoiningTime());
                editor.putString(GlobalVariables.SELUSER_ACTIVE,topUserDTO.getActiveDate());
                editor.putString(GlobalVariables.SELUSER_WALLETBAL,topUserDTO.getWallet());
                editor.putString(GlobalVariables.SELUSER_REFERCODE,topUserDTO.getUserReferalCode());
                method.preferences.setValue("userTotalDeposit",topUserDTO.getTotalPaid());
                method.preferences.setValue("userTotalRedeemed",topUserDTO.getTotalRedeemed());
                method.preferences.setValue("totalreffered",topUserDTO.getTotalReferals());

                editor.apply();
                Log.d(TAG, "onClick: "+GlobalVariables.SELUSER_MOBILE+preferences.getString(GlobalVariables.SELUSER_MOBILE,""));

                context.startActivity(new Intent(context, HistoryActivity.class));
            }
        });

        holder.btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "onClick: "+topUserDTOS.get(position).getPayMentMode()+"\n"+topUserDTOS.get(position).getName());
                topUserDTO=topUserDTOS.get(position);
                if(method.adminDTO.getManageUserList().equals("2")){
                    UserListDataAdapter.setupUpdateDialog(v);
                }else{
                    method.showToasty((Activity) context,"2","You Dont Have This Access \n Please" +
                            " Ask Super To Grant Full Access ");
                }

            }
        });

        holder.btnWhatsapp.setOnClickListener(view -> {
            if(method.adminDTO.getManageUserList().equals("2")){
                try {
                    topUserDTO = topUserDTOS.get(position);
                    //  topUserDTO.setMobile(topUserDTOS.get(position).getMobile());
                    Uri uri = Uri.parse("smsto:" + "+91"+topUserDTO.getMobile());
                    // Toast.makeText(context, "" + topUserDTO.getMobile(), Toast.LENGTH_SHORT).show();
                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                    String message="Hello I User : I Need Help Regarding "+method.adminDTO.getAppName()+" Usages";
                    sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                    sendIntent.setPackage("com.whatsapp");
                    context.startActivity(sendIntent);


          /*  String toNumber =topUserDTOS.get(position).getMobile(); // contains spaces.
            toNumber = toNumber.replace("+", "").replace(" ", "");
            String message="Hello I User : I Need Help Regarding "+method.adminDTO.getAppName()+" Usages";
            Intent sendIntent2 = new Intent("android.intent.action.MAIN");
            sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            sendIntent.setAction(Intent.ACTION_SENDTO);
            sendIntent.setPackage("com.whatsapp");
            sendIntent.setType("text/plain");
                context.startActivity(sendIntent2);*/

                }catch (Exception e){
                    Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                }


            }else{
                method.showToasty((Activity) context,"2","You Dont Have This Access \n Please" +
                        " Ask Super To Grant Full Access ");
            }

        });



        holder.btnChat.setOnClickListener(view -> {
            if(method.adminDTO.getManageUserList().equals("2")){


                topUserDTO=topUserDTOS.get(position);
                try {
                    Uri uri = Uri.parse("smsto:" + topUserDTO.getMobile());
                    //Toast.makeText(context, "" + topUserDTO.getMobile(), Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(context, OneTwoOneChat.class);
                    in.putExtra(GlobalVariables.userMobile, topUserDTO.getMobile());
                    in.putExtra(GlobalVariables.userName, topUserDTO.getName());
                    context.startActivity(in);
                }catch (Exception e){
                    Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                }

            }else{
                method.showToasty((Activity) context,"2","You Dont Have This Access \n Please" +
                        " Ask Super To Grant Full Access ");
            }


        });


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
        return topUserDTOS.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout demoSection;
        ImageView enableBtn,disableBtn,historyBtn, btnEditUser,btnChat;
        de.hdodenhof.circleimageview.CircleImageView idImage;
        TextView idName,idMobile,tvUserStatus,tvUserReferCode, tvTopTimestamp, tvRegisterdate,tvActivedate,tvDemoPass;
        ImageView ivLink,ivCopyId,ivCopyPass, btnWhatsapp;
        TextInputEditText depositCoinsEt;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idName=itemView.findViewById(R.id.tvTopTitle);
           idMobile=itemView.findViewById(R.id.tvTopUserPhone);
            tvUserStatus=itemView.findViewById(R.id.tvUserStatus);
            tvUserReferCode = itemView.findViewById(R.id.tvUserReferCode);
            tvTopTimestamp = itemView.findViewById(R.id.tvTopTimestamp);
            enableBtn = itemView.findViewById(R.id.enableBtn);
            disableBtn=itemView.findViewById(R.id.disableBtn);
            historyBtn=itemView.findViewById(R.id.historyBtn);
            tvRegisterdate=itemView.findViewById(R.id.tvRegisterdate);
            tvActivedate=itemView.findViewById(R.id.tvActivedate);
            btnEditUser=itemView.findViewById(R.id.btnEditUser);
            btnWhatsapp = itemView.findViewById(R.id.btnWhatsapp);
            btnChat = itemView.findViewById(R.id.btnChat);

        }
    }

    private void updateonStatusClick(int i) {
        if(i == 1){
            method.params.clear();
            method.params.put("userDetailsUpdate", "userDetailsUpdate");
            method.params.put("txnStatus","1");
            method.params.put("mobile",(topUserDTO.getMobile()));

            if(method.adminDTO.getManageUserList().equals("2")){

                method.updateSetting((Activity) context, RestAPI.USER_UPDATE);

            }else{
                method.showToasty((Activity) context,"2","You Dont Have This Access \n Please" +
                        " Ask Super To Grant Full Access ");
            }



        } if(i == 2){
            method.params.clear();
            method.params.put("userDetailsUpdate", "userDetailsUpdate");
            method.params.put("txnStatus","2");
            method.params.put("mobile",(topUserDTO.getMobile()));

            if(method.adminDTO.getManageUserList().equals("2")){

                method.updateSetting((Activity) context, RestAPI.USER_UPDATE);


            }else{
                method.showToasty((Activity) context,"2","You Dont Have This Access \n Please" +
                        " Ask Super To Grant Full Access ");
            }


        }
    }

    public static void setupUpdateDialog(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_user, v.findViewById(R.id.editUid));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        etEditCreateIdUsername = view.findViewById(R.id.etEditCreateIdUsername);
        etEditCreateIdPass = view.findViewById(R.id.etEditCreateIdPass);
        UserNumber = view.findViewById(R.id.UserNumber);
        etEditCreatedIdBalance = view.findViewById(R.id.etEditCreatedIdBalance);
        etDeviceID = view.findViewById(R.id.etDeviceID);
        etDeviceToken = view.findViewById(R.id.etDeviceToken);
        etEditCreatedDate = view.findViewById(R.id.etEditCreatedDate);
        etuserReferalcode= view.findViewById(R.id.etuserReferalcode);
        etuserReferredcode= view.findViewById(R.id.etuserReferredcode);
        Button btnSaveUser=view.findViewById(R.id.btnSaveUser);
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
        Log.d(TAG, "onpopup: "+topUserDTO.getName());
        etEditCreateIdUsername.setText(topUserDTO.getName());
        etEditCreatedIdBalance.setText(topUserDTO.getWallet());
        etEditCreateIdPass.setText(topUserDTO.getPassword());
        UserNumber.setText(topUserDTO.getMobile());
        etDeviceID.setText(topUserDTO.getDeviceId());
        etDeviceToken.setText(topUserDTO.getDeviceToken());
        etEditCreatedDate.setText(topUserDTO.getJoiningTime());
        etuserReferalcode.setText(topUserDTO.getUserReferalCode());
        etuserReferredcode.setText(topUserDTO.getRefferedBy());

        if(!topUserDTO.getAllow().equals("1")){
            sw.getTextOff();
            sw.setSelected(false);
            sw.setChecked(false);
            AStatus="2";
        } else {
            sw.setSelected(true);
            AStatus="1";
            sw.setChecked(true);
        }







        view.findViewById(R.id.btnSaveUser).setOnClickListener(view1 -> {
            if(etEditCreateIdUsername.getText().toString().equals("")){
                etEditCreateIdUsername.setError("Field Is Required");
            }if(etEditCreatedIdBalance.getText().toString().equals("")){
                etEditCreatedIdBalance.setError("Field Is Required");
            }if(etEditCreateIdPass.getText().toString().equals("")){
                etEditCreateIdPass.setError("Field Is Required");
            }if(UserNumber.getText().toString().equals("")){
                UserNumber.setError("Field Is Required");
            }if(etDeviceID.getText().toString().equals("")){
                etDeviceID.setError("Field Is Required");
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

        if(method.adminDTO.getManageUserList().equals("2")){

            method.params.clear();
            method.params.put("userDetailsUpdate", "userDetailsUpdate");
            method.params.put("allow",AStatus);
            method.params.put("wallet",etEditCreatedIdBalance.getText().toString());
            method.params.put("name",etEditCreateIdUsername.getText().toString());
            method.params.put("mobile",UserNumber.getText().toString());
            method.params.put("device_id",etDeviceID.getText().toString());
            method.params.put("password",etEditCreateIdPass.getText().toString());
            method.params.put("user_referal_code",(etuserReferalcode.getText().toString()));
            method.params.put("reffered_by",(etuserReferredcode.getText().toString()));

            method.updateSetting((Activity) context, RestAPI.USER_UPDATE);

        }else{
            method.showToasty((Activity) context,"2","You Dont Have This Access \n Please" +
                    " Ask Super To Grant Full Access ");
        }



    }



    /*public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        topUserDTOS.clear();
        if (charText.length() == 0) {
            topUserDTOS.addAll(userBookingsList);
        } else {
            for (HomeTopUserDTO userBooking : userBookingsList) {
                if ((userBooking.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getMobile().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getUserReferalCode().toLowerCase(Locale.getDefault())
                        .contains(charText))) {
                    topUserDTOS.add(userBooking);
                }
            }
        }
        notifyDataSetChanged();
    }*/

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if(charText.equals("pending")){
            charText="0";
        } if((charText.equals("success"))||(charText.equals("approved"))){
            charText="1";
        } if((charText.equals("rejected"))||(charText.equals("cancelled"))){
            charText="2";
        } if((charText.equals("processing"))||(charText.equals("process"))){
            charText="4";
        }
        topUserDTOS.clear();
        if (charText.length() == 0) {
            topUserDTOS.addAll(userBookingsList);
        } else {
            for (HomeTopUserDTO userBooking : userBookingsList) {
                if ((userBooking.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getAllow().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getUserReferalCode().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getMobile().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getEmail().toLowerCase(Locale.getDefault())
                        .contains(charText))) {
                    topUserDTOS.add(userBooking);
                }

            }
        }
        notifyDataSetChanged();
    }


}
