package main.ru.geekbrains.serverside.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.ru.geekbrains.clientside.model.FileData;
import main.ru.geekbrains.clientside.model.FileSyncData;
import main.ru.geekbrains.clientside.model.RequestType;
import main.ru.geekbrains.clientside.model.ResponseData;
import main.ru.geekbrains.clientside.service.FileServiceClientImpl;
import main.ru.geekbrains.utilits.ResponseUtil;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Hashtable;

public class FileServerServiceImpl implements FileServerService {
    private final String DIR_SERVER = "sourcefilesonserver";
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private BufferedOutputStream buffStream;
    private ResponseData successResponse;
    Hashtable<String, FileData> fileDataListServer;
    Path sourcePath = Paths.get(DIR_SERVER);

    public FileServerServiceImpl(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        fileDataListServer = new Hashtable();
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
            successResponse = ResponseUtil.createSuccessAddFileResponse(fileData.getCurrentFileName() + " загрузился");
            successResponse.setCurrentFileName(fileData.getCurrentFileName());
            successResponse.setCurrentFilePath(fileData.getCurrentFilePath());
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
            successResponse = ResponseUtil.createSuccessDeleteFileResponse(fileData.getCurrentFileName() + " удалили");
            successResponse.setCurrentFileName(fileData.getCurrentFileName());
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

    @Override
    public void fillDataTableView() throws IOException {
        fullWalkFileTree(sourcePath);
    }

    @Override
    public void sendListFilesServer() {

    }


    private void fullWalkFileTree(Path sourcePath) throws IOException {
        Files.walkFileTree(sourcePath, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("This is visit file : " + file);
                FileData fileData = new FileData(file.getFileName().toString());
                fileData.setCurrentFilePath(file.toAbsolutePath().toString());
                byte[] array = Files.readAllBytes(Paths.get(file.toAbsolutePath().toString()));
                fileData.setContent(array);
                fileData.setShared(true);
                FileServerServiceImpl.this.fileDataListServer.put(fileData.getCurrentFileName(), fileData);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.println("This is visit file failed : " + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("This is post visit directory : " + dir);
                return FileVisitResult.CONTINUE;
            }
        });


    }

    private String getPathFile(FileData fileData) {
        Path sourcePath = Paths.get(DIR_SERVER);
        String fileName = fileData.getCurrentFileName();
        return sourcePath.toAbsolutePath().toString() + File.separator + fileName;
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

    public Hashtable<String, FileData> getFileDataListServer() {
        return fileDataListServer;
    }
}
