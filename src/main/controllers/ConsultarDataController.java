package controllers;

import connection.dao.DaoFactory;
import connection.dao.ProductDao;
import connection.dao.UserDao;
import entities.Product;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
import org.sqlite.SQLiteException;
import utils.Utils;

import javax.persistence.EntityManager;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultarDataController implements Initializable {


    @FXML
    Button excelExport;

    @FXML
    TextField userInput;

    @FXML
    Button getUser;


    @FXML
    Label codfunc;

    @FXML
    Label nomefunc;

    @FXML
    Label numtabela;

    @FXML
    DatePicker datePicker;

    @FXML
    TableView<Product> dataTable;

    @FXML
    TableColumn<User, Long> codprod;

    @FXML
    TableColumn<User, String> descricao;

    @FXML
    TableColumn<User, String> date;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    List<Product> list = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codprod.setCellValueFactory(new PropertyValueFactory<User, Long>("codprod"));
        descricao.setCellValueFactory(new PropertyValueFactory<User, String>("descricao"));
        date.setCellValueFactory(new PropertyValueFactory<User, String>("date"));

        excelExport.setOnAction(event -> {
            try{
                Utils.generateExcelFile(dataTable, codfunc, codfunc.getText(), nomefunc.getText());
            }
            catch (Exception e){
                Utils.showErrorDialog("Erro", e.getMessage());
            }
        });

        userInput.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                getUser();
            }
        });

        getUser.setOnAction(event -> getUser());
    }

    private void getUser() {
        try{
            Long id = Long.parseLong(userInput.getText());
            UserDao userDao = DaoFactory.createUserDao();

            User user = userDao.getUserByid(id);

            if (user != null) {
                codfunc.setText(Long.toString(id));
                nomefunc.setText(user.getUsername());
            } else {
                Utils.showErrorDialog("Erro", "Usuário não encontrado");
            }
        }
        catch(Exception e){
            Utils.showErrorDialog("Erro", "Insira um código de usuário!");
        }

        getData();
    }

    private void getData(){
        if(datePicker.getValue() == null){
            getDataWithoutDate();
        }
        else{
            getDataWithDate();
        }
        userInput.requestFocus();
    }

    private void getDataWithDate(){

        try{
            String date = datePicker.getValue().format(format);
            ProductDao dao = DaoFactory.createProdDao();
            list = dao.getAll(date, userInput.getText());
            dataTable.getItems().setAll(list);
            numtabela.setText(Integer.toString(dataTable.getItems().size()));
        }
        catch (Exception e){
            userInput.setText("");
        }
    }

    private void getDataWithoutDate(){

        try{
            ProductDao dao = DaoFactory.createProdDao();
            list = dao.getAllWithoutDate(userInput.getText());
            dataTable.getItems().setAll(list);
            numtabela.setText(Integer.toString(dataTable.getItems().size()));
        }
        catch (Exception e){
            userInput.setText("");
        }
    }
}
