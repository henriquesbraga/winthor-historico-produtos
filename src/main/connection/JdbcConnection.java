package connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import connection.dao.DaoFactory;
import connection.dao.UserDao;
import entities.DatabaseConfig;
import org.apache.log4j.Logger;
import utils.Utils;

import java.sql.Connection;

import java.sql.SQLException;

public class JdbcConnection {

  final static Logger logger = Logger.getLogger(JdbcConnection.class);

  private static HikariConfig config = new HikariConfig();
  private static HikariDataSource ds;

  static {

    try{
      DatabaseConfig dbconf = getConfig();
      config.setDriverClassName("oracle.jdbc.driver.OracleDriver");
      config.setJdbcUrl( dbconf.getConnectionString() );
      config.setUsername( dbconf.getUser() );
      config.setPassword( dbconf.getPassword() );
      config.addDataSourceProperty( "cachePrepStmts" , "true" );
      config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
      config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
      config.addDataSourceProperty( "maximumPoolSize" , "20" );
      ds = new HikariDataSource( config );
    }
    catch(Exception e){
      Utils.showErrorDialog("Erro", e.getMessage());
    }
  }

  private JdbcConnection() {}

  public static Connection getConnection() throws SQLException {
    return ds.getConnection();
  }

  public static DatabaseConfig getConfig(){
    UserDao dao = DaoFactory.createUserDao();
    DatabaseConfig dbconf = dao.getConfig();
    return  dbconf;
  }

}
