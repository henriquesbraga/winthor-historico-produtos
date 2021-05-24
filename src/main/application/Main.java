package application;

import databaseHandlers.DbConfigHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Scene scene;


    @Override
    public void start(Stage stage) throws Exception{
        scene = new Scene(loadFXML("primary"));
        stage.setTitle("Cadastrar histórico de alteração");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
        Image icon = new Image("/caixa.png");
        stage.getIcons().add(icon);
        stage.setOnCloseRequest((e) -> Platform.exit());
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/scenes/" +fxml+".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        DbConfigHandler.checkData();
        launch(args);
    }
}
