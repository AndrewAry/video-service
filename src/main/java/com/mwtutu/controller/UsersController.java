package com.mwtutu.controller;


import com.mwtutu.service.UsersService;
import com.mwtutu.utils.ResStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(value="用户相关业务的接口", tags= {"用户相关业务的controller"})
public class UsersController {

    @Autowired
    private UsersService usersService;

    @ApiOperation(value="获取用户列表", notes="获取用户列表接口")
    @GetMapping(value = "/user")
    public ResStatus getUserList(){
        return ResStatus.ok(usersService.allUser());
    }
}
