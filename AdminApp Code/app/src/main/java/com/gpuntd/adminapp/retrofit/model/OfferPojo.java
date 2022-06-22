
package com.gpuntd.adminapp.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OfferPojo {

    @SerializedName("darwinbarkk")
    @Expose
    private Darwinbarkk darwinbarkk;

    public Darwinbarkk getDarwinbarkk() {
        return darwinbarkk;
    }

    public void setDarwinbarkk(Darwinbarkk darwinbarkk) {
        this.darwinbarkk = darwinbarkk;
    }

    public class Darwinbarkk {

        @SerializedName("success")
        @Expose
        private String success;
        @SerializedName("Results")
        @Expose
        private List<Result> results = null;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

    }

    public class Result {

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




}



