package com.omri.trackinglibrary.api;

import com.google.gson.annotations.SerializedName;

public class UserStatusRequest {
    @SerializedName("is_active")
    private boolean isActive;

    public UserStatusRequest(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}