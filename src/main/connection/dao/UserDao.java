package connection.dao;

import entities.DatabaseConfig;
import entities.User;

public interface UserDao {
  User getUserByid(Long id);
  DatabaseConfig getConfig();
  void updateConfig(DatabaseConfig databaseConfig);
}
