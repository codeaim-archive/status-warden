package com.codeaim.statuswarden.web.dashboard.controller;

import com.codeaim.statuswarden.common.model.User;
import com.codeaim.statuswarden.common.repository.UserRepository;
import com.codeaim.statuswarden.web.dashboard.model.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.*;

@Controller
public class UserController
{
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(Map<String, Object> model)
    {
        model.put("title", "Login");

        return "user/login";
    }

    @RequestMapping("/register")
    public String register()
    {
        return "user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(
        @Valid Registration registration,
        BindingResult result
    )
    {
        if (result.hasErrors())
        {
            return "user/register";
        }

        Optional<User> optionalUser = userRepository.findByEmail(registration.getEmail());
        if (optionalUser.isPresent())
        {
            result.rejectValue("email", "An account already exists for this email.");
            return "user/register";
        }
        userRepository.save(User.builder()
            .accessToken(UUID.randomUUID().toString())
            .email(registration.getEmail())
            .name(registration.getName())
            .password(new BCryptPasswordEncoder().encode(registration.getPassword()))
            .roles(Arrays.asList("USER", "ADMIN"))
            .build());

        return "redirect:/login";
    }

    @RequestMapping("/forgot-password")
    public String forgotPassword()
    {
        return "user/forgot-password";
    }

    @RequestMapping("/password-reset")
    public String passwordReset()
    {
        return "user/password-reset";
    }
}
