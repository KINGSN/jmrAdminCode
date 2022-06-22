package com.gpuntd.adminapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profileuser {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("admin_user_id")
    @Expose
    private String admin_user_id;
    @SerializedName("admin_refer_code")
    @Expose
    private String admin_refer_code;
    @SerializedName("admin_password")
    @Expose
    private String admin_password;
    @SerializedName("admin_name")
    @Expose
    private String admin_name;
    @SerializedName("admin_email")
    @Expose
    private String admin_email;
    @SerializedName("admin_mobile")
    @Expose
    private String admin_mobile;
    @SerializedName("profileImg")
    @Expose
    private String profileImg;

    @SerializedName("deviceToken")
    @Expose
    private String deviceToken;

    @SerializedName("admin_status")
    @Expose
    private String admin_status;
    @SerializedName("manageUserList")
    @Expose
    private String manageUserList;
    @SerializedName("manageDepositList")
    @Expose
    private String manageDepositList;
    @SerializedName("manageCreateIDList")
    @Expose
    private String manageCreateIDList;
    @SerializedName("manageWithdrawal")
    @Expose
    private String manageWithdrawal;
    @SerializedName("managePassword")
    @Expose
    private String managePassword;
    @SerializedName("manageCloseID")
    @Expose
    private String manageCloseID;
    @SerializedName("manageNotification")
    @Expose
    private String manageNotification;
    @SerializedName("manageAppSettings")
    @Expose
    private String manageAppSettings;
    @SerializedName("manageAdminList")
    @Expose
    private String manageAdminList;
    @SerializedName("admin_login_date")
    @Expose
    private String admin_login_date;
    @SerializedName("admin_joining_date")
    @Expose
    private String admin_joining_date;

    public Profileuser(String id, String admin_user_id, String admin_refer_code, String admin_password, String admin_name,
                       String admin_email, String admin_mobile, String profileImg,String deviceToken ,String admin_status, String manageUserList,
                       String manageDepositList, String manageCreateIDList, String manageWithdrawal, String managePassword,
                       String manageCloseID, String manageNotification, String manageAppSettings, String manageAdminList,
                       String admin_login_date, String admin_joining_date) {
        this.id = id;
        this.admin_user_id = admin_user_id;
        this.admin_refer_code = admin_refer_code;
        this.admin_password = admin_password;
        this.admin_name = admin_name;
        this.admin_email = admin_email;
        this.admin_mobile = admin_mobile;
        this.profileImg = profileImg;
        this.deviceToken=deviceToken;
        this.admin_status = admin_status;
        this.manageUserList = manageUserList;
        this.manageDepositList = manageDepositList;
        this.manageCreateIDList = manageCreateIDList;
        this.manageWithdrawal = manageWithdrawal;
        this.managePassword = managePassword;
        this.manageCloseID = manageCloseID;
        this.manageNotification = manageNotification;
        this.manageAppSettings = manageAppSettings;
        this.manageAdminList = manageAdminList;
        this.admin_login_date = admin_login_date;
        this.admin_joining_date = admin_joining_date;
    }





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdmin_user_id() {
        return admin_user_id;
    }

    public void setAdmin_user_id(String admin_user_id) {
        this.admin_user_id = admin_user_id;
    }

    public String getAdmin_refer_code() {
        return admin_refer_code;
    }

    public void setAdmin_refer_code(String admin_refer_code) {
        this.admin_refer_code = admin_refer_code;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAdmin_email() {
        return admin_email;
    }

    public void setAdmin_email(String admin_email) {
        this.admin_email = admin_email;
    }

    public String getAdmin_mobile() {
        return admin_mobile;
    }

    public void setAdmin_mobile(String admin_mobile) {
        this.admin_mobile = admin_mobile;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getAdmin_status() {
        return admin_status;
    }

    public void setAdmin_status(String admin_status) {
        this.admin_status = admin_status;
    }

    public String getManageUserList() {
        return manageUserList;
    }

    public void setManageUserList(String manageUserList) {
        this.manageUserList = manageUserList;
    }

    public String getManageDepositList() {
        return manageDepositList;
    }

    public void setManageDepositList(String manageDepositList) {
        this.manageDepositList = manageDepositList;
    }

    public String getManageCreateIDList() {
        return manageCreateIDList;
    }

    public void setManageCreateIDList(String manageCreateIDList) {
        this.manageCreateIDList = manageCreateIDList;
    }

    public String getManageWithdrawal() {
        return manageWithdrawal;
    }

    public void setManageWithdrawal(String manageWithdrawal) {
        this.manageWithdrawal = manageWithdrawal;
    }

    public String getManagePassword() {
        return managePassword;
    }

    public void setManagePassword(String managePassword) {
        this.managePassword = managePassword;
    }

    public String getManageCloseID() {
        return manageCloseID;
    }

    public void setManageCloseID(String manageCloseID) {
        this.manageCloseID = manageCloseID;
    }

    public String getManageNotification() {
        return manageNotification;
    }

    public void setManageNotification(String manageNotification) {
        this.manageNotification = manageNotification;
    }

    public String getManageAppSettings() {
        return manageAppSettings;
    }

    public void setManageAppSettings(String manageAppSettings) {
        this.manageAppSettings = manageAppSettings;
    }

    public String getManageAdminList() {
        return manageAdminList;
    }

    public void setManageAdminList(String manageAdminList) {
        this.manageAdminList = manageAdminList;
    }

    public String getAdmin_login_date() {
        return admin_login_date;
    }

    public void setAdmin_login_date(String admin_login_date) {
        this.admin_login_date = admin_login_date;
    }

    public String getAdmin_joining_date() {
        return admin_joining_date;
    }

    public void setAdmin_joining_date(String admin_joining_date) {
        this.admin_joining_date = admin_joining_date;
    }


}