package sinhan.server1.global.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure().load();
    }
}
