package com.gpuntd.adminapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gpuntd.adminapp.Models.GetCommentDTO;
import com.gpuntd.adminapp.Models.Profileuser;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;


import java.util.ArrayList;


public class
AdapterViewComment extends BaseAdapter {
    private static final String TAG ="KINGSN" ;
    private Context mContext;
    private ArrayList<GetCommentDTO> getCommentDTOList;
    private Profileuser userDTO;
    private ImageView ivImageD;
    private TextView tvCloseD, tvNameD;
    private Dialog dialogImg;
    private Method method;

    public AdapterViewComment(Context mContext, ArrayList<GetCommentDTO> getCommentDTOList, Profileuser userDTO) {
        this.mContext = mContext;
        this.getCommentDTOList = getCommentDTOList;
        this.userDTO = userDTO;


    }

    @Override
    public int getCount() {
        return getCommentDTOList.size();
    }

    @Override
    public Object getItem(int postion) {
        return getCommentDTOList.get(postion);
    }

    @Override
    public long getItemId(int postion) {
        return postion;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        method=new Method(parent.getContext());
        //ViewHolder holder = new ViewHolder();
        if (!getCommentDTOList.get(position).getSend_by().equalsIgnoreCase(method.adminDTO.getAdminMobile())) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_view_comment, parent, false);

        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_view_comment_my, parent, false);

        }

        TextView textViewMessage = (TextView) view.findViewById(R.id.textViewMessage);
        TextView textViewTime = (TextView) view.findViewById(R.id.textViewTime);
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        ImageView ivView = (ImageView) view.findViewById(R.id.ivView);

        if (getCommentDTOList.get(position).getChat_type().equalsIgnoreCase("2")) {
            ivView.setVisibility(View.VISIBLE);
        } else {
            ivView.setVisibility(View.GONE);
        }

        Glide.with(mContext).
                load(getCommentDTOList.get(position).getImage())
                .placeholder(R.drawable.dummyuser_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivView);

        textViewMessage.setText(getCommentDTOList.get(position).getMessage());

     //  textViewMessage.setBackgroundTintMode(textViewMessage.setBackground(R.color.successColor));

          if(getCommentDTOList.get(position).getSend_by().equals(GlobalVariables.profileuser.getAdmin_mobile())){
              if(getCommentDTOList.get(position).getStatus().equals("1")){
              textViewMessage.setCompoundDrawablesWithIntrinsicBounds(0 ,0, R.drawable.ic_done_all_black, 0);
              setTextViewDrawableColor(textViewMessage,R.color.black_off);
          }else{
                  Log.d(TAG, "getView: "+getCommentDTOList.get(position).getStatus());
                  textViewMessage.setCompoundDrawablesWithIntrinsicBounds(0 ,0, R.drawable.ic_done_all_black, 0);
                  setTextViewDrawableColor(textViewMessage,R.color.successColor);
              }

      }
        tvName.setText(getCommentDTOList.get(position).getSender_name());

        ivView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogshare(position);
            }
        });
        try {

            textViewTime.setText(method.convertTimestampDateToTime(method.correctTimestamp((getCommentDTOList.get(position).getDate()))));


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    public void dialogshare(int pos) {
        dialogImg = new Dialog(mContext, android.R.style.Theme_Dialog);
        dialogImg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogImg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogImg.setContentView(R.layout.dailog_image_view);


        tvCloseD = (TextView) dialogImg.findViewById(R.id.tvCloseD);
        tvNameD = (TextView) dialogImg.findViewById(R.id.tvNameD);

        ivImageD = (ImageView) dialogImg.findViewById(R.id.ivImageD);
        dialogImg.show();
        dialogImg.setCancelable(false);

        Glide.with(mContext).
                load(getCommentDTOList.get(pos).getImage())
                .placeholder(R.drawable.dummyuser_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImageD);

        tvNameD.setText(getCommentDTOList.get(pos).getSender_name());
        tvCloseD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogImg.dismiss();

            }
        });

    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
            }
        }
    }

}
