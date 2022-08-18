package fpt.edu.capstone.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import fpt.edu.capstone.entities.FOSUser;
import fpt.edu.capstone.entities.Role;
import fpt.edu.capstone.implementService.IFOSUserService;
import fpt.edu.capstone.repo.FOSUserRepository;
import fpt.edu.capstone.repo.RoleRepository;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FOSUserService implements IFOSUserService {
    @Autowired
    public FOSUserRepository fosUserRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private Cloudinary cloudinary;
    @Override
    public FOSUser addFOSUser(FOSUser fosUser) {
//        fosUser.setPassword(bcryptEncoder.encode(fosUser.getPassword()));
        fosUser.setStatus(true);
        return fosUserRepository.save(fosUser);
    }

    @Override
    public FOSUser updateFOSUser(FOSUser fosUser) {
        Role role = roleRepository.findByRoleId(fosUser.getRole().getRoleId());
        if(fosUser != null){
            FOSUser fosUser1 = fosUserRepository.findFOSUserById(fosUser.getUserId());
            if(fosUser1 != null){
                fosUser1.setFullName(fosUser.getFullName());
//                fosUser1.setUserName(fosUser.getUserName());
//                fosUser1.setPassword(bcryptEncoder.encode(fosUser.getPassword()));
                fosUser1.setRole(role);
                fosUser1.setContact(fosUser.getContact());
                fosUser1.setEmail(fosUser.getEmail());
                fosUser1.setGender(fosUser.getGender());
                fosUser1.setStatus(fosUser.getStatus());
                fosUser1.setProfileImage(fosUser.getProfileImage());

                return  fosUserRepository.save(fosUser1);
            }
        }
        return null;
    }

    @Override
    public boolean deleteFOSUser(Long id) {
        FOSUser fosUser = fosUserRepository.findFOSUserById(id);
        if(fosUser != null){
//            fosUserRepository.delete(fosUser);
            if(fosUser.getStatus() == true){
                fosUser.setStatus(false);
            }else {
                fosUser.setStatus(true);
            }
            fosUserRepository.save(fosUser);
            return true;
        }
        return false;
    }

    @Override
    public List<FOSUser> getAllFOSUser() {
        return fosUserRepository.findAll();
    }

    @Override
    public FOSUser getFOSUserById(Long id) {
        FOSUser fosUser = fosUserRepository.findFOSUserById(id);
        if(fosUser != null){
            return fosUser;
        }
        return null;
    }

    @Override
    public boolean checkExistUserByUserNameAndContactAndEmail(FOSUser fosUser) {
        FOSUser fosUser1 = fosUserRepository.checkExistUserByUserNameAndContactAndEmail(fosUser.getUserName(), fosUser.getContact(),fosUser.getEmail());
        if (fosUser1 != null){
            return true;
        }
        return false;
    }

    @Override
    public FOSUser addFOSUserImage(MultipartFile file, FOSUser fosUser) {
        try {
            Map r = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type","auto"));
            String img = (String) r.get("secure_url");
            fosUser.setProfileImage(img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fosUserRepository.save(fosUser);
    }

    @Override
    public boolean checkExistUserByUserName(FOSUser fosUser) {
        FOSUser fosUser1 = fosUserRepository.findUserByUserName(fosUser.getUserName());
        if (fosUser1 != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkExistUserByContact(FOSUser fosUser) {
        FOSUser fosUser1 = fosUserRepository.findUserByContact(fosUser.getContact());
        if (fosUser1 != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkExistUserByEmail(FOSUser fosUser) {
        FOSUser fosUser1 = fosUserRepository.findUserByEamil(fosUser.getEmail());
        if (fosUser1 != null){
            return true;
        }
        return false;
    }

    @Override
    public FOSUser getFOSUserByEmail(String email) {
        return fosUserRepository.findUserByEamil(email);
    }

    @Override
    public FOSUser getByUserName(String userName) {
        return fosUserRepository.findByUserName(userName);
    }
}
