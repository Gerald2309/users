package com.example.users.Controller;

import com.example.users.Model.User;
import com.example.users.Repository.UserRepository;
import com.example.users.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository UserRepository;


    //@GetMapping()
    //public ResponseEntity<List<User>> getUsers() {
    //    List<User> users = userService.getUsers();
    //    return ResponseEntity.ok(users);
    //}

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.getUserById(id)
                .map(user -> {
                     user.setFirstName(updatedUser.getFirstName());
                     user.setLastName(updatedUser.getLastName());
                     user.setGender(updatedUser.getGender());
                     user.setActive(updatedUser.isActive());
                     user.setBalance(updatedUser.getBalance());

                     return ResponseEntity.ok(userService.saveUser(user));
                 })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> {
                     userService.deleteUser(id);
                     return ResponseEntity.noContent().build();
                 })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<User> usersPage = UserRepository.findAll(pageable);
        List<User> users = usersPage.getContent();

        if (users == null) {
            users = new ArrayList<>();
        }

        return ResponseEntity.ok(users);
    }
}