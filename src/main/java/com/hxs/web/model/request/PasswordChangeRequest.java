package com.hxs.web.model.request;


import javax.validation.constraints.NotEmpty;

/**
 * @author HSteidel
 */
public class PasswordChangeRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String currentPassword;

    @NotEmpty
    private String newPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
