package lk.ijse.eca.appointmentservice.service.impl;

import lk.ijse.eca.appointmentservice.client.DoctorServiceClient;
import lk.ijse.eca.appointmentservice.client.PatientServiceClient;
import lk.ijse.eca.appointmentservice.dto.AppointmentDto;
import lk.ijse.eca.appointmentservice.dto.PatientDto;
import lk.ijse.eca.appointmentservice.entity.Appointment;
import lk.ijse.eca.appointmentservice.exception.AppointmentNotFoundException;
import lk.ijse.eca.appointmentservice.mapper.AppointmentMapper;
import lk.ijse.eca.appointmentservice.repository.AppointmentRepository;
import lk.ijse.eca.appointmentservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final PatientServiceClient patientServiceClient;
    private final DoctorServiceClient doctorServiceClient;

    @Override
    @Transactional
    public AppointmentDto createAppointment(AppointmentDto dto) {
        log.debug("Creating appointment for patient: {}, doctorSlot: {}", dto.getPatientId(), dto.getDoctorSlotId());

        // Validate both references before any DB write
        PatientDto patient = patientServiceClient.getPatient(dto.getPatientId());
        doctorServiceClient.validateDoctorSlot(dto.getDoctorSlotId());

        Appointment appointment = appointmentMapper.toEntity(dto);
        Appointment saved = appointmentRepository.save(appointment);
        log.info("Appointment created successfully with ID: {}", saved.getId());
        return appointmentMapper.toDto(saved, patient);
    }

    @Override
    @Transactional
    public AppointmentDto updateAppointment(Long id, AppointmentDto dto) {
        log.debug("Updating appointment with ID: {}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Appointment not found for update: {}", id);
                    return new AppointmentNotFoundException(id);
                });

        // Validate both references before updating
        PatientDto patient = patientServiceClient.getPatient(dto.getPatientId());
        doctorServiceClient.validateDoctorSlot(dto.getDoctorSlotId());

        appointmentMapper.updateEntity(dto, appointment);
        Appointment updated = appointmentRepository.save(appointment);
        log.info("Appointment updated successfully: {}", id);
        return appointmentMapper.toDto(updated, patient);
    }

    @Override
    @Transactional
    public void deleteAppointment(Long id) {
        log.debug("Deleting appointment with ID: {}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Appointment not found for deletion: {}", id);
                    return new AppointmentNotFoundException(id);
                });

        appointmentRepository.delete(appointment);
        log.info("Appointment deleted successfully: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentDto getAppointment(Long id) {
        log.debug("Fetching appointment with ID: {}", id);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Appointment not found: {}", id);
                    return new AppointmentNotFoundException(id);
                });

        PatientDto patient = patientServiceClient.getPatient(appointment.getPatientId());
        return appointmentMapper.toDto(appointment, patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDto> getAllAppointments() {
        log.debug("Fetching all appointments");

        List<AppointmentDto> appointments = appointmentRepository.findAll()
                .stream()
                .map(appointment -> {
                    PatientDto patient = patientServiceClient.getPatient(appointment.getPatientId());
                    return appointmentMapper.toDto(appointment, patient);
                })
                .toList();

        log.debug("Fetched {} appointment(s)", appointments.size());
        return appointments;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDto> getAppointmentsByDoctorSlotId(String doctorSlotId) {
        log.debug("Fetching appointments for doctorSlotId: {}", doctorSlotId);

        List<AppointmentDto> appointments = appointmentRepository.findByDoctorSlotIdOrderByDateDescIdDesc(doctorSlotId)
                .stream()
                .map(appointment -> {
                    PatientDto patient = patientServiceClient.getPatient(appointment.getPatientId());
                    return appointmentMapper.toDto(appointment, patient);
                })
                .toList();

        log.debug("Fetched {} appointment(s) for doctorSlotId: {}", appointments.size(), doctorSlotId);
        return appointments;
    }
}


