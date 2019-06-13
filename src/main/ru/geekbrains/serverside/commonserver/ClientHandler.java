package main.ru.geekbrains.serverside.commonserver;

import main.ru.geekbrains.clientside.model.*;
import main.ru.geekbrains.serverside.service.FileServerServiceImpl;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private ClientServer server;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private final String directory = "/sourcefilesonserver";
    private FileServerServiceImpl fileService;


    public ClientHandler(Socket socket) {
        try {

            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            fileService = new FileServerServiceImpl(objectInputStream, objectOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object object = objectInputStream.readObject();

                if (object instanceof FileSyncData) {
                    FileSyncData fileSyncData = (FileSyncData) object;

                    FileData fileData = fileSyncData.getFileData();
                    String ownerId = fileSyncData.getOwnerId();
                    if (fileSyncData.getRequestType() == RequestType.ADD) {
                        fileService.add(fileData);
                    }

                    if (fileSyncData.getRequestType() == RequestType.DELETE) {
                        fileService.delete(fileData);

                    }
                    if (fileSyncData.getRequestType() == RequestType.DOWNLOAD) {
                        fileService.download(fileData);

                    }
                } else if (object instanceof FolderSyncData) {
                    FolderSyncData folderSyncData = (FolderSyncData) object;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

