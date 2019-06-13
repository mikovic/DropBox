package main.ru.geekbrains.serverside.service;

import main.ru.geekbrains.clientside.model.FileData;
import main.ru.geekbrains.clientside.model.FileSyncData;
import main.ru.geekbrains.clientside.model.RequestType;
import main.ru.geekbrains.clientside.model.ResponseData;
import main.ru.geekbrains.utilits.ResponseUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileServerServiceImpl implements FileServerService {
    private final String DIR_SERVER = "sourcefilesonserver";
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private BufferedOutputStream buffStream;
    private ResponseData successResponse;

    public FileServerServiceImpl(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    public void add(FileData fileData) {
        byte[] content = fileData.getContent();
        String filePathServer = getPathFile(fileData);

        try {
            buffStream = new BufferedOutputStream(
                    new FileOutputStream(filePathServer));
            buffStream.write(content);
            buffStream.close();
            successResponse = ResponseUtil.createSuccessResponse(fileData.getCurrentFileName() + " загрузился");

            objectOutputStream.writeObject(successResponse);
            objectOutputStream.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean delete(FileData fileData) {
        boolean flag = false;
        String filePathServer = getPathFile(fileData);
        File filePath = new File(filePathServer);
        if (filePath.exists()) {
            filePath.delete();
            successResponse = ResponseUtil.createErrorResponse(fileData.getCurrentFileName() + " удалили");
            try {
                objectOutputStream.writeObject(successResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean download(FileData fileData) {
        boolean flag = false;

        String filePathServer = getPathFile(fileData);
        File filePath = new File(filePathServer);
        if (filePath.exists()) {

            byte[] content = new byte[0];
            try {
                content = Files.readAllBytes(Paths.get(filePathServer));
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileData file = new FileData();
            file.setContent(content);
            file.setCurrentFileName(filePath.getName());
            FileSyncData fileSyncData = new FileSyncData();
            fileSyncData.setFileData(file);
            fileSyncData.setRequestType(RequestType.DOWNLOAD);
            try {
                objectOutputStream.writeObject(fileSyncData);
            } catch (IOException e) {
                e.printStackTrace();
            }


            flag = true;
        }
        return flag;
    }

    private String getPathFile(FileData fileData) {
        Path sourcePath = Paths.get(DIR_SERVER);
        String fileName = fileData.getCurrentFileName();
        return sourcePath.toAbsolutePath().toString()+ File.separator + fileName;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }
}
