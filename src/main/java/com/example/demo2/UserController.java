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

@Controller // This means that this class is a Controller
@RequestMapping(path = "/user") // This means URL's start with /demo (after Application path)
public class UserController {
  @Autowired // This means to get the bean called userRepository
  // Which is auto-generated by Spring, we will use it to handle the data
  private UserRepository userRepository;

  @PostMapping(path = "/add") // Map ONLY POST Requests
  public @ResponseBody String addNewUser(
      @RequestParam String username, @RequestParam String password, @RequestParam Integer user_id) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request

    Users n = new Users();
    n.setName(username);
    n.setPassword(password);
    n.setId(user_id);
    userRepository.save(n);
    return "Saved";
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<Users> getAllUsers() {
    // This returns a JSON or XML with the users
    return userRepository.findAll();
  }

  @DeleteMapping(path = "/delete") // Map ONLY DELETE Requests
  public @ResponseBody String deleteUser(@RequestParam Integer user_id) {
    // Check if user exists
    if (userRepository.existsById(user_id)) {
      userRepository.deleteById(user_id);
      return "Deleted";
    } else {
      return "User not found";
    }
  }

    @PutMapping(path = "/update") // Map ONLY PUT Requests
  public @ResponseBody String updateUser(@RequestParam Integer user_id,
                                         @RequestParam String username,
                                         @RequestParam String password) {
    // Check if user exists
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
    // Find user by username
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
}
}