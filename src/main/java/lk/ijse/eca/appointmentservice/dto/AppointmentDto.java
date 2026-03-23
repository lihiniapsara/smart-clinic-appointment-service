package lk.ijse.eca.appointmentservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDto {

    private Long id;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotBlank(message = "Patient ID is required")
    private String patientId;

    @NotBlank(message = "Doctor Slot ID is required")
    private String doctorSlotId;

    private PatientDto patient;
}


