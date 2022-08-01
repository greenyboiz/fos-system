package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.FOSUser;
import fpt.edu.capstone.entities.Role;
import fpt.edu.capstone.implementService.IFOSUserService;
import fpt.edu.capstone.repo.FOSUserRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtUserDetailsServiceTest {
    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;

    @Mock
    private FOSUserRepository fosUserRepository;

    @Mock
    private PasswordEncoder bcryptEncoder;

    @Test
    public void saveUserTest(){
        Role roleExpect = new Role(1l,"ADMIN");
        Role roleUpdate = new Role(2l,"STAFF");

        FOSUser expect = new FOSUser(1l, "hoang tien dat111111","dat1", "12345",true,"0966564666","dat1@gmail.com",true,"image",roleExpect);
        FOSUser actual = new FOSUser(1l, "hoang tien dat","dat1", "12345",true,"0966564666","dat1@gmail.com",true,"image",roleUpdate);

        expect.setPassword(bcryptEncoder.encode(expect.getPassword()));
        Mockito.when(fosUserRepository.save(actual)).thenReturn(expect);
        FOSUser result = jwtUserDetailsService.save(actual);
        Assert.assertEquals(result,expect);
    }
}
