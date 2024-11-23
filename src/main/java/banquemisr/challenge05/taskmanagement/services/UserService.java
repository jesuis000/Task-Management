package banquemisr.challenge05.taskmanagement.services;

import banquemisr.challenge05.taskmanagement.dto.RegisterDTO;
import banquemisr.challenge05.taskmanagement.models.UserModel;
import banquemisr.challenge05.taskmanagement.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterDTO insertUser(UserModel user) throws SQLIntegrityConstraintViolationException {

        System.out.println("user.getPassWord()====" + user.getPassWord());

        user.setPassWord(passwordEncoder.encode(user.getPassWord()));

        userRepository.save(user);

        return RegisterDTO.fromUserModel(user);

    }

    public UserModel getByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }


    public void updateUser(UserModel userModel) {
        userRepository.save(userModel);
    }
}
