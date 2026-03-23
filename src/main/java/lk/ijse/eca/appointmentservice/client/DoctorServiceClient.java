package lk.ijse.eca.appointmentservice.client;

import lk.ijse.eca.appointmentservice.exception.DoctorServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@Slf4j
public class DoctorServiceClient {

    private final RestClient restClient;

    public DoctorServiceClient(@LoadBalanced RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("http://DOCTOR-SERVICE")
                .build();
    }

    public void validateDoctorSlot(String doctorSlotId) {
        log.debug("Validating doctor slot in Doctor-Service for ID: {}", doctorSlotId);
        try {
            restClient.get()
                    .uri("/api/v1/doctors/{id}", doctorSlotId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException e) {
            log.error("Failed to validate doctor slot for ID: {}", doctorSlotId, e);
            throw new DoctorServiceException(
                    "Unable to validate doctor slot: " + doctorSlotId, e);
        }
    }
}


