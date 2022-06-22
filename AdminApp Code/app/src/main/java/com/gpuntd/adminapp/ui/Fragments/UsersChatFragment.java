package com.gpuntd.adminapp.ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gpuntd.adminapp.Models.ChatListDTO;
import com.gpuntd.adminapp.Models.GetCommentDTO;
import com.gpuntd.adminapp.Models.HomeTopUserDTO;
import com.gpuntd.adminapp.Models.Offer_Data;
import com.gpuntd.adminapp.OneTwoOneChat;
import com.gpuntd.adminapp.Util.GlobalVariables;
import com.gpuntd.adminapp.Util.Method;
import com.gpuntd.adminapp.Util.RestAPI;
import com.gpuntd.adminapp.adapter.AdapterViewComment;
import com.gpuntd.adminapp.adapter.ChatListAdapter;
import com.gpuntd.adminapp.adapter.UserListDataAdapter;
import com.gpuntd.adminapp.databinding.FragmentChatlistBinding;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UsersChatFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "KINGSN";
    FragmentChatlistBinding binding;
    private Context mContext;
    private Method method;
    private RecyclerView rvChatList;
    public ChatListAdapter chatListAdapter;
    private ArrayList<ChatListDTO> chatList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentChatlistBinding.inflate(inflater, container, false);
        // offerrecyclerView = binding.offersRv;
        method = new Method(requireContext());
        //MyrecyclerView = binding.rvMyID;
        View root = binding.getRoot();
       // binding.swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) requireActivity());
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getChat();
            }
        });
        getChat();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getChat(){

        method.loadingDialogg(requireActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RestAPI.GET_CHAT_HISTORY_API,
                response -> {
                    // Toast.makeText(LoginActivity.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
                    try {
                       // System.out.println(response);
                        JSONObject jsonObject = new JSONObject(response);

                        JSONObject jsonObject1 = jsonObject.getJSONObject(GlobalVariables.AppSid);

                        String success = jsonObject1.getString("success");

                        if (success.equals("1")) {

                            try {
                                chatList = new ArrayList<>();
                                Type getpetDTO = new TypeToken<List<ChatListDTO>>() {
                                }.getType();
                                chatList = (ArrayList<ChatListDTO>) new Gson().fromJson(jsonObject1.getJSONArray("Results").toString(), getpetDTO);
                                showData();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    method.loadingDialog.dismiss();
                                    binding.swipeRefreshLayout.setRefreshing(false);
                                }
                            }, GlobalVariables.dismissAfter);
                        }else{
                            method.loadingDialog.dismiss();
                            binding.swipeRefreshLayout.setRefreshing(false);
                            Method.alertBox("2",jsonObject1.getString("title"),jsonObject1.getString("msg"), requireActivity(),"Select User To Chat");

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
                        binding.swipeRefreshLayout.setRefreshing(false);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("users_login", "KINGSN");
                params.put("mobile",method.adminDTO.getAdminMobile() );
                // params.put("device_id",method.getDeviceId(requireActivity()));

                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);

    }



    public HashMap<String, String> getparm() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put(GlobalVariables.USER_ID, method.adminDTO.getAdminMobile());
        return parms;
    }

    public void showData() {

        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        binding.rvChatList.setLayoutManager(mLayoutManager);

        chatListAdapter = new ChatListAdapter(getActivity(), chatList);
        binding.rvChatList.setAdapter(chatListAdapter);

        chatListAdapter = new ChatListAdapter(getActivity(), chatList);
        //binding.rvChatList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvChatList.setAdapter(chatListAdapter);
        binding.swipeRefreshLayout.setRefreshing(false);



    }


    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        getChat();
    }
}