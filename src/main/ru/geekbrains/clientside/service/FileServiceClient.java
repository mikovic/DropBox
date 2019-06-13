package main.ru.geekbrains.clientside.service;

import javafx.collections.ObservableList;
import main.ru.geekbrains.clientside.model.FileData;

import java.io.IOException;
import java.nio.file.Path;

public interface FileServiceClient {

    public void fillDataTableView() throws IOException;


}
