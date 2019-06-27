package main.ru.geekbrains.clientside.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author ilnaz-92@yandex.ru
 * Created on 2019-05-20
 */

public class FileData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String currentFileName;
    private String currentFilePath;
    private boolean shared;
    private byte[] content; // can be nullable
    private String mime; //can be nullable
    private String previousFileName; //can be nullable
    private String previousFilePath; //can be nullable
    private int size; // can be nullable

    public FileData(String fileName) {
        this.currentFileName = fileName;
    }

    public FileData() {

    }

    public String getCurrentFileName() {
        return currentFileName;
    }

    public void setCurrentFileName(String fileName) {
        currentFileName = fileName;
    }





    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getPreviousFileName() {
        return previousFileName;
    }

    public void setPreviousFileName(String previousFileName) {
        this.previousFileName = previousFileName;
    }

    public String getPreviousFilePath() {
        return previousFilePath;
    }

    public void setPreviousFilePath(String previousFilePath) {
        this.previousFilePath = previousFilePath;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
