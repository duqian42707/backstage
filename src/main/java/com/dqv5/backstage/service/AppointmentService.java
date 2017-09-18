package com.dqv5.backstage.service;

import com.dqv5.backstage.model.Appointment;
import com.dqv5.backstage.repository.AppointmentRepository;
import com.dqv5.backstage.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dqwork on 2017/4/14.
 */
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public void saveOrUpdate(Map modelInfo) {
        if (modelInfo.get("appointId") == null) {
            Appointment model = new Appointment();
            model.setUserId(modelInfo.get("userId").toString());
            model.setCollege(modelInfo.get("college") == null ? null : modelInfo.get("college").toString());
            model.setAcademy(modelInfo.get("academy") == null ? null : modelInfo.get("academy").toString());
            model.setStudent(modelInfo.get("student") == null ? null : modelInfo.get("student").toString());
            model.setTeacher(modelInfo.get("teacher") == null ? null : modelInfo.get("teacher").toString());
            model.setType(modelInfo.get("type").toString());
            String appointDate = modelInfo.get("appointDate").toString();
            model.setAppointDate(DateTimeUtil.formatToDateYMD(appointDate));
            model.setWeek(modelInfo.get("week").toString());
            String startTime = modelInfo.get("startTime").toString();
            String endTime = modelInfo.get("endTime").toString();
            model.setStartTime(DateTimeUtil.formatToDateYMDHms(startTime));
            model.setEndTime(DateTimeUtil.formatToDateYMDHms(endTime));
            model.setIndependent(modelInfo.get("independent").toString().equals("true") ? "1" : "0");
            model.setRemark(modelInfo.get("remark") == null ? null : modelInfo.get("remark").toString());
            model.setModTime(new Date());
            model.setState("1");
            model = appointmentRepository.save(model);
            model.setAppointId(model.get_id());
            model = appointmentRepository.save(model);
        }

    }

    public List getAppointmentList() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointment(Integer id) {
        return appointmentRepository.findAppointmentByAppointId(id);
    }
}
