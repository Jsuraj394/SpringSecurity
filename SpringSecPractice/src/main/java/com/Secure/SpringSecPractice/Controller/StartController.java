package com.Secure.SpringSecPractice.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class StartController {


    @GetMapping("/")
    public String greet(HttpServletRequest request) {
        return "Hello, welcome to the Spring Security Practice Application! used for testing purpose only"+ request.getSession().getId(); // this method is for testing purpose only
    }

    @GetMapping("csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }


}
