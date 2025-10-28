package com.sword.usermanagementsystem.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class HomeController{
    @RequestMapping("/")
    public ResponseEntity<String> index(){
        return ResponseEntity.ok().body("hello");
    }
}