package ftn.notificationservice.services;

import ftn.notificationservice.domain.dtos.NotificationDto;
import ftn.notificationservice.domain.dtos.NotificationRequest;
import ftn.notificationservice.domain.entities.Notification;
import ftn.notificationservice.domain.mappers.NotificationMapper;
import ftn.notificationservice.repositories.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final AmqpTemplate notificationTemplate;

    @RabbitListener(queues = "notificationQueue", concurrency = "5")
    public void notificationListener(Message message) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message.getBody());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        NotificationRequest notificationRequest = (NotificationRequest) objectInputStream.readObject();
        sendNotification(notificationRequest);
    }

    public void sendNotification(NotificationRequest notificationRequest) {
        Notification notification = NotificationMapper.INSTANCE.fromRequest(notificationRequest);
        notification.setMessage();

        //TODO get user -> if notifications allowed -> send
        //TODO email sender

        notificationRepository.save(notification);
    }

    public List<NotificationDto> getNotificationsByUserId(UUID userId) {
        List<Notification> notifications = notificationRepository.findAllByUserId(userId);
        return notifications.stream().map(NotificationMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

}
