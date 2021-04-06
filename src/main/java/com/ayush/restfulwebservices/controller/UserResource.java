package com.ayush.restfulwebservices.controller;

import com.ayush.restfulwebservices.Dao.UserDAO;
import com.ayush.restfulwebservices.service.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    @Autowired
    private UserDAO userDAO;


    // retrieve all users
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDAO.findAll();
    }

    // retrieve one user
    @GetMapping("user/{id}")
    public User findUser(@PathVariable int id) {
        User user = userDAO.findOne(id);
        if(user == null) {
            throw new UserNotFoundException("id" + id);
        }
        return user;
    }

    // Create new user
    @PostMapping("/user")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userDAO.save(user);

        // this will get url of current request
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                // this will add id as new path to current request
                .path("/{id}")
                // this will get the value of the id and pass it to above id
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(uri).build();

    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = userDAO.deleteById(id);

        if(user==null)
            throw new UserNotFoundException("id-"+ id);
    }


}
