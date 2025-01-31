package com.hms2.service;

import com.hms2.Entity.User;
import com.hms2.Repository.UserRepository;
import com.hms2.payload.LoginDto;
import com.hms2.payload.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private JWTService jwtService;
    @Autowired private ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String saveUser(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);

        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            return "user already exits";
        }

        Optional<User> opEmail = userRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()){
            return "email already exits";
        }

        Optional<User> opMobile = userRepository.findByMobile(user.getMobile());
        if(opMobile.isPresent()){
            return "mobile already exits";
        }

        // Hash the password using BCryptPasswordEncoder
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        String encodedPassword  = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        userDto.setRole(user.getRole());
        User user1 = modelMapper.map(userDto, User.class);
        userRepository.save(user1);
        return "saved user";
    }

    public String savePropertyOwner(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            return "user already exits";
        }

        Optional<User> opEmail = userRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()){
            return "Email already exits";
        }

        Optional<User> opMobile = userRepository.findByMobile(user.getMobile());
        if(opMobile.isPresent()){
            return "Mobile is already exits";
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));
        user.setRole("ROLE_OWNER");
        userRepository.save(user);
        return "property owner saved";
    }

    public String saveBlogManagerAccount(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);

        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            return "user Already exits "+ userDto.getUsername();
        }

        Optional<User> opEmail = userRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()){
            return "email already exits "+ userDto.getEmail();
        }
        Optional<User> opMobile = userRepository.findByMobile(user.getMobile());
        if(opMobile.isPresent()){
            return "mobile already exits "+userDto.getMobile();
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        user.setRole(userDto.getRole());
        userRepository.save(user);

        return "saved blog manager account.";
    }

    public String verifyLogin(LoginDto loginDto){
        Optional<User> opUser = userRepository.findByUsername(loginDto.getUsername());
        if(opUser.isEmpty()){
            return "user not found" + loginDto.getUsername();
        }
        User user = opUser.get();
        if(BCrypt.checkpw(loginDto.getPassword(),user.getPassword())){
            String token = jwtService.generateToken(user.getUsername());
            return token;
        }else{
            return null;
        }

    }

}
