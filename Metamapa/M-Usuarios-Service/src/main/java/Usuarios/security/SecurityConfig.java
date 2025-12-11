package Usuarios.security;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.core.annotation.Order;

// Imports para la gestión de contraseñas
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// Import necesario si quieres filtrar por método (POST, GET, etc.)
import org.springframework.http.HttpMethod;

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
  public SecurityFilterChain appSecurityFilterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService) throws Exception {
    http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                    // 1. Endpoints públicos y de autenticación (CORREGIDO)
                    .requestMatchers(
                            "/actuator/**",
                            "/usuarios/api-auth/**",  // Coincide con AuthController
                            "/usuarios",
                            "/login",
                            "/.well-known/**",
                            "/oauth2/**",
                            "/error"
                    ).permitAll()

                    // 2. Permitir creación de usuarios sin login (POST /usuarios)
                    .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()

                    // 3. Todo lo demás requiere autenticación
                    .anyRequest().authenticated())

            .formLogin(form -> form
                    .usernameParameter("email")
                    .defaultSuccessUrl("http://localhost:9000/index.html", true)
            )
            .logout(logout -> logout
                    .logoutUrl("/usuarios/logout")
                    .logoutSuccessUrl("http://localhost:9000/index.html")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
            )
            // Configuración de Social Login (Auth0/Google)
            .oauth2Login(oauth2 -> oauth2
                    // Después del Social Login exitoso, inicia el flujo de obtención de token de TU SAS
                    .defaultSuccessUrl("/oauth2/authorize?client_id=metamapa-service&redirect_uri=http://localhost:9000/callback&scope=openid%20read&response_type=code&code_challenge=xyz&code_challenge_method=S256", true)

                    .userInfoEndpoint(userInfo -> userInfo
                            .userService(customOAuth2UserService) // Usa el parámetro inyectado
                    )
            )
            // Deshabilita CSRF para desarrollo y peticiones API
            .csrf(csrf -> csrf.disable());

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    // PERMITIR ACCESO DESDE EL CLIENTE LIVIANO (PUERTO 9000)
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:9000"));

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