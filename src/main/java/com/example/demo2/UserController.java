package com.example.demo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/user")
public class UserController {
  @Autowired private UserRepository userRepository;

  @PostMapping(path = "/add")
  public @ResponseBody String addNewUser(
      @RequestParam String username, @RequestParam String password, @RequestParam Integer user_id) {
    Users n = new Users();
    n.setName(username);
    n.setPassword(password);
    n.setId(user_id);
    userRepository.save(n);
    return "Saved";
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<Users> getAllUsers() {
    return userRepository.findAll();
  }

  @DeleteMapping(path = "/delete")
  public @ResponseBody String deleteUser(@RequestParam Integer user_id) {
    if (userRepository.existsById(user_id)) {
      userRepository.deleteById(user_id);
      return "Deleted";
    } else {
      return "User not found";
    }
  }

  @PutMapping(path = "/update")
  public @ResponseBody String updateUser(
      @RequestParam Integer user_id, @RequestParam String username, @RequestParam String password) {
    if (userRepository.existsById(user_id)) {
      Users n = userRepository.findById(user_id).get();
      n.setName(username);
      n.setPassword(password);
      userRepository.save(n);
      return "Updated";
    } else {
      return "User not found";
    }
  }

  @GetMapping(path = "/search")
  public @ResponseBody Users getUserByUsername(@RequestParam String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
  }
}