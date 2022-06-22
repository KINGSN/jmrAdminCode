package com.gpuntd.adminapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.textfield.TextInputEditText;
import com.gpuntd.adminapp.Models.Create_ID_Data;
import com.gpuntd.adminapp.Models.HomeGraphDTO;
import com.gpuntd.adminapp.Models.HomeTopUserDTO;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class TopUserDataAdapter extends RecyclerView.Adapter<TopUserDataAdapter.MyViewHolder> {


    private static final String TAG ="KINGSN" ;
    //private static final String TAG="RecyclerAdapter";
    public List<HomeTopUserDTO> topUserDTOS;
    public final Context context;
    TopUserDataAdapter binding;
    private SharedPreferences preferences, preferences1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Method method;
    AlertDialog alertDialog;

    // int count=0;
    public TopUserDataAdapter(List<HomeTopUserDTO> topUserDTOS, Context context) {
        this.topUserDTOS = topUserDTOS;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Log.i(TAG, "onCreateViewHolder: " + count++);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_top_user_home, parent, false);
        method=new Method(parent.getContext());

        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final HomeTopUserDTO topUserDTO = topUserDTOS.get(position);
        holder.setIsRecyclable(false);

        holder.idName.setText(topUserDTO.getName());
        holder.idMobile.setText(topUserDTO.getMobile());
        holder.tvTopTimestamp.setText(topUserDTO.getJoiningTime());


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
        ImageView enableBtn,disableBtn,historyBtn, btnCreateId;
        de.hdodenhof.circleimageview.CircleImageView idImage;
        TextView idName,idMobile,tvUserStatus,tvUserReferCode, tvTopTimestamp, name,tvDemoID,tvDemoPass;
        ImageView ivLink,ivCopyId,ivCopyPass;
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

        }
    }


}
