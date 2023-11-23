package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "RolePermissions")
public class RolePermissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "roleid")
    private Roles role;

    @ManyToOne
    @JoinColumn(name = "permissionsid")
    private Permissions permissions;

    public RolePermissions(Integer id, Roles role, Permissions permissions) {
        this.id = id;
        this.role = role;
        this.permissions = permissions;
    }

    public RolePermissions(){}

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }
}
