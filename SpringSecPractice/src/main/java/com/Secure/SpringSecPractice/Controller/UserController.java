package com.Secure.SpringSecPractice.Controller;

import com.Secure.SpringSecPractice.Entity.Users;
import com.Secure.SpringSecPractice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Users registerUser(@RequestBody Users user) {
        return userService.registerUser(user);
    }


    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        System.out.println("in the controller layer");
        return userService.verify(user);
    }


}
