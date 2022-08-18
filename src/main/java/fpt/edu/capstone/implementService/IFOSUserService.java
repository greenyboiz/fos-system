package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.FOSUser;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFOSUserService {
    public FOSUser addFOSUser(FOSUser fosUser);

    public FOSUser updateFOSUser(FOSUser fosUser);

    public boolean deleteFOSUser(Long id);

    public List<FOSUser> getAllFOSUser();

    public FOSUser getFOSUserById(Long id);

    boolean checkExistUserByUserNameAndContactAndEmail(FOSUser fosUser);

    FOSUser addFOSUserImage(MultipartFile file, FOSUser fosUser);

    boolean checkExistUserByUserName(FOSUser fosUser);

    boolean checkExistUserByContact(FOSUser fosUser);

    boolean checkExistUserByEmail(FOSUser fosUser);

    FOSUser getFOSUserByEmail(String email);

    FOSUser getByUserName(String userName);
}
