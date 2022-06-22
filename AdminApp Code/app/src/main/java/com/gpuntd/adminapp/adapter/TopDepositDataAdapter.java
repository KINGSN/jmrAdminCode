package com.gpuntd.adminapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.textfield.TextInputEditText;
import com.gpuntd.adminapp.HistoryActivity;
import com.gpuntd.adminapp.MainActivity;
import com.gpuntd.adminapp.Models.HomeTopDepositsDTO;
import com.gpuntd.adminapp.Models.HomeTopUserDTO;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class TopDepositDataAdapter extends RecyclerView.Adapter<TopDepositDataAdapter.MyViewHolder> {


    private static final String TAG ="KINGSN" ;
    //private static final String TAG="RecyclerAdapter";
   ArrayList<HomeTopDepositsDTO> topDepositsDTOS;
    private static Context context;
    TopDepositDataAdapter binding;
    private SharedPreferences preferences, preferences1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static Method method;
    AlertDialog alertDialog;
    private Dialog dialogImg;
    private ImageView ivImageD;
    private TextView tvCloseD, tvNameD;
    public static  HomeTopDepositsDTO topDepositsDTO;
    private ArrayList<HomeTopDepositsDTO> userBookingsList;

    public static TextInputEditText etEditCreateIdUsername,etEditCreateIdPass,etEditCreatedIdBalance,etEditCreatedIdTotalDepo
            ,etEditCreatedIdWithdrawal
            ,etEditCreatedDate;
    public static String AStatus="1";

    // int count=0;
    public TopDepositDataAdapter(ArrayList<HomeTopDepositsDTO> topDepositsDTOS, Context context) {
        this.topDepositsDTOS = topDepositsDTOS;
        this.context = context;
        userBookingsList = new ArrayList<>();
        userBookingsList.addAll(topDepositsDTOS);
        preferences = context.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Log.i(TAG, "onCreateViewHolder: " + count++);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_top_deposits_home, parent, false);
        method=new Method(parent.getContext());

        return new MyViewHolder(view);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        topDepositsDTO = topDepositsDTOS.get(position);
        holder.setIsRecyclable(false);

        holder.idName.setText(topDepositsDTO.getName());
        holder.idMobile.setText(topDepositsDTO.getMobile());
        holder.tvTopTimestamp.setText(topDepositsDTO.getTxnDate());
        holder.tvUserWallet.setText("Deposit Amount"+topDepositsDTO.getAmount());

        holder.tvViewDetails.setOnClickListener(view -> {
           // Toast.makeText(view.getContext(), "flipping the view", Toast.LENGTH_SHORT).show();
            holder.easyFlipView.flipTheView();
        });

        holder.bakViewDetails.setOnClickListener(view -> {
          //  Toast.makeText(view.getContext(), "flipping the view", Toast.LENGTH_SHORT).show();
            holder.easyFlipView.flipTheView();
        });

        holder.enableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                topDepositsDTO = topDepositsDTOS.get(position);
                if(topDepositsDTO.getTxnType().equals("0")){

                    if(method.adminDTO.getManageDepositList().equals("2")){
                        setupUpdateDialog(v);
                    }else{
                        method.showToasty((Activity) context,"2","You Dont Have This Access \n Please" +
                                " Ask Super To Grant Full Access ");
                    }


                }else{
                    updateonClick(1);
                }

            }
        });

        holder.disableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                topDepositsDTO = topDepositsDTOS.get(position);
                updateonClick(2);
            }
        });

        holder.historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // updateonClick(3);

                topDepositsDTO = topDepositsDTOS.get(position);
              //  topDepositsDTO.setMobile(topUserDTOS.get(position).getMobile());
                GlobalVariables.SELUSER_MOBILE=topDepositsDTO.getMobile();
                editor.putString(GlobalVariables.SELUSER_MOBILE,topDepositsDTO.getMobile());
                editor.putString(GlobalVariables.SELUSER_NAME,topDepositsDTO.getName());
                editor.putString(GlobalVariables.SELUSER_REGDATE,topDepositsDTO.getJoiningTime());
                editor.putString(GlobalVariables.SELUSER_ACTIVE,topDepositsDTO.getActiveDate());
                editor.putString(GlobalVariables.SELUSER_WALLETBAL,topDepositsDTO.getWallet());
                editor.putString(GlobalVariables.SELUSER_REFERCODE,topDepositsDTO.getUserReferalCode());
                method.preferences.setValue("userTotalDeposit",topDepositsDTO.getTotalPaid());
                method.preferences.setValue("userTotalRedeemed",topDepositsDTO.getTotalRedeemed());
                method.preferences.setValue("totalreffered",topDepositsDTO.getTotalReferals());
                editor.apply();
                Log.d(TAG, "onClick: "+GlobalVariables.SELUSER_MOBILE+preferences.getString(GlobalVariables.SELUSER_MOBILE,""));

                context.startActivity(new Intent(context, HistoryActivity.class));
            }
        });



        if (topDepositsDTO.getTxnStatus().equals("1")) {
            // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.tvUserStatus.setText("Verified");
            holder.tvUserStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.flipsucc));
            holder.enableBtn.setVisibility(View.GONE);
            holder.disableBtn.setVisibility(View.VISIBLE);


        }else if (topDepositsDTO.getTxnStatus().equals("0")) {
            // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.tvUserStatus.setText("Pending");
            holder.tvUserStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.warningColor));
            holder.enableBtn.setVisibility(View.VISIBLE);

        }else if (topDepositsDTO.getTxnStatus().equals("2")) {
            // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.tvUserStatus.setText("Cancelled");
            holder.tvUserStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.errorColor)) ;
            holder.disableBtn.setVisibility(View.GONE);
            holder.enableBtn.setVisibility(View.VISIBLE);

        }else if (topDepositsDTO.getTxnStatus().equals("4")) {
            // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.tvUserStatus.setText("Processing");
            holder.tvUserStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.browser_actions_bg_grey)) ;
            holder.enableBtn.setVisibility(View.VISIBLE);
            holder.disableBtn.setVisibility(View.VISIBLE);
        }

        if (topDepositsDTO.getPayMentMode().equals("0")) {
            // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.tvDepoMethod.setText("Online Payment");
            holder.tvDepoDetail.setText(topDepositsDTO.getOrderId());
            holder.viewImg.setVisibility(View.GONE);
        }else if (topDepositsDTO.getPayMentMode().equals("2")) {
            // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.tvDepoMethod.setText("Wallet Payment");
            holder.tvDepoDetail.setText(topDepositsDTO.getOrderId());
            holder.viewImg.setVisibility(View.GONE);
        }else  {
            // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
            Log.d(TAG, "dialogshare: "+position+topDepositsDTO.getPayMentMode());
            holder.tvDepoMethod.setText("Screenshort Payment");
            holder.tvDepoDetail.setText(topDepositsDTO.getOrderId()); ;
            holder.viewImg.setVisibility(View.VISIBLE);
            holder.viewImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: "+topDepositsDTOS.get(position).getPayMentMode()+"\n"+topDepositsDTOS.get(position).getName());

                    dialogshare(topDepositsDTOS.get(position).getPayMentMode(),topDepositsDTOS.get(position).getName());
                }
            });
        }

        if(!topDepositsDTO.getImg().equals("")) {
            Log.d("KINGSN", "onBindViewHolder: "+topDepositsDTO.getImg());
            Glide.with(context.getApplicationContext())
            //Glide.with(context)
                    .load(topDepositsDTO.getImg())
                    .placeholder(R.drawable.round_bg)
                    .dontAnimate()
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.idImage);
        }else{
            Glide.with(context.getApplicationContext())
           // Glide.with(context)
                    .load(R.mipmap.ic_launcher_round)
                    .placeholder(R.drawable.round_bg)
                    .dontAnimate()
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.idImage);

        }

        switch (topDepositsDTO.getTxnType()) {
            case "0":


                holder.tvTopTitle3.setText("Create ID");
                holder.tvDepoWebsite.setText(topDepositsDTO.getIdWebsite());
                holder.tvDepoUsername.setText(topDepositsDTO.getIdUsername());
                holder.tvDepoAmount.setText(topDepositsDTO.getAmount());
                holder.tvDepoOrderId.setText(topDepositsDTO.getOrderId());


                break;
            case "1":


                holder.tvTopTitle3.setText("Deposit To ID");
                holder.tvDepoWebsite.setText(topDepositsDTO.getIdWebsite());
                holder.tvDepoUsername.setText(topDepositsDTO.getIdUsername());
                holder.tvDepoAmount.setText(topDepositsDTO.getAmount());
                holder.tvDepoOrderId.setText(topDepositsDTO.getOrderId());
                break;

            case "2":
               holder.tvTopTitle3.setText("Wallet Deposit");
                holder.tvDepoWebsite.setText(topDepositsDTO.getName()+" Wallet ");
                holder.tvDepoUsername.setText(topDepositsDTO.getUserReferalCode());
                holder.tvDepoAmount.setText(topDepositsDTO.getAmount());
                holder.tvDepoOrderId.setText(topDepositsDTO.getOrderId());
                break;

            case "4":
              /*  holder.idName.setText("Deposit To Wallet");
                holder.vtxnl.setVisibility(View.GONE);
                holder.idtxnl.setVisibility(View.GONE);
                holder.subtitle1.setVisibility(View.GONE);
                holder.amount.setText("+" + passbookData.getAmount());
               */
                break;
        }
    }

    private static void updateonClick(int i) {
        if(method.adminDTO.getManageDepositList().equals("2")){

            if(i == 1){
                method.params.put("userMobile", (topDepositsDTO.getUserMobile()));
                method.params.put("depositUpdate", "depositUpdate");
                method.params.put("txnStatus","1");
                method.params.put("id",(topDepositsDTO.getId()));
                method.params.put("orderId",(topDepositsDTO.getOrderId()));
                Log.d(TAG, "setupUpdateDialog2: "+method.params);
                method.updateSetting((Activity) context, RestAPI.USER_UPDATE);

            } if(i == 2){
                method.params.clear();
                method.params.put("depositUpdate", "depositUpdate");
                method.params.put("userMobile", (topDepositsDTO.getUserMobile()));
                method.params.put("txnStatus","2");
                method.params.put("id",(topDepositsDTO.getId()));
                method.params.put("orderId",(topDepositsDTO.getOrderId()));
                method.params.put("idUsername", (topDepositsDTO.getIdUsername()));
                method.params.put("idPassword",(topDepositsDTO.getIdPassword()));

                method.updateSetting((Activity) context, RestAPI.USER_UPDATE);

            }

        }else{
            method.showToasty((Activity) context,"2","You Dont Have This Access \n Please" +
                    " Ask Super To Grant Full Access ");
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
        LinearLayout demoSection;
        public ImageView enableBtn,disableBtn,historyBtn, btnCreateId;
        de.hdodenhof.circleimageview.CircleImageView idImage;
        TextView idName,idMobile,tvUserStatus,tvUserReferCode, tvTopTimestamp,tvUserWallet,tvViewDetails,tvDepoMethod,tvDepoDetail;
        TextView tvDepoUsername,tvDepoAmount,tvDepoOrderId,tvDepoIdBal,tvTopTitle3,tvDepoWebsite,bakViewDetails;
        ImageView ivLink,ivCopyId,ivCopyPass,viewImg;
        TextInputEditText depositCoinsEt;
        EasyFlipView easyFlipView;
        LinearLayout depolinear;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idName=itemView.findViewById(R.id.tvTopTitle);
           idMobile=itemView.findViewById(R.id.tvTopUserPhone);
            tvUserStatus=itemView.findViewById(R.id.tvUserStatus);
            tvUserReferCode = itemView.findViewById(R.id.tvUserReferCode);
            tvUserWallet = itemView.findViewById(R.id.tvUserWallet);
            tvTopTimestamp = itemView.findViewById(R.id.tvTopTimestamp);
            enableBtn = itemView.findViewById(R.id.enableBtn);
            disableBtn=itemView.findViewById(R.id.disableBtn);
            historyBtn=itemView.findViewById(R.id.historyBtn);
            tvViewDetails=itemView.findViewById(R.id.tvViewDetails);
            easyFlipView=itemView.findViewById(R.id.flipView);
            tvDepoMethod=itemView.findViewById(R.id.tvDepoMethod);
            tvDepoDetail=itemView.findViewById(R.id.tvDepoDetail);
            tvDepoUsername=itemView.findViewById(R.id.tvDepoUsername);
            tvDepoAmount=itemView.findViewById(R.id.tvDepoAmount);
            tvDepoOrderId=itemView.findViewById(R.id.tvDepoOrderId);
           bakViewDetails=itemView.findViewById(R.id.bakViewDetails);
            depolinear=itemView.findViewById(R.id.depolinear);
            tvDepoWebsite=itemView.findViewById(R.id.tvDepoWebsite);
            tvTopTitle3=itemView.findViewById(R.id.tvTopTitle3);
            idImage=itemView.findViewById(R.id.idImage);
            viewImg=itemView.findViewById(R.id.viewImg);
        }
    }


    public void dialogshare(String img,String name) {
        Log.d(TAG, "iMAGE: "+img);
        dialogImg = new Dialog(context, android.R.style.Theme_Dialog);
        Objects.requireNonNull(dialogImg.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogImg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogImg.setContentView(R.layout.dailog_image_view);


        tvCloseD = (TextView) dialogImg.findViewById(R.id.tvCloseD);
        tvNameD = (TextView) dialogImg.findViewById(R.id.tvNameD);

        ivImageD = (ImageView) dialogImg.findViewById(R.id.ivImageD);
        dialogImg.show();
        dialogImg.setCancelable(false);

        Glide.with(context).
                load(img)
                .placeholder(R.drawable.dummyuser_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImageD);

        tvNameD.setText(name);
        tvCloseD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogImg.dismiss();


            }
        });

    }

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
        topDepositsDTOS.clear();
        if (charText.length() == 0) {
            topDepositsDTOS.addAll(userBookingsList);
        } else {
            for (HomeTopDepositsDTO userBooking : userBookingsList) {
                if ((userBooking.getIdName().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getOrderId().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getAmount().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getMobile().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getTxnStatus().toLowerCase(Locale.getDefault())
                        .contains(charText))) {
                    topDepositsDTOS.add(userBooking);
                }

            }
        }
        notifyDataSetChanged();
    }

    public void   filter2(String charText,String chartext2) {
        charText = charText.toLowerCase(Locale.getDefault());
       /* if(charText.equals("pending")){
            charText="0";
        } if((charText.equals("success"))||(charText.equals("approved"))){
            charText="1";
        } if((charText.equals("rejected"))||(charText.equals("cancelled"))){
            charText="2";
        } if((charText.equals("processing"))||(charText.equals("process"))){
            charText="4";
        }*/
        topDepositsDTOS.clear();

        /*if (charText.length() == 0) {
            topDepositsDTOS.addAll(userBookingsList);

        } else*/ {
            for (HomeTopDepositsDTO userBooking : userBookingsList) {
                Log.d(TAG, "filter2: "+"text1ToMatch "+charText+" match with" +(userBooking.getTxnType().toLowerCase(Locale.getDefault())
                )+userBooking.getTxnStatus() +" ji "+chartext2);
                if ((userBooking.getTxnType().toLowerCase(Locale.getDefault())
                        .equals(charText) && (userBooking.getTxnStatus().toLowerCase(Locale.getDefault())
                        .equals(chartext2))) ) {
                    topDepositsDTOS.add(userBooking);
                }else if ((userBooking.getTxnType().toLowerCase(Locale.getDefault())
                        .equals(charText) && (userBooking.getTxnStatus().toLowerCase(Locale.getDefault())
                        .equals(chartext2))) ) {
                    topDepositsDTOS.add(userBooking);
                }

            }
        }
        notifyDataSetChanged();
    }



    public static void setupUpdateDialog(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_created_id, v.findViewById(R.id.editcrId));
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
       LinearLayout idpasslay= view.findViewById(R.id.idpasslay);
       TextView titleTv=view.findViewById(R.id.titleTv);

        sw.setVisibility(View.GONE);
        etEditCreatedIdBalance.setVisibility(View.GONE);
        etEditCreatedIdTotalDepo.setVisibility(View.GONE);
        etEditCreatedIdWithdrawal.setVisibility(View.GONE);
        etEditCreatedDate.setVisibility(View.GONE);
        etEditCreatedIdWithdrawal.setVisibility(View.GONE);
        idpasslay.setVisibility(View.GONE);
        titleTv.setText("Update With Credentials");

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
        Log.d(TAG, "onpopup: "+topDepositsDTO.getIdUsername());
        etEditCreateIdUsername.setText(topDepositsDTO.getIdUsername());
        etEditCreateIdPass.setText(topDepositsDTO.getIdPassword());








        view.findViewById(R.id.btnSaveCreatedId).setOnClickListener(view1 -> {
            if(method.adminDTO.getManageCreateIDList().equals("2")) {
                if (Objects.requireNonNull(etEditCreateIdUsername.getText()).toString().equals("")) {
                    etEditCreateIdUsername.setError("Field Is Required");
                }
                if (Objects.requireNonNull(etEditCreateIdPass.getText()).toString().equals("")) {
                    etEditCreateIdPass.setError("Field Is Required");
                } else {
                    method.params.clear();
                    method.params.put("idUsername", etEditCreateIdUsername.getText().toString());
                    method.params.put("idPassword", etEditCreateIdPass.getText().toString());
                    Log.d(TAG, "setupUpdateDialog: " + method.params);
                    alertDialog.dismiss();
                    updateonClick(1);


                }
            }else{
                method.showToasty((Activity) context,"2","You Dont Have This Access \n Please" +
                        " Ask Super To Grant Full Access ");
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


}
