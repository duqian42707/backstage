package com.dqv5.backstage.service;

import com.dqv5.backstage.dao.AppointmentDao;
import com.dqv5.backstage.model.Appointment;
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
    private AppointmentDao appointmentDao;

    public void saveOrUpdate(Map modelInfo) {
        if (modelInfo.get("appointId") == null) {
            Appointment model = new Appointment();
            Integer userId = Integer.valueOf(modelInfo.get("userId").toString());
            model.setUserId(userId);
            model.setCollege(modelInfo.get("college") == null ? null : modelInfo.get("college").toString());
            model.setAcademy(modelInfo.get("academy") == null ? null : modelInfo.get("academy").toString());
            model.setStudent(modelInfo.get("student") == null ? null : modelInfo.get("student").toString());
            model.setTeacher(modelInfo.get("teacher") == null ? null : modelInfo.get("teacher").toString());
            model.setType(modelInfo.get("type").toString());
//            model.setAppointDate();
            model.setWeek(modelInfo.get("week").toString());
//            model.setStartTime();
//            model.setEndTime();
            model.setIndependent(modelInfo.get("independent").toString() == "true" ? "1" : "0");
            model.setRemark(modelInfo.get("remark") == null ? null : modelInfo.get("remark").toString());
            model.setModTime(new Date());
            model.setState("1");
            appointmentDao.save(model);
        } else {
//            Appointment oldModel = appointmentDao.get(Appointment.class, model.getAppointId());
//            appointmentDao.merge(oldModel);
        }

    }

    public List getAppointmentList() {
        return appointmentDao.getAppointmentList();
    }

    public Map getAppointment(Integer id) {
        List<Map> list = appointmentDao.getAppointmentById(id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
