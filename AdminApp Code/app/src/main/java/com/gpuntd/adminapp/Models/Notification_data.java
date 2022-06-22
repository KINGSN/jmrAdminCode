package com.gpuntd.adminapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification_data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userMobile")
    @Expose
    private String userMobile;
    @SerializedName("nType")
    @Expose
    private String nType;
    @SerializedName("norderId")
    @Expose
    private String norderId;
    @SerializedName("nAmount")
    @Expose
    private String nAmount;
    @SerializedName("nTitle")
    @Expose
    private String nTitle;
    @SerializedName("nMessage")
    @Expose
    private String nMessage;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("nCreatedDate")
    @Expose
    private String nCreatedDate;
    @SerializedName("nUpdatedDate")
    @Expose
    private String nUpdatedDate;

    public Notification_data(String id, String userMobile, String nType, String norderId,
                             String nAmount, String nTitle, String nMessage, String status,
                             String nCreatedDate, String nUpdatedDate)
    {
        this.id = id;
        this.userMobile = userMobile;
        this.nType=nType;
        this.norderId = norderId;
        this.nAmount = nAmount;
        this.nTitle = nTitle;
        this.nMessage = nMessage;
        this.status = status;
        this.nCreatedDate = nCreatedDate;
        this.nUpdatedDate = nUpdatedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getnType() {
        return nType;
    }

    public void setnType(String nType) {
        this.nType = nType;
    }

    public String getNorderId() {
        return norderId;
    }

    public void setNorderId(String norderId) {
        this.norderId = norderId;
    }

    public String getnAmount() {
        return nAmount;
    }

    public void setnAmount(String nAmount) {
        this.nAmount = nAmount;
    }

    public String getnTitle() {
        return nTitle;
    }

    public void setnTitle(String nTitle) {
        this.nTitle = nTitle;
    }

    public String getnMessage() {
        return nMessage;
    }

    public void setnMessage(String nMessage) {
        this.nMessage = nMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getnCreatedDate() {
        return nCreatedDate;
    }

    public void setnCreatedDate(String nCreatedDate) {
        this.nCreatedDate = nCreatedDate;
    }

    public String getnUpdatedDate() {
        return nUpdatedDate;
    }

    public void setnUpdatedDate(String nUpdatedDate) {
        this.nUpdatedDate = nUpdatedDate;
    }
}
