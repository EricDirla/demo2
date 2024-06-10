package com.example.demo2.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo2.domain.Tasks;
import com.example.demo2.domain.Users;
import com.example.demo2.repository.TaskRepository;

@Controller
@RequestMapping(path = "/task")
public class TaskController {

    @Autowired
              
    private TaskRepository taskRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewTask(@RequestParam Users user,
            @RequestParam String task_name,
            @RequestParam String description,
            @RequestParam LocalDateTime due_date,
            @RequestParam Boolean is_completed) {

        Tasks t = new Tasks();
        t.setUser(user);
        t.setTaskName(task_name);
        t.setDescription(description);
        t.setDueDate(due_date);
        t.setIsCompleted(is_completed);
        taskRepository.save(t);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Tasks> getAllTasks() {
        return taskRepository.findAll();
    }

        @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteTask(@RequestParam Integer task_id) {
        // Check if task exists
        if (taskRepository.existsById(task_id)) {
            taskRepository.deleteById(task_id);
            return "Deleted";
        } else {
            return "Task not found";
        }
    }

    @PutMapping(path = "/update")
    public @ResponseBody String updateTask(@RequestParam Integer task_id,
                                           @RequestParam String task_name,
                                           @RequestParam String description,
                                           @RequestParam LocalDateTime due_date,
                                           @RequestParam Boolean is_completed) {
        if (taskRepository.existsById(task_id)) {
            Tasks t = taskRepository.findById(task_id).get();
            t.setTaskName(task_name);
            t.setDescription(description);
            t.setDueDate(due_date);
            t.setIsCompleted(is_completed);
            taskRepository.save(t);
            return "Updated";
        } else {
            return "Task not found";
        }
    }
}
