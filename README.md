# Appointment-Service

A microservice responsible for managing patient appointments and doctor-slot assignments. It integrates with Patient-Service and Doctor-Service via REST.

## Tech Stack

| Technology | Details |
|---|---|
| Java | 25 |
| Spring Boot | 4.0.3 |
| Spring Cloud | 2025.1.0 |
| Spring Data JPA | Persistence layer |
| MySQL | Relational database (port `14500`) |
| Spring RestClient | Inter-service HTTP client |
| MapStruct | DTO <-> Entity mapping |
| Spring Validation | Bean validation |
| Eureka Client | Service discovery |
| Config Client | Externalized configuration |

## Service Details

| Property | Value |
|---|---|
| Port | `8002` |
| Artifact ID | `Appointment-Service` |
| Group ID | `lk.ijse.eca` |
| Base Path | `/api/v1/appointments` |

## API Endpoints

| Method | Path | Description | Content-Type |
|---|---|---|---|
| `POST` | `/api/v1/appointments` | Create an appointment | `application/json` |
| `GET` | `/api/v1/appointments` | Get all appointments | — |
| `GET` | `/api/v1/appointments?doctorSlotId={id}` | Filter by doctor slot | — |
| `GET` | `/api/v1/appointments/{id}` | Get appointment by ID | — |
| `PUT` | `/api/v1/appointments/{id}` | Update appointment | `application/json` |
| `DELETE` | `/api/v1/appointments/{id}` | Delete appointment | — |

## Sample Request

```json
{
  "date": "2025-01-15",
  "patientId": "123456789V",
  "doctorSlotId": "CARD-001"
}
```

## Sample Response

```json
{
  "id": 1,
  "date": "2025-01-15",
  "patientId": "123456789V",
  "doctorSlotId": "CARD-001",
  "patient": {
    "name": "Kasun Perera",
    "address": "123 Main Street, Colombo",
    "mobile": "0771234567",
    "email": "kasun@example.com",
    "picture": "/api/v1/patients/123456789V/picture"
  }
}
```

## Startup Order

1. Config-Server (`9000`)
2. Service-Registry (`9001`)
3. Api-Gateway (`7000`)
4. Patient-Service (`8000`)
5. Doctor-Service (`8001`)
6. Appointment-Service (`8002`)
