package com.mwtutu.service;

import com.mwtutu.domain.UsersEvent;
import com.mwtutu.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsersService {

    @Autowired
    private UsersMapper usersMapper;

    public List<UsersEvent> allUser(){
        return usersMapper.selectAll();
    }

}
