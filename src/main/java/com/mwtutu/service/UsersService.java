package com.mwtutu.service;

import com.mwtutu.domain.UsersEvent;
import com.mwtutu.mapper.UsersMapper;
import com.mwtutu.utils.UUIDUtils;
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

    public boolean queryUserNameIsExist(String username) {

        UsersEvent usersEvent = new UsersEvent();
        usersEvent.setUsername(username);

        UsersEvent result = usersMapper.selectOne(usersEvent);

        return result == null ? false : true;
    }

    public void  saveUser(UsersEvent usersEvent){
        String userId = UUIDUtils.getUUID();
        usersEvent.setId(userId);
        usersMapper.insert(usersEvent);
    }

}
