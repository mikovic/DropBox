package main.ru.geekbrains.clientside.model;

import java.io.Serializable;

public class FolderSyncData implements Serializable
{
    private FolderData rootFolder;
    private String ownerId;
    private RequestType requestType;

    public FolderData getRootFolder()
    {
        return rootFolder;
    }

    public void setRootFolder(FolderData rootFolder)
    {
        this.rootFolder = rootFolder;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }

    public RequestType getRequestType()
    {
        return requestType;
    }

    public void setRequestType(RequestType requestType)
    {
        this.requestType = requestType;
    }
}
