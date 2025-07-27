package com.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.user.entity.User;
import com.user.repository.UserCredentialRepository;
import com.user.service.AuthService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceApplicationTests {
	 @Mock
	    private UserCredentialRepository userRepository;

	    @Mock
	    private PasswordEncoder passwordEncoder;

	    @InjectMocks
	    private AuthService userService;

	    @Test
	    void testRegisterUser() {
	        User user = new User(1, "john", "pass123", "user", "john@example.com");

	        when(userRepository.save(any(User.class))).thenReturn(user);

	        User registered = userService.saveUser(user);

	        assertNotNull(registered);
	        assertEquals("john", registered.getUsername());
	        verify(userRepository, times(1)).save(any(User.class));
	    }

	    @Test
	    void testGetUserById() {
	        User user = new User(1, "john", "pass123", "user", "john@example.com");

	        when(userRepository.findById(1)).thenReturn(Optional.of(user));

	        User found = userService.getById(1);

	        assertNotNull(found);
	        assertEquals("john", found.getUsername());
	        verify(userRepository).findById(1);
	    }

	    @Test
	    void testGetAllUsers() {
	        List<User> users = List.of(
	            new User(1, "john", "pass", "user", "john@example.com"),
	            new User(2, "jane", "pass", "admin", "jane@example.com")
	        );

	        when(userRepository.findAll()).thenReturn(users);

	        List<User> allUsers = userService.getAllUsers();

	        assertNotNull(allUsers);
	        assertEquals(2, allUsers.size());
	        verify(userRepository).findAll();
	    }

}
