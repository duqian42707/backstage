package com.dqv5.backstage.controller;

import com.dqv5.backstage.service.BasicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by dq on 2017/3/31.
 */
@Controller
@RequestMapping(value = "/user")
public class BasicUserController {
    @Autowired
    private BasicUserService basicUserService;
}
