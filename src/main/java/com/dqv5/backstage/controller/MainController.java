package com.dqv5.backstage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dqwork on 2017/2/20.
 */
@Controller
@RequestMapping(value = "/")
public class MainController {

    @RequestMapping(value = "/main")
    public String main(){
        return "/main";
    }

    @RequestMapping(value = "/index")
    public String index(){
        return "/main";
    }
}
