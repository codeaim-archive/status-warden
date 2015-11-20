package com.codeaim.statuswarden.web.controller;

import com.codeaim.statuswarden.web.view.model.Login;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class UserController
{
    @Value("${com.codeaim.statuswarden.web.api.url}")
    private String apiUrl;

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String login(Login login)
    {
        return "user/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(
        @Valid Login login,
        BindingResult result
    )
    {
        if (result.hasErrors()) {
            return "user/login";
        }

        return "redirect:/monitor";
    }

    @RequestMapping("/register")
    public String register()
    {
        return "user/register";
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
