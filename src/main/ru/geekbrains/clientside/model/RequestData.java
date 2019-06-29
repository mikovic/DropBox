package main.ru.geekbrains.clientside.model;

import java.io.Serializable;

public class RequestData implements Serializable {
    private static final long serialVersionUID = 7L;
    private String message; //can be null
    private String currentFileName;
    private String newFileName;
    private String currentFilePathServer;
    private RequestType requestType;

    public String getCurrentFileName() {
        return currentFileName;
    }

    public void setCurrentFileName(String currentFileName) {
        this.currentFileName = currentFileName;
    }

    public String getCurrentFilePathServer() {
        return currentFilePathServer;
    }

    public void setCurrentFilePathServer(String currentFilePathServer) {
        this.currentFilePathServer = currentFilePathServer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
}
