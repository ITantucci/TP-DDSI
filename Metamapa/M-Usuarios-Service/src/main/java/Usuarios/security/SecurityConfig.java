package Usuarios.security;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.core.annotation.Order; // 👈 Importa esto


@Configuration
public class SecurityConfig {

  private final CustomOAuth2UserService customOAuth2UserService;

  // 3. 👇 Añade el constructor para la Inyección de Dependencias
  public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
    this.customOAuth2UserService = customOAuth2UserService;
  }

  @Bean
  @Order(2) // 👈 PRIORIDAD 2: Se ejecuta si la Prioridad 1 no coincidió
  public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(auth -> auth
                    // 👈 ¡Añade /oauth2/** para permitir la redirección de Social Login!
                    .requestMatchers("/api-auth/**", "/login", "/.well-known/**", "/oauth2/**").permitAll()
                    .anyRequest().authenticated())

            .formLogin(form -> form
                    .usernameParameter("email").defaultSuccessUrl("/api-auth/me", true)
            )
            // 👇 ESTA LÍNEA HABILITA EL SOCIAL LOGIN (Google, GitHub, etc.)
            .oauth2Login(oauth2 -> oauth2
                    .userInfoEndpoint(userInfo -> userInfo
                            .userService(customOAuth2UserService)
                    )
            )

            .csrf(csrf -> csrf.disable());

    return http.build();
  }
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    // Esto expondrá el AuthenticationManager que 'formLogin' necesita.
    // Usará automáticamente tu nuevo 'UsuarioDetailsService' y tu 'PasswordEncoder'.
    return config.getAuthenticationManager();
  }
}