package ftn.notificationservice.domain.dtos;

import ftn.notificationservice.domain.entities.NotificationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NotEmpty
    private String userId;
    private String email;
    @NotNull
    private NotificationType notificationType;

}
