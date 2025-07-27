package com.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.dto.ResponseDto;
import com.user.entity.User;
import com.user.exception.UserCustomException;
import com.user.repository.UserCredentialRepository;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public User saveUser(User credential) {
    	if(repository.findByUsername(credential.getUsername()).isPresent()) {
    		User u = repository.findByUsername(credential.getUsername()).get();
        		throw new UserCustomException("User already exist");
    	}
    	
    	
    	
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
//        credential.setId(repository.count() + 1 + "");
        credential.setRole(credential.getRole().toUpperCase());
        return repository.save(credential);
        
    }

    public String generateToken(String username,String role) {
        return jwtService.generateToken(username,role);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
    
    public User getById(int id) {
    	return repository.findById(id).orElseThrow(()-> new UserCustomException("User not found"));
    }
    public List<User> getAllUsers(){
    	return repository.findAll();
    }
    
    public User getByUserName(String username) {
    	return repository.findByUsername(username).orElseThrow(()-> new UserCustomException("User not found"));
    }

}
