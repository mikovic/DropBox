package main.ru.geekbrains.clientside.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FolderData implements Serializable
{
    private String name;
    private FolderData parentFolder;
    private List<FileData> files = new ArrayList<>();
    private List<FolderData> folders = new ArrayList<>();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public FolderData getParentFolder()
    {
        return parentFolder;
    }

    public void setParentFolder(FolderData parentFolder)
    {
        this.parentFolder = parentFolder;
    }

    public List<FileData> getFiles()
    {
        return files;
    }

    public void setFiles(List<FileData> files)
    {
        this.files = files;
    }

    public List<FolderData> getFolders()
    {
        return folders;
    }

    public void setFolders(List<FolderData> folders)
    {
        this.folders = folders;
    }
}

