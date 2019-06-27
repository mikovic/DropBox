package main.ru.geekbrains.clientside;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import main.ru.geekbrains.clientside.controllers.MainController;
import main.ru.geekbrains.clientside.model.*;
import main.ru.geekbrains.clientside.service.ConnectService;
import main.ru.geekbrains.clientside.service.FileServiceClient;
import main.ru.geekbrains.clientside.service.FileServiceClientImpl;

import java.io.*;
import java.lang.management.PlatformLoggingMXBean;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class Connect implements Closeable, ConnectService {
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;
    private Socket socket;
    private final String DIR = "sourcefilesonclient";

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private BufferedOutputStream buffStream;
    private MainController mainController;
    private FileServiceClient fileService;
    Thread thread;

    public Connect(FileServiceClient fileService, MainController mainController) {

        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while (true) {
                        Object object = in.readObject();
                        if (object instanceof ResponseData) {
                            ResponseData responseData = (ResponseData) object;
                            if (responseData.getResponseStatus().equals(ResponseStatusType.SUCCESSADDFILE)) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        String currentFileName = responseData.getCurrentFileName();
                                        ObservableList<FileData> fileDataListServer = fileService.getFileDataListServer();
                                        FileData fileData = fileService.findFileDataFromList(fileDataListServer, currentFileName);
                                        if (fileData != null) {
                                            fileService.getFileDataListServer().remove(fileData);

                                        }
                                        try {
                                            fileData = fileService.getFileData(currentFileName);
                                            fileData.setShared(true);
                                            fileService.getFileDataListServer().add(fileData);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        setlabelText(responseData.getMessage());
                                        setFieldText1("");
                                    }
                                });


                            }
                            if (responseData.getResponseStatus().equals(ResponseStatusType.SUCCESSDELETEFILE)) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        String currentFileName = responseData.getCurrentFileName();
                                        ObservableList<FileData> fileDataListServer = fileService.getFileDataListServer();
                                        FileData fileData = fileService.findFileDataFromList(fileDataListServer, currentFileName);
                                        fileService.getFileDataListServer().remove(fileData);
                                        fileService.deleteFileDataFromList(fileDataListServer, currentFileName);
                                        setlabelText(responseData.getMessage());
                                        setFieldText2("");
                                    }
                                });
                            }
                        }
                        if (object instanceof FileSyncData) {
                            FileSyncData fileSyncData = (FileSyncData) object;
                            if (fileSyncData.getRequestType().equals(RequestType.DOWNLOAD)) {


                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        FileData fileData = fileSyncData.getFileData();
                                        String fileName = fileData.getCurrentFileName();
                                        String newFileName = fileService.findNewFileName(fileName);
                                        if (!fileName.equals(newFileName)) {
                                            fileData.setPreviousFileName(fileName);
                                            fileData.setCurrentFileName(newFileName);
                                            fileService.getFileDataList().add(fileData);

                                        }

                                        byte[] content = fileData.getContent();
                                        String filePathClient = getPathClient(fileData);
                                        try {
                                            buffStream = new BufferedOutputStream(
                                                    new FileOutputStream(filePathClient));
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            buffStream.write(content);
                                            buffStream.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        setlabelText(fileData.getCurrentFileName() + " WAS UPLOADED SUCCESSFULLY");
                                        setFieldText2("");


                                    }
                                });


                            }

                        } else if (object instanceof Hashtable) {
                            Hashtable<String, FileData> fileDataListServer = (Hashtable<String, FileData>) object;
                            Set<Map.Entry<String, FileData>> set = fileDataListServer.entrySet();
                            for (Map.Entry<String, FileData> me : set) {
                                fileService.getFileDataListServer().add(me.getValue());
                            }
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    getMainController().getTable2().setItems(fileService.getFileDataListServer());
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        this.fileService = fileService;
        this.mainController = mainController;
    }

    private String getPathClient(FileData fileData) {
        String fileName = fileData.getCurrentFileName();
        return DIR + File.separator + fileName;
    }

    private void send(FileData fileData) {

    }

    private void delete(FileData fileData) {

    }

    private void deleteFileServer(FileData fileData) {

    }

    private void download(FileData fileData) {

    }

    @Override
    public void close() throws IOException {

        socket.close();
    }

    @Override
    public void share(FileData selectFileData) throws IOException {
        FileSyncData fileSyncData = new FileSyncData();
        fileSyncData.setFileData(selectFileData);
        fileSyncData.setRequestType(RequestType.ADD);
        out.writeObject(fileSyncData);
        out.flush();

    }

    @Override
    public void setlabelText(String text) {
        getMainController().getLabel().setText(text);
    }

    @Override
    public void setFieldText1(String text) {
        getMainController().getTextField1().setText(text);
    }

    @Override
    public void setFieldText2(String text) {
        getMainController().getTextField2().setText(text);
    }

    @Override
    public void getListFileFromServer() {

    }

    @Override
    public boolean deleteClientFileData(FileData fileData) throws IOException {
        boolean flag = false;
        if (fileService.deleteClientFileData(fileData)) {

            setlabelText("FILE " + fileData.getCurrentFileName() + " was deleted");
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean deleteServerFileData(FileData fileData) throws IOException {
        FileSyncData fileSyncData = new FileSyncData();
        fileSyncData.setFileData(fileData);
        fileSyncData.setRequestType(RequestType.DELETE);
        out.writeObject(fileSyncData);
        out.flush();
        return true;
    }

    @Override
    public boolean downLoadFile(FileData fileData) throws IOException {
        FileSyncData fileSyncData = new FileSyncData();
        fileSyncData.setFileData(fileData);
        fileSyncData.setRequestType(RequestType.DOWNLOAD);
        out.writeObject(fileSyncData);
        out.flush();
        return true;
    }


    public MainController getMainController() {
        return mainController;
    }

    public void setFileService(FileServiceClientImpl fileService) {
        this.fileService = fileService;
    }

}
