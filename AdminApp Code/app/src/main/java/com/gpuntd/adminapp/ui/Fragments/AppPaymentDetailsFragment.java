package com.gpuntd.adminapp.ui.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.databinding.FragmentApppaymentBinding;
import com.gpuntd.adminapp.databinding.FragmentAppsettingsBinding;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppPaymentDetailsFragment extends Fragment {
    private static final String TAG ="KINGSN" ;
    FragmentApppaymentBinding binding;
    private Method method;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = FragmentApppaymentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        preferences = this.requireActivity().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();

        method = new Method(requireActivity());

        setData();

        return root;
    }

    private void setData() {

        binding.etInAppPaytmUpiName.setText(GlobalVariables.settings.getAdminUpiName());
        binding.etInAppPaytmUpiMobile.setText(GlobalVariables.settings.getUpiMobileNo());
        binding.etInAppPaytmUpiId.setText(GlobalVariables.settings.getAdminUpi());
        binding.etInAppPaytmSettingName.setText(GlobalVariables.settings.getAdminPaytmName());
        binding.etInAppPaytmSettingMobile.setText(GlobalVariables.settings.getAdminPaytmNo());
        binding.etInAppPaytmSettingId.setText(GlobalVariables.settings.getAdminUpi());
        binding.etInAppGooglePayName.setText(GlobalVariables.settings.getAdminGpayName());
        binding.etInAppGooglePayMobile.setText(GlobalVariables.settings.getAdminGpaymobileNo());
        binding.etInAppGooglePayId.setText(GlobalVariables.settings.getAdminGpay());
        binding.etInAppBankHolderName.setText(GlobalVariables.settings.getAdminAccountName());
        binding.etInAppBankIFSCCode.setText(GlobalVariables.settings.getAdminAccountIfsc());
        binding.etInAppBankAccountNumber.setText(GlobalVariables.settings.getAdminAccountNo());
//        binding.etInAppBankHolderName.setText(GlobalVariables.settings.getAppAuthor());




        binding.btnSaveOfflineScreenshots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.requireNonNull(binding.etInAppPaytmUpiName.getText()).toString().isEmpty()) {
                    binding.etInAppPaytmUpiName.setError("Field is Required");
                }
                else if (Objects.requireNonNull(binding.etInAppPaytmUpiMobile.getText()).toString().isEmpty()) {
                    binding.etInAppPaytmUpiMobile.setError("Field is Required");
                }
                else if (Objects.requireNonNull(binding.etInAppPaytmUpiId.getText()).toString().isEmpty()) {
                    binding.etInAppPaytmUpiId.setError("Field is Required");
                }else if (Objects.requireNonNull(binding.etInAppPaytmSettingName.getText()).toString().isEmpty()) {
                    binding.etInAppPaytmSettingName.setError("Field is Required");
                }
                else if (Objects.requireNonNull(binding.etInAppPaytmSettingMobile.getText()).toString().isEmpty()) {
                    binding.etInAppPaytmSettingMobile.setError("Field is Required");
                }
                else  if (Objects.requireNonNull(binding.etInAppGooglePayName.getText()).toString().isEmpty()) {
                    binding.etInAppGooglePayName.setError("Field is Required");
                } else if (Objects.requireNonNull(binding.etInAppGooglePayMobile.getText()).toString().isEmpty()) {
                    binding.etInAppGooglePayMobile.setError("Field is Required");
                }
                else if (Objects.requireNonNull(binding.etInAppGooglePayId.getText()).toString().isEmpty()) {
                    binding.etInAppGooglePayId.setError("Field is Required");
                }else if (Objects.requireNonNull(binding.etInAppBankHolderName.getText()).toString().isEmpty()) {
                    binding.etInAppBankHolderName.setError("Field is Required");
                }
                else if (Objects.requireNonNull(binding.etInAppBankIFSCCode.getText()).toString().isEmpty()) {
                    binding.etInAppBankIFSCCode.setError("Field is Required");
                }
                else  if (Objects.requireNonNull(binding.etInAppBankAccountNumber.getText()).toString().isEmpty()) {
                    binding.etInAppBankAccountNumber.setError("Field is Required");
                }
                else {
                    method.params.clear();
                    method.params.put("appPayment_submit", "appPayment_submit");
                    method.params.put("adminUpiName",(binding.etInAppPaytmUpiName.getText()).toString());
                    method.params.put("upiMobileNo",(binding.etInAppPaytmUpiMobile.getText()).toString());
                    method.params.put("adminUpi",(binding.etInAppPaytmUpiId.getText()).toString());
                    method.params.put("adminPaytmName",(binding.etInAppPaytmSettingName.getText()).toString());
                    method.params.put("adminGpayName",(binding.etInAppGooglePayName.getText()).toString());

                    method.params.put("adminGpaymobileNo",(binding.etInAppGooglePayMobile.getText()).toString());
//                    method.params.put("adminUpi",(binding.etInAppGooglePayId.getText()).toString());
                    method.params.put("adminAccountNo",(binding.etInAppBankAccountNumber.getText()).toString());
                    method.params.put("adminAccountName",(binding.etInAppBankHolderName.getText()).toString());
                    method.params.put("adminAccountIfsc",(binding.etInAppBankIFSCCode.getText()).toString());
                    method.updateSetting(requireActivity(), RestAPI.SETTING_UPDATE1);



                }
            }

        });


    }


}
