package connection.dao.impl;

import connection.JdbcConnection;
import connection.SQLiteJDBC;
import connection.dao.UserDao;
import entities.DatabaseConfig;
import entities.User;
import utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoJdbc implements UserDao {
  @Override
  public User getUserByid(Long id) {
    String SQL = "SELECT nome FROM PCEMPR WHERE MATRICULA = ?";
    try(Connection conn = JdbcConnection.getConnection()){
      try(PreparedStatement st = conn.prepareStatement(SQL)){
        st.setLong(1, id);
        try(ResultSet rs = st.executeQuery()){
          if(rs.next()){
            User user = new User();
            user.setId(id);
            user.setUsername(rs.getString("nome"));
            return user;
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
  public DatabaseConfig getConfig() {
    String SQL = "SELECT * from DatabaseConfig where id = 1";
    try(Connection conn = SQLiteJDBC.getConnection()){
      try(PreparedStatement st = conn.prepareStatement(SQL)){
        try(ResultSet rs = st.executeQuery()){
          DatabaseConfig databaseConfig = new DatabaseConfig();
          databaseConfig.setUser(rs.getString("dbuser"));
          databaseConfig.setPassword(rs.getString("dbpassword"));
          databaseConfig.setConnectionString(rs.getString("dbpath"));
          return databaseConfig;
        }
      }
    }
    catch (SQLException e){
      Utils.showErrorDialog("Erro", e.getMessage());
    }
    return null;
  }

  @Override
  public void updateConfig(DatabaseConfig databaseConfig) {
    String SQL = "update DatabaseConfig  set dbuser = ?, dbpassword = ?, dbpath = ? where id = 1;" ;
    try(Connection conn = SQLiteJDBC.getConnection()){
      try(PreparedStatement st = conn.prepareStatement(SQL)){

        st.setString(1, databaseConfig.getUser());
        st.setString(2, databaseConfig.getPassword());
        st.setString(3, databaseConfig.getConnectionString());
        st.executeUpdate();

      }
    }
    catch (SQLException e){
      Utils.showErrorDialog("Erro", e.getMessage());
    }
  }
}
