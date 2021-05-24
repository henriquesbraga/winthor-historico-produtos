package connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteJDBC {

  final static Logger logger = Logger.getLogger(SQLiteJDBC.class);

  private static HikariConfig config = new HikariConfig();
  private static HikariDataSource ds;

  static {
    config.setDriverClassName("org.sqlite.JDBC");
    config.setJdbcUrl( "jdbc:sqlite:sample.db" );
    config.addDataSourceProperty( "cachePrepStmts" , "true" );
    config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
    config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
    config.addDataSourceProperty( "maximumPoolSize" , "10" );
    ds = new HikariDataSource( config );
  }

  private SQLiteJDBC() {}

  public static Connection getConnection() throws SQLException {
    return ds.getConnection();
  }
}
