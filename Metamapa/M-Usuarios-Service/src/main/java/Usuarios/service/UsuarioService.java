package Usuarios.service;

import Usuarios.business.Usuarios.Usuario;
import Usuarios.business.Usuarios.Rol;
import Usuarios.persistencia.RepositorioUsuarios;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UsuarioService {

  private final RepositorioUsuarios usuarioRepo;
  private final PasswordEncoder passwordEncoder;

  public Usuario registrar(String email, String contrasenia, String nombre, String apellido) {
    if (usuarioRepo.existsByEmail(email)) {
      throw new RuntimeException("Ya existe un usuario con ese email.");
    }

    Usuario u = Usuario.builder()
        .email(email)
        .contraseniaHasheada(passwordEncoder.encode(contrasenia))
        .nombre(nombre)
        .apellido(apellido)
        .roles(Set.of(Rol.VISUALIZADOR))
        .build();

    return usuarioRepo.save(u);
  }

  public Optional<Usuario> buscarPorEmail(String email) {
    return usuarioRepo.findByEmail(email);
  }

  public List<Usuario> listarUsuarios() {
    return usuarioRepo.findAll();
  }

  public Usuario sincronizarUsuarioSSO(String email, String nombreCompleto) {
    Optional<Usuario> usuarioExistente = usuarioRepo.findByEmail(email);

    if (usuarioExistente.isPresent()) {
      // Usuario ya existe, lo retornamos (posiblemente lo actualizamos)
      return usuarioExistente.get();
    } else {
      // Usuario no existe, lo creamos

      // 🚨 Recomendación: Usar el builder si es la forma estándar de crear usuarios
      // Esto garantiza que todos los campos requeridos sean inicializados.
      Usuario nuevoUsuario = Usuario.builder()
              .email(email)
              // 🚨 Contraseña no válida: Usa un prefijo para indicar que es SSO.
              // Esto previene que alguien pueda adivinar una contraseña vacía y acceder al formulario local.
              .contraseniaHasheada("{ssoregistrado}" + UUID.randomUUID().toString())
              .nombre(nombreCompleto) // Usamos el nombre completo como nombre
              .apellido("") // Dejamos el apellido vacío o lo parseamos del nombreCompleto
              .roles(Set.of(Rol.VISUALIZADOR)) // 🚨 Asignar roles por defecto
              .build();

      return usuarioRepo.save(nuevoUsuario);
    }
  }
}
