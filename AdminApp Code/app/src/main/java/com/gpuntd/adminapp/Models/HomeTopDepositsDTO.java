package com.gpuntd.adminapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HomeTopDepositsDTO implements Serializable {

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
    @SerializedName("userMobile")
    @Expose
    private String userMobile;
    @SerializedName("txnType")
    @Expose
    private String txnType;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("createdId")
    @Expose
    private String createdId;
    @SerializedName("Idcreated")
    @Expose
    private String idcreated;
    @SerializedName("payMentMode")
    @Expose
    private String payMentMode;
    @SerializedName("payMode")
    @Expose
    private String payMode;
    @SerializedName("txnStatus")
    @Expose
    private String txnStatus;
    @SerializedName("idUsername")
    @Expose
    private String idUsername;
    @SerializedName("idPassword")
    @Expose
    private String idPassword;
    @SerializedName("adminComment")
    @Expose
    private String adminComment;
    @SerializedName("txnDate")
    @Expose
    private String txnDate;
    @SerializedName("cancelledDate")
    @Expose
    private String cancelledDate;
    @SerializedName("verifiedDate")
    @Expose
    private String verifiedDate;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("idWebsite")
    @Expose
    private String idWebsite;
    @SerializedName("idName")
    @Expose
    private String idName;

    @SerializedName("wDetails")
    @Expose
    private String wDetails;

    public HomeTopDepositsDTO(String id, String mobile, String password, String name, String city, String email, String wallet,
                              String totalPaid, String totalRedeemed, String userReferalCode, String refferedBy, String refferedPaid,
                              String totalReferals, String allow, String deviceId, String deviceToken, String profilePic, String activeDate,
                              String onesignalPlayerid, String onesignalPushtoken, String joiningTime, String userMobile, String txnType,
                              String orderId, String amount, String createdId, String idcreated, String payMentMode, String payMode,
                              String txnStatus, String idUsername, String idPassword, String adminComment, String txnDate,
                              String cancelledDate, String verifiedDate,String img,String idWebsite,String idName,String wDetails) {
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
        this.userMobile = userMobile;
        this.txnType = txnType;
        this.orderId = orderId;
        this.amount = amount;
        this.createdId = createdId;
        this.idcreated = idcreated;
        this.payMentMode = payMentMode;
        this.payMode = payMode;
        this.txnStatus = txnStatus;
        this.idUsername = idUsername;
        this.idPassword = idPassword;
        this.adminComment = adminComment;
        this.txnDate = txnDate;
        this.cancelledDate = cancelledDate;
        this.verifiedDate = verifiedDate;
        this.img=img;
        this.idWebsite=idWebsite;
        this.idName=idName;
        this.wDetails=wDetails;
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

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreatedId() {
        return createdId;
    }

    public void setCreatedId(String createdId) {
        this.createdId = createdId;
    }

    public String getIdcreated() {
        return idcreated;
    }

    public void setIdcreated(String idcreated) {
        this.idcreated = idcreated;
    }

    public String getPayMentMode() {
        return payMentMode;
    }

    public void setPayMentMode(String payMentMode) {
        this.payMentMode = payMentMode;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    public String getIdUsername() {
        return idUsername;
    }

    public void setIdUsername(String idUsername) {
        this.idUsername = idUsername;
    }

    public String getIdPassword() {
        return idPassword;
    }

    public void setIdPassword(String idPassword) {
        this.idPassword = idPassword;
    }

    public String getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(String cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public String getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(String verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIdWebsite() {
        return idWebsite;
    }

    public void setIdWebsite(String idWebsite) {
        this.idWebsite = idWebsite;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getwDetails() {
        return wDetails;
    }

    public void setwDetails(String wDetails) {
        this.wDetails = wDetails;
    }
}
