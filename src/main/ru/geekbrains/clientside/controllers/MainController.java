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
import main.ru.geekbrains.clientside.service.FileServiceClient;
import main.ru.geekbrains.clientside.service.FileServiceClientImpl;

import java.io.IOException;


public class MainController {

    public final String EMPTY_CELL = "EMPTY CELL !!!";
    public final String FILE_NOT = "FILE DOES NOT EXIST !!!";
    private FileServiceClient fileService;
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


    public void buttonClientAction(ActionEvent event) throws IOException {
        FileData fileData;
        Object source = event.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        // FileData selectFileData = (FileData) table1.getSelectionModel().getSelectedItem();
        if (textField1.getText().trim().equals("")) {
            setLabelText(EMPTY_CELL);
            return;
        } else {
            String fileName = textField1.getText().trim();
            fileData = fileService.getFileData(fileName);
            if (fileData == null) {
                setLabelText(FILE_NOT);
                textField1.setText("");
                return;
            }
            choiceClientButton((Button) source, fileData);
        }

    }

    private void choiceClientButton(Button source, FileData selectFileData) throws IOException {
        Button clickedButton = source;
        switch (clickedButton.getId()) {
            case "btnRenameClientFile":

                break;
            case "btnDeleteClientFile":
                connect.deleteClientFileData(selectFileData);
                break;

            case "btnShareFile":
                connect.share(selectFileData);
                break;


        }
    }

    public void buttonServerAction(ActionEvent event) throws IOException {
        FileData fileData;
        Object source = event.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        // FileData selectFileData = (FileData) table1.getSelectionModel().getSelectedItem();
        if (textField2.getText().trim().equals("")) {
            setLabelText(EMPTY_CELL);
            return;
        } else {
            String fileName = textField2.getText().trim();
            fileData = fileService.findFileDataFromList(fileService.getFileDataListServer(), fileName);
            if (fileData == null) {
                setLabelText(FILE_NOT);
                textField1.setText("");
                return;
            }
            choiceServerButton((Button) source, fileData);
        }

    }

    private void choiceServerButton(Button source, FileData selectFileData) throws IOException {
        Button clickedButton = source;
        switch (clickedButton.getId()) {

            case "btnDeleteServerFile":
                connect.deleteServerFileData(selectFileData);
                break;
            case "btnDownloadFile":
                connect.downLoadFile(selectFileData);
                break;

        }
    }


    private void startClientSocket() {


        fileService = new FileServiceClientImpl();
        connect = new Connect(fileService, this);

    }

    public Label getLabel() {
        return label;
    }

    @FXML
    public void initialize() throws IOException {
        startClientSocket();
        table1.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table2.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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
                FileData selectFileData = (FileData) table2.getSelectionModel().getSelectedItem();
                if (event.getClickCount() == 2) {

                    textField2.setText(selectFileData.getCurrentFileName());

                }
            }
        });


        table1.setItems(fileService.getFileDataList());
        setLabelText("YOU HAVE " + fileService.getFileDataList().size() + " FILES ON CLIENT SIDE!");

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

    public void setLabelText(String labelText) {
        label.setText(labelText);
    }

    public FileServiceClient getFileService() {
        return fileService;
    }
}
