package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    public Role addRole(Role role);

    public Role updateRole(Role role);

    public boolean deleteRole(Long id);

    public List<Role> getAllRole();

    public Role getRoleById(Long id);

    boolean checkRoleExist(String roleName);
}
