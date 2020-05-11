package com.bosssoft.mutilsubmit.service.impl;

import com.bosssoft.mutilsubmit.common.Constant;
import com.bosssoft.mutilsubmit.common.ResponseCode;
import com.bosssoft.mutilsubmit.exception.ServiceException;
import com.bosssoft.mutilsubmit.service.TokenServiceM;
import com.bosssoft.mutilsubmit.util.JedisUtil;
import com.bosssoft.mutilsubmit.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by A on 2020/5/11.
 */

@Service
public class TokenServiceMImpl implements TokenServiceM {


    private static final String TOKEN_NAME = "token";

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public String createToken() {
        String str = RandomUtil.UUID32();
        StrBuilder token = new StrBuilder();
        token.append(Constant.Redis.TOKEN_PREFIX).append(str);

        jedisUtil.set(token.toString(), token.toString(), Constant.Redis.EXPIRE_TIME_MINUTE);

        return token.toString();
    }


    @Override
    public void checkToken(HttpServletRequest request) {

        System.out.println("222tokenservice-checkToken");


        String token = request.getHeader(TOKEN_NAME);
        System.out.println("token====="+token);
        if (StringUtils.isBlank(token)) {// header中不存在token
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isBlank(token)) {// parameter中也不存在token
                throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
            }
        }

        if (!jedisUtil.exists(token)) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

        Long del = jedisUtil.del(token);
        if (del <= 0) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
    }

}
