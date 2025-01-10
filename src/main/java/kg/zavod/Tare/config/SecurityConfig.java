package kg.zavod.Tare.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kg.zavod.Tare.dto.ResponseDto;
import kg.zavod.Tare.dto.state.ResponseState;
import kg.zavod.Tare.sequrity.JwtTokenFilter;
import kg.zavod.Tare.sequrity.JwtTokenProvider;
import kg.zavod.Tare.sequrity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    @Value("${cors.security.url}")
    private String corsUrl;
    public static final String[] SWAGGER_PUBLIC_ROUTES = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };

    /**
     * Создает бин для шифрования паролей
     * @return - объект шифрующий пароли
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Создает бин, который контролирует аутентификацию
     * @param configuration - конфигурация аутентификации
     * @return - менеджер аутентификации
     * @throws Exception - выбрасывается в случае ошибки
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Создает бин, который является цепочкой безопасности
     * @param http - конфигурация безопасности
     * @return - цепочка безопасности
     * @throws Exception - выбрасывается в случае ошибки
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(config -> corsConfiguration()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType("application/json;charset=UTF-8");
                            response.setCharacterEncoding("UTF-8");
                            String jsonResponse = objectMapper.writeValueAsString(getResponseForUnauthorized());
                            response.getWriter().write(jsonResponse);
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/json;charset=UTF-8");
                            response.setCharacterEncoding("UTF-8");
                            String jsonResponse = objectMapper.writeValueAsString(getResponseForForbidden());
                            response.getWriter().write(jsonResponse);
                        })
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("product/basket").permitAll()
                        .requestMatchers(HttpMethod.POST).permitAll()
                        .requestMatchers(HttpMethod.PUT).permitAll()
                        .requestMatchers(HttpMethod.DELETE).permitAll()
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/role/**").authenticated()
                        .requestMatchers("/auth/logout").authenticated()
                        .anyRequest().permitAll()
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
                //.addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Создает бин конфигурации Cors
     * @return - объект конфигурации Cors
     */
    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        corsConfig.addAllowedOrigin(corsUrl);
        corsConfig.addAllowedOrigin("http://localhost");
        corsConfig.addAllowedMethod(HttpMethod.GET);
        corsConfig.addAllowedMethod(HttpMethod.POST);
        corsConfig.addAllowedMethod(HttpMethod.PUT);
        corsConfig.addAllowedMethod(HttpMethod.DELETE);
        corsConfig.addAllowedHeader("Authorization");
        return corsConfig;
    }

    /**
     * Метод позволяет получить ответ с ошибкой "Не авторизован"
     * @return - ответ
     */
    private ResponseDto<String> getResponseForUnauthorized() {
        return ResponseDto.buildResponse(ResponseState.ERROR, "Не авторизован");
    }

    /**
     * Метод позволяет получить ответ с ошибкой "Доступ запрещен"
     * @return - ответ
     */
    private ResponseDto<String> getResponseForForbidden() {
        return ResponseDto.buildResponse(ResponseState.ERROR, "Доступ запрещен");
    }

    /**
     * Метод позволяет получить пользователя из контекста безопасности
     * @return - пользователь
     */
    public static UserDetailsImpl getUserFromSecurityContext() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}