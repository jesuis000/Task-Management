package banquemisr.challenge05.taskmanagement.security;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DotEnvConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }
}
