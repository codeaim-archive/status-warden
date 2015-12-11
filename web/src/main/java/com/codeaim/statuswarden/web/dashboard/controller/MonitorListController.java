package com.codeaim.statuswarden.web.dashboard.controller;

import com.codeaim.statuswarden.common.model.Monitor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class MonitorListController
{
    @RequestMapping("/")
    public String monitorList(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model)
    {
        model.addAttribute("monitors", getMonitors());
        return "monitor/list";
    }

    private Monitor[] getMonitors()
    {
        try
        {
            return new RestTemplate().getForObject("/api/jim", Monitor[].class);
        } catch (Exception exception)
        {
            return new Monitor[0];
        }
    }

}
