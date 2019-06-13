package main.ru.geekbrains.serverside.service;

import main.ru.geekbrains.clientside.model.FileData;

public interface FileServerService {
    public void add(FileData fileData);
    public boolean delete(FileData fileData);
    public boolean download(FileData fileData);
}
