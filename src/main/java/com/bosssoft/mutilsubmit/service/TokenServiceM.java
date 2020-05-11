package com.bosssoft.mutilsubmit.service;

import com.bosssoft.mutilsubmit.common.ServerResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by A on 2020/5/11.
 */
public interface TokenServiceM {

    String createToken();

    void checkToken(HttpServletRequest request);


}
