package lk.ijse.eca.appointmentservice.exception;

public class AppointmentNotFoundException extends RuntimeException {

    public AppointmentNotFoundException(Long id) {
        super("Appointment with ID '" + id + "' not found");
    }
}


