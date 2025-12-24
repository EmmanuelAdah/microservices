package com.server.userservice.data.models;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_USER,
    ROLE_ADMIN;

    public Role role;

    public void setRole(Role role){
        this.role = role;
    }
}
