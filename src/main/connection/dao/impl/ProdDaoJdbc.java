package connection.dao.impl;

import connection.JdbcConnection;
import connection.SQLiteJDBC;
import connection.dao.ProductDao;
import entities.Product;
import utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdDaoJdbc implements ProductDao {

  @Override
  public Product getProdById(Long id) {
    String SQL = "SELECT descricao FROM PCPRODUT WHERE CODPROD = ?";
    try(Connection conn = JdbcConnection.getConnection()){
      try(PreparedStatement st = conn.prepareStatement(SQL)){
        st.setLong(1, id);
        try(ResultSet rs = st.executeQuery()){
          if(rs.next()){
            Product prod = new Product();
            prod.setCodprod(id);
            prod.setDescricao(rs.getString("descricao"));
            return prod;
          }
          else {
            return null;
          }
        }
      }
    }
    catch (SQLException e){
      Utils.showErrorDialog("Erro", e.getMessage());
    }
    return null;
  }

  @Override
  public void insertProd(Product prod) {
    String SQLINSERT = "INSERT INTO Product (codprod, descricao, date, codfunc) VALUES (?,?,?,?)";
    try(Connection conn  = SQLiteJDBC.getConnection()){
      try(PreparedStatement st = conn.prepareStatement(SQLINSERT)){
        st.setLong(1, prod.getCodprod());
        st.setString(2, prod.getDescricao());
        st.setString(3, prod.getDate());
        st.setLong(4, Long.parseLong(prod.getCodfunc()));
        st.executeUpdate();
      }
    }
    catch (SQLException e){
      Utils.showErrorDialog("Erro", e.getMessage());
    }
  }

  @Override
  public void deleteProd(Long codprod) {
    String SQL = "DELETE FROM Product WHERE codprod = ?";
    try(Connection conn  = SQLiteJDBC.getConnection()){
      try(PreparedStatement st = conn.prepareStatement(SQL)){
        st.setLong(1,codprod);
        st.executeUpdate();
      }
    }
    catch (SQLException e){
      Utils.showErrorDialog("Erro", e.getMessage());
    }
  }

  @Override
  public List<Product> getAll(String date, String userInput) {

    String SQL = "select * from Product where date like \"%" + date + "%\" and codfunc = " + userInput + ";";

    List<Product> list = new ArrayList<>();

    try (Connection conn = SQLiteJDBC.getConnection()) {
      try (PreparedStatement st = conn.prepareStatement(SQL)) {
        try (ResultSet rs = st.executeQuery()) {
          while (rs.next()) {
            Product prod = new Product();
            prod.setCodprod(rs.getLong("codprod"));
            prod.setDescricao(rs.getString("descricao"));
            prod.setDate(rs.getString("date"));
            prod.setCodfunc(rs.getString("codfunc"));
            list.add(prod);
          }
        }
      }

    } catch (SQLException e) {
      Utils.showErrorDialog("Erro", e.getMessage());
    }
    return list;
  }

  @Override
  public List<Product> getAllWithoutDate(String userInput) {
    String SQL = "select * from Product where codfunc =" + userInput + ";";

    List<Product> list = new ArrayList<>();

    try (Connection conn = SQLiteJDBC.getConnection()) {
      try (PreparedStatement st = conn.prepareStatement(SQL)) {
        try (ResultSet rs = st.executeQuery()) {
          while (rs.next()) {
            Product prod = new Product();
            prod.setCodprod(rs.getLong("codprod"));
            prod.setDescricao(rs.getString("descricao"));
            prod.setDate(rs.getString("date"));
            prod.setCodfunc(rs.getString("codfunc"));
            list.add(prod);
          }
        }
      }

    } catch (SQLException e) {
      Utils.showErrorDialog("Erro", e.getMessage());
    }
    return list;
  }
}
