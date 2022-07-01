package com.example.demo.implementService;

import com.example.demo.entities.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    public Role addRole(Role role);

    public Role updateRole(Role role);

    public boolean deleteRole(Long id);

    public List<Role> getAllRole();

    public Optional<Role> getRoleById(Long id);
}
