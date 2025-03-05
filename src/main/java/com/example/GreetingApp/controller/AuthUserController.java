package com.example.GreetingApp.controller;


import com.example.GreetingApp.dto.AuthUserDTO;
import com.example.GreetingApp.dto.LoginDTO;
import com.example.GreetingApp.dto.ResponseDTO;
import com.example.GreetingApp.model.AuthUser;
import com.example.GreetingApp.services.AuthenticationService;
import com.example.GreetingApp.services.EmailSenderService;
import com.example.GreetingApp.utility.JwtToken;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    JwtToken jwtUtility;
    @Autowired
    EmailSenderService emailService;


    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody AuthUserDTO userDTO) throws Exception{
        System.out.println(7);
        AuthUser user=authenticationService.register(userDTO);
        ResponseDTO responseUserDTO =new ResponseDTO("User details is submitted!",user);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO){
        String result=authenticationService.login(loginDTO);
        ResponseDTO responseUserDTO=new ResponseDTO("Login successfully!!",result);
        return  new ResponseEntity<>(responseUserDTO,HttpStatus.OK);
    }

}