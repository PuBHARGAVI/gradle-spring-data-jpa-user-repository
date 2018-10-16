package com.hxs.web.model.request;


import javax.validation.constraints.NotEmpty;

/**
 * @author hsteidel
 */
public class UserCreateRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
