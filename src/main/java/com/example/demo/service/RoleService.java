package com.example.demo.service;

import com.example.demo.entities.Role;
import com.example.demo.implementService.IRoleService;
import com.example.demo.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Role role) {
        if(role != null){
            Role role1 = roleRepository.findByRoleId(role.getRoleId());
            if(role1 != null){
                role1.setRoleName(role.getRoleName());
                return roleRepository.save(role1);
            }
        }

        return null;
    }

    @Override
    public boolean deleteRole(Long id) {
        Role role = roleRepository.getById(id);
        if(role != null){
            roleRepository.delete(role);
            return true;
        }
        return false;
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return Optional.empty();
    }
}
