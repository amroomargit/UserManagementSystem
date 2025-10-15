package com.sword.usermanagementsystem.controllers;
import org.springframework.web.bind.annotation.*;


@RestController
public class HomeController{
    @RequestMapping("/")
    public String index(){
        return "hello";
    }
}