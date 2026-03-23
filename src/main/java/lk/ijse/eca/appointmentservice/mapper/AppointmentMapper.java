package lk.ijse.eca.appointmentservice.mapper;

import lk.ijse.eca.appointmentservice.dto.AppointmentDto;
import lk.ijse.eca.appointmentservice.dto.PatientDto;
import lk.ijse.eca.appointmentservice.entity.Appointment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AppointmentMapper {

    @Mapping(target = "patient", source = "patient")
    AppointmentDto toDto(Appointment appointment, PatientDto patient);

    @Mapping(target = "id", ignore = true)
    Appointment toEntity(AppointmentDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntity(AppointmentDto dto, @MappingTarget Appointment appointment);
}


