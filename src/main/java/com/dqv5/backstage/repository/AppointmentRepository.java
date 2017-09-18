package com.dqv5.backstage.repository;

import com.dqv5.backstage.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dqwork on 2017/4/14.
 */
@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, Integer> {
    public Appointment findAppointmentByAppointId(Integer id);
}
