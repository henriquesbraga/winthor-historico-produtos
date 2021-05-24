package databaseHandlers;

import connection.SQLiteJDBC;
import entities.DatabaseConfig;
import utils.Utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class DbConfigHandler {
  public static void insertConfig(){
    DatabaseConfig db = new DatabaseConfig(null, "jdbc:oracle:thin:@localhost:1521:XE","hr", "1234");
    String SQL = "INSERT INTO DatabaseConfig VALUES ('hr', 1234, 'jdbc:oracle:thin:@localhost:1521:XE')";
    try(Connection conn = SQLiteJDBC.getConnection()){
      try(Statement st = conn.createStatement()){
        st.execute(SQL);
      }
    }
    catch (SQLException e){
      e.printStackTrace();
    }
  }

  public static void checkData(){
    String databaseconfig = "CREATE TABLE IF NOT EXISTS \"DatabaseConfig\" ( "
    + "\"id\"	INTEGER NOT NULL, "
    + "\"dbuser\"	TEXT NOT NULL, "
    + "\"dbpassword\"	TEXT NOT NULL, "
    + "\"dbpath\"	TEXT NOT NULL, "
    + "PRIMARY KEY(\"id\"));";

    String product = "CREATE TABLE IF NOT EXISTS \"Product\" ( "
    + "\"id\"	INTEGER NOT NULL, "
    + "\"codprod\"	INTEGER NOT NULL, "
    + "\"descricao\"	TEXT NOT NULL, "
    + "\"date\"	TEXT NOT NULL, "
    + "\"codfunc\"	INTEGER NOT NULL, "
    + "PRIMARY KEY(\"id\" AUTOINCREMENT));";

    String SQLInsertDefaultUser = "insert or ignore into DatabaseConfig VALUES (1,'username','password','jdbc:oracle:thin:@serverip:1521:servicename');";

    try(Connection conn = SQLiteJDBC.getConnection()){
      try(Statement st = conn.createStatement()){
        st.execute(databaseconfig);
        st.execute(product);
        st.execute(SQLInsertDefaultUser);
      }
    }
    catch (SQLException e){
      Utils.showErrorDialog("Erro", e.getMessage());
    }
  }
}
