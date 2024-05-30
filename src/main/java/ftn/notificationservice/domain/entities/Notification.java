package ftn.notificationservice.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public void setMessage() {
        switch (notificationType) {
            case HOST_RATING:
                this.message = "You have a new rating!";
                break;
            case HOTEL_RATING:
                this.message = "One of your accommodations has received a new rating!";
                break;
            case RESERVATION_REQUEST:
                this.message = "You have a new reservation request!";
                break;
            case RESERVATION_CANCEL:
                this.message = "One of your reservations has been canceled.";
                break;
            case RESERVATION_RESPONSE_ACCEPT:
                this.message = "Your request for reservation has been accepted!";
                break;
            case RESERVATION_RESPONSE_REJECT:
                this.message = "Your request for reservation has been rejected.";
                break;
        }
    }

}
