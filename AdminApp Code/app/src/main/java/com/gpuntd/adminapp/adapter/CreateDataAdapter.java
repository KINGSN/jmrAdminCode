package com.gpuntd.adminapp.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cocosw.bottomsheet.BottomSheet;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gpuntd.adminapp.CreateIdEditActivity;
import com.gpuntd.adminapp.CreateIdModeActivity;
import com.gpuntd.adminapp.Models.Create_ID_Data;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.Ex;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.ImageCompression;
import com.gpuntd.adminapp.Util.MainFragment;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static androidx.core.app.ActivityCompat.startActivityForResult;


public class CreateDataAdapter extends RecyclerView.Adapter<CreateDataAdapter.MyViewHolder> {

    private static final String TAG ="KINGSN";
    private List<Create_ID_Data> mContacts = null;
    public ArrayList<Create_ID_Data> create_data;
    public static Context context;
    CreateDataAdapter binding;
    public Integer positionn;
    public static Create_ID_Data createIdData;
    public static Method method;
    // int count=0;
    public static TextInputEditText etEditCreateIdName,etEditCreateIdWebsite,etEditDemoId,etEditDemoPassword,etEditDemoLink
            ,etEditMinimumRefill,etEditMinimumWithdrawal,etEditMinimumMaintainBalance,etEditMaximumWithdrawal,etEditCreatedDate;
    public static  CircleImageView etEditCreateIdLogo;
    public BottomSheet.Builder builder;
    public int PICK_FROM_CAMERA = 1;
    public static int PICK_FROM_GALLERY = 2;
    public static int CROP_CAMERA_IMAGE = 3;
    public static int CROP_GALLERY_IMAGE = 4;
    public static Uri picUri;
    public String encodedImage;
    public static String pathOfImage;
    public static String AStatus="1";
    public static Bitmap bm;
    public static ImageCompression imageCompression;
    public static File file;
    CreateIdModeActivity createIdModeActivity;

    private HashMap<String, File> paramsFile = new HashMap<>();
    HashMap<String, String> values = new HashMap<>();
    JSONObject jsonObject;
    RequestQueue rQueue;
    String encodeImageString;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences, preferences1;

