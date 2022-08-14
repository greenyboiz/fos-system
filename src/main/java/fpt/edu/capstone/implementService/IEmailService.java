package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.FOSUser;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IEmailService {
    void sendMailForgetPass(FOSUser fosUser) throws MessagingException, UnsupportedEncodingException;
}
