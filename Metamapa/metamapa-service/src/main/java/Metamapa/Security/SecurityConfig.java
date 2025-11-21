package Metamapa.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  private final RateLimitingFilter rateLimitingFilter;
  private final IpFilter ipFilter;

  public SecurityConfig(RateLimitingFilter rateLimitingFilter, IpFilter ipFilter) {
    this.rateLimitingFilter = rateLimitingFilter;
    this.ipFilter = ipFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/actuator/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .anyRequest().permitAll() // por ahora no pedimos login
            )
            .csrf(csrf -> csrf.disable())
            // primero filtro por IP, después rate limit
            .addFilterBefore(ipFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(rateLimitingFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
