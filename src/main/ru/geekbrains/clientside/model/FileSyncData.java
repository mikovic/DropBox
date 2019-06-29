package main.ru.geekbrains.clientside.model;

import java.io.Serializable;

public class FileSyncData implements Serializable
{private static final long serialVersionUID = 2L;
    private FileData fileData;
    private RequestType requestType;
    private String ownerId;
    private String newFileName;

    public FileData getFileData()
    {
        return fileData;
    }

    public void setFileData(FileData fileData)
    {
        this.fileData = fileData;
    }

    public RequestType getRequestType()
    {
        return requestType;
    }

    public void setRequestType(RequestType requestType)
    {
        this.requestType = requestType;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }
}

