package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.Role;
import fpt.edu.capstone.implementService.IRoleService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    private IRoleService iRoleService;

    @GetMapping("/role")
    public ResponseEntity<?> getAllRoles(){
        List<Role> roles = iRoleService.getAllRole();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "successfull",true, roles)
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/role/add")
    public ResponseEntity<?> saveRole(@RequestBody Role role){
        boolean checkRoleExist = iRoleService.checkRoleExist(role.getRoleName());
        if(!checkRoleExist){
            iRoleService.addRole(role);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, iRoleService.addRole(role))
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("fail", "Role is exist",false, null)
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/role/update")
    public ResponseEntity<?> updateRole(@RequestBody Role role){
        Role role1 = iRoleService.updateRole(role);
        if (role1 != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "update role successfull",true, role1)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Role not exist",false, null)
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/role/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable("id") Long id){
        boolean deleteRole = iRoleService.deleteRole(id);
        if(deleteRole){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete roleId = " + id + " successfull",true, deleteRole)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "roleId = " + id + " not exist",false, null)
        );
    }
}
