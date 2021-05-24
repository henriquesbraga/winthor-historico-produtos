package controllers;

import connection.dao.DaoFactory;
import connection.dao.ProductDao;
import connection.dao.UserDao;
import entities.Product;
import entities.User;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.KeyCode;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.ResourceBundle;


public class PrimaryController implements Initializable {

  @FXML
  MenuBar menuBar;

  @FXML
  Menu file;

  @FXML
  Menu config;

  @FXML
  Menu help;

  @FXML
  MenuItem excelExport;

  @FXML
  MenuItem consultarData;

  @FXML
  MenuItem dbConfig;

  @FXML
  MenuItem about;

  @FXML
  TextField userInput;

  @FXML
  TextField prodInput;

  @FXML
  Button getUser;

  @FXML
  Button getProd;

  @FXML
  Button deleteItem;

  @FXML
  Label codfunc;

  @FXML
  Label nomefunc;

  @FXML
  Label numtabela;

  @FXML
  TableView<Product> dataTable;

  @FXML
  TableColumn<User, Long> codprod;

  @FXML
  TableColumn<User, String> descricao;

  @FXML
  TableColumn<User, String> date;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    //Colunas da tabela
    codprod.setCellValueFactory(new PropertyValueFactory<User, Long>("codprod"));
    descricao.setCellValueFactory(new PropertyValueFactory<User, String>("descricao"));
    date.setCellValueFactory(new PropertyValueFactory<User, String>("date"));

    //Abre o Dialogo para exportar planilha
    excelExport.setOnAction((event) -> {
      try{
        Utils.generateExcelFile(dataTable, codfunc, codfunc.getText(), nomefunc.getText());
      }
      catch (Exception e){
        e.printStackTrace();
      }
    });

    //Abre as configuraçõs do banco
    dbConfig.setOnAction((event) -> {
      try {
        Utils.loadFXML("dbconfig", "Configurações", "configuracoes", false);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    //Abre o sobre
    about.setOnAction((event) -> {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sobre");
        alert.setHeaderText(null);
        alert.setContentText("Product History v.2.0 \n Created by: Henrique Braga \n Github Page: https://github.com/henriquesbraga/");
        alert.showAndWait();
    });

    //Abre a janela de pesquisar por data
    consultarData.setOnAction((event) -> {
      try {
        Utils.loadFXML("consultardata", "Por Período", "caixa",true);
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    });

    //Chama o metodo para deletar um item do banco e da lista
    deleteItem.setOnAction((event) -> {
      deleteData(dataTable.getSelectionModel().getSelectedItem());
    });

    //Enter para buscar o user e pular para proximo campo
    userInput.setOnKeyPressed(event -> {
      if(event.getCode() == KeyCode.ENTER){
        getUserName();
        prodInput.requestFocus();
      }
    });

    //Enter para pesquisar o produto
    prodInput.setOnKeyPressed(event -> {
      if(event.getCode() == KeyCode.ENTER){
        getProduct();
      }
    });

    //Acção para buscar o usuario
    getUser.setOnAction(event -> getUserName());

    //Ação para buscar o produto
    getProd.setOnAction(event -> getProduct());

    addAutoScroll(dataTable);
  }

  private void getData(Product prod){
    prod.setDate(printDate());
    prod.setCodfunc(codfunc.getText());
    dataTable.getItems().add(prod);
    numtabela.setText(Integer.toString(dataTable.getItems().size()));
    prod.setId(null);
    ProductDao dao = DaoFactory.createProdDao();
    dao.insertProd(prod);
  }

  private void deleteData(Product prod){
    try{
      ProductDao dao = DaoFactory.createProdDao();
      dao.deleteProd(prod.getCodprod());
      dataTable.getItems().removeAll(dataTable.getSelectionModel().getSelectedItem());
      dataTable.refresh();
    }
    catch (NullPointerException e){
      Utils.showErrorDialog("Erro", "Selecione um item!");
    }
  }

  private String printDate(){
    String pattern = "dd/MM/yyyy HH:mm:ss";
    DateFormat df = new SimpleDateFormat(pattern);
    Date today = Calendar.getInstance().getTime();
    String todayAsString = df.format(today);
    return todayAsString;
  }

  private void getUserName(){

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
    catch(NumberFormatException e){
      Utils.showErrorDialog("Erro", "Insira um código de usuário!");
    }
  }

  private void getProduct(){

      try{
        Long idu = Long.parseLong(userInput.getText());
        Long id = Long.parseLong(prodInput.getText());
        ProductDao prodDao = DaoFactory.createProdDao();
        Product prod = prodDao.getProdById(id);
        if (prod != null) {
          getData(prod);
        } else {
          Utils.showErrorDialog("Erro", "Produto não encontrado");
        }
      }
      catch(NumberFormatException e){
        Utils.showErrorDialog("Erro", "Preencha todos os campos!");
      }
    prodInput.setText("");
  }

  //Adiciona auto-scroll
  public static <S> void addAutoScroll(final TableView<S> table) {
    if (table == null) {
      throw new NullPointerException();
    }

    table.getItems().addListener((ListChangeListener<S>) (c -> {
      c.next();
      final int size = table.getItems().size();
      if (size > 0) {
        table.scrollTo(size - 1);
      }
    }));
  }

}
