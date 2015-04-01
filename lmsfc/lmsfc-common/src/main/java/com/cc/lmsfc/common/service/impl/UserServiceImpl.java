package com.cc.lmsfc.common.service.impl;

import com.cc.lmsfc.common.dao.UserDAO;
import com.cc.lmsfc.common.jpa.BaseRepository;
import com.cc.lmsfc.common.model.security.Role;
import com.cc.lmsfc.common.model.security.User;
import com.cc.lmsfc.common.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tomchen on 15-3-25.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User,UserDAO,String> implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Override
    protected UserDAO getRepository() {
        return userDAO;
    }


    @Override
    public User getUserByUserName(String userName,boolean loadPermissions) {
        List<User> users = userDAO.findByUserName(userName);
        if(CollectionUtils.isEmpty(users)){
            return null;
        }

        User user = users.get(0);
        if(loadPermissions){
            loadLazy(user);
        }

        return user;
    }

    private void loadLazy(User user){
        for(Role r : user.getRoles()){
            r.getResources().size();
        }
    }
}
