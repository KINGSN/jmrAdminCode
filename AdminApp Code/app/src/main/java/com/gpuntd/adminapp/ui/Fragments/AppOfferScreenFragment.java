package com.gpuntd.adminapp.ui.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.gpuntd.adminapp.R;
import com.gpuntd.adminapp.Util.Method;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppOfferScreenFragment extends Fragment {
    private static final String TAG ="KINGSN" ;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    Method method;
    com.airbnb.lottie.LottieAnimationView animationView;

    public AppOfferScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_appoffer, container, false);

        method=new Method(getActivity());
       // method.loadingDialogg((AppCompatActivity) getActivity());

        return v;
    }




}
