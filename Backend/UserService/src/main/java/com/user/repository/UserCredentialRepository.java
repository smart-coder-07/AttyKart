package com.user.repository;

//import org.springframework.data.jpa.repository.JpaRepository;


import com.user.entity.User;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserCredentialRepository  extends MongoRepository<User,String> {
    Optional<User> findByUsername(String username);
}
