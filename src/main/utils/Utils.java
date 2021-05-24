package utils;

import application.Main;
import entities.Product;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {

  private static Scene scene;

  public static void loadFXML(String fxml, String title, String iconFile, boolean resizable) throws IOException {
    Stage stage = new Stage();
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/scenes/" +fxml+".fxml"));
    scene = new Scene(fxmlLoader.load());
    stage.setTitle(title);
    stage.setScene(scene);
    stage.setResizable(resizable);
    Image icon = new Image("/" + iconFile + ".png");
    stage.getIcons().add(icon);
    stage.show();
  }

  public static Alert showDialogTwoButtons(String title, String message){
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setContentText(message);
    return alert;
  }

  public static void showConfirmDialog(String title, String message){
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.show();
  }

  public static void showErrorDialog(String title, String message){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.show();
  }

  public static EntityManager getManager(){
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("sqlite");
    EntityManager em = emf.createEntityManager();
    return em;
  }

  public static void generateExcelFile(TableView<Product> dataTable, Label label, String codfunc, String nomefunc) throws IOException {
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet worksheet = workbook.createSheet("POI Worksheet");

    //Estilos
    //Negrito
    Font indicador = workbook.createFont();
    indicador.setBold(true);
    indicador.setFontHeightInPoints((short) 12);
    indicador.setColor(IndexedColors.BLACK.getIndex());
    CellStyle indicadorCellStyle = workbook.createCellStyle();
    indicadorCellStyle.setFont(indicador);

    //Normal
    //Negrito
    Font normalFont = workbook.createFont();
    normalFont.setBold(false);
    normalFont.setFontHeightInPoints((short) 12);
    normalFont.setColor(IndexedColors.BLACK.getIndex());
    CellStyle normalCellStyle = workbook.createCellStyle();
    normalCellStyle.setFont(normalFont);


    //Linha 1: cod, nome do user
    HSSFRow row1 = worksheet.createRow((short) 0);

    HSSFCell cellA1 = row1.createCell((short) 0);
    cellA1.setCellValue("Usuário: ");
    cellA1.setCellStyle(indicadorCellStyle);

    HSSFCell cellA2 = row1.createCell((short) 1);
    cellA2.setCellValue(codfunc + " - " + nomefunc);
    cellA2.setCellStyle(normalCellStyle);

    //Linha 2 em branco
    HSSFRow row2 = worksheet.createRow((short) 1);
    HSSFCell cellBlank = row2.createCell((short) 0);
    cellBlank.setCellValue("");


    //Linha 3: Tabela - colunas
    HSSFRow row3 = worksheet.createRow((short) 2);
    for (int j = 0; j < dataTable.getColumns().size(); j++){
      HSSFCell tableColumn = row3.createCell((short) j);
      tableColumn.setCellValue(dataTable.getColumns().get(j).getText());
      tableColumn.setCellStyle(indicadorCellStyle);
    }

    //Linha 4: Dados
    for (int i = 0; i < dataTable.getItems().size(); i++){
      HSSFRow dataRow = worksheet.createRow((short) i + 3);
      //preenche a tabela
      for (int j = 0; j < dataTable.getColumns().size(); j++){
        if(dataTable.getColumns().get(j).getCellData(i) != null){
          dataRow.createCell(j).setCellValue(dataTable.getColumns().get(j).getCellData(i).toString());
          dataRow.setRowStyle(normalCellStyle);
        }
        else{
          dataRow.createCell(j).setCellValue("ping");
        }
      }
    }

    worksheet.autoSizeColumn(0);
    worksheet.autoSizeColumn(1);
    worksheet.autoSizeColumn(2);
    worksheet.autoSizeColumn(3);



    Stage stage;
    stage = (Stage) label.getScene().getWindow();

    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("excel files (*.xls)", "*.xls");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showSaveDialog(stage);

    if (file != null) {
      FileOutputStream fileOut = new FileOutputStream(file);
      workbook.write(fileOut);
      fileOut.close();
    }
  }



}
