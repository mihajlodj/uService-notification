package ftn.notificationservice.services;

import ftn.notificationservice.domain.dtos.NotificationDto;
import ftn.notificationservice.domain.dtos.NotificationRequest;
import ftn.notificationservice.domain.dtos.UserDto;
import ftn.notificationservice.domain.entities.Notification;
import ftn.notificationservice.domain.mappers.NotificationMapper;
import ftn.notificationservice.repositories.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final RestService restService;
    private final EmailService emailService;

    @RabbitListener(queues = "notificationQueue", concurrency = "5")
    public void notificationListener(NotificationRequest notificationRequest) throws IOException, ClassNotFoundException {
        sendNotification(notificationRequest);
    }

    public void sendNotification(NotificationRequest notificationRequest) {
        Notification notification = NotificationMapper.INSTANCE.fromRequest(notificationRequest);
        notification.setMessage();

        UserDto user = restService.getUserById(notification.getUserId());

        if (user.isNotificationsAllowed()) {
            emailService.sendMessage(user.getEmail(), "New message from FTN booking", notification.getMessage());
            notification.setEmail(user.getEmail());
            notificationRepository.save(notification);
            log.info("Sent notification to " + user.getEmail());
        }
    }

    public List<NotificationDto> getNotificationsByUserId(UUID userId) {
        List<Notification> notifications = notificationRepository.findAllByUserId(userId);
        return notifications.stream().map(NotificationMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

}
