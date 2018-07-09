package com.github.wxiaoqi.security.xjsystem.biz;

//import com.ace.cache.annotation.Cache;
//import com.ace.cache.annotation.CacheClear;
import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-08 16:23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserBiz {

//    @Autowired
//    private MenuMapper menuMapper;
    @Autowired
    private UserAuthUtil userAuthUtil;



}
