package com.Secure.SpringSecPractice.Service;

import com.Secure.SpringSecPractice.Entity.Users;
import com.Secure.SpringSecPractice.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    AuthenticationManager authenticationManager;

    public Users registerUser(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public String verify(Users user) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        System.out.println("in the service layer");
            // creating an authentication token using the username and password provided by the user and passing it to the authentication manager for authentication
        return auth.isAuthenticated() ? jwtService.generateToken(user.getUsername()) : "Authentication failed";  // returning a simple message based on the authentication result
    }
}
