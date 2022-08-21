package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.Role;
import fpt.edu.capstone.repo.RoleRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoleServiceTest {
    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Test
    public void addRoleTest(){
        Role expect = new Role(1l,"ROLE_ADMIN");
        Role actual = new Role(2l,"ROLE_STAFF");
        Mockito.when(roleRepository.save(actual)).thenReturn(expect);
        Role result = roleService.addRole(actual);
        Assert.assertEquals(result,expect);
    }

    @Test
    public void addRoleTestFail(){
        Role expect = new Role(1l,"ROLE_ADMIN");
        Role actual = new Role(2l,"ROLE_STAFF");
        Mockito.when(roleRepository.save(actual)).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> roleService.addRole(actual));
        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void updateRoleTest(){
        Role expect = new Role(1l,"ROLE_ADMIN");
        Role actual = new Role(1l,"ROLE_STAFF");
        Mockito.when(roleRepository.findByRoleId(actual.getRoleId())).thenReturn(expect);
        Mockito.when(roleRepository.save(actual)).thenReturn(expect);
        Role result = roleService.updateRole(actual);
        Assert.assertEquals(result,expect);

    }

    @Test
    public void updateRoleTestFail(){
        Role expect = new Role(1l,"ROLE_ADMIN");
        Role actual = new Role(1l,"ROLE_STAFF");
        Mockito.when(roleRepository.findByRoleId(actual.getRoleId())).thenReturn(expect);
        Mockito.when(roleRepository.save(actual)).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> roleService.updateRole(actual));

        Assert.assertEquals("",result.getMessage());

    }

    @Test
    public void deleteRoleTest(){
        Role expect = new Role(1l,"ROLE_ADMIN");
        Role actual = new Role(1l,"ROLE_STAFF");
        Mockito.when(roleRepository.findByRoleId(actual.getRoleId())).thenReturn(expect);
        roleRepository.delete(actual);
        Mockito.verify(roleRepository,Mockito.timeout(1)).delete(actual);

        boolean result = roleService.deleteRole(actual.getRoleId());
        Assert.assertEquals(result,true);
    }

    @Test
    public void getAllRoleTest(){
        List<Role> expect = new ArrayList<>();
        expect.add(new Role(1l,"ROLE_ADMIN"));
        List<Role> actual = new ArrayList<>();
        actual.add(new Role(1l,"ROLE_STAFF"));
        Mockito.when(roleRepository.findAll()).thenReturn(expect);
        List<Role> results = roleService.getAllRole();
        Assert.assertEquals(results,expect);
    }

    @Test
    public void getAllRoleTestFail(){
        List<Role> expect = new ArrayList<>();
        expect.add(new Role(1l,"ROLE_ADMIN"));
        List<Role> actual = new ArrayList<>();
        actual.add(new Role(1l,"ROLE_STAFF"));
        Mockito.when(roleRepository.findAll()).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> roleService.getAllRole());

        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void getRoleByIdTest(){
        Role expect = new Role(1l,"ROLE_ADMIN");
        Role actual = new Role(1l,"ROLE_STAFF");
        Mockito.when(roleRepository.findByRoleId(actual.getRoleId())).thenReturn(expect);

        Role result = roleService.getRoleById(actual.getRoleId());
        Assert.assertEquals(result,expect);
    }

    @Test
    public void getRoleByIdTestFail(){
        Role expect = new Role(1l,"ROLE_ADMIN");
        Role actual = new Role(1l,"ROLE_STAFF");
        Mockito.when(roleRepository.findByRoleId(actual.getRoleId())).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> roleService.getRoleById(actual.getRoleId()));

        Assert.assertEquals("",result.getMessage());
    }
}
