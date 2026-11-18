package com.arenahub.arenahub.database.models.enums;

import lombok.Getter;

@Getter
public enum UserRoles {

    ADMIN("admin"),
    GESTOR("gestor"),
    CLIENTE("client");

    private String role;

    UserRoles(String role) {
        this.role = role;
    }
}