    public CreateDataAdapter(List<Create_ID_Data> create_data, Context context) {
        this.create_data = (ArrayList<Create_ID_Data>) create_data;
        CreateDataAdapter.context = context;
        this.mContacts = create_data;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Log.i(TAG, "onCreateViewHolder: " + count++);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_create_id_item, parent, false);
        method=new Method(parent.getContext());
        preferences = parent.getContext().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
         createIdData = create_data.get(position);
          positionn=position;
        holder.setIsRecyclable(false);
        //Picasso.with(context)
        //      .load(listData
        //            .getImage_url())
        //  .into(holder.imageView);
        if (createIdData.getIdStatus().equals("1"))  {
           // holder.idName.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.idName.setText(createIdData.getIdName());
            holder.idWebsite.setText(createIdData.getIdWebsite());
            holder.idCreatedAt.setText(createIdData.getIdCreatedDate());
            if(!createIdData.getIdImage().equals("")) {
                Log.d("KINGSN", "onBindViewHolder: "+createIdData.getIdImage());
                Glide.with(context)
                        .load(createIdData.getIdImage())
                        .placeholder(R.drawable.round_bg)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.idImage);

            }
            holder.tvDemoID.setText(createIdData.getDemoId());
            holder.tvDemoPass.setText(createIdData.getDemoPass());
            holder.idCreatedAt.setText(createIdData.getIdCreatedDate());
            String url=createIdData.getDemoLink();
            holder.ivLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    open(url );
                }
            });

        }


        MyViewHolder.btnCreateId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createIdData=create_data.get(position);
                GlobalVariables.createIdData = create_data.get(position);
                editor.apply();
                //setupUpdateDialog(v);

                Intent intent = new Intent(context, CreateIdEditActivity.class);
                intent.putStringArrayListExtra("createIdData",  createIdData);
               // intent.putExtra("Position", position);
                intent.putExtra("createId", createIdData.getId());
                intent.putExtra("idimageurl", createIdData.getIdImage());
                intent.putExtra("idname", createIdData.getIdName());
                intent.putExtra("idwebsite", createIdData.getIdWebsite());
                intent.putExtra("minRefill", createIdData.getMinRefill());
                intent.putExtra("minWithdrawal", createIdData.getMinWithdrawal());
                intent.putExtra("minMaintainBal", createIdData.getMinMaintainBal());
                intent.putExtra("maxWithdrawal", createIdData.getMaxWithdrawal());
                if (method.adminDTO.getManageCreateIDList().equals("2")) {
                    context.startActivity(intent);
                }else{
                    method.showToasty((Activity) context,"2","You Dont Have This Access \n Please" +
                            " Ask Super To Grant Full Access ");
                }
            }
        });

        holder.ivCopyId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   method.copyToclipboard1(context,holder.tvDemoID);
            }
        });

        holder.ivCopyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  method.copyToclipboard1(context,holder.tvDemoPass);
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
        return create_data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout demoSection;
        public static  Button arrowDownBtn, btnCreateId;
        de.hdodenhof.circleimageview.CircleImageView idImage;
        TextView idName,idWebsite, date, name,tvDemoID,tvDemoPass,idCreatedAt;
        ImageView ivLink,ivCopyId,ivCopyPass;
        TextInputEditText depositCoinsEt;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idName=itemView.findViewById(R.id.idName);
           idWebsite=itemView.findViewById(R.id.idWebsite);
            idImage=itemView.findViewById(R.id.idImage);
            arrowDownBtn = itemView.findViewById(R.id.arrowDown);
            demoSection = itemView.findViewById(R.id.demoSection);
            btnCreateId = itemView.findViewById(R.id.btnCreateId);
            tvDemoID=itemView.findViewById(R.id.tvDemoID);
            tvDemoPass=itemView.findViewById(R.id.tvDemoPass);
            ivLink=itemView.findViewById(R.id.ivLink);
            ivCopyPass=itemView.findViewById(R.id.ivCopyPass);
            ivCopyId=itemView.findViewById(R.id.ivCopyId);
            idCreatedAt=itemView.findViewById(R.id.idCreatedAt);

            arrowDownBtn.setOnClickListener(view -> {
                demoSection.animate().
                        translationY(view.getHeight())
                        .setDuration(300);
                demoSection.setVisibility((demoSection.getVisibility() == View.VISIBLE)
                        ? View.GONE
                        : View.VISIBLE);

            });
        }


    }

    // filter name in Search Bar
    public void filter(String characterText) {
        characterText = characterText.toLowerCase(Locale.getDefault());
        mContacts.clear();
        if (characterText.length() == 0) {
            mContacts.addAll(create_data);
        } else {
            for (Create_ID_Data createIdData: create_data) {
                if (createIdData.getIdName().toLowerCase(Locale.getDefault()).contains(characterText)) {
                    mContacts.add(createIdData);
                }
            }

        }
        notifyDataSetChanged();
    }

    public static void setupUpdateDialog(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_create_id_mode, v.findViewById(R.id.editcId));
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        etEditCreateIdName = view.findViewById(R.id.etEditCreateIdName);
        etEditCreateIdWebsite = view.findViewById(R.id.etEditCreateIdWebsite);
        etEditDemoId = view.findViewById(R.id.etEditDemoId);
        etEditDemoPassword = view.findViewById(R.id.etEditDemoPassword);
        etEditDemoLink = view.findViewById(R.id.etEditDemoLink);
        etEditMinimumRefill = view.findViewById(R.id.etEditMinimumRefill);
        etEditMinimumWithdrawal = view.findViewById(R.id.etEditMinimumWithdrawal);
        etEditMinimumMaintainBalance = view.findViewById(R.id.etEditMinimumMaintainBalance);
        etEditMaximumWithdrawal = view.findViewById(R.id.etEditMaximumWithdrawal);
        etEditCreatedDate = view.findViewById(R.id.etEditCreatedDate);
       TextInputLayout etEditCreatedDatel = view.findViewById(R.id.etEditCreatedDatel);
        TextInputLayout etEditDemoIdl = view.findViewById(R.id.etEditIdl);
        Button btnSaveCreatedId=view.findViewById(R.id.btnSaveCreatedId);
        ImageView close=view.findViewById(R.id.btnCloseDialog);
        ImageView idImage=view.findViewById(R.id.idImage);
        Switch sw = (Switch) view.findViewById(R.id.switch1);
        etEditCreateIdLogo = view.findViewById(R.id.etEditCreateIdLogo);

        etEditCreatedDatel.setVisibility(View.GONE);
        etEditDemoIdl.setVisibility(View.GONE);
        if(GlobalVariables.offerType.equals("edit")){
            etEditCreatedDatel.setVisibility(View.GONE);
            etEditDemoIdl.setVisibility(View.GONE);
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

            etEditCreateIdName.setText(createIdData.getIdName());
            etEditCreateIdWebsite.setText(createIdData.getIdWebsite());
            etEditDemoId.setText(createIdData.getDemoId());
            etEditDemoPassword.setText(createIdData.getDemoPass());
            etEditDemoLink.setText(createIdData.getDemoLink());
            etEditMinimumRefill.setText(createIdData.getMinRefill());
            etEditMinimumWithdrawal.setText(createIdData.getMinWithdrawal());
            etEditMinimumMaintainBalance.setText(createIdData.getMinMaintainBal());
            etEditMaximumWithdrawal.setText(createIdData.getMaxWithdrawal());
            etEditCreatedDate.setText(createIdData.getIdCreatedDate());
            etEditCreatedDate.setVisibility(View.GONE);
            etEditDemoId.setVisibility(View.GONE);
            if(!createIdData.getIdStatus().equals("1")){
                sw.getTextOff();
                sw.setSelected(false);
                sw.setChecked(false);
                AStatus="2";
            } else {
                sw.setSelected(true);
                AStatus="1";
                sw.setChecked(true);
            }




            //  etEditDemoId.setText(createIdData.getDemoId());
            if(!createIdData.getIdImage().equals("")){
                Glide.with(view.getContext())
                        .load(createIdData.getIdImage())
                        .placeholder(R.drawable.dummyuser_image)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into((ImageView) etEditCreateIdLogo);

            }else{
                Glide.with(view.getContext())
                        .load(view.getContext().getResources().getDrawable(R.drawable.ic_profile))
                        .placeholder(R.drawable.dummyuser_image)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into((ImageView) etEditCreateIdLogo);
            }


        }


        view.findViewById(R.id.btnSaveCreatedId).setOnClickListener(view1 -> {
            if(etEditCreateIdName.getText().toString().equals("")){
                etEditCreateIdName.setError("Field Is Required");
            }if(etEditCreateIdWebsite.getText().toString().equals("")){
                etEditCreateIdWebsite.setError("Field Is Required");
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
            }else {
                try {
                    onSubmit(Method.bm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        });



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.btnChooseFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                method.getGalleryImage((Activity) context);

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
                method.jsonObject.put("Uid","");
                method.jsonObject.put("idName",etEditCreateIdName.getText().toString());
                method.jsonObject.put("idStatus",AStatus);
                method.jsonObject.put("idWebsite",etEditCreateIdWebsite.getText().toString());
                method.jsonObject.put("idCreatedAt",etEditCreatedDate.getText().toString());

                method.jsonObject.put("minRefill",etEditMinimumRefill.getText().toString());
                method.jsonObject.put("minWithdrawal",etEditMinimumWithdrawal.getText().toString());
                method.jsonObject.put("maxWithdrawal",etEditMaximumWithdrawal.getText().toString());
                method.jsonObject.put("minMaintainBal",etEditMinimumMaintainBalance.getText().toString());
                method.jsonObject.put("demoId",etEditDemoId.getText().toString());
                method.jsonObject.put("demoPass",etEditDemoPassword.getText().toString());
                method.jsonObject.put("demoLink",etEditDemoLink.getText().toString());



            } catch (JSONException e) {
                Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }
        if (Method.file == null){
            try {
                method.jsonObject = new JSONObject();
                //  Log.e("Image name", etxtUpload.getText().toString().trim());
                method.jsonObject.put("Uid","");
                method.jsonObject.put("idName",etEditCreateIdName.getText().toString());
                method.jsonObject.put("idStatus",AStatus);
                method.jsonObject.put("idWebsite",etEditCreateIdWebsite.getText().toString());
                method.jsonObject.put("idCreatedAt",etEditCreatedDate.getText().toString());

                method.jsonObject.put("minRefill",etEditMinimumRefill.getText().toString());
                method.jsonObject.put("minWithdrawal",etEditMinimumWithdrawal.getText().toString());
                method.jsonObject.put("maxWithdrawal",etEditMaximumWithdrawal.getText().toString());
                method.jsonObject.put("minMaintainBal",etEditMinimumMaintainBalance.getText().toString());
                method.jsonObject.put("demoId",etEditDemoId.getText().toString());
                method.jsonObject.put("demoPass",etEditDemoPassword.getText().toString());
                method.jsonObject.put("demoLink",etEditDemoLink.getText().toString());

            } catch (JSONException e) {
                // Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }

        method.updateWithImg((Activity) context, RestAPI.CREATE_ID_UPDATE);

    }




}
