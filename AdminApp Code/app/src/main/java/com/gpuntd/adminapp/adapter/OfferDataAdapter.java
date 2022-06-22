package com.gpuntd.adminapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Base64;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cocosw.bottomsheet.BottomSheet;
import com.google.android.material.textfield.TextInputEditText;
import com.gpuntd.adminapp.Models.Create_ID_Data;
import com.gpuntd.adminapp.Models.Offer_Data;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.ImageCompression;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class OfferDataAdapter extends RecyclerView.Adapter<OfferDataAdapter.MyViewHolder> {

    private static final String TAG ="KINGSN";
    private List<Offer_Data> mContacts = null;
    List<Offer_Data> offer_data;
    public ArrayList<Offer_Data> offer_data1;
    public static Offer_Data offerData;
    public static Context context;
    OfferDataAdapter binding;
    public Integer positionn=null;
    // int count=0;.
    public static TextInputEditText etEditAppOfferTitle,etEditOfferPicUrl,etEditOfferId;
    Switch spinnerOfferStatus;
    public static ImageView idImage;
    public BottomSheet.Builder builder;
    public int PICK_FROM_CAMERA = 1;
    public static int PICK_FROM_GALLERY = 2;
    public static int CROP_CAMERA_IMAGE = 3;
    public static int CROP_GALLERY_IMAGE = 4;
    public static Uri picUri;
    public String encodedImage;
    public static String pathOfImage;
    public static String AStatus;
    public static Bitmap bm;
    public static ImageCompression imageCompression;
    public static File file;
    public static Method method;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences, preferences1;

    public OfferDataAdapter(List<Offer_Data> offer_data, Context context) {
        this.offer_data = offer_data;
        OfferDataAdapter.context = context;
        this.mContacts = offer_data;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Log.i(TAG, "onCreateViewHolder: " + count++);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_create_app_offer, parent, false);
        method=new Method(parent.getContext());
        preferences = parent.getContext().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull OfferDataAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        offerData = offer_data.get(position);
        positionn=position;
        holder.setIsRecyclable(false);
        //Picasso.with(context)
        //      .load(listData
        //            .getImage_url())
        //  .into(holder.imageView);
        if (offerData.getOfferStatus().equals("1"))  {
            holder.tvAppOfferStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.flipsucc));
            holder.tvAppOfferStatus.setText("Enabled");


        }else{
            holder.tvAppOfferStatus.setText("Disabled");
            holder.tvAppOfferStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.themeRed));
        }

        holder.idName.setText(offerData.getTitle());

        if(!offerData.getBody().equals("")) {
            Log.d("KINGSN", "onBindViewHolder: "+offerData.getBody());
            Glide.with(context)
                    .load(offerData.getBody())
                    .placeholder(R.drawable.ic_user2)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.idImage);

        }

        holder.cDate.setText(offerData.getOfCreatedDate());

        holder.editOfferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerData = offer_data.get(position);
                Log.d(TAG, "onClick: ");
               /*  GlobalVariables.offerData = offerData;
                 editor.apply();*/
                GlobalVariables.offerType ="edit";
                setupUpdateDialog(v);
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
        return offer_data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout demoSection;
        Button arrowDownBtn, btnCreateId;
        ImageView idImage,editOfferBtn;
        TextView idName,cDate,tvAppOfferStatus;
        ImageView ivLink,ivCopyId,ivCopyPass;
        TextInputEditText etEditAppOfferTitle,etEditOfferPicUrl,etEditOfferId;
        Switch spinnerOfferStatus;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idName=itemView.findViewById(R.id.tvTitleAppOffer);
            cDate=itemView.findViewById(R.id.tvAppOfferCreatedDate);
            idImage=itemView.findViewById(R.id.ivAppOffer);
            editOfferBtn=itemView.findViewById(R.id.editOfferBtn);
            tvAppOfferStatus=itemView.findViewById(R.id.tvAppOfferStatus);

         /*   arrowDownBtn.setOnClickListener(view -> {
                demoSection.animate().
                        translationY(view.getHeight())
                        .setDuration(300);
                demoSection.setVisibility((demoSection.getVisibility() == View.VISIBLE)
                        ? View.GONE
                        : View.VISIBLE);

            });*/

        }
    }

   public static void setupUpdateDialog(View v) {
       AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
       View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_app_banner, v.findViewById(R.id.editoffer));
       builder.setView(view);
       final AlertDialog alertDialog = builder.create();
       etEditAppOfferTitle = view.findViewById(R.id.etEditAppOfferTitle);
       etEditOfferPicUrl = view.findViewById(R.id.etEditOfferPicUrl);
       etEditOfferId = view.findViewById(R.id.etEditOfferId);
       idImage = view.findViewById(R.id.ivAppOffer);

       Switch sw = (Switch) view.findViewById(R.id.spinnerOfferStatus);

       if(GlobalVariables.offerType=="edit"){
           etEditOfferId.setText(offerData.getId());
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

           if (!offerData.getOfferStatus().equals("1")) {
               sw.getTextOff();
               sw.setSelected(false);
               sw.setChecked(false);
               AStatus="2";
           } else {
               sw.setSelected(true);
               AStatus="1";
               sw.setChecked(true);
           }

           etEditAppOfferTitle.setText(offerData.getTitle());

           etEditOfferPicUrl.setText(offerData.getBody());

           //  etEditDemoId.setText(createIdData.getDemoId());
           if (!offerData.getBody().equals("")) {
               Glide.with(view.getContext())
                       .load(offerData.getBody())
                       .placeholder(R.drawable.dummyuser_image)
                       .dontAnimate()
                       .diskCacheStrategy(DiskCacheStrategy.ALL)
                       .into((ImageView) idImage);

           } else {
               Glide.with(view.getContext())
                       .load(view.getContext().getResources().getDrawable(R.drawable.ic_profile))
                       .placeholder(R.drawable.dummyuser_image)
                       .dontAnimate()
                       .diskCacheStrategy(DiskCacheStrategy.ALL)
                       .into((ImageView) idImage);
           }
       }


       view.findViewById(R.id.btnSaveAppOffer).setOnClickListener(view1 -> {
           if (etEditAppOfferTitle.getText().toString().equals("")) {
               etEditAppOfferTitle.setError("Field Is Required");
           }/*if(etEditCreateIdWebsite.getText().toString().equals("")){
                etEditCreateIdWebsite.setError("Field Is Required");
            }if(etEditDemoId.getText().toString().equals("")){
                etEditDemoId.setError("Field Is Required");
            }if(etEditDemoPassword.getText().toString().equals("")){
                etEditDemoPassword.setError("Field Is Required");
            }if(etEditDemoLink.getText().toString().equals("")){
                etEditDemoLink.setError("Field Is Required");
            }if(etEditMinimumRefill.getText().toString().equals("")){
                etEditMinimumRefill.setError("Field Is Required");
            }if(etEditMinimumWithdrawal.getText().toString().equals("")){
                etEditMinimumWithdrawal.setError("Field Is Required");
            }if(etEditMinimumMaintainBalance.getText().toString().equals("")){
                etEditMinimumMaintainBalance.setError("Field Is Required");
            }if(etEditMaximumWithdrawal.getText().toString().equals("")){
                etEditMaximumWithdrawal.setError("Field Is Required");
            }if(etEditCreatedDate.getText().toString().equals("")) {
                etEditCreatedDate.setError("Field Is Required");
            }*/ else {
               try {
                   //onsubmit(view.getContext());
                   onSubmit(method.bm);
               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }

       });



       // view.findViewById(R.id.btnChooseFile);
       view.findViewById(R.id.btnOfferChooseFile).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               method.getGalleryImage((Activity) context);

           }
       });

       view.findViewById(R.id.dilogclose).setOnClickListener(new View.OnClickListener() {
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


    public static void onSubmit(Bitmap bm) throws JSONException {

        if (Method.file != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            try {
                method.jsonObject = new JSONObject();
                String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());
                method.jsonObject.put("imagename", imgname);
                method.jsonObject.put("image", encodedImage);
                //  Log.e("Image name", etxtUpload.getText().toString().trim());
                method.jsonObject.put("Uid",etEditOfferId.getText().toString());
                method.jsonObject.put("offerStatus",AStatus);
                method.jsonObject.put("offertitle",etEditAppOfferTitle.getText().toString());



            } catch (JSONException e) {
                Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }
        if (Method.file == null){
            try {
                method.jsonObject = new JSONObject();
                //  Log.e("Image name", etxtUpload.getText().toString().trim());
                method.jsonObject.put("Uid",etEditOfferId.getText().toString());
                method.jsonObject.put("offerStatus",AStatus);
                method.jsonObject.put("offertitle",etEditAppOfferTitle.getText().toString());


            } catch (JSONException e) {
                // Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }

        method.updateWithImg((Activity) context, RestAPI.OFFER_UPDATE);

    }


}
