package main.ru.geekbrains.clientside;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.ru.geekbrains.clientside.controllers.MainController;
import main.ru.geekbrains.clientside.service.FileServiceClient;
import main.ru.geekbrains.clientside.service.FileServiceClientImpl;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxml/main.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();
        mainController.setMainStage(primaryStage);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);

    }
}
