package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;


@Entity
public class Product {

  private Long id;


  private Long codprod;


  private String descricao;


  private String date;


  private String codfunc;

  public Product(){
  }

  public Product(Long id, Long codprod, String descricao, String date, String codfunc) {
    this.id = id;
    this.codprod = codprod;
    this.descricao = descricao;
    this.date = date;
    this.codfunc = codfunc;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCodprod() {
    return codprod;
  }

  public void setCodprod(Long codprod) {
    this.codprod = codprod;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getCodfunc() {
    return codfunc;
  }

  public void setCodfunc(String codfunc) {
    this.codfunc = codfunc;
  }

  @Override
  public String toString() {
    return "Product{" +
            "id=" + id +
            ", codprod=" + codprod +
            ", descricao='" + descricao + '\'' +
            ", date='" + date + '\'' +
            ", codfunc='" + codfunc + '\'' +
            '}';
  }
}
