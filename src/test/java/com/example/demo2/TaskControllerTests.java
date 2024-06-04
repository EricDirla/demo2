package com.example.demo2;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(TaskController.class)
public class TaskControllerTests {

    @Autowired
    private TaskController taskController;
    
    @MockBean
    private TaskRepository taskRepository;

    private Tasks task;
    private Users user;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setId(1); // Assuming Users class has an `id` field and appropriate setters
        user.setName("testuser");
        user.setPassword("testpass");

        // Setting up task without task_id
        task = new Tasks();
        task.setUser(user);
        task.setTaskName("Test Task");
        task.setDescription("Test Description");
        task.setDueDate(LocalDateTime.now().plusDays(1));
        task.setIsCompleted(false);
    }

    @Test
    void testAddNewTask() {
        String response = taskController.addNewTask(user, "Test Task", "Test Description", LocalDateTime.now().plusDays(1), false);
        assertThat(response).isEqualTo("Saved");
    }

    @Test
    void testGetAllTasks() {
        Mockito.when(taskRepository.findAll()).thenReturn(List.of(task));
        Iterable<Tasks> response = taskController.getAllTasks();
        assertThat(response).contains(task);
    }

    @Test
    void testDeleteTask() {
        Mockito.when(taskRepository.existsById(1)).thenReturn(true);
        String response = taskController.deleteTask(1);
        assertThat(response).isEqualTo("Deleted");
    }

    @Test
    void testDeleteNonExistingTask() {
        Mockito.when(taskRepository.existsById(1)).thenReturn(false);
        String response = taskController.deleteTask(1);
        assertThat(response).isEqualTo("Task not found");
    }

    @Test
    void testUpdateTask() {
        Mockito.when(taskRepository.existsById(1)).thenReturn(true);
        Mockito.when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        String response = taskController.updateTask(1, "Updated Task", "Updated Description", LocalDateTime.now().plusDays(2), true);
        assertThat(response).isEqualTo("Updated");
    }

    @Test
    void testUpdateNonExistingTask() {
        Mockito.when(taskRepository.existsById(1)).thenReturn(false);
        String response = taskController.updateTask(1, "Updated Task", "Updated Description", LocalDateTime.now().plusDays(2), true);
        assertThat(response).isEqualTo("Task not found");
    }
}