package ftn.notificationservice.config.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Bean
    public Queue notificationQueue() {
        return new Queue("notificationQueue");
    }

    //systemctl status rabbitmq-server.service
    //systemctl start rabbitmq-server
    //          (stop)
    //sudo systemctl enable  rabbitmq-server
    //               (disable)

    //sudo rabbitmq-server

}
