package kg.zavod.Tare.sequrity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Класс содержит параметры для создания токена.
 * Данные берутся из properties через ConfigurationProperties
 */
@Component
@Data
@ConfigurationProperties(prefix = "jwt.secret")
public class JwtProperties {
    private String key;
    private long access;
    private long refresh;
}