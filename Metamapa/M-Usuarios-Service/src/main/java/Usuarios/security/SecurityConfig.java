package Usuarios.security;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.*;
import java.util.*;

@Configuration
public class SecurityConfig {
  @Bean
  public PasswordEncoder passwordEncoder() {
    String encodingId = "bcrypt";
    Map<String, PasswordEncoder> encoders = new HashMap<>();
    encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
    encoders.put(encodingId, new BCryptPasswordEncoder());
    return new DelegatingPasswordEncoder(encodingId, encoders);
  }

  @Bean
  @Order(2)
  public SecurityFilterChain appSecurityFilterChain(HttpSecurity http, @Lazy CustomOAuth2UserService customOAuth2UserService) throws Exception {
    http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/usuarios/api-auth/me").authenticated()
                    .requestMatchers(
                            "/actuator/**",
                            "/usuarios/api-auth/**",
                            "/usuarios",
                            "/login",
                            "/logout",
                            "/usuarios/logout",
                            "/login.html",
                            "/.well-known/**",
                            "/oauth2/**",
                            "/error"
                    ).permitAll()

                    .anyRequest().authenticated())

            .formLogin(form -> form
                    .loginPage("/login.html")
                    .loginProcessingUrl("/login")
                    .usernameParameter("email")
                    .defaultSuccessUrl("http://ec2-18-206-12-169.compute-1.amazonaws.com:9000/index.html", true)
                    .permitAll()
            )

            .logout(logout -> logout
                    .logoutUrl("/usuarios/logout")
                    .logoutSuccessUrl("http://ec2-18-206-12-169.compute-1.amazonaws.com:9000/index.html")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
            )
            .oauth2Login(oauth2 -> oauth2
                    .defaultSuccessUrl("http://ec2-18-206-12-169.compute-1.amazonaws.com:9000/index.html", false)
                    .userInfoEndpoint(userInfo -> userInfo
                            .userService(customOAuth2UserService)
                    )
            )
            .csrf(csrf -> csrf.disable());

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    // PERMITIR ACCESO DESDE EL CLIENTE LIVIANO (PUERTO 9000)
    configuration.setAllowedOrigins(List.of("http://localhost:9000", "http://ec2-18-206-12-169.compute-1.amazonaws.com:9000"));

    // Permitir credenciales (cookies, tokens)
    configuration.setAllowCredentials(true);

    // Métodos permitidos para las peticiones
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

    // Headers que se permiten en la petición
    configuration.setAllowedHeaders(List.of("*"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // Aplicar esta configuración a TODOS los endpoints (/**)
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}