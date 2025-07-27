package com.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

//import org.springframework.data.jpa.repository.JpaRepository;


import com.user.entity.User;

public interface UserCredentialRepository  extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}
