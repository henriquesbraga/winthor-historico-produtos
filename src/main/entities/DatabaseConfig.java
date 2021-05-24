package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;


@Entity
public class DatabaseConfig {

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "config_seq")
  @SequenceGenerator(name = "config_seq", sequenceName = "config_seq", allocationSize = 1, initialValue = 1)
  private Integer id;

  @Column(nullable = false)
  private String path;

  @Column(nullable = false)
  private String user;


  @Column(nullable = false)
  private String password;

  public DatabaseConfig() {
  }

  public DatabaseConfig(Integer id, String path, String user, String password) {
    this.id = id;
    this.path = path;
    this.user = user;
    this.password = password;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getConnectionString() {
    return path;
  }

  public void setConnectionString(String connectionString) {
    this.path = connectionString;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
