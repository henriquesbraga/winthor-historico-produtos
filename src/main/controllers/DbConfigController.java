package controllers;

import connection.dao.DaoFactory;
import connection.dao.UserDao;
import entities.DatabaseConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;
import utils.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class DbConfigController implements Initializable {

  final static Logger logger = Logger.getLogger(DbConfigController.class);
  @FXML
  Label testLabel;


  @FXML
  TextField connectString;

  @FXML
  TextField user;

  @FXML
  PasswordField password;

  @FXML
  Button confirmButton;

  private void insertData(){
    DatabaseConfig dbconf = new DatabaseConfig(null, connectString.getText(), user.getText(), password.getText());
    UserDao dao = DaoFactory.createUserDao();
    dao.updateConfig(dbconf);
    Utils.showConfirmDialog("Inserção de dados", "Dados Inseridos com sucesso!");
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    UserDao dao = DaoFactory.createUserDao();
    DatabaseConfig dbconf = dao.getConfig();
    connectString.setText(dbconf.getConnectionString());
    user.setText(dbconf.getUser());
    password.setText(dbconf.getPassword());
    confirmButton.setOnAction(event -> insertData());
  }






}
