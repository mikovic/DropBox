package main.ru.geekbrains.clientside.controllers;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.ru.geekbrains.clientside.Connect;
import main.ru.geekbrains.clientside.model.FileData;
import main.ru.geekbrains.clientside.service.FileServiceClientImpl;

import java.io.IOException;

public class MainController {


    private FileServiceClientImpl fileService;
    private Connect connect;
    @FXML
    private Button btnRenameClientFile;
    @FXML
    private Button btnDeleteClientFile;
    @FXML
    private Button btnShareFile;
    @FXML
    private Button btnDownloadFile;
    @FXML
    private Button btnDeleteServerFile;


    @FXML
    private Label label;


    @FXML
    private TableView table1;

    @FXML
    private TableView table2;
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;

    @FXML
    private TableColumn tableColumn1;
    @FXML
    private TableColumn tableColumn2;



    public void buttonAction(ActionEvent event)  throws IOException {
        Object source = event.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        Button clickedButton = (Button) source;
        FileData selectFileData = (FileData) table1.getSelectionModel().getSelectedItem();
        switch (clickedButton.getId()) {
            case "btnRenameClientFile":

                break;
            case "btnDeleteClientFile":
                break;

            case "btnShareFile":
                connect.share(selectFileData);
                break;
            case "btnDeleteServerFile":
                break;
            case "btnDownloadFile":
                break;

        }


    }
    private void startClientSocket(){

        fileService = new FileServiceClientImpl();
        connect = new Connect(this);
        connect.setFileService(fileService);
    }

    public Label getLabel() {
        return label;
    }

    @FXML
    public void initialize() throws IOException {
        startClientSocket();

        tableColumn1.setCellValueFactory(new PropertyValueFactory<FileData, String>("currentFileName"));
        tableColumn2.setCellValueFactory(new PropertyValueFactory<FileData, String>("currentFileName"));


        fileService.fillDataTableView();
        fileService.getFileDataList().addListener(new ListChangeListener<FileData>() {
            @Override
            public void onChanged(Change<? extends FileData> c) {

            }
        });
        table1.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                FileData selectFileData = (FileData) table1.getSelectionModel().getSelectedItem();
                if (event.getClickCount() == 2) {
                    textField1.setText(selectFileData.getCurrentFileName());

                }
            }
        });

        table2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileData selectFileData = (FileData) table1.getSelectionModel().getSelectedItem();
                if (event.getClickCount() == 2) {

                    textField2.setText(selectFileData.getCurrentFileName());

                }
            }
        });


        table1.setItems(fileService.getFileDataList());


    }

    public TextField getTextField1() {
        return textField1;
    }

    public TextField getTextField2() {
        return textField2;
    }

    public TableView getTable2() {
        return table2;
    }
}
