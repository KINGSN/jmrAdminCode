package com.gpuntd.adminapp.Models;

import java.io.Serializable;

/**
 * Created by VARUN on 01/01/19.
 */
public class payment_modeDTO implements Serializable{
    String admin_user_id="";
    String admin_password="";
    String admin_name="";
    String sl_no;
    String mode_name = "";
    String mode_icon="";
    String mode_hint = "";
    String instruction = "";
    String status = "";
    String minimum_redeem = "";
    String updated_updated_date = "";



   /* public payment_modeDTO(String id, String sl_no, String mode_name, String mode_icon, String mode_hint, String instruction, String minimum_redeem, String status, String updated_date) {
        this.id="";
        this.sl_no="";
        this.mode_name = "";
        this.mode_icon="";
        this.mode_hint = "";
        this.instruction = "";
        this.status = "";
        this.minimum_redeem = "";
        this.updated_updated_date = "";


    }
*/

    public String getAdmin_user_id() {
        return admin_user_id;
    }

    public void setAdmin_user_id(String admin_user_id) {
        this.admin_user_id = admin_user_id;
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

    public String getSl_no() {
        return sl_no;
    }

    public void setSl_no(String sl_no) {
        this.sl_no = sl_no;
    }

    public String getMode_name() {
        return mode_name;
    }

    public void setMode_name(String mode_name) {
        this.mode_name = mode_name;
    }

    public String getMode_icon() {
        return mode_icon;
    }

    public void setMode_icon(String mode_icon) {
        this.mode_icon = mode_icon;
    }

    public String getMode_hint() {
        return mode_hint;
    }

    public void setMode_hint(String mode_hint) {
        this.mode_hint = mode_hint;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getMinimum_redeem() {
        return minimum_redeem;
    }

    public void setMinimum_redeem(String minimum_redeem) {
        this.minimum_redeem = minimum_redeem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdated_updated_date() {
        return updated_updated_date;
    }

    public void setUpdated_updated_date(String updated_updated_date) {
        this.updated_updated_date = updated_updated_date;
    }
}
