package com.gpuntd.adminapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.PrefManager;

public class TermsActivity extends AppCompatActivity {
    private static final String TAG = "KINGSN";
    private SharedPreferences preferences,sharedPreferences;
    private SharedPreferences.Editor editor;
    private PrefManager prefManager;
    private Method method;
    private ImageView backbtn;

    private TextView privacy;
    private String type="";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        prefManager = new PrefManager(this);
        Log.d(TAG, "onCreateView: "+ prefManager.getValue("OnesignalappKey"));
        preferences = TermsActivity.this.getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        method=new Method(this);
        if(getIntent().hasExtra("type")){
            type="1";
        }

        privacy=(TextView) findViewById(R.id.privacy);

       // privacy.setText(GlobalVariables.settings.getAppDescription());
        privacy.setText(Html.fromHtml(GlobalVariables.settings.getAppDescription(), Html.FROM_HTML_MODE_COMPACT));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}