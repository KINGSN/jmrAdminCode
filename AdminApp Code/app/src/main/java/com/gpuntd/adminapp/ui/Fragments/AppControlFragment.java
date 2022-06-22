package com.gpuntd.adminapp.ui.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cocosw.bottomsheet.BottomSheet;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.databinding.FragmentAppcontrolBinding;
import com.gpuntd.adminapp.databinding.FragmentAppsettingsBinding;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppControlFragment extends Fragment {
    private static final String TAG ="KINGSN" ;
    FragmentAppcontrolBinding binding;
    private Method method;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = FragmentAppcontrolBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        preferences = this.requireActivity().getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();

        method = new Method(requireActivity());

       // setData();

        return root;
    }

    private void setData() {

        binding.etNewUpdatedVersion.setText(GlobalVariables.settings.getNewVersion());
        binding.etNewUpdatedLink.setText(GlobalVariables.settings.getUpdateLink());
        binding.etInAppTelegramLink.setText(GlobalVariables.settings.getTelegramlink());
        binding.etInAppYoutubeLink.setText(GlobalVariables.settings.getYoutubeLink());
        binding.etInAppFacebookPage.setText(GlobalVariables.settings.getFacebookPage());




        binding.btnSaveAppControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.requireNonNull(binding.etNewUpdatedVersion.getText()).toString().isEmpty()) {
                    binding.etNewUpdatedVersion.setError("Field is Required");
                }
               else if (Objects.requireNonNull(binding.etNewUpdatedLink.getText()).toString().isEmpty()) {
                    binding.etNewUpdatedLink.setError("Field is Required");
                }
                else if (Objects.requireNonNull(binding.etInAppTelegramLink.getText()).toString().isEmpty()) {
                    binding.etInAppTelegramLink.setError("Field is Required");
                }
                else if (Objects.requireNonNull(binding.etInAppYoutubeLink.getText()).toString().isEmpty()) {
                    binding.etInAppYoutubeLink.setError("Field is Required");
                }
                else if (Objects.requireNonNull(binding.etInAppFacebookPage.getText()).toString().isEmpty()) {
                    binding.etInAppFacebookPage.setError("Field is Required");
                }
                 else {
                    method.params.clear();
                    method.params.put("appcontrol_submit", "appcontrol_submit");
                    method.params.put("new_version",(binding.etNewUpdatedVersion.getText()).toString());
                    method.params.put("update_link",(binding.etNewUpdatedLink.getText()).toString());
                    method.params.put("telegramlink",(binding.etInAppTelegramLink.getText()).toString());
                    method.params.put("youtube_link",(binding.etInAppYoutubeLink.getText()).toString());
                    method.params.put("facebook_page",(binding.etInAppFacebookPage.getText()).toString());
                    method.updateSetting(requireActivity(), RestAPI.SETTING_UPDATE1);

                }
            }

        });


    }


}
