package com.gpuntd.adminapp.ui.settings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.adapter.TabAdapter;
import com.gpuntd.adminapp.databinding.FragmentSettingsBinding;


public class SettingsFragment extends Fragment {

    private static final String TAG = "KINGSN";
    FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
      //  method = new Method(requireContext());

        TabAdapter walletAdapter=new TabAdapter(requireActivity(),getParentFragmentManager());
        TabLayout tabLayout = root.findViewById(R.id.tabLayout);
        ViewPager viewPager = root.findViewById(R.id.viewPagerSetting);
        viewPager.setAdapter(walletAdapter);

        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        super.onDestroy();
    }



}