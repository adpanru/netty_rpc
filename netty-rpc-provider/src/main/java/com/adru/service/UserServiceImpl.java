package com.adru.service;


import com.adru.api.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Override
    public String isSuccess(String name) {
        log.info("user:{Successful call}",name);
        return "You've successfully called";
    }
}
