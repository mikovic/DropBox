package main.ru.geekbrains.clientside.service;

import main.ru.geekbrains.clientside.model.FileData;

import java.io.IOException;

public interface ConnectService {
    public void share(FileData selectFileData) throws IOException;
    public void setlabelText(String text);
    public void setFieldText1(String text);
    public void setFieldText2(String text);
    public void getListFileFromServer();
    public boolean deleteClientFileData(FileData fileData) throws IOException;
    public boolean deleteServerFileData(FileData fileData) throws IOException;
    public boolean downLoadFile(FileData fileData) throws IOException;

}
