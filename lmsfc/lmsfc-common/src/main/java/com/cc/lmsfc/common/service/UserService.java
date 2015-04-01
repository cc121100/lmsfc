package com.cc.lmsfc.common.service;

import com.cc.lmsfc.common.dao.UserDAO;
import com.cc.lmsfc.common.jpa.BaseRepository;
import com.cc.lmsfc.common.model.security.User;
import com.cc.lmsfc.common.service.impl.BaseServiceImpl;

import java.io.Serializable;

/**
 * Created by tomchen on 15-3-25.
 */
public interface UserService extends BaseService<User,UserDAO,String>{

    User getUserByUserName(String userName, boolean loadPermissions);


}
