package lk.ijse.eca.appointmentservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lk.ijse.eca.appointmentservice.dto.AppointmentDto;
import lk.ijse.eca.appointmentservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AppointmentDto> createAppointment(
            @Valid @RequestBody AppointmentDto dto) {
        log.info("POST appointment - patientId: {}, doctorSlotId: {}", dto.getPatientId(), dto.getDoctorSlotId());
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(dto));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDto> getAppointment(
            @PathVariable @Positive(message = "Appointment ID must be a positive number") Long id) {
        log.info("GET appointment/{}", id);
        return ResponseEntity.ok(appointmentService.getAppointment(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentDto>> getAppointments(
            @RequestParam(required = false) String doctorSlotId) {
        if (StringUtils.hasText(doctorSlotId)) {
            log.info("GET appointments filter by doctorSlotId={}", doctorSlotId);
            return ResponseEntity.ok(appointmentService.getAppointmentsByDoctorSlotId(doctorSlotId));
        }
        log.info("GET appointments - retrieving all appointments");
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AppointmentDto> updateAppointment(
            @PathVariable @Positive(message = "Appointment ID must be a positive number") Long id,
            @Valid @RequestBody AppointmentDto dto) {
        log.info("PUT appointment/{}", id);
        return ResponseEntity.ok(appointmentService.updateAppointment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(
            @PathVariable @Positive(message = "Appointment ID must be a positive number") Long id) {
        log.info("DELETE appointment/{}", id);
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}


