package com.gpuntd.adminapp.adapter;
/**
 * Created by VARUN on 01/01/19.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gpuntd.adminapp.Models.ChatListDTO;
import com.gpuntd.adminapp.OneTwoOneChat;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.databinding.AdapterChatListBinding;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {

    private static final String TAG = "KINGSN";
    Context mContext;
    ArrayList<ChatListDTO> chatList;
    AdapterChatListBinding binding;
    LayoutInflater layoutInflater;
    Method method;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    long now = System.currentTimeMillis();


    public ChatListAdapter(Context mContext, ArrayList<ChatListDTO> chatList) {
        this.mContext = mContext;
        this.chatList = chatList;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_chat_list, parent, false);
        method=new Method(parent.getContext());
        preferences = parent.getContext().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        return new MyViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.binding.userName.setText(chatList.get(position).getName());
        holder.binding.message.setText(chatList.get(position).getMessage());

        try {
           // holder.binding.time.setText(method.convertTimestampDate((chatList.get(position).getSend_at()));
            holder.binding.time.setText(method.convertTimestampDateToTime(method.correctTimestamp((chatList.get(position).getSend_at()))));
            //String timeAgo = TimeAgo.getTimeAgo(TIMESTAMP);
            holder.binding.time.setText(Method.getTimeAgo(Long.parseLong(chatList.get(position).getSend_at())));
            Log.d(TAG, "onBindViewHolder: "+Method.getTimeAgo(Long.parseLong(chatList.get(position).getSend_at())));


        } catch (Exception e) {
            e.printStackTrace();
        }
       /* Glide.with(mContext).
                load(chatList.get(position).getArtistImage())
                .placeholder(R.drawable.dummyuser_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.binding.IVprofile);*/
        if(chatList.get(position).getStatus().equals("1")){
          // holder.binding.message.setDrawable(ContextCompat.getColor(mContext, R.color.warningColor));
            if(!chatList.get(position).getSend_by().equals(GlobalVariables.profileuser.getAdmin_mobile())){
                holder.binding.newMsg1.setText(chatList.get(position).getUnreadMsgNo());
               // holder.binding.newMsg1.setText("69");
                holder.binding.newMsg.setVisibility(View.VISIBLE);
            }
           //holder.binding.message.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(mContext,R.drawable.ring_green), null);

       }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, OneTwoOneChat.class);
                in.putExtra(GlobalVariables.userMobile, chatList.get(position).getUser_id());
                in.putExtra(GlobalVariables.userMobile1, chatList.get(position).getUser_id());
                in.putExtra(GlobalVariables.userName, chatList.get(position).getName());
                method.preferences.setValue(GlobalVariables.userName,chatList.get(position).getName());
                editor.putString(GlobalVariables.sendBy, chatList.get(position).getUser_id());
                editor.putString(GlobalVariables.sendTo, GlobalVariables.profileuser.getAdmin_mobile());
                editor.apply();
                mContext.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {

        return chatList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        AdapterChatListBinding binding;

        public MyViewHolder(AdapterChatListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}