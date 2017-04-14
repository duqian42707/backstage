package com.dqv5.backstage.controller;

import com.dqv5.backstage.model.Appointment;
import com.dqv5.backstage.service.AppointmentService;
import com.dqv5.backstage.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dqwork on 2017/4/14.
 */
@Controller
@RequestMapping(value = "/appointment")
public class AppointmentController {
    private Logger logger = LoggerFactory.getLogger(AppointmentController.class);
    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping(value = "/saveOrUpdate")
    @ResponseBody
    public Map saveOrUpdate(String json) {
        Map map = new HashMap();
        Map modelInfo = (Map) JsonUtil.toObject(json);
        appointmentService.saveOrUpdate(modelInfo);
        map.put("status", true);
        return map;
    }

    @RequestMapping(value = "/appointmentList")
    @ResponseBody
    public List appointmentList() {
        List list = appointmentService.getAppointmentList();
        return list;
    }

    @RequestMapping(value = "/getAppointment")
    @ResponseBody
    public Map getAppointment(Integer id){
        Map map = appointmentService.getAppointment(id);
        return map;
    }
}
