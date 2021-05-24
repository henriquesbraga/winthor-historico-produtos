package connection.dao;

import connection.dao.impl.ProdDaoJdbc;
import connection.dao.impl.UserDaoJdbc;

public class DaoFactory {
  public static UserDao createUserDao(){
    return new UserDaoJdbc();
  }
  public static ProductDao createProdDao() {return new ProdDaoJdbc();}
}
