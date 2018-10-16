package com.hxs.web.model.request;


import javax.validation.constraints.NotEmpty;

/**
 * @author hsteidel
 */
public class RoleRequest {

    private Long id;

    @NotEmpty
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

}
