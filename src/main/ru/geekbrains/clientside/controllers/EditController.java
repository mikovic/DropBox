package main.ru.geekbrains.clientside.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.event.ActionEvent;

public class EditController {

    @FXML
    private Label labelModal;

    @FXML
    private TextField textFieldModal;

    @FXML
    private Button buttonOK;
    @FXML
    private Button buttonCancel;




    public void buttonRenameAction(javafx.event.ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }
    }

    public void actionClose(javafx.event.ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
