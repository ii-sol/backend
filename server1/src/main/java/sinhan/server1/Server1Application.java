package sinhan.server1;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Server1Application implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    public Server1Application(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(Server1Application.class, args);
    }

    @Bean
    public Queue queue(@Value("${rabbitmq.queue}") String queueName) {
        return new Queue(queueName, false);
    }

    @Override
    public void run(String... args) throws Exception {
        rabbitTemplate.convertAndSend("hello", "Hello from Producer!");
    }
}
