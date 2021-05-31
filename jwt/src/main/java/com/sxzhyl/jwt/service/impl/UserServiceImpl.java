package com.sxzhyl.jwt.service.impl;

import com.sxzhyl.jwt.entity.TUser;
import com.sxzhyl.jwt.mapper.UserMapper;
import com.sxzhyl.jwt.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qbb
 * @since 2021-05-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, TUser> implements IUserService {

}
