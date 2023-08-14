package com.example.app.service;

import com.example.app.model.Role;
import com.example.app.dto.RoleDto;

public interface RoleService {
    Role createRole(RoleDto roleDto);
    Role updateRole(RoleDto roleDto);
}
