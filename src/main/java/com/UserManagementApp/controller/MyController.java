package com.UserManagementApp.controller;

import com.UserManagementApp.model.User;
import com.UserManagementApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class MyController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @ModelAttribute
    private void userDetails(Model m, Principal p){
        String email = p.getName();
        User user = userRepository.findByEmail(email);
        m.addAttribute("user",user);
    }

    @GetMapping("/")
    public String home(){
        return "user/home";
    }

    @GetMapping("/changePassword")
    public String loadChangePassword(){
        return "user/changePass";
    }

    @PostMapping("/updatePass")
    public String changePassword(Principal p, @RequestParam("oldPass") String oldPass,
                                 @RequestParam("newPass") String newPass, HttpSession session){

        String email = p.getName();
        User loginUser = userRepository.findByEmail(email);

        boolean f =passwordEncoder.matches(oldPass,loginUser.getPassword());


        if(f){

            loginUser.setPassword(passwordEncoder.encode(newPass));
            User updatePasswordUser = userRepository.save(loginUser);
            if(updatePasswordUser != null){
                session.setAttribute("msg", "Password change success");
            }else{
                session.setAttribute("msg", "Something wrong on server");
            }
        }else{
            session.setAttribute("msg", "Old password incorrect");
        }

        return "/user/changePass";
    }


}
