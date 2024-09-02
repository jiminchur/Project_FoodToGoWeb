package com.foodtogo.mono.user.dto.request;

public class RoleUpdateRequest {
    private String newRole;

    // Constructor
    public RoleUpdateRequest(String newRole) {
        this.newRole = newRole;
    }

    // Getter and Setter
    public String getNewRole() {
        return newRole;
    }

    public void setNewRole(String newRole) {
        this.newRole = newRole;
    }
}
