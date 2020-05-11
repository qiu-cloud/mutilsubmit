package com.bosssoft.mutilsubmit.controller;

import com.bosssoft.mutilsubmit.annotation.ApiIdempotent;
import com.bosssoft.mutilsubmit.service.TestService;
import com.bosssoft.mutilsubmit.service.TokenServiceM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by A on 2020/5/9.
 */
@RestController
public class BusinessController {

    @Autowired
    private TokenServiceM tokenService;

    @Autowired
    private TestService testService;



    @PostMapping("/get/token")
    public String  getToken(){
        String token = tokenService.createToken();
        if (StringUtils.isNotEmpty(token)) {

            return token;
        }
        return StringUtils.EMPTY;
    }


    @ApiIdempotent
    @PostMapping("/test/Idempotence")
    public String testIdempotence() {
        String businessResult = testService.testIdempotence();



        System.out.println("/test/Idempotence=testIdempotence");

        if (StringUtils.isNotEmpty(businessResult)) {

            return businessResult;
        }
        return StringUtils.EMPTY;
    }





}



