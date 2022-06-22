package com.gpuntd.adminapp.retrofit;

import com.gpuntd.adminapp.Models.Profileuser;
import com.gpuntd.adminapp.retrofit.model.OfferPojo;

import retrofit2.Call;
import retrofit2.http.GET;

//yaha sare method declare honge jo bhi url endpoints hote hai , and get post annotation ke sath
// matlab idhar set krne ke bad bas directly get kar skte na ?abhi thoda config krna hai retrofit ko uske baad isdha fucntion call kr  and done
public interface GopuntAPI {

    @GET("api.php?getOffer=i/")
    Call<OfferPojo> getOffers();

    @GET("api.php?adminSettings=i/")
    Call<Profileuser> getProfileUser();

}// abe ye anything kya hai
