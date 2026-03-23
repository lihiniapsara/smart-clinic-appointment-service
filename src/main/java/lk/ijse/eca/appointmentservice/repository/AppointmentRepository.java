package lk.ijse.eca.appointmentservice.repository;

import lk.ijse.eca.appointmentservice.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorSlotIdOrderByDateDescIdDesc(String doctorSlotId);
}


