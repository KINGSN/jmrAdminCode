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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.gpuntd.adminapp.Models.HomeTopDepositsDTO;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

import static android.content.Context.MODE_PRIVATE;


public class WitdrawalsDataAdapter extends RecyclerView.Adapter<WitdrawalsDataAdapter.MyViewHolder> {


    private static final String TAG ="KINGSN" ;
    //private static final String TAG="RecyclerAdapter";
    public List<HomeTopDepositsDTO> topDepositsDTOS;
    public final Context context;
    WitdrawalsDataAdapter binding;
    private SharedPreferences preferences, preferences1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Method method;
    AlertDialog alertDialog;
    private Dialog dialogImg;
    private ImageView ivImageD;
    private TextView tvCloseD, tvNameD;
    public  HomeTopDepositsDTO topDepositsDTO;
    private ArrayList<HomeTopDepositsDTO> userBookingsList;

    // int count=0;
    public WitdrawalsDataAdapter(List<HomeTopDepositsDTO> topDepositsDTOS, Context context) {
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
        View view = layoutInflater.inflate(R.layout.item_withdraw_request, parent, false);
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
        holder.tvUserWallet.setText(topDepositsDTO.getWallet());
        holder.tvReqDate.setText(topDepositsDTO.getTxnDate());
        holder.tvUpdateDate.setText(topDepositsDTO.getVerifiedDate());
        holder.tvUserWid.setText(topDepositsDTO.getAmount());
        holder.tvDepoDetail.setText(topDepositsDTO.getwDetails());

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
            public void onClick(View v) {
                topDepositsDTO = topDepositsDTOS.get(position);
                if(!topDepositsDTO.getTxnStatus().equals("1")){
                    if(method.adminDTO.getManageWithdrawal().equals("2")){
                        updateonClick(1);
                    }else{
                        method.showToasty((Activity) context,"2","You Dont Have This Access \n Please" +
                                " Ask Super To Grant Full Access ");
                    }

                }else {
                    Toasty.error(context, "You Cant Verify A Verified Details Again", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.disableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topDepositsDTO = topDepositsDTOS.get(position);
               // updateonClick(2);
                if(!topDepositsDTO.getTxnStatus().equals("2")){
                    updateonClick(2);
                }else {
                    Toasty.error(context, "You Cant Verify A Verified Details Again", Toast.LENGTH_SHORT).show();
                }
            }
        });



        holder.historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topDepositsDTO = topDepositsDTOS.get(position);
                //  topDepositsDTO.setMobile(topUserDTOS.get(position).getMobile());
                GlobalVariables.SELUSER_MOBILE=topDepositsDTO.getMobile();
                editor.putString(GlobalVariables.SELUSER_MOBILE,topDepositsDTO.getMobile());
                editor.putString(GlobalVariables.SELUSER_NAME,topDepositsDTO.getName());
                editor.putString(GlobalVariables.SELUSER_REGDATE,topDepositsDTO.getJoiningTime());
                editor.putString(GlobalVariables.SELUSER_ACTIVE,topDepositsDTO.getActiveDate());
                editor.putString(GlobalVariables.SELUSER_WALLETBAL,topDepositsDTO.getWallet());
                editor.putString(GlobalVariables.SELUSER_REFERCODE,topDepositsDTO.getUserReferalCode());
                editor.apply();
                Log.d(TAG, "onClick: "+GlobalVariables.SELUSER_MOBILE+preferences.getString(GlobalVariables.SELUSER_MOBILE,""));

                context.startActivity(new Intent(context, HistoryActivity.class));
            }
        });


        holder.ivCopyId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method.copyToclipboard1(context, holder.tvDepoDetail);
                holder.ivCopyId.setColorFilter(ContextCompat.getColor(context, R.color.transparent));
                holder.ivCopyId.setColorFilter(ContextCompat.getColor(context, R.color.successColor));

            }
        });

        holder.ivCopyorderId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method.copyToclipboard1(context, holder.tvDepoOrderId);
                holder.ivCopyorderId.setColorFilter(ContextCompat.getColor(context, R.color.transparent));
                holder.ivCopyorderId.setColorFilter(ContextCompat.getColor(context, R.color.successColor));

            }
        });

        switch (topDepositsDTO.getTxnStatus()) {
            case "1":
                // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.tvUserStatus.setText("Verified");
              /*  holder.enableBtn.setClickable(false);
                holder.disableBtn.setClickable(true);*/
                holder.tvUserStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.flipsucc));
                holder.lltvReqDate.setVisibility(View.VISIBLE);
                holder.lltvUpdateDate.setVisibility(View.VISIBLE);


                break;
            case "0":
                // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.tvUserStatus.setText("Pending");
                holder.enableBtn.setClickable(true);
                holder.disableBtn.setClickable(true);
                holder.tvUserStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.warningColor));
                holder.lltvReqDate.setVisibility(View.VISIBLE);
                holder.lltvUpdateDate.setVisibility(View.GONE);

                break;
            case "2":
                // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.tvUserStatus.setText("Cancelled");
              /*  holder.enableBtn.setClickable(true);
                holder.disableBtn.setClickable(false);*/
                holder.tvUserStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.errorColor));
                holder.lltvReqDate.setVisibility(View.VISIBLE);
                holder.lltvUpdateDate.setVisibility(View.VISIBLE);

                break;
            case "4":
                // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.tvUserStatus.setText("Processing");
                holder.tvUserStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.browser_actions_bg_grey));
                holder.lltvReqDate.setVisibility(View.VISIBLE);
                holder.lltvUpdateDate.setVisibility(View.VISIBLE);
                break;
        }

        if((topDepositsDTO.getPayMentMode().equals("2")) & (topDepositsDTO.getPayMode().equals("2"))) {
            // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
           /* holder.tvDepoMethod.setText("Wallet Payment");*/
           // holder.tvDepoDetail.setText(topDepositsDTO.getOrderId());
            holder.viewImg.setVisibility(View.GONE);
        }else  {
            // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.tvDepoMethod.setText("Online Payment");
          //  holder.tvDepoDetail.setText(topDepositsDTO.getOrderId());
            holder.viewImg.setVisibility(View.GONE);
        }

        if(!topDepositsDTO.getImg().equals("")) {
            Log.d("KINGSN", "onBindViewHolder: "+topDepositsDTO.getImg());
            Glide.with(context)
                    .load(topDepositsDTO.getImg())
                    .placeholder(R.drawable.round_bg)
                    .dontAnimate()
                    .centerInside()
                    .fitCenter()
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.idImage);

            Glide.with(context)
                    .load(topDepositsDTO.getImg())
                    .placeholder(R.drawable.round_bg)
                    .dontAnimate()
                    .centerInside()
                    .fitCenter()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.idImage1);

        }else{
            Glide.with(context)
                    .load(R.drawable.ic_passbook)
                    .dontAnimate()
                    .centerInside()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.idImage);

            Glide.with(context)
                    .load(R.drawable.ic_passbook)
                    .dontAnimate()
                    .centerInside()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.idImage1);

        }

        switch (topDepositsDTO.getTxnType()) {
            case "5":


                holder.tvTopTitle3.setText("Withdraw From ID  Details");
                holder.tvDepoWebsite.setText(topDepositsDTO.getIdWebsite());
                holder.tvDepoUsername.setText(topDepositsDTO.getIdUsername());
                holder.tvDepoAmount.setText(topDepositsDTO.getAmount());
                holder.tvDepoOrderId.setText(topDepositsDTO.getOrderId());
                holder.tvDepoMethod1.setText("Withdraw From ID");


                break;
            case "6":


                holder.tvTopTitle3.setText("Withdraw From Wallet Details");
                holder.tvDepoWebsite.setText(topDepositsDTO.getName());
                holder.tvDepoUsername.setText(topDepositsDTO.getUserReferalCode());
                holder.tvDepoAmount.setText(topDepositsDTO.getAmount());
                holder.tvDepoOrderId.setText(topDepositsDTO.getOrderId());
                holder.tvDepoMethod1.setText("Withdraw From Wallet");

                break;


        }

        switch (topDepositsDTO.getPayMode()) {
            case "0":

              holder.tvDepoDetail.setText("Withdraw From ID Request Details");

                break;
            case "1":
                holder.tvDepoDetail.setText("Withdraw From ID Request Details");
                break;


        }
    }

    private void updateonClick(int i) {
        if(method.adminDTO.getManageWithdrawal().equals("2")) {
            if (i == 1) {
                method.params.clear();
                method.params.put("WidthrawalsUpdate", "WidthrawalsUpdate");
                method.params.put("userMobile", (topDepositsDTO.getUserMobile()));
                method.params.put("txnStatus", "1");
                method.params.put("orderId", (topDepositsDTO.getOrderId()));

                method.updateSetting((Activity) context, RestAPI.USER_UPDATE);

            }
            if (i == 2) {
                method.params.clear();
                method.params.put("WidthrawalsUpdate", "WidthrawalsUpdate");
                method.params.put("userMobile", (topDepositsDTO.getUserMobile()));
                method.params.put("txnStatus", "2");
                method.params.put("orderId", (topDepositsDTO.getOrderId()));

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
        de.hdodenhof.circleimageview.CircleImageView idImage,idImage1;
        TextView idName,idMobile,tvUserStatus,tvUserReferCode, tvDepoDetails,tvUserWallet,tvViewDetails,tvDepoMethod,tvDepoDetail;
        TextView tvDepoUsername,tvDepoAmount,tvDepoOrderId,tvReqDate,tvDepoMethod1,tvUpdateDate,tvTopTitle3,tvDepoWebsite,bakViewDetails,tvUserWid;
        ImageView ivLink,ivCopyId,ivCopyorderId,viewImg;
        TextInputEditText depositCoinsEt;
        EasyFlipView easyFlipView;
        LinearLayout depolinear,lltvReqDate,lltvUpdateDate;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idName=itemView.findViewById(R.id.tvTopTitle);
           idMobile=itemView.findViewById(R.id.tvTopUserPhone);
            tvUserStatus=itemView.findViewById(R.id.tvUserStatus);
            tvUserReferCode = itemView.findViewById(R.id.tvUserReferCode);
            tvUserWallet = itemView.findViewById(R.id.tvUserWallet);
            enableBtn = itemView.findViewById(R.id.enableBtn);
            disableBtn=itemView.findViewById(R.id.disableBtn);
            historyBtn=itemView.findViewById(R.id.historyBtn);
            tvViewDetails=itemView.findViewById(R.id.tvViewDetails);
            easyFlipView=itemView.findViewById(R.id.flipView);
            tvDepoMethod=itemView.findViewById(R.id.tvDepoMethod);
            tvDepoDetail=itemView.findViewById(R.id.tvDepoDetail);
            tvDepoDetails=itemView.findViewById(R.id.tvDepoDetails);
            tvDepoUsername=itemView.findViewById(R.id.tvDepoUsername);
            tvDepoAmount=itemView.findViewById(R.id.tvDepoAmount);
            tvDepoOrderId=itemView.findViewById(R.id.tvDepoOrderId);
           bakViewDetails=itemView.findViewById(R.id.bakViewDetails);
            depolinear=itemView.findViewById(R.id.depolinear);
            tvDepoWebsite=itemView.findViewById(R.id.tvDepoWebsite);
            tvTopTitle3=itemView.findViewById(R.id.tvTopTitle3);
            idImage=itemView.findViewById(R.id.idImage);
            viewImg=itemView.findViewById(R.id.viewImg);
            tvReqDate=itemView.findViewById(R.id.tvReqDate);
            tvUpdateDate=itemView.findViewById(R.id.tvUpdateDate);
            lltvReqDate=itemView.findViewById(R.id.lltvReqDate);
            lltvUpdateDate=itemView.findViewById(R.id.lltvUpdateDate);
            tvUserWid=itemView.findViewById(R.id.tvUserWid);
            tvDepoMethod1=itemView.findViewById(R.id.tvDepoMethod1);
            idImage1=itemView.findViewById(R.id.idImage1);
            ivCopyId=itemView.findViewById(R.id.ivCopyId);
            ivCopyorderId=itemView.findViewById(R.id.ivCopyorderId);
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
        topDepositsDTOS.clear();
        if (charText.length() == 0) {
            topDepositsDTOS.addAll(userBookingsList);
        } else {
            for (HomeTopDepositsDTO userBooking : userBookingsList) {
                if ((userBooking.getIdName().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getOrderId().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (userBooking.getAmount().toLowerCase(Locale.getDefault())
                        .contains(charText))) {
                    topDepositsDTOS.add(userBooking);
                }
            }
        }
        notifyDataSetChanged();
    }
}
