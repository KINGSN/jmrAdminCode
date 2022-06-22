package com.gpuntd.adminapp.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeDataDTO implements Serializable {
    ArrayList<HomeGraphDTO> homeGraphs = new ArrayList<>();
    ArrayList<Profileuser> profileusers = new ArrayList<>();
    ArrayList<Settings> settings = new ArrayList<>();
    ArrayList<HomeTopUserDTO> homeTopUserDTO = new ArrayList<>();
    ArrayList<HomeTopDepositsDTO> homeTopDeposits = new ArrayList<>();

    public ArrayList<HomeGraphDTO> getHomeGraphs(ArrayList<HomeGraphDTO> homeGraphDTOArrayList) {
        return homeGraphs;
    }

    public void setHomeGraphs(ArrayList<HomeGraphDTO> homeGraphs) {
        this.homeGraphs = homeGraphs;
    }

    public ArrayList<Profileuser> getProfileusers() {
        return profileusers;
    }

    public void setProfileusers(ArrayList<Profileuser> profileusers) {
        this.profileusers = profileusers;
    }

    public ArrayList<Settings> getSettings() {
        return settings;
    }

    public void setSettings(ArrayList<Settings> settings) {
        this.settings = settings;
    }

    public ArrayList<HomeTopUserDTO> getHomeTopUserDTO() {
        return homeTopUserDTO;
    }

    public void setHomeTopUserDTO(ArrayList<HomeTopUserDTO> homeTopUserDTO) {
        this.homeTopUserDTO = homeTopUserDTO;
    }

    public ArrayList<HomeTopDepositsDTO> getHomeTopDeposits() {
        return homeTopDeposits;
    }

    public void setHomeTopDeposits(ArrayList<HomeTopDepositsDTO> homeTopDeposits) {
        this.homeTopDeposits = homeTopDeposits;
    }
}
