package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.AuthRequest;
import com.user.dto.ResponseDto;
import com.user.entity.User;
import com.user.repository.UserCredentialRepository;
import com.user.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;
    @Autowired
    private UserCredentialRepository userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @GetMapping("/test")
    public ResponseEntity<String> test(){
    	return new ResponseEntity<>(" User Service Testing successfully", HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable @Valid String id){
    	return new ResponseEntity<>(service.getById(id),HttpStatus.ACCEPTED);
    }

    @PostMapping("/register")
    public User addNewUser(@RequestBody @Valid User user) {
        return service.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseDto getToken(@RequestBody @Valid AuthRequest authRequest) {
    	System.out.println("yes .."+authRequest.getUsername()+"  "+authRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        System.out.println(authenticate.isAuthenticated());
        if (authenticate.isAuthenticated()) {
        	User user=
                	userRepo.findByUsername(authRequest.getUsername()).get();
        	String token=
        	//service.generateToken(authRequest.getUsername(),user.getRole());
        		service.generateToken(user.getId()+"",user.getRole());
        	
        	ResponseDto resDto=new ResponseDto();
        	resDto.setToken(token);
        	resDto.setRole(user.getRole());
        	return resDto;
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam String token) {
        service.validateToken(token);
        return "Token is valid";
    }
    
    @GetMapping("/user/{username}")
    public User getByUserName(@Valid @PathVariable String username) {
    	return service.getByUserName(username);
    }
}
