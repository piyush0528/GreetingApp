package com.example.GreetingApp.services;


import com.example.GreetingApp.Exception.UserException;
import com.example.GreetingApp.dto.AuthUserDTO;
import com.example.GreetingApp.dto.LoginDTO;
import com.example.GreetingApp.model.AuthUser;
import com.example.GreetingApp.repository.AuthUserRepository;
import com.example.GreetingApp.utility.JwtToken;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    JwtToken tokenUtil;

    @Override
    public AuthUser register(AuthUserDTO userDTO) throws Exception{

        String hashedPassword=encoder.encode(userDTO.getPassword());
        AuthUser user=new AuthUser(userDTO);
        user.setPassword(hashedPassword);
        authUserRepository.save(user);
        String token=tokenUtil.createToken(user.getUserId());

        emailSenderService.sendEmail(user.getEmail(),"Registered in Greeting App", "Hii...."
                +user.getFirstName()+"\n You have been successfully registered!\n\n Your registered details are:\n\n User Id:  "
                +user.getUserId()+"\n First Name:  "
                +user.getFirstName()+"\n Last Name:  "
                +user.getLastName()+"\n Email:  "
                +user.getEmail()+"\n Address:  "
                +"\n Token:  " +token);
        return user;
    }

    @Override
    public String login(LoginDTO loginDTO){

        Optional<AuthUser> user= Optional.ofNullable(authUserRepository.findByEmail(loginDTO.getEmail()));
        if (user.isPresent() && encoder.matches(loginDTO.getPassword(),user.get().getPassword())){
            emailSenderService.sendEmail(user.get().getEmail(),"Logged in Successfully!", "Hii...."+user.get().getFirstName()+"\n\n You have successfully logged in into Greeting App!");
            return "Congratulations!! You have logged in successfully!";
        }else {
            throw new UserException("Sorry! Email or Password is incorrect!");
        }
    }
}