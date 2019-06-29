package main.ru.geekbrains.utilits;

import main.ru.geekbrains.clientside.model.FileData;
import main.ru.geekbrains.clientside.model.RequestData;
import main.ru.geekbrains.clientside.model.RequestType;

public class RequestUtil {

    public static RequestData createRequestRenameFile(FileData fileData, String newFileName){
        RequestData requestData = new RequestData();
        requestData.setRequestType(RequestType.RENAME);
        requestData.setCurrentFileName(fileData.getCurrentFileName());
        requestData.setCurrentFilePathServer(fileData.getCurrentFilePathServer());
        requestData.setNewFileName(newFileName);
        return requestData;
    }
    public static RequestData createRequestDeleteFile(FileData fileData){
        RequestData requestData = new RequestData();
        requestData.setRequestType(RequestType.DELETE);
        requestData.setCurrentFileName(fileData.getCurrentFileName());
        requestData.setCurrentFilePathServer(fileData.getCurrentFilePathServer());
        return requestData;
    }

}
