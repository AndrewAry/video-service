package com.mwtutu.controller;


import com.mwtutu.service.UsersService;
import com.mwtutu.utils.ResStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping(value = "/user")
    public ResStatus test(){
        return ResStatus.ok(usersService.allUser());
    }
}
