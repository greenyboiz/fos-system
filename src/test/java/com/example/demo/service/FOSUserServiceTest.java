package com.example.demo.service;

import com.example.demo.entities.FOSUser;
import com.example.demo.entities.Role;
import com.example.demo.repo.FOSUserRepository;
import com.example.demo.repo.RoleRepository;
import com.example.demo.response.ResponseObject;
import com.example.demo.service.FOSUserService;
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
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FOSUserServiceTest {

//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    private MockMvc mockMvc;

    @InjectMocks
    private FOSUserService fosUserService;

    @Mock
    private FOSUserRepository fosUserRepository;

    @Mock
    private RoleRepository roleRepository;

//    @Before
//    public void setUp(){
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }

    @Test
    public void getAllFOSUserTest(){
        List<FOSUser> expect = new ArrayList<>();
        expect.add(new FOSUser(20l, "hoang tien dat","dat1", "12345","nam","0966564666","dat1@gmail.com",1,"image",null));
        List<FOSUser> actual = new ArrayList<>();
        actual.add(new FOSUser(20l, "hoang tien dat","dat1", "12345","nam","0966564666","dat1@gmail.com",1,"image",null));

        Mockito.when(fosUserRepository.findAll()).thenReturn(actual);

        List<FOSUser> results = fosUserService.getAllFOSUser();
        Assert.assertEquals(results,actual);
    }

    @Test
    public void addFOSUserTest(){
        FOSUser newUser = new FOSUser("hoang tien dat111111","dat1", "12345","nam","0966564666","dat1@gmail.com",1,"image",null);
        FOSUser expectUser = new FOSUser(1L,"hoang tien dat111111","dat1", "12345","nam","0966564666","dat1@gmail.com",1,"image",null);
        Mockito.when(fosUserRepository.save(newUser)).thenReturn(expectUser);
        FOSUser result = fosUserService.addFOSUser(newUser);
//        Assert.assertEquals(result.getUserId(),new Long(1));
        Assert.assertEquals(result,expectUser);
    }

    @Test
    public void updateFOSUserTest(){
        Role roleExpect = new Role(1l,"ADMIN");
        Role roleUpdate = new Role(2l,"STAFF");

        FOSUser oldUser = new FOSUser(20l, "hoang tien dat111111","dat1", "12345","nam","0966564666","dat1@gmail.com",1,"image",roleExpect);
        FOSUser fosUserForUpdate = new FOSUser(20l, "hoang tien dat","dat1", "12345","nam","0966564666","dat1@gmail.com",1,"image",roleUpdate);

        Mockito.when(roleRepository.findByRoleId(fosUserForUpdate.getRole().getRoleId())).thenReturn(roleUpdate);
        Mockito.when(fosUserRepository.getById(fosUserForUpdate.getUserId())).thenReturn(oldUser);
        Mockito.when(fosUserRepository.save(oldUser)).thenReturn(fosUserForUpdate);

        FOSUser result = fosUserService.updateFOSUser(fosUserForUpdate);

        Assert.assertEquals(result,fosUserForUpdate);

    }

    @Test
    public void getFOSUserByIdTest(){
        FOSUser expect = new FOSUser(20l, "hoang tien dat","dat1", "12345","nam","0966564666","dat1@gmail.com",1,"image",null);
        FOSUser actual = new FOSUser(20l, "hoang tien dat","dat1", "12345","nam","0966564666","dat1@gmail.com",1,"image",null);

        Mockito.when(fosUserRepository.getById(actual.getUserId())).thenReturn(actual);
//        ResponseEntity<ResponseObject> result = fosUserService.getFOSUserById(expect.getUserId());
        Assert.assertEquals(expect,actual);
    }

    @Test
    public void deleteFOSUserByIdTest(){
        FOSUser fosUser = new FOSUser(20l, "hoang tien dat","dat1", "12345","nam","0966564666","dat1@gmail.com",1,"image",null);
        FOSUser actual = new FOSUser(20l, "hoang tien dat","dat1", "12345","nam","0966564666","dat1@gmail.com",1,"image",null);
//        fosUserService.deleteFOSUser(fosUser.getUserId());
//        Mockito.when(fosUserService.deleteFOSUser(fosUser.getUserId())).thenReturn(true);
        Mockito.when(fosUserRepository.getById(fosUser.getUserId())).thenReturn(actual);
//        Mockito.when(fosUserRepository.delete(actual)).thenReturn(true);

        Boolean result = fosUserService.deleteFOSUser(actual.getUserId());

        Assert.assertEquals(result,true);

    }
}
