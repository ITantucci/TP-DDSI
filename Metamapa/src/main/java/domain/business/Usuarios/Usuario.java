package domain.business.Usuarios;
import java.util.List;

public class Usuario {
  private String email;
  private String contraseniaHasheada;
  private Perfil perfil;
  private List<Rol> roles;

  //TODO: cambiar getters y setters

  //getters

  public String getContraseniaHasheada() {
    return contraseniaHasheada;
  }

  public String getEmail() {
    return email;
  }

  public Perfil getPerfil() {
    return perfil;
  }

  public List<Rol> getRoles() {
    return roles;}

  //setters

  public void setContraseniaHasheada(String contraseniaHasheada) {
    this.contraseniaHasheada = contraseniaHasheada;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPerfil(Perfil perfil) {
    this.perfil = perfil;
  }

  public void setRoles(List<Rol> roles) {
    this.roles = roles;
  }


  // metodos

  public Boolean tieneRole(Rol rol) {
    return this.roles.contains(rol);
  }

}
