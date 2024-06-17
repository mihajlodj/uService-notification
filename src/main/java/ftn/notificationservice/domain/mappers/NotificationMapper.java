package ftn.notificationservice.domain.mappers;

import ftn.notificationservice.domain.dtos.NotificationDto;
import ftn.notificationservice.domain.dtos.NotificationRequest;
import ftn.notificationservice.domain.entities.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    Notification fromRequest(NotificationRequest notificationRequest);

    NotificationDto toDto(Notification notification);

}
