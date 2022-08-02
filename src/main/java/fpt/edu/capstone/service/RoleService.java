package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.Role;
import fpt.edu.capstone.implementService.IRoleService;
import fpt.edu.capstone.repo.RoleRepository;
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
        Role role = roleRepository.findByRoleId(id);
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
    public Role getRoleById(Long id) {
        return roleRepository.findByRoleId(id);
    }

    @Override
    public boolean checkRoleExist(String roleName) {
        Role role = roleRepository.checkRoleExist(roleName);
        if(role != null){
            return true;
        }
        return false;
    }
}
