package lk.ijse.eca.appointmentservice.service;

import lk.ijse.eca.appointmentservice.dto.AppointmentDto;

import java.util.List;

public interface AppointmentService {

    AppointmentDto createAppointment(AppointmentDto dto);

    AppointmentDto updateAppointment(Long id, AppointmentDto dto);

    void deleteAppointment(Long id);

    AppointmentDto getAppointment(Long id);

    List<AppointmentDto> getAllAppointments();

    List<AppointmentDto> getAppointmentsByDoctorSlotId(String doctorSlotId);
}


