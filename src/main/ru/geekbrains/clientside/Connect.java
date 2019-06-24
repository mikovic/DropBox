package main.ru.geekbrains.clientside;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import main.ru.geekbrains.clientside.controllers.MainController;
import main.ru.geekbrains.clientside.model.*;
import main.ru.geekbrains.clientside.service.ConnectService;
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
    private FileServiceClientImpl fileService;
    Thread thread;

    public Connect(MainController mainController) {

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
                            if (responseData.getResponseStatus().equals(ResponseStatusType.SUCCESS)) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        setlabelText(responseData.getMessage());
                                        setFieldText1("");
                                    }
                                });


                            }
                            if (responseData.getResponseStatus().equals(ResponseStatusType.ERROR)) {

                            }
                        }
                        if (object instanceof FileSyncData) {
                            FileSyncData fileSyncData = (FileSyncData) object;
                            if (fileSyncData.getRequestType().equals(RequestType.DOWNLOAD)) {
                                FileData fileData = fileSyncData.getFileData();
                                byte[] content = fileData.getContent();
                                String filePathClient = getPathClient(fileData);
                                buffStream = new BufferedOutputStream(
                                        new FileOutputStream(filePathClient));
                                buffStream.write(content);
                                buffStream.close();

                            }

                        } else if (object instanceof Hashtable) {
                            Hashtable<String, FileData> fileDataListServer = (Hashtable<String, FileData>) object;
                            Set<Map.Entry<String, FileData>> set = fileDataListServer.entrySet();
                           for (Map.Entry<String, FileData> me : set){
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
                                    mainController.getTable2().setItems(fileService.getFileDataListServer());
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


    public MainController getMainController() {
        return mainController;
    }

    public void setFileService(FileServiceClientImpl fileService) {
        this.fileService = fileService;
    }
}
