package com.gpuntd.adminapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gpuntd.adminapp.MainActivity;
import com.gpuntd.adminapp.Models.Notification_data;
import com.gpuntd.adminapp.R;

import java.util.ArrayList;
import java.util.List;


public class NotificationDataAdapter extends RecyclerView.Adapter<NotificationDataAdapter.MyViewHolder> {

    //private static final String TAG="RecyclerAdapter";
    List<Notification_data> notification_data;
    private final Context context;
    NotificationDataAdapter notificationDataAdapter;
   private NotificationDataAdapterListner notificationDataAdapterrListner;
    // int count=0;
    public NotificationDataAdapter(List<Notification_data> notification_data, Context context) {
        this. notification_data= notification_data;
        this.context = context;
    }

    public NotificationDataAdapter(ArrayList<Notification_data> notification_data, Context context, NotificationDataAdapterListner notificationDataAdapterListner) {
        this.notification_data = notification_data;
        this.context = context;
        this.notificationDataAdapterrListner = notificationDataAdapterListner;
        //sharedPrefrence = SharedPrefrence.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Log.i(TAG, "onCreateViewHolder: " + count++);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_notification_item, parent, false);
        return new MyViewHolder(view);
    }


    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Notification_data notificationData = notification_data.get(position);

        holder.amount.setText(notificationData.getnAmount());
        holder.tvNotiTitle.setText(notificationData.getnTitle());
        holder.tvNotiDetails.setText(notificationData.getnMessage());
        holder.txnDate.setText(notificationData.getnCreatedDate());

     /*   if(!notificationData.getImage().equals("")) {
            Log.d("KINGSN", "onBindViewHolder: "+notificationData.getImage());
            Glide.with(context)
                    .load(passbookData.getImage())
                    .placeholder(R.drawable.round_bg)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.txnimage);

        }
        if (passbookData.getTxnType().equals("0"))  {

            holder.idName.setText("Create ID");
            holder.idName1.setText("Deposit to Wallet");
            holder.vtxnl.setVisibility(View.VISIBLE);
            holder.idtxnl.setVisibility(View.VISIBLE);
            if(passbookData.getStatus().equals("0")){
                holder.tvStatus.setText("Pending");
                holder.tvStatus1.setText("Pending");
                holder.amount.setText("-"+passbookData.getAmount());
                holder.amount1.setText("+"+passbookData.getAmount());
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.warningColor));
                holder.tvStatus1.setTextColor(ContextCompat.getColor(context, R.color.warningColor));
                holder.txnDate.setText(passbookData.getCreatedDate());
                holder.txnDate1.setText(passbookData.getApprovalDate());
            }else if (passbookData.getStatus().equals("1")){
                holder.tvStatus.setText("Success");
                holder.tvStatus1.setText("Success");
                holder.amount.setText("-"+passbookData.getAmount());
                holder.amount1.setText("+"+passbookData.getAmount());
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.successColor));
                holder.tvStatus1.setTextColor(ContextCompat.getColor(context, R.color.successColor));
                holder.txnDate.setText(passbookData.getCreatedDate());
                holder.txnDate1.setText(passbookData.getApprovalDate());
            }else if (passbookData.getStatus().equals("2")) {
                holder.tvStatus.setText("Rejected");
                holder.tvStatus1.setText("Rejected");
                holder.amount.setText("-" + passbookData.getAmount());
                holder.amount1.setText("+" + passbookData.getAmount());
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.errorColor));
                holder.tvStatus1.setTextColor(ContextCompat.getColor(context, R.color.errorColor));
                holder.txnDate.setText(passbookData.getCreatedDate());
                holder.txnDate1.setText(passbookData.getApprovalDate());

            }
           // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));


          *//*  if(!passbookData.getImage().equals("")) {
                Log.d("KINGSN", "onBindViewHolder: "+passbookData.getImage());
                Glide.with(context)
                        .load(passbookData.getImage())
                        .placeholder(R.drawable.round_bg)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.txnimage);

            }*//*

        }else if(passbookData.getTxnType().equals("3")){
            holder.idName.setText("Change ID Password");
            holder.idName1.setText(passbookData.getUsername());
            holder.vtxnl.setVisibility(View.GONE);
            holder.idtxnl.setVisibility(View.GONE);
            holder.amount.setVisibility(View.GONE);
            holder.subtitle1.setText(passbookData.getUsername());

            if(passbookData.getStatus().equals("0")) {
                holder.tvStatus.setText("Pending");
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.warningColor));
                holder.txnDate.setText(passbookData.getCreatedDate());
            }else if(passbookData.getStatus().equals("1")) {
                holder.tvStatus.setText("Approved");
                holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.successColor));
                holder.txnDate.setText(passbookData.getApprovalDate());
            }
        }
*/

        holder.btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MainActivity.class);
                if((!notificationData.getNorderId().equals(""))){
                    intent.putExtra("norderId",notificationData.getNorderId());
                    intent.putExtra("nType",notificationData.getnType());
                    intent.putExtra("namount",notificationData.getnAmount());
                    intent.putExtra("ntiTitle",notificationData.getnTitle());
                    intent.putExtra("nMessage",notificationData.getnMessage());
                    intent.putExtra("nCreatedDate",notificationData.getnCreatedDate());
                }

                context.startActivity(intent);
               // passbookDataAdapterListner.passbookDataAdapterListner(passbookData.get(getAdapterPosition()), getAdapterPosition());
            }
        });

    }
    public interface NotificationDataAdapterListner {
        void notificationDataAdapterListner(Notification_data dataPosition, int position);
    }
    @Override
    public int getItemCount() {
        return notification_data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout notiViewDetails;
        Button btnOpenDetails,btnViewDetails;
        CardView passbookCard;
        View vtxnl;
        de.hdodenhof.circleimageview.CircleImageView txnimage;
        TextView tvNotiTitle,tvNotiDetails,idWebsite,tvStatus,idName1,idWebsite2,txnDate1,txnDate,tvStatus1,amount1,amount,date, name,subtitle1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNotiTitle=itemView.findViewById(R.id.tvNotiTitle);
            tvNotiDetails=itemView.findViewById(R.id.tvNotiDetails);
            amount=itemView.findViewById(R.id.amount);
            txnDate=itemView.findViewById(R.id.txnDate);
            btnOpenDetails = itemView.findViewById(R.id.btnOpenDetails);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
            notiViewDetails= itemView.findViewById(R.id.notiViewDetails);

            btnOpenDetails.setOnClickListener(view -> {
               /* notiViewDetails.animate().
                        translationY(view.getHeight())
                        .setDuration(100);*/
                notiViewDetails.setVisibility((notiViewDetails.getVisibility() == View.VISIBLE)
                        ? View.GONE
                        : View.VISIBLE);

            });
        }
    }
}
