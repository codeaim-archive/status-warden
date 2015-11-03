package com.codeaim.statuswarden.web.controller;

import com.codeaim.statuswarden.common.model.Monitor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class MonitorController
{
    @Value("${com.codeaim.statuswarden.web.api.url}")
    private String apiUrl;

    @RequestMapping("/monitor")
    public String monitorList(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model)
    {
        model.addAttribute("monitors", getMonitors());
        return "monitors";
    }

    private Monitor[] getMonitors()
    {
        try
        {
            return new RestTemplate().getForObject(apiUrl, Monitor[].class);
        } catch (Exception exception)
        {
            return new Monitor[0];
        }
    }

}
