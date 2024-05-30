package ftn.notificationservice.domain.dtos;

import ftn.notificationservice.domain.entities.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    private String userId;
    private String email;
    private NotificationType notificationType;

}
