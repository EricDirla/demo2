package com.example.demo2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private UserController userController;

    @MockBean
    private UserRepository userRepository;

    private Users user;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setId(1);
        user.setName("testuser");
        user.setPassword("testpass");
    }

    @Test
    void testAddNewUser() {
        String response = userController.addNewUser("testuser", "testpass", 1);
        assertThat(response).isEqualTo("Saved");
    }

    @Test
    void testGetAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        Iterable<Users> response = userController.getAllUsers();
        assertThat(response).contains(user);
    }

    @Test
    void testDeleteUser() {
        Mockito.when(userRepository.existsById(1)).thenReturn(true);
        String response = userController.deleteUser(1);
        assertThat(response).isEqualTo("Deleted");
    }

    @Test
    void testDeleteNonExistingUser() {
        Mockito.when(userRepository.existsById(1)).thenReturn(false);
        String response = userController.deleteUser(1);
        assertThat(response).isEqualTo("User not found");
    }

    @Test
    void testUpdateUser() {
        Mockito.when(userRepository.existsById(1)).thenReturn(true);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        String response = userController.updateUser(1, "updateduser", "updatedpass");
        assertThat(response).isEqualTo("Updated");
    }

    @Test
    void testUpdateNonExistingUser() {
        Mockito.when(userRepository.existsById(1)).thenReturn(false);
        String response = userController.updateUser(1, "updateduser", "updatedpass");
        assertThat(response).isEqualTo("User not found");
    }

    @Test
    void testGetUserByUsername() {
        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        Users response = userController.getUserByUsername("testuser");
        assertThat(response).isEqualTo(user);
    }

    @Test
    void testGetUserByUsernameNotFound() {
        Mockito.when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            userController.getUserByUsername("nonexistent");
        });
    }
}