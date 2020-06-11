package server.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;

  @NotNull(message = "Please provide your username")
  private String username;

  @NotNull(message = "Please provide your password")
  private String password;

  @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER)
//  @JoinTable(
//          name = "usr_roles",
//          joinColumns = @JoinColumn(name = "users_id"),
//          inverseJoinColumns = @JoinColumn(name = "roles_id"))
  private Set<Role> roles;

  public void setId(Long id) {
    this.id = id;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public User(String username, String password, Set<Role> roles) {
    this.username = username;
    this.password = password;
    this.roles =roles;
  }

  public User() {
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getRoles();
  }

  public String getPassword() {
    return password;
  }
}