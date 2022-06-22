package com.gpuntd.adminapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer_Data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Body")
    @Expose
    private String body;
    @SerializedName("offerStatus")
    @Expose
    private String offerStatus;
    @SerializedName("ofCreatedDate")
    @Expose
    private String ofCreatedDate;
    public Offer_Data(String id, String title, String body, String offerStatus, String ofCreatedDate) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.offerStatus = offerStatus;
        this.ofCreatedDate = ofCreatedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public String getOfCreatedDate() {
        return ofCreatedDate;
    }

    public void setOfCreatedDate(String ofCreatedDate) {
        this.ofCreatedDate = ofCreatedDate;
    }
}
