package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.FOSUser;
import fpt.edu.capstone.implementService.IEmailService;
import fpt.edu.capstone.implementService.IFOSUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private IFOSUserService ifosUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String sendFrom;

    @Override
    public void sendMailForgetPass(FOSUser fosUser) throws MessagingException, UnsupportedEncodingException {
        String subject = "Forget password from FOS_MLD sytem";
        String senderName = "FOS_MLD";
        String password = generateString(10);
        fosUser.setPassword(passwordEncoder.encode(password));
        ifosUserService.addFOSUser(fosUser);
        String mailContent = "Dear [[username]],<br><br>" + "Mật khẩu mới của bạn là :<br>"
                + "<h3>[[password]]</h3>" + "Cảm ơn,<br>" + "The FOS_MLD!";

        mailContent = mailContent.replace("[[username]]", fosUser.getUserName());
        mailContent = mailContent.replace("[[password]]", password);

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sendFrom, senderName);
            helper.setTo(fosUser.getEmail());
            helper.setSubject(subject);
            helper.setText(mailContent, true);
            javaMailSender.send(message);

//            logger.info("email sent to email: " + email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String generateString(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        String password = "";

        password += lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password += capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password += specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password += numbers.charAt(random.nextInt(numbers.length()));

        for (int i = 4; i < length; i++) {
            password += combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password;
    }
}
