package com.gpuntd.adminapp.ui.Fragments;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;

import com.gpuntd.adminapp.ReportActivity;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.databinding.FragmentApppaymentBinding;
import com.gpuntd.adminapp.databinding.FragmentPaymodeBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import ru.slybeaver.slycalendarview.SlyCalendarDialog;

import static android.content.Context.MODE_PRIVATE;


public class AppPaymentModeFragment extends Fragment {
    private static final String TAG ="KINGSN" ;
    FragmentPaymodeBinding binding;
    private Method method;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public String payMode="paytm_gateway";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = FragmentPaymodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        preferences = this.requireActivity().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();

        method = new Method(requireActivity());

        setData();

        return root;
    }

    private void setData() {

        binding.etInAppPaytmMerchentId.setText(GlobalVariables.settings.getPaytm_mid());
        binding.etInAppPaytmMerchetKey.setText(GlobalVariables.settings.getPaytm_Key());
        binding.etInApppayUmId.setText(GlobalVariables.settings.getPayumoney_mid());
        binding.etInApppayUmKey.setText(GlobalVariables.settings.getPayumoney_key());
        binding.etInApppayUmSalt.setText(GlobalVariables.settings.getPayumoney_salt());


//        binding.etInAppBankHolderName.setText(GlobalVariables.settings.getAppAuthor());

            if(GlobalVariables.settings.getPayment_gateway().toLowerCase().equals("paytm_gateway")){
                binding.activePaymode.setSelection(0);
                binding.paytmPaymode.setSelection(0);
                binding.payuPaymode.setSelection(1);
            }else if(GlobalVariables.settings.getPayment_gateway().toLowerCase().equals("payumoney_gateway")){
                binding.activePaymode.setSelection(1);
                binding.paytmPaymode.setSelection(1);
                binding.payuPaymode.setSelection(0);
            }

                if(GlobalVariables.settings.getOffline_paymentGateway().toLowerCase().equals("true")){
                    binding.activeoffPaymode.setSelection(0);

                }else{
                    binding.activeoffPaymode.setSelection(1);

                }


                binding.etInAppPaytmMerchentId.setText(GlobalVariables.settings.getPaytm_mid());
                binding.etInAppPaytmMerchetKey.setText(GlobalVariables.settings.getPaytm_Key());

                binding.etInApppayUmId.setText(GlobalVariables.settings.getPayumoney_mid());
                binding.etInApppayUmKey.setText(GlobalVariables.settings.getPayumoney_key());


        binding.btnSavePaymode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.requireNonNull(binding.activePaymode).getSelectedItem()==null) {
                    binding.activePaymode.setPrompt("Please Select Any Mode");
                }
                else   if (Objects.requireNonNull(binding.activeoffPaymode).getSelectedItem()==null) {
                    binding.activeoffPaymode.setPrompt("Please Select Any Mode");
                }
                else if (Objects.requireNonNull(binding.etInAppPaytmMerchetKey.getText()).toString().isEmpty()) {
                    binding.etInAppPaytmMerchetKey.setError("Field is Required");
                }else if (Objects.requireNonNull(binding.etInAppPaytmMerchentId.getText()).toString().isEmpty()) {
                    binding.etInAppPaytmMerchentId.setError("Field is Required");
                }
                else if (Objects.requireNonNull(binding.etInApppayUmId.getText()).toString().isEmpty()) {
                    binding.etInApppayUmId.setError("Field is Required");
                }
                else  if (Objects.requireNonNull(binding.etInApppayUmKey.getText()).toString().isEmpty()) {
                    binding.etInApppayUmKey.setError("Field is Required");
                }
                else {
                    method.params.clear();
                    method.params.put("appPayMode_submit", "appPayMode_submit");
                    method.params.put("payment_gateway",(payMode));
                    method.params.put("offline_paymentGateway",(binding.activeoffPaymode.getSelectedItem().toString()));
                    method.params.put("paytm_mid",(binding.etInAppPaytmMerchentId.getText()).toString());
                    method.params.put("paytm_key",(binding.etInAppPaytmMerchetKey.getText()).toString());
                    method.params.put("razorpay_mid",(""));

                    method.params.put("razorpay_key",(""));
//                    method.params.put("adminUpi",(binding.etInAppGooglePayId.getText()).toString());
                    method.params.put("payumoney_mid",(binding.etInApppayUmId.getText()).toString());
                    method.params.put("payumoney_key",(binding.etInApppayUmKey.getText()).toString());
                    method.params.put("payumoney_salt",(binding.etInApppayUmSalt.getText()).toString());
                    method.updateSetting(requireActivity(), RestAPI.SETTING_UPDATE1);



                }
            }

        });

        binding.activePaymode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (binding.activePaymode.getSelectedItemPosition()==0){
                    // Your code here
                   payMode="payment_gateway";
                }else  if (binding.activePaymode.getSelectedItemPosition()==1){
                    // Your code here
                    payMode="payumoney_gateway";
                    method.params.put("payment_gateway","payumoney_gateway");
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                method.loadingDialog.dismiss();
                payMode=GlobalVariables.settings.getPayment_gateway();
            }
        });

    }


}
