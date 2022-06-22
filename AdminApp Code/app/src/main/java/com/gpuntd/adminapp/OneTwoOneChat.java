package com.gpuntd.adminapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cocosw.bottomsheet.BottomSheet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gpuntd.adminapp.Models.ChatListDTO;
import com.gpuntd.adminapp.Models.GetCommentDTO;
import com.gpuntd.adminapp.Models.Profileuser;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.ImageCompression;
import com.gpuntd.adminapp.Util.MainFragment;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.adapter.AdapterViewComment;
import com.gpuntd.adminapp.network.NetworkManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class OneTwoOneChat extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{
    //private String TAG = OneTwoOneChat.class.getSimpleName();
    private String TAG = "KINGSN";
    private ListView lvComment;
    private ImageView buttonSendMessage, IVback, emojiButton;
    private AdapterViewComment adapterViewComment;
    private String id = "";
    private ArrayList<GetCommentDTO> getCommentDTOList;
    private  InputMethodManager inputManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EmojiconEditText edittextMessage;
    private  EmojIconActions emojIcon;
    private RelativeLayout relative;
    private Context mContext;
    private ChatListDTO chatListDTO;
    private  HashMap<String, String> parmsGet = new HashMap<>();
    private TextView tvNameHedar;
    private Profileuser userDTO;
    IntentFilter intentFilter = new IntentFilter();
    private Method method;
    private SharedPreferences preferences, preferences1;
    private LinearLayout mContainerActions, mContainerImg;
    private boolean actions_container_visible = false, img_container_visible = false;;
    private ImageView mActionImage, mPreviewImg, mDeleteImg, mActionContainerImg;

    BottomSheet.Builder builder;
    Uri picUri;
    int PICK_FROM_CAMERA = 1, PICK_FROM_GALLERY = 2;
    int CROP_CAMERA_IMAGE = 3, CROP_GALLERY_IMAGE = 4;
    String imageName;
    String pathOfImage;
    Bitmap bm;
    ImageCompression imageCompression;
    byte[] resultByteArray;
    File file;
    Bitmap bitmap = null;
    private HashMap<String, File> paramsFile = new HashMap<>();
    HashMap<String, String> values = new HashMap<>();
    JSONObject jsonObject;
    RequestQueue rQueue;
    String encodeImageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_two_one_chat);
        mContext = OneTwoOneChat.this;
        preferences = getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        jsonObject = new JSONObject();
        method=new Method(this);
        Log.d(TAG, "onCreate: "+method.adminDTO.getAdminMobile());
        jsonObject = new JSONObject();
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        intentFilter.addAction(GlobalVariables.CHAT_NOTIFICATION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, intentFilter);

        Log.d(TAG, "onCreate: "+getIntent().getExtras());
        Log.d(TAG, "onCreateIntent2: "+getIntent().getStringExtra("sendBy"));

        parmsGet.put(GlobalVariables.adminMobile, method.adminDTO.getAdminMobile());
        parmsGet.put(GlobalVariables.userMobile, method.preferences.getValue(GlobalVariables.userMobile));

        Log.d("KIN1", "run: "+parmsGet);
        if (getIntent().hasExtra(GlobalVariables.userMobile1)){
            //chatListDTO = (ChatListDTO) getIntent().getSerializableExtra(Consts.CHAT_LIST_DTO);

            Log.d(TAG, "onCreate:hello2 "+getIntent().hasExtra(GlobalVariables.userMobile1));
            parmsGet.put(GlobalVariables.adminMobile, method.adminDTO.getAdminMobile());
            parmsGet.put(GlobalVariables.userMobile,getIntent().getStringExtra(GlobalVariables.userMobile));
            Log.d("KIN2", "run: "+parmsGet);

        }else{
            Log.d(TAG, "onCreate:hello "+getIntent().hasExtra(GlobalVariables.userMobile1));
            parmsGet.put(GlobalVariables.adminMobile, method.adminDTO.getAdminMobile());
            parmsGet.put(GlobalVariables.userMobile, method.preferences.getValue(GlobalVariables.userMobile));

            Log.d("KIN3", "run: "+parmsGet);
        }
        setUiAction();

    }

    @Override
    protected void onResume() {
        super.onResume();
       // Log.e(TAG, "Value " + prefrence.getIntValue("Value"));
    }

    public void setUiAction() {
        mContainerActions = (LinearLayout) findViewById(R.id.container_actions);
        mContainerActions.setVisibility(View.GONE);
        mActionImage = (ImageView) findViewById(R.id.addFilesImg);
        mActionImage.setOnClickListener(this);

        mContainerImg = (LinearLayout) findViewById(R.id.container_img);
        mContainerImg.setVisibility(View.GONE);

        mPreviewImg = (ImageView) findViewById(R.id.previewImg);
        mDeleteImg = (ImageView) findViewById(R.id.deleteImg);
        mActionContainerImg = (ImageView) findViewById(R.id.actionContainerImg);


        tvNameHedar = (TextView) findViewById(R.id.tvNameHedar);
      //  tvNameHedar.setText(getIntent().getStringExtra(GlobalVariables.userName));

        tvNameHedar.setText(method.preferences.getValue(GlobalVariables.userName));

        relative = (RelativeLayout) findViewById(R.id.relative);
        edittextMessage = (EmojiconEditText) findViewById(R.id.edittextMessage);
        emojiButton = (ImageView) findViewById(R.id.emojiButton);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        lvComment = (ListView) findViewById(R.id.lvComment);
        buttonSendMessage = (ImageView) findViewById(R.id.buttonSendMessage);
        IVback = (ImageView) findViewById(R.id.IVback);
        buttonSendMessage.setOnClickListener(this);
        IVback.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        Log.e("Runnable", "FIRST");
                                        if (NetworkManager.isConnectToInternet(mContext)) {
                                            swipeRefreshLayout.setRefreshing(true);

                                            Log.d("KIN", "run: "+parmsGet);
                                            getComment();

                                        } else {
                                            method.showToasty(OneTwoOneChat.this, "2",getResources().getString(R.string.internet_concation));
                                        }
                                    }
                                }
        );

        emojIcon = new EmojIconActions(this, relative, edittextMessage, emojiButton, "#495C66", "#DCE1E2", "#E6EBEF");
        emojIcon.ShowEmojIcon();
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e("Keyboard", "open");
                hideActionsContainer();

                if (img_container_visible)
                {
                    mActionContainerImg.setVisibility(View.GONE);
                }

            }

            @Override
            public void onKeyboardClose() {
                Log.e("Keyboard", "close");
            }
        });



   /*     if (pathOfImage != null && pathOfImage.length() > 0)
        {
            mPreviewImg.setImageURI(Uri.fromFile(new File(pathOfImage)));
            showImageContainer();
        }
*/
        mDeleteImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                picUri = null;
                pathOfImage = "";

                hideImageContainer();
            }
        });

        mActionContainerImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (actions_container_visible)
                {
                    hideActionsContainer();
                    return;
                }

                showActionsContainer();
            }
        });


        builder = new BottomSheet.Builder(OneTwoOneChat.this).sheet(R.menu.menu_cards);
        builder.title(getResources().getString(R.string.select_img));
        builder.listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.camera_cards:
                        if (Method.hasPermissionInManifest(OneTwoOneChat.this, PICK_FROM_CAMERA, Manifest.permission.CAMERA)) {
                            if (Method.hasPermissionInManifest(OneTwoOneChat.this, PICK_FROM_GALLERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                try {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    File file = getOutputMediaFile(1);
                                    if (!file.exists()) {
                                        try {

                                            file.createNewFile();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        //Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.asd", newFile);
                                        picUri = FileProvider.getUriForFile(OneTwoOneChat.this.getApplicationContext(), OneTwoOneChat.this.getApplicationContext().getPackageName() + ".fileprovider", file);
                                    } else {
                                        picUri = Uri.fromFile(file); // create
                                    }

                                    GlobalVariables.IMAGE_URI_CAMERA=picUri.toString();
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri); // set the image file
                                    startActivityForResult(intent, PICK_FROM_CAMERA);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        break;
                    case R.id.gallery_cards:
                        if (Method.hasPermissionInManifest(OneTwoOneChat.this, PICK_FROM_CAMERA, Manifest.permission.CAMERA)) {
                            if (Method.hasPermissionInManifest(OneTwoOneChat.this, PICK_FROM_GALLERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                File file = getOutputMediaFile(1);
                                if (!file.exists()) {
                                    try {
                                        file.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                picUri = Uri.fromFile(file);

                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_img)), PICK_FROM_GALLERY);

                            }
                        }
                        break;
                    case R.id.cancel_cards:
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        });
                        break;
                }
            }
        });
    }


    private File getOutputMediaFile(int type) {
        String root =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File mediaStorageDir = new File(root, GlobalVariables.APP_NAME);

        /**Create the storage directory if it does not exist*/
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        /**Create a media file name*/
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    GlobalVariables.APP_NAME + timeStamp + ".png");

           // imageName =  Consts.APP_NAME + timeStamp + ".png";
        } else {
            return null;
        }
        return mediaFile;
    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString= Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CROP_CAMERA_IMAGE) {
            if (data != null) {
                Log.d(TAG, "onActivityResult1: "+(data.getExtras()));
                picUri = Uri.parse(Objects.requireNonNull(data.getExtras()).getString("resultUri"));
                try {
                    //bitmap = MediaStore.Images.Media.getBitmap(SaveDetailsActivityNew.this.getContentResolver(), resultUri);
                    pathOfImage = picUri.getPath();
                    imageCompression = new ImageCompression(OneTwoOneChat.this);
                    imageCompression.execute(pathOfImage);
                    imageCompression.setOnTaskFinishedEvent(new ImageCompression.AsyncResponse() {
                        @Override
                        public void processFinish(String imagePath) {
                            showImageContainer();
                            Glide.with(OneTwoOneChat.this).load("file://" + imagePath)
                                    .thumbnail(0.5f)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(mPreviewImg);
                            try {
                                // bitmap = MediaStore.Images.Media.getBitmap(SaveDetailsActivityNew.this.getContentResolver(), resultUri);
                                file = new File(imagePath);
                                paramsFile.put(GlobalVariables.IMAGE, file);
                                Log.e("image", imagePath);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == CROP_GALLERY_IMAGE) {
            if (data != null) {
                Log.d(TAG, "onActivityResult2: "+(data.getExtras()));
                picUri = Uri.parse(Objects.requireNonNull(data.getExtras()).getString("resultUri"));

                try {
                    bm = MediaStore.Images.Media.getBitmap(OneTwoOneChat.this.getContentResolver(), picUri);
                    pathOfImage = picUri.getPath();
                    imageCompression = new ImageCompression(OneTwoOneChat.this);
                    imageCompression.execute(pathOfImage);
                    imageCompression.setOnTaskFinishedEvent(new ImageCompression.AsyncResponse() {
                        @Override
                        public void processFinish(String imagePath) {
                            showImageContainer();
                            Glide.with(OneTwoOneChat.this).load("file://" + imagePath)
                                    .thumbnail(0.5f)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(mPreviewImg);
                            Log.e("image", imagePath);
                            try {
                                file = new File(imagePath);
                                paramsFile.put(GlobalVariables.IMAGE, file);

                                Log.e("image", imagePath);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == PICK_FROM_CAMERA && resultCode == RESULT_OK) {
            if (picUri != null) {
                //  Log.d(TAG, "onActivityResult3: "+(data.getExtras()));
                picUri = Uri.parse((GlobalVariables.IMAGE_URI_CAMERA));
                startCropping(picUri, CROP_CAMERA_IMAGE);
            } else {
                picUri = Uri.parse((GlobalVariables.IMAGE_URI_CAMERA));
                startCropping(picUri, CROP_CAMERA_IMAGE);
            }
        }
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            try {
                // Log.d(TAG, "onActivityResult4: "+(data.getExtras()));
                Uri tempUri = Objects.requireNonNull(data).getData();
                Log.e("front tempUri", "" + tempUri);
                if (tempUri != null) {
                    startCropping(tempUri, CROP_GALLERY_IMAGE);
                } else {

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void startCropping(Uri uri, int requestCode) {
        Intent intent = new Intent(OneTwoOneChat.this, MainFragment.class);
        intent.putExtra("imageUri", uri.toString());
        intent.putExtra("requestCode", requestCode);
        startActivityForResult(intent, requestCode);
    }

    public void showActionsContainer() {
        actions_container_visible = true;
        mContainerActions.setVisibility(View.VISIBLE);
        mActionContainerImg.setBackgroundResource(R.drawable.ic_cancelled);
    }

    public void hideActionsContainer() {
        actions_container_visible = false;
        mContainerActions.setVisibility(View.GONE);
        mActionContainerImg.setBackgroundResource(R.drawable.ic_attach_chat);
    }

    public void showImageContainer() {
        img_container_visible = true;
        mContainerImg.setVisibility(View.VISIBLE);
        mActionContainerImg.setVisibility(View.GONE);
    }

    public void hideImageContainer() {
        img_container_visible = false;
        mContainerImg.setVisibility(View.GONE);
        mActionContainerImg.setVisibility(View.VISIBLE);
        mActionContainerImg.setBackgroundResource(R.drawable.ic_attach_chat);
    }
    public boolean validateMessage() {
        if (edittextMessage.getText().toString().trim().length() <= 0) {
            edittextMessage.setError(getResources().getString(R.string.val_comment));
            edittextMessage.requestFocus();
            return false;
        } else {
            edittextMessage.setError(null);
            edittextMessage.clearFocus();
            return true;
        }
    }

   public void submit() throws JSONException {
        if (!validateMessage()) {
            return;
        } else {
            try {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {

            }
            if (NetworkManager.isConnectToInternet(mContext)) {
                doComment(bm);
            } else {
                method.showToasty(OneTwoOneChat.this, "2",getResources().getString(R.string.internet_concation));
            }


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSendMessage:
                try {
                    submit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.IVback:
                finish();
                break;
            case R.id.addFilesImg:
                hideActionsContainer();
                builder.show();
                break;
        }
    }

    @Override
    public void onRefresh() {
        Log.e("ONREFREST_Firls", "FIRS");
        getComment();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finish();
    }

  /*  public void getComment() {
        new HttpsRequest(Consts.GET_CHAT_API, parmsGet, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                swipeRefreshLayout.setRefreshing(false);
                if (flag) {
                    try {
                        getCommentDTOList = new ArrayList<>();
                        Type getpetDTO = new TypeToken<List<GetCommentDTO>>() {
                        }.getType();
                        getCommentDTOList = (ArrayList<GetCommentDTO>) new Gson().fromJson(response.getJSONArray("my_chat").toString(), getpetDTO);
                        showData();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                }
            }
        });
    }*/

    public void showData()
    {
        adapterViewComment = new AdapterViewComment(mContext, getCommentDTOList, userDTO);
        lvComment.setAdapter(adapterViewComment);
        lvComment.setSelection(getCommentDTOList.size() - 1);
        swipeRefreshLayout.setRefreshing(false);
    }


    public void doComment(Bitmap bm) {

        if (file != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            try {
                // jsonObject = new JSONObject();
                String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());
                jsonObject.put("imagename", imgname);
                //  Log.e("Image name", etxtUpload.getText().toString().trim());
                jsonObject.put("image", encodedImage);
                jsonObject.put("mobile",  getIntent().getStringExtra(GlobalVariables.userMobile));
                jsonObject.put("artist_id",method.adminDTO.getAdminMobile());
                jsonObject.put("sender_name",  method.adminDTO.getAdminName());
                jsonObject.put("reciever_name",  getIntent().getStringExtra(GlobalVariables.userName));
                jsonObject.put("email",  method.adminDTO.getAdminEmail());
                jsonObject.put("message",method.getEditTextValue(edittextMessage));
                jsonObject.put("send_by", method.adminDTO.getAdminMobile());
                jsonObject.put("city",  method.adminDTO.getAdminUserId());
                jsonObject.put("chat_type","2");
                // jsonObject.put("aa", "aa");


            } catch (JSONException e) {
                // Log.e(TAG,"JSONObject Here"+ e.toString());
            }


        }else {
            try {
                jsonObject.put("imagename", "");
                jsonObject.put("chat_type","1");
                jsonObject.put("mobile",  getIntent().getStringExtra(GlobalVariables.userMobile));
                jsonObject.put("artist_id",method.adminDTO.getAdminMobile());
                jsonObject.put("sender_name",  method.adminDTO.getAdminName());
                jsonObject.put("reciever_name",  getIntent().getStringExtra(GlobalVariables.userName));
                jsonObject.put("email",  method.adminDTO.getAdminEmail());
                jsonObject.put("message",method.getEditTextValue(edittextMessage));
                jsonObject.put("send_by", method.adminDTO.getAdminMobile());
                jsonObject.put("city",  method.adminDTO.getAdminUserId());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        method.loadingDialogg(OneTwoOneChat.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, RestAPI.SEND_CHAT_API, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.e(TAG,"aaaaaaa"+ jsonObject.toString());
                        method.loadingDialog.dismiss();
                        try {
                            // Toast.makeText(getApplication(), jsonObject.getString("title"), Toast.LENGTH_SHORT).show();
                            String success = jsonObject.getString("success");
                            String title = jsonObject.getString("title");
                            String message = jsonObject.getString("message");
                            String btntxt=GlobalVariables.btntxt;

                            if(success.equals("1")){
                                edittextMessage.setText("");
                                hideImageContainer();
                                getComment();
                                file = null;
                                pathOfImage = "";
                               // Method.alertBox("1",title,message,OneTwoOneChat.this,btntxt);
                            }else{
                                Method.alertBox("0",title,message,OneTwoOneChat.this,btntxt);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        rQueue.getCache().clear();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                method.loadingDialog.dismiss();
                Log.e(TAG,"aaaaaaa"+ volleyError.toString());
                Method.alertBox("0","Something went wrong","We Regret For Such Inconvenience..! Please try Again ",OneTwoOneChat.this,GlobalVariables.btntxt);

            }
        });

        rQueue = Volley.newRequestQueue(OneTwoOneChat.this);
        rQueue.add(jsonObjectRequest);
         Log.d(TAG, "uploadImage: "+jsonObjectRequest+jsonObjectRequest+jsonObject);

    }



    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.requireNonNull(intent.getAction()).equalsIgnoreCase(GlobalVariables.CHAT_NOTIFICATION)) {
               if(isActivityActive(OneTwoOneChat.this)){
                   //method.showToasty(OneTwoOneChat.this,"1","");
                   getComment();
                }else{
                   method.showToasty(OneTwoOneChat.this,"1","You Have A new Chat");
               }

                Log.e("CHAT_NOTIFICATION","CHAT_NOTIFICATION");
             //   Log.e(TAG, "Value " + prefrence.getIntValue("Value"));

            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
          //  unregisterReceiver(mBroadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getComment(){

        method.loadingDialogg(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.API_GET_CHAT,
                response -> {
                    // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                    try {
                        System.out.println(response);
                        JSONObject jsonObject = new JSONObject(response);

                        JSONObject jsonObject1 = jsonObject.getJSONObject(GlobalVariables.AppSid);

                        String success = jsonObject1.getString("success");

                        if (success.equals("1")) {

                            try {
                                getCommentDTOList = new ArrayList<>();
                                Type getpetDTO = new TypeToken<List<GetCommentDTO>>() {
                                }.getType();
                                getCommentDTOList = (ArrayList<GetCommentDTO>) new Gson().fromJson(jsonObject1.getJSONArray("Results").toString(), getpetDTO);
                                showData();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    method.loadingDialog.dismiss();
                                }
                            }, GlobalVariables.dismissAfter);
                        }else{
                            method.loadingDialog.dismiss();

                           // Method.alertBox("2",jsonObject1.getString("title"),jsonObject1.getString("msg"), OneTwoOneChat.this,"Go to Home");

                        }

                    }catch (JSONException e){
                        e.printStackTrace();

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        //Toast.makeText(WalletActivity.this,"Error...!",Toast.LENGTH_SHORT).show();
                        method.loadingDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("users_login", "KINGSN");
               // params.put("mobile",getIntent().getStringExtra(GlobalVariables.userMobile) );
               // params.put("device_id",method.getDeviceId(requireActivity()));
                Log.d("KIN FINAL", "run: "+parmsGet);
                return parmsGet;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(OneTwoOneChat.this);
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);

    }

    public static boolean isActivityActive(Activity activity) {
        if (null != activity)
            return !activity.isFinishing() && !activity.isDestroyed();
        return false;
    }

}
