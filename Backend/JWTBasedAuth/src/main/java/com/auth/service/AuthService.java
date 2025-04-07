package com.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.dto.ResponseDto;
import com.auth.entity.User;
import com.auth.exception.UserCustomException;
import com.auth.repository.UserCredentialRepository;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(User credential) {
    	if(repository.findByUsername(credential.getUsername()).isPresent()) {
    		User u = repository.findByUsername(credential.getUsername()).get();
        		throw new UserCustomException("User already exist");
    	}
    	
    	
    	
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "user added to the system";
    }

    public String generateToken(String username,String role) {
        return jwtService.generateToken(username,role);
    }

    public void validateToken(String token, String username) {
        jwtService.validateToken(token,username);
    }
    
    public User getById(Long id) {
    	return repository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
    }

}
