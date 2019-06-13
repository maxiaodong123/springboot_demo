package com.example.mp.service.impl;

import com.example.mp.model.User;
import com.example.mp.mapper.UserMapper;
import com.example.mp.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mxd
 * @since 2019-06-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
