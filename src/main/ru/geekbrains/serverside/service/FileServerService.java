package main.ru.geekbrains.serverside.service;

import main.ru.geekbrains.clientside.model.FileData;
import main.ru.geekbrains.clientside.model.RequestData;

import java.io.IOException;

public interface FileServerService {
    public void add(FileData fileData);
    public boolean delete(FileData fileData);
    public boolean download(FileData fileData);
    public void fillDataTableView() throws IOException;
    public void sendListFilesServer();
    public boolean rename(RequestData requestData);
}
