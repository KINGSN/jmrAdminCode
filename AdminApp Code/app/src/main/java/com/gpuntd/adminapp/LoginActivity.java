package com.gpuntd.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;
import com.gpuntd.adminapp.Interface.ApiServices;
import com.gpuntd.adminapp.Models.AppSettings.AppSettings;
import com.gpuntd.adminapp.Models.CheckUser.CheckUser;
import com.gpuntd.adminapp.Models.CheckUser.Result;
import com.gpuntd.adminapp.Models.payment_modeDTO;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.PrefManager;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.databinding.ActivityLoginBinding;
import com.gpuntd.adminapp.preferences.SharedPrefrence;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static android.R.layout.simple_spinner_item;
import static com.google.firebase.auth.PhoneAuthProvider.getCredential;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "KINGSN";
    ActivityLoginBinding binding;
    private PrefManager prefManager;
    private Method method;
    List<Result> BookList;
   public ArrayList<Result> appsettings = new ArrayList<>();
    private String countryCode;
    private FirebaseAuth auth, mAuth;
    String verificationcodebysystem;
    String codeBySystem, mobileNumber;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private Context mContext;

    private Spinner spinner_widthrawal;
    private ArrayList<payment_modeDTO> goodModelArrayList = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<String>();
    private JSONArray result;
    private List<payment_modeDTO> payment_data;

    public String password,adminID,adminUserID;
    public  SharedPrefrence preferences;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        setTheme(R.style.Theme_Gpuntd_NoActionBar);
        method = new Method(LoginActivity.this);
        auth = FirebaseAuth.getInstance();
        mAuth=FirebaseAuth.getInstance();

        preferences= SharedPrefrence.getInstance(LoginActivity.this);
        // editor = preferences.edit();
       // getSuperHeroes();
        retrieveJSON();

        countryCode="+"+binding.countryCodePicker.getSelectedCountryCode();
        binding.countryCodePicker.setOnCountryChangeListener(() -> countryCode="+"+binding.countryCodePicker.getSelectedCountryCode());

        binding.btnLogin.setOnClickListener(view -> {


             adminUserID=binding.spinnerWidthrawal.getSelectedItem().toString();
            if (adminUserID.isEmpty()) {
                binding.spinnerWidthrawal.setPrompt("Please Select  admin ID");
            } else if (Objects.requireNonNull(binding.passPageEt.getText()).toString().isEmpty()) {
                binding.passPageEt.setError("Please Enter password");
            }else {
                // verifyCode(userOtp);
                // method.loadingDialogg(LoginActivity.this);
                if(password.equals(binding.passPageEt.getText().toString())){
                    //method.loadingDialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    preferences.setBooleanValue(String.valueOf(GlobalVariables.USER_IS_LOGIN),true);
                    preferences.setValue(GlobalVariables.adminUserId,adminUserID);
                   // preferences.getValue(GlobalVariables.adminUserId);
                    method.getHomeData2(LoginActivity.this);
                 //   method.showToasty(this,"1",  GlobalVariables.adminUserId+preferences.getValue(GlobalVariables.adminUserId));
                    startActivity(intent);

                }else{
                    binding.passPageEt.setError("Incorrect Paswword");
                }
            }

        });



        binding.spinnerWidthrawal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

                Log.d("KINGSN", "onItemSelected: ");

                adminID=binding.spinnerWidthrawal.getSelectedItem().toString();
                password=getAdmin_password(position);
                adminUserID=getAdmin_UserId(position);
                //binding.passPageEt.setHint("Please Enter Your Password");
                Log.d(TAG, "onItemSelected: "+binding.spinnerWidthrawal.getSelectedItem().toString()+"\n   admin_password"+password);

                // instruction.setText(getinstruction(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        binding.btnOTPSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adminUserID=binding.spinnerWidthrawal.getSelectedItem().toString();
                if (adminUserID.isEmpty()) {
                    binding.spinnerWidthrawal.setPrompt("Please Select  admin ID");
                } else if (Objects.requireNonNull(binding.passPageEt.getText()).toString().isEmpty()) {
                    binding.passPageEt.setError("Please Enter password");
                }else {
                    // verifyCode(userOtp);
                    // method.loadingDialogg(LoginActivity.this);
                    if(password.equals(binding.passPageEt.getText().toString())){
                        method.loadingDialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //intent.putExtra ("mobile","8251941213");
                        startActivity(intent);

                    }else{
                        binding.passPageEt.setError("Incorrect Paswword");
                    }
                }



            }
        });
    }


    public void users_login() {

        ApiServices settings1 = RestAPI.getApiService();
        Call<CheckUser> call = settings1.check_user("8251941210", "5178");
        //  Log.d(TAG, "SignIn: "+settings1);
        call.enqueue(new Callback<CheckUser>() {


            @Override
            public void onResponse(Call<CheckUser> call, Response<CheckUser> response) {
                Log.d(TAG, "onResponse: " + response);
                Toast.makeText(LoginActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                if (response.code() == 200) {
                    List<Result> resultList = null;
                    if (response.body() != null) {
                        resultList = response.body().getResult();
                    }

                    BookList = new ArrayList<>();
                    assert response.body() != null;
                    //  BookList = response.body().getResult();
                    Log.e("KINGSN", "BOOKSIZE" + BookList.size());
                    Log.e("KINGSN", "BOOKLIST_add" + resultList.get(0).getName());
                    // prefManager.setValue(response.body().getResult().get(0).getOnesignalappKey(), response.body().getResult().get(0).getAppAuthor());
                    // BookList = (response.getJSONObject("data").toString());
                    Log.e("TAG", "response 33: " + new Gson().toJson(response.body()));
                    Log.d(TAG, "onCreateView: " + prefManager.getValue("onesignalappKey"));
                    //  AppSettings spin = response.body();
                    // Result result= spin.setResult(spin.getResult());

                    Gson gson = new Gson();
                    String favData = gson.toJson(response.body());



                }
            }

            @Override
            public void onFailure(@NotNull Call<CheckUser> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage() + "error  " + t);
                //  Toast.makeText(LoginActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
    }

    private void getSuperHeroes() {
        Call<AppSettings> call = RestAPI.getApiService().settings();
    call.enqueue(new Callback<AppSettings>() {
        @Override
        public void onResponse(Call<AppSettings> call, Response<AppSettings> response) {
            Log.d(TAG, "NOTHING IS IMPOSSIBLE  "+response);
          /*  try {
                appsettings = new ArrayList<>();
                Type getpetDTO = new TypeToken<List<AppSettings>>() {
                }.getType();
                assert response.body() != null;
                appsettings = new Gson().fromJson(response.body().getResult().toString(), getpetDTO);

                //showData();

            } catch (Exception e) {
                e.printStackTrace();
            }*/

            assert response.body() != null;
        //    Log.d(TAG, "onResponse: "+ response.body().getResult().indexOf(0));

        }

        @Override
        public void onFailure(Call<AppSettings> call, Throwable t) {
            Log.d(TAG, "onResponse: "+t);
        }
    });

    }


    private void sendVerificationCodeToUser(String mobileNumber) {
        //loadingDialog.dismiss();
        method.loadingDialog.dismiss();

      /*  PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber( mobileNumber)       // Phone number to verify
                        .setTimeout(30L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);*/


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileNumber,        // Phone number to verify
                20,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,// Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;

                    verificationcodebysystem= s;
                    method.loadingDialogg(LoginActivity.this);
                    binding.numberlayout.setVisibility(View.GONE);
                    binding.otpLayout.setVisibility(View.VISIBLE);
                    binding.otpSentTv.setText("We have sent an OTP on "+mobileNumber);


                    method.showToasty(LoginActivity.this,"1","Otp Has Been Sent");
                    // Toasty.success(LoginActivity.this, "Password reset Code sent, please check", Toast.LENGTH_SHORT).show();
                    method.loadingDialog.dismiss();
                }

                @Override
                public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                    super.onCodeAutoRetrievalTimeOut(s);
                    method.loadingDialog.dismiss();
                    // onBackPressed();
                    //Toast.makeText(ForgotActivity.this, "Timeout", Toast.LENGTH_SHORT).show();
                    // method.showToasty(LoginActivity.this,"2","Otp Retrieval Times Upp..! Try Again");

                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    method.loadingDialogg(LoginActivity.this);
                    if (code != null) {
                        binding.veriCodeEt.setText(code);
                        verifyCode(code);
                        method.loadingDialog.dismiss();



                    }
                }


                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    method.loadingDialog.dismiss();
                    //  Toast.makeText(ForgotActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(ForgotActivity.this,"verification failed", Toast.LENGTH_SHORT).show();
                    method.showToasty(LoginActivity.this,"2","verification failed");
                    Log.w(TAG, "onVerificationFailed", e);

                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        // Invalid request
                        Log.e("Error", "" +"Invalid request");
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        // The SMS quota for the project has been exceeded
                        Log.e("Error", "" +"The SMS quota for the project has been exceeded");
                    }

                    // Show a message and update the UI
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);

        method.loadingDialog.dismiss();


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Verification completed successfully here Either
                            // store the data or do whatever desire
                            Log.d(TAG, "onComplete: mobile int"+mobileNumber);
                            method.loadingDialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra ("mobile",binding.phone.getText().toString() );
                            startActivity(intent);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                method.loadingDialog.dismiss();
                                // Toast.makeText(RegisterActivity.this, "Verification Not Completed! Try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void retrieveJSON() {
        String URLstring = "https://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, RestAPI.API_ADMIN_LIST,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            //  JSONArray dataArray1  = obj.getJSONArray("data");
                            Log.d("KINGSN", "sTATUS" + obj.getString("success")+"data_array");
                            if(obj.getString("success").equals("1")){

                                goodModelArrayList = new ArrayList<>();
                                JSONObject j = null;
                                JSONArray dataArray  = obj.getJSONArray("data");
                                //j = new JSONObject(response);

                                //Storing the Array of JSON String to our JSON Array
                                result = obj.getJSONArray("data");


                                for (int i = 0; i < dataArray.length(); i++) {

                                    payment_data = new ArrayList<>();
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    payment_modeDTO playerModel = new payment_modeDTO();
                                   /*
                                    payment_modeDTO ld3=new payment_modeDTO(dataobj.getString("id"),dataobj.getString("sl_no"),
                                            dataobj.getString("mode_name"),
                                            dataobj.getString("mode_icon"),dataobj.getString("mode_hint")
                                            ,dataobj.getString("instruction"),dataobj.getString("minimum_redeem"),dataobj.getString("status")
                                            ,dataobj.getString("updated_date"));
                                    Log.d("KINGSN", "sTATUS4" + obj.getString("success")+ld3);
                                    payment_data.add(ld3);*/
                                    playerModel.setAdmin_user_id(dataobj.getString("admin_user_id"));
                                    playerModel.setAdmin_password(dataobj.getString("admin_password"));
                                    playerModel.setAdmin_name(dataobj.getString("admin_name"));
                                    goodModelArrayList.add(playerModel);


                                  /*  recyclerView=findViewById(R.id.wallet_adapter);
                                    recyclerAdapters= new SpinnerAdapter(payment_data,WalletActivity.this);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(WalletActivity.this));*/

                                    Log.d("KINGSN", "sTATUS" + goodModelArrayList.size()+"data_array");

                                }/*recyclerView.setAdapter(recyclerAdapters);*/

                                for (int i = 0; i < goodModelArrayList.size(); i++){
                                    names.add(goodModelArrayList.get(i).getAdmin_user_id().toString());
                                }

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(LoginActivity.this, simple_spinner_item, names);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                //  spinner.setAdapter(spinnerArrayAdapter);
                                // spinner_widthrawal.setAdapter(spinnerArrayAdapter);
                                binding.spinnerWidthrawal.setAdapter(spinnerArrayAdapter);
                                binding.spinnerWidthrawal.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                binding.spinnerWidthrawal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                //  removeSimpleProgressDialog();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    private String getAdmin_password(int position){
        String session="";
        try {
            JSONObject json = result.getJSONObject(position);
            session = json.getString("admin_password");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return session;
    }
    private String getAdmin_UserId(int position){
        String session="";
        try {
            JSONObject json = result.getJSONObject(position);
            session = json.getString("admin_user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return session;
    }
}