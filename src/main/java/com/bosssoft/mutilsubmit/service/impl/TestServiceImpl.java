package com.bosssoft.mutilsubmit.service.impl;


import com.bosssoft.mutilsubmit.service.TestService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public String testIdempotence() {
        return "幂等校验已处理，token已删除";
    }





}
