package main.ru.geekbrains.clientside.service;

import main.ru.geekbrains.clientside.model.FileData;

import java.io.IOException;

public interface ConnectService {
    void share(FileData selectFileData) throws IOException;
    void setlabelText(String text);
    void setFieldText1(String text);
    void setFieldText2(String text);
    void getListFileFromServer();
}
