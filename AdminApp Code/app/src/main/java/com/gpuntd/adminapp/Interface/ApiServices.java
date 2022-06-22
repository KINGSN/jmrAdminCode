package com.gpuntd.adminapp.Interface;

import com.gpuntd.adminapp.Models.AppSettings.AppSettings;
import com.gpuntd.adminapp.Models.CheckUser.CheckUser;
import com.gpuntd.adminapp.Models.GeneralSettings.GeneralSettings;
import com.gpuntd.adminapp.Models.HomeDataDTO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {
    String baseUrl =" https://darwinbark.com/projects/gopunt/";

    @GET("api_v1/api.php?settings")
    Call<AppSettings> settings();

    @GET("general_setting")
    Call<GeneralSettings> general_settings();


    @FormUrlEncoded
    @POST("api.php?check_user")
    Call<CheckUser> check_user(@Field("phone") String phone,
                               @Field("device_id") String device_id);
/*

    @POST("users_api/register")
    Call<User> createUser(@Body User user);Method.loadingdialog

    @FormUrlEncoded
    @POST("api.php?user_register")
    Call<User> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("user_refrence_code") String confirm_code
    );
    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("api.php?user_register")
    Call<User> createPost(@FieldMap Map<String, String> fields);
*/


    @GET("top-headlines")
    Call<HomeDataDTO> getNews(
            @Query("country") String country,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey


    );


}
