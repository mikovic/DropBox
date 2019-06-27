package main.ru.geekbrains.clientside.model;

import java.io.Serializable;

public class ResponseData implements Serializable
{
    private static final long serialVersionUID = 5L;
    private FileData fileData; // can be null
    private FolderData folderData; // can be null
    private ResponseStatusType responseStatus;
    private String message; //can be null
    private String currentFileName;

    public FileData getFileData()
    {
        return fileData;
    }

    public void setFileData(FileData fileData)
    {
        this.fileData = fileData;
    }

    public FolderData getFolderData()
    {
        return folderData;
    }

    public void setFolderData(FolderData folderData)
    {
        this.folderData = folderData;
    }

    public ResponseStatusType getResponseStatus()
    {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatusType responseStatus)
    {
        this.responseStatus = responseStatus;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setCurrentFileName(String currentFileName) {
        this.currentFileName = currentFileName;
    }

    public String getCurrentFileName() {
        return currentFileName;
    }
}

