package com.gpuntd.adminapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.PrefManager;
import com.gpuntd.adminapp.databinding.ActivityHelpBinding;

public class HelpActivity extends AppCompatActivity {
    ActivityHelpBinding binding;
    private SharedPreferences.Editor editor;
    private PrefManager prefManager;
    private SharedPreferences preferences,sharedPreferences;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_help);
        setSupportActionBar(binding.toolbarHelp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prefManager = new PrefManager(this);
        preferences = HelpActivity.this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();

        binding.toolbarHelp.setNavigationOnClickListener(view -> {
            super.onBackPressed();
        });

        binding.wasapSection.setOnClickListener(view -> {
            //setupAddWhatsappDialog();
            String message="hello I Need Help Regarding "+GlobalVariables.settings.getAppName()+" Usages";
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO,
                    Uri.parse("smsto:" + "" + GlobalVariables.settings.getAppContact() + "?body=" +message));
            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        });

        binding.btnAddConcern.setOnClickListener(view -> {
            //setupAddWhatsappDialog();

        });



        binding.btnCallUs.setOnClickListener(view -> {
            //setupAddWhatsappDialog();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + GlobalVariables.settings.getAppContact()));
            startActivity(intent);

        });
    }



}