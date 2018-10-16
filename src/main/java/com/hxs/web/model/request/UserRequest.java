package com.hxs.web.model.request;


import javax.validation.constraints.NotEmpty;

/**
 * @author hsteidel
 */
public class UserRequest {

    private Long id;

    @NotEmpty
    private String username;

    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
