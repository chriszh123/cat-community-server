package com.zgf.service.impl;

import com.zgf.mapper.UserMapper;
import com.zgf.model.User;
import com.zgf.model.UserExample;
import com.zgf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by zgf
 * Date 2018/12/2 22:02
 * Description
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void saveOrUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andOpenidEqualTo(user.getOpenid());
        List<User> users = this.userMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(users)) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            this.userMapper.insert(user);
        } else {
            user.setGmtModified(System.currentTimeMillis());
            this.userMapper.updateByExampleSelective(user, example);
        }
    }
}
