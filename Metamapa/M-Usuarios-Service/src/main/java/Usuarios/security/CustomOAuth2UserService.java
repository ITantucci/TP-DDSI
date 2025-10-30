package Usuarios.security;

import Usuarios.service.UsuarioService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
  private final UsuarioService usuarioService;

  public CustomOAuth2UserService(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    // 1. Delega la obtención del usuario a Spring Security
    OAuth2User oauth2User = delegate.loadUser(userRequest);

    // 2. Extrae los datos (los "claims" de Auth0)
    String email = oauth2User.getAttribute("email");
    String nombre = oauth2User.getAttribute("name");

    // 3. Sincroniza con tu base de datos
    if (email != null) {
      usuarioService.sincronizarUsuarioSSO(email, nombre);
    }

    // 4. Retorna el usuario autenticado por Auth0
    return oauth2User;
  }
}