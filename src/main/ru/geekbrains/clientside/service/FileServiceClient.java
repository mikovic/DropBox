package main.ru.geekbrains.clientside.service;

import javafx.collections.ObservableList;
import main.ru.geekbrains.clientside.controllers.MainController;
import main.ru.geekbrains.clientside.model.FileData;

import java.io.IOException;
import java.nio.file.Path;

public interface FileServiceClient {


    public void fillDataTableView() throws IOException;


    public ObservableList getFileDataListServer();

    public ObservableList getFileDataList();

    public boolean checkFileDataList(ObservableList<FileData> fileDataList, String fileName);

    public FileData findFileDataFromList(ObservableList<FileData> fileDataList, String fileName);

    public boolean deleteFileDataFromList(ObservableList<FileData> fileDataList, String fileName);

    public boolean deleteClientFileData(FileData fileData) throws IOException;

    public FileData findFileData(FileData fileData);

    public FileData getFileData(String fileName) throws IOException;

    public String findNewFileName(String currentFileName);



    public boolean renameFileData (FileData fileData, String newFileName);

}
