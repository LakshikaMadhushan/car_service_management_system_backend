package com.esoft.carservice.service.Impl;

import com.esoft.carservice.configuration.exception.CustomException;
import com.esoft.carservice.configuration.exception.ServiceException;
import com.esoft.carservice.dto.requset.UpdatePasswordRequestDTO;
import com.esoft.carservice.entity.User;
import com.esoft.carservice.repository.UserRepository;
import com.esoft.carservice.service.UserService;
import com.esoft.carservice.util.EmailSender;
import com.esoft.carservice.util.PasswordGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static com.esoft.carservice.constant.ResponseCodes.OPERATION_FAILED;
import static com.esoft.carservice.constant.ResponseCodes.RESOURCE_NOT_FOUND;

@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

    private PasswordGenerator passwordGenerator;
    private UserRepository userRepository;
    private EmailSender emailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordGenerator passwordGenerator, UserRepository userRepository, EmailSender emailSender) {
        this.passwordGenerator = passwordGenerator;
        this.userRepository = userRepository;
        this.emailSender = emailSender;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePassword(UpdatePasswordRequestDTO requestDTO) {
        log.info("Execute method UpdatePasswordRequestDTO : @param : {} ", requestDTO);
        try {
            Optional<User> optionalUser = userRepository.findLatestByEmail(requestDTO.getEmail());
            if (!optionalUser.isPresent()) {
                throw new ServiceException(RESOURCE_NOT_FOUND, "Sorry, the user you are finding cannot be found. ");
            }
            User user = optionalUser.get();

            String newPassword = passwordGenerator.generateRandomPassword();
            String password = passwordEncoder.encode(newPassword);
            user.setPassword(password);
            userRepository.save(user);
            //send service email

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
            StringBuilder body = new StringBuilder();
            body.append("Dear Customer,\n\n");
            body.append("We hope this message finds you well. We wanted to inform you about new code.\n\n");

            body.append("\n");
            body.append("Date: ").append(dateFormat.format(new Date())).append("\n");
            body.append("Code : ").append(newPassword).append("\n");


            body.append("Thank you for choosing our service. If you have any questions or need further assistance, feel free to reach out.\n\n");
            body.append("Best Regards,\nYour Service Team");


            emailSender.sendEmail(requestDTO.getEmail(), "User Credentials Information", body.toString());

        } catch (Exception e) {
            log.error("Method UpdatePasswordRequestDTO : " + e.getMessage(), e);
            throw new CustomException(OPERATION_FAILED, e.getMessage());
        }
    }

}
