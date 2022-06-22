package com.gpuntd.adminapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeGraphDTO implements Serializable {
    @SerializedName("totalActiveUser")
    @Expose
    private String totalActiveUser;
    @SerializedName("totaldeActiveUser")
    @Expose
    private String totaldeActiveUser;
    @SerializedName("totalBlockedUser")
    @Expose
    private String totalBlockedUser;
    @SerializedName("totalUser")
    @Expose
    private String totalUser;
    @SerializedName("totalDPending")
    @Expose
    private String totalDPending;
    @SerializedName("totalDVerified")
    @Expose
    private String totalDVerified;
    @SerializedName("totalDRejected")
    @Expose
    private String totalDRejected;
    @SerializedName("totalcIDPending")
    @Expose
    private String totalcIDPending;
    @SerializedName("totalcIDVerified")
    @Expose
    private String totalcIDVerified;
    @SerializedName("totalcIDRejected")
    @Expose
    private String totalcIDRejected;
    @SerializedName("totalwPending")
    @Expose
    private String totalwPending;
    @SerializedName("totalwVerified")
    @Expose
    private String totalwVerified;
    @SerializedName("totalwRejected")
    @Expose
    private String totalwRejected;
    @SerializedName("totalclosePending")
    @Expose
    private String totalclosePending;
    @SerializedName("totalcloseVerified")
    @Expose
    private String totalcloseVerified;
    @SerializedName("totalcloseRejected")
    @Expose
    private String totalcloseRejected;
    private String totalD;
    private String totalcID;
    private String totalw;
    private String totalClose;
    private String totalADeposited;
    private String totalAWithdrawals;
    public HomeGraphDTO(String totalActiveUser,String totaldeActiveUser, String totalBlockedUser, String totalUser,
                        String totalD,String totalDPending, String totalDVerified, String totalDRejected,String totalcID,
                        String totalcIDPending, String totalcIDVerified, String totalcIDRejected, String totalw,String totalwPending,
                        String totalwVerified, String totalwRejected,String totalClose, String totalclosePending, String totalcloseVerified,
                        String totalcloseRejected,String totalADeposited,String totalAWithdrawals) {
        super();
        this.totalActiveUser = totalActiveUser;
        this.totaldeActiveUser=totaldeActiveUser;
        this.totalBlockedUser = totalBlockedUser;
        this.totalUser = totalUser;
        this.totalD=totalD;
        this.totalDPending = totalDPending;
        this.totalDVerified = totalDVerified;
        this.totalDRejected = totalDRejected;
        this.totalcID=totalcID;
        this.totalcIDPending = totalcIDPending;
        this.totalcIDVerified = totalcIDVerified;
        this.totalcIDRejected = totalcIDRejected;
        this.totalw=totalw;
        this.totalwPending = totalwPending;
        this.totalwVerified = totalwVerified;
        this.totalwRejected = totalwRejected;
        this.totalClose=totalClose;
        this.totalclosePending = totalclosePending;
        this.totalcloseVerified = totalcloseVerified;
        this.totalcloseRejected = totalcloseRejected;
        this.totalcloseVerified = totalcloseVerified;
        this.totalcloseRejected = totalcloseRejected;
        this.totalADeposited=totalADeposited;
        this.totalAWithdrawals=totalAWithdrawals;
    }

    public String getTotalActiveUser() {
        return totalActiveUser;
    }

    public void setTotalActiveUser(String totalActiveUser) {
        this.totalActiveUser = totalActiveUser;
    }

    public String getTotaldeActiveUser() {
        return totaldeActiveUser;
    }

    public void setTotaldeActiveUser(String totaldeActiveUser) {
        this.totaldeActiveUser = totaldeActiveUser;
    }

    public String getTotalBlockedUser() {
        return totalBlockedUser;
    }

    public void setTotalBlockedUser(String totalBlockedUser) {
        this.totalBlockedUser = totalBlockedUser;
    }

    public String getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(String totalUser) {
        this.totalUser = totalUser;
    }

    public String getTotalDPending() {
        return totalDPending;
    }

    public void setTotalDPending(String totalDPending) {
        this.totalDPending = totalDPending;
    }

    public String getTotalDVerified() {
        return totalDVerified;
    }

    public void setTotalDVerified(String totalDVerified) {
        this.totalDVerified = totalDVerified;
    }

    public String getTotalDRejected() {
        return totalDRejected;
    }

    public void setTotalDRejected(String totalDRejected) {
        this.totalDRejected = totalDRejected;
    }

    public  String getTotalcIDPending() {
        return totalcIDPending;
    }

    public void setTotalcIDPending(String totalcIDPending) {
        this.totalcIDPending = totalcIDPending;
    }

    public String getTotalcIDVerified() {
        return totalcIDVerified;
    }

    public void setTotalcIDVerified(String totalcIDVerified) {
        this.totalcIDVerified = totalcIDVerified;
    }

    public String getTotalcIDRejected() {
        return totalcIDRejected;
    }

    public void setTotalcIDRejected(String totalcIDRejected) {
        this.totalcIDRejected = totalcIDRejected;
    }

    public String getTotalwPending() {
        return totalwPending;
    }

    public void setTotalwPending(String totalwPending) {
        this.totalwPending = totalwPending;
    }

    public String getTotalwVerified() {
        return totalwVerified;
    }

    public void setTotalwVerified(String totalwVerified) {
        this.totalwVerified = totalwVerified;
    }

    public String getTotalwRejected() {
        return totalwRejected;
    }

    public void setTotalwRejected(String totalwRejected) {
        this.totalwRejected = totalwRejected;
    }

    public String getTotalclosePending() {
        return totalclosePending;
    }

    public void setTotalclosePending(String totalclosePending) {
        this.totalclosePending = totalclosePending;
    }

    public String getTotalcloseVerified() {
        return totalcloseVerified;
    }

    public void setTotalcloseVerified(String totalcloseVerified) {
        this.totalcloseVerified = totalcloseVerified;
    }

    public String getTotalcloseRejected() {
        return totalcloseRejected;
    }

    public void setTotalcloseRejected(String totalcloseRejected) {
        this.totalcloseRejected = totalcloseRejected;
    }

    public String getTotalD() {
        return totalD;
    }

    public void setTotalD(String totalD) {
        this.totalD = totalD;
    }

    public String getTotalcID() {
        return totalcID;
    }

    public void setTotalcID(String totalcID) {
        this.totalcID = totalcID;
    }

    public String getTotalw() {
        return totalw;
    }

    public void setTotalw(String totalw) {
        this.totalw = totalw;
    }

    public String getTotalClose() {
        return totalClose;
    }

    public void setTotalClose(String totalClose) {
        this.totalClose = totalClose;
    }

    public String getTotalADeposited() {
        return totalADeposited;
    }

    public void setTotalADeposited(String totalADeposited) {
        this.totalADeposited = totalADeposited;
    }

    public String getTotalAWithdrawals() {
        return totalAWithdrawals;
    }

    public void setTotalAWithdrawals(String totalAWithdrawals) {
        this.totalAWithdrawals = totalAWithdrawals;
    }
}
