package com.gpuntd.adminapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HomeTopUserDTO implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("wallet")
    @Expose
    private String wallet;
    @SerializedName("total_paid")
    @Expose
    private String totalPaid;
    @SerializedName("total_redeemed")
    @Expose
    private String totalRedeemed;
    @SerializedName("user_referal_code")
    @Expose
    private String userReferalCode;
    @SerializedName("reffered_by")
    @Expose
    private String refferedBy;
    @SerializedName("reffered_paid")
    @Expose
    private String refferedPaid;
    @SerializedName("total_referals")
    @Expose
    private String totalReferals;
    @SerializedName("allow")
    @Expose
    private String allow;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("active_date")
    @Expose
    private String activeDate;
    @SerializedName("onesignal_playerid")
    @Expose
    private String onesignalPlayerid;
    @SerializedName("onesignal_pushtoken")
    @Expose
    private String onesignalPushtoken;
    @SerializedName("joining_time")
    @Expose
    private String joiningTime;

    public HomeTopUserDTO(String id, String mobile, String password, String name, String city, String email, String wallet, String totalPaid, String totalRedeemed, String userReferalCode, String refferedBy, String refferedPaid, String totalReferals, String allow, String deviceId, String deviceToken, String profilePic, String activeDate, String onesignalPlayerid, String onesignalPushtoken, String joiningTime) {
        super();
        this.id = id;
        this.mobile = mobile;
        this.password = password;
        this.name = name;
        this.city = city;
        this.email = email;
        this.wallet = wallet;
        this.totalPaid = totalPaid;
        this.totalRedeemed = totalRedeemed;
        this.userReferalCode = userReferalCode;
        this.refferedBy = refferedBy;
        this.refferedPaid = refferedPaid;
        this.totalReferals = totalReferals;
        this.allow = allow;
        this.deviceId = deviceId;
        this.deviceToken = deviceToken;
        this.profilePic = profilePic;
        this.activeDate = activeDate;
        this.onesignalPlayerid = onesignalPlayerid;
        this.onesignalPushtoken = onesignalPushtoken;
        this.joiningTime = joiningTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getTotalRedeemed() {
        return totalRedeemed;
    }

    public void setTotalRedeemed(String totalRedeemed) {
        this.totalRedeemed = totalRedeemed;
    }

    public String getUserReferalCode() {
        return userReferalCode;
    }

    public void setUserReferalCode(String userReferalCode) {
        this.userReferalCode = userReferalCode;
    }

    public String getRefferedBy() {
        return refferedBy;
    }

    public void setRefferedBy(String refferedBy) {
        this.refferedBy = refferedBy;
    }

    public String getRefferedPaid() {
        return refferedPaid;
    }

    public void setRefferedPaid(String refferedPaid) {
        this.refferedPaid = refferedPaid;
    }

    public String getTotalReferals() {
        return totalReferals;
    }

    public void setTotalReferals(String totalReferals) {
        this.totalReferals = totalReferals;
    }

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(String activeDate) {
        this.activeDate = activeDate;
    }

    public String getOnesignalPlayerid() {
        return onesignalPlayerid;
    }

    public void setOnesignalPlayerid(String onesignalPlayerid) {
        this.onesignalPlayerid = onesignalPlayerid;
    }

    public String getOnesignalPushtoken() {
        return onesignalPushtoken;
    }

    public void setOnesignalPushtoken(String onesignalPushtoken) {
        this.onesignalPushtoken = onesignalPushtoken;
    }

    public String getJoiningTime() {
        return joiningTime;
    }

    public void setJoiningTime(String joiningTime) {
        this.joiningTime = joiningTime;
    }
}
