package com.mwtutu.controller;

import com.mwtutu.mongodb.MongodbRepository;
import com.mwtutu.utils.Debug;
import com.mwtutu.utils.MongoUtil;
import com.mwtutu.utils.ResStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value="测试MongoDB的接口", tags= {"测试MongoDB的controller"})
public class TestController {

    @Autowired
    private MongodbRepository mongodbRepository;

    @ApiOperation(value="测试连接", notes="测试连接MongoDB")
    @GetMapping(value = "/test")
    public ResStatus test(){
        List list = MongoUtil.getListFromDBCursor(mongodbRepository.getCollection("Users").find().iterator());
        Debug.printlnList(list);
        return ResStatus.ok(list);
    }
}
