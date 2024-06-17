package ftn.notificationservice.services;

import ftn.notificationservice.AuthPostgresIntegrationTest;
import ftn.notificationservice.domain.dtos.NotificationDto;
import ftn.notificationservice.domain.dtos.NotificationRequest;
import ftn.notificationservice.domain.dtos.UserDto;
import ftn.notificationservice.domain.entities.NotificationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Sql("/sql/notifications.sql")
public class NotificationServiceTest extends AuthPostgresIntegrationTest {

    @Autowired
    private NotificationService notificationService;
    @MockBean
    private RestService restService;
    @MockBean
    private EmailService emailService;

    @Test
    public void testSendNotification() {
        String userId = "e49fcaa5-d45b-4556-9d91-13e58187fea6";
        String userEmail = "guest@ftn.com";

        NotificationRequest request = NotificationRequest.builder()
                .userId(userId)
                .notificationType(NotificationType.RESERVATION_CANCEL)
                .build();

        UserDto mockUserDto = UserDto.builder()
                .id(UUID.fromString(userId))
                .email(userEmail)
                .notificationsAllowed(true)
                .build();


        when(restService.getUserById(any(UUID.class))).thenReturn(mockUserDto);
        doNothing().when(emailService).sendMessage(anyString(), anyString(), anyString());

        notificationService.sendNotification(request);

        verify(emailService).sendMessage(eq(mockUserDto.getEmail()), eq("New message from FTN booking"), anyString());
    }

    @Test
    public void testGetNotifications() {
        String userId = "e49fcaa5-d45b-4556-9d91-13e58187fea6";
        List<NotificationDto> notifications = notificationService.getNotificationsByUserId(UUID.fromString(userId));
        assertEquals(2, notifications.size());
    }

}
