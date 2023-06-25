package com.UserManagementApp.controller;

import com.UserManagementApp.model.User;
import com.UserManagementApp.repository.UserRepository;
import com.UserManagementApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    private void userDetails(Model m, Principal p){

        if(p != null){
            String email = p.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user",user);
        }
    }


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user1, HttpSession session) {

//        System.out.println(user1 );

        boolean f = userService.checkEmail(user1.getEmail());
        if (f) {
            session.setAttribute("msg", "Email id already Exist");
        } else {
            User user2 = userService.createUser(user1);
            if (user2 != null) {
                session.setAttribute("msg", "Registered Successfully");
            } else {
                session.setAttribute("msg", "Something went wrong in server");
            }
        }

        return "redirect:/register";
    }

}
