package com.hms2.controller;

import com.hms2.Entity.User;
import com.hms2.payload.JwtToken;
import com.hms2.payload.LoginDto;
import com.hms2.payload.ProfileDto;
import com.hms2.payload.UserDto;
import com.hms2.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UserService userService;
    @Autowired private ModelMapper modelMapper;
    
    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto){
        String saveUser = userService.saveUser(userDto);
        return new ResponseEntity<>(saveUser,HttpStatus.OK);
    }

    @PostMapping("/propertyOwner/sign-up")
    public ResponseEntity<?> createPropertyOwner(@RequestBody UserDto userDto){
        String savedPropertyOwner = userService.savePropertyOwner(userDto);
        return new ResponseEntity<>(savedPropertyOwner,HttpStatus.OK);
    }

    @PostMapping("/blog/sign-up")
    public ResponseEntity<?> createBlogManagerAccount(@RequestBody UserDto userDto){
        String saved = userService.saveBlogManagerAccount(userDto);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        String token = userService.verifyLogin(loginDto);

        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(token);
        jwtToken.setType("JWT");

        if(token!=null){
            return new ResponseEntity<>(token, HttpStatus.OK);
        }

      return new ResponseEntity<>("Invalid", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/userProfile")
    public ResponseEntity<ProfileDto> getUserProfile(@AuthenticationPrincipal User user){
        ProfileDto dto = modelMapper.map(user, ProfileDto.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
