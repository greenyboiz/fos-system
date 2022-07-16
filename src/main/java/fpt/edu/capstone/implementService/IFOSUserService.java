package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.FOSUser;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IFOSUserService {
    public FOSUser addFOSUser(FOSUser fosUser);

    public FOSUser updateFOSUser(FOSUser fosUser);

    public boolean deleteFOSUser(Long id);

    public List<FOSUser> getAllFOSUser();

    public ResponseEntity<ResponseObject> getFOSUserById(Long id);

    boolean checkExistUserByUserNameAndContactAndEmail(FOSUser fosUser);
}
