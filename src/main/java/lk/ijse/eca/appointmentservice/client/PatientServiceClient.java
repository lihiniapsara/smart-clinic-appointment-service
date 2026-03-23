package lk.ijse.eca.appointmentservice.client;

import lk.ijse.eca.appointmentservice.dto.PatientDto;
import lk.ijse.eca.appointmentservice.exception.PatientServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@Slf4j
public class PatientServiceClient {

    private final RestClient restClient;

    public PatientServiceClient(@LoadBalanced  RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("http://PATIENT-SERVICE")
                .build();
    }

    public PatientDto getPatient(String patientId) {
        log.debug("Fetching patient from Patient-Service for ID: {}", patientId);
        try {
            return restClient.get()
                    .uri("/api/v1/patients/{id}", patientId)
                    .retrieve()
                    .body(PatientDto.class);
        } catch (RestClientException e) {
            log.error("Failed to fetch patient details for ID: {}", patientId, e);
            throw new PatientServiceException(
                    "Unable to retrieve patient details for: " + patientId, e);
        }
    }
}


