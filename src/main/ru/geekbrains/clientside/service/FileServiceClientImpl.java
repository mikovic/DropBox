package main.ru.geekbrains.clientside.service;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.ru.geekbrains.clientside.Connect;
import main.ru.geekbrains.clientside.controllers.MainController;
import main.ru.geekbrains.clientside.model.FileData;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileServiceClientImpl implements FileServiceClient {

    Path sourcePath = Paths.get("sourcefilesonclient");

    ObservableList<FileData> fileDataList = FXCollections.observableArrayList();
    ObservableList<FileData> fileDataListServer = FXCollections.observableArrayList();

    public FileServiceClientImpl() {

    }


    public ObservableList<FileData> getFileDataList() {
        return fileDataList;
    }

    @Override
    public boolean checkFileDataList(ObservableList<FileData> fileDataList, String fileName) {
        boolean flag = false;
        for (FileData fileData : fileDataList) {
            if (fileData.getCurrentFileName().equals(fileName)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public String findNewFileName(String currentFileName) {
        String newFileName = currentFileName;
        String[] words = currentFileName.split("\\.");
        int i = 1;
        while (checkFileDataList(fileDataList, newFileName)) {
            newFileName = words[0] + "(" + i + ")" + "." + words[1];
            i++;
        }

        return newFileName;
    }



    @Override
    public boolean renameFileData(FileData fileData, String newFileName) {

        boolean flag = false;
        String oldFileName = fileData.getCurrentFileName();
        String path = fileData.getCurrentFilePath();
        String[] oldWords = oldFileName.split("\\.");
        String newNameFile = newFileName + "." + oldWords[1];
        String newPath = path.replace(oldFileName, newNameFile);
        File file = new File(path);
        File newFile = new File(newPath);
        file.renameTo(newFile);
        FileData newFileData = new FileData();
        FileData searchFileData = findFileDataFromList(fileDataList, oldFileName);
        newFileData.setCurrentFileName(newNameFile);
        newFileData.setPreviousFileName(oldFileName);
        newFileData.setCurrentFilePath(newPath);
        newFileData.setShared(searchFileData.isShared());
        newFileData.setContent(searchFileData.getContent());
        newFileData.setPreviousFilePath(path);
        newFileData.setCurrentFilePath(newPath);
        fileDataList.remove(searchFileData);
        fileDataList.add(newFileData);
        return true;
    }



    @Override
    public FileData findFileDataFromList(ObservableList<FileData> fileDataList, String fileName) {
        FileData searchFileData = null;
        for (FileData fileData : fileDataList) {
            if (fileData.getCurrentFileName().equals(fileName)) {
                searchFileData = fileData;
            }
        }
        return searchFileData;
    }

    @Override
    public boolean deleteFileDataFromList(ObservableList<FileData> fileDataList, String fileName) {
        boolean flag = false;


        FileData searchFileData = findFileDataFromList(fileDataList, fileName);
        if (searchFileData != null) {
            fileDataList.remove(searchFileData);
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean deleteClientFileData(FileData fileData) throws IOException {
        final boolean[] flag = {false};
        String currentFileName = fileData.getCurrentFileName();
        Files.walkFileTree(sourcePath, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String filePath = file.toAbsolutePath().toString();
                if (filePath.endsWith(currentFileName)) {
                    Files.delete(file);
                    deleteFileDataFromList(fileDataList, currentFileName);
                    flag[0] = true;
                    return FileVisitResult.TERMINATE;
                }

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


        return flag[0];
    }

    @Override
    public FileData findFileData(FileData fileData) {
        return null;
    }

    @Override
    public void fillDataTableView() throws IOException {

        fullWalkFileTree(sourcePath);
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
                FileServiceClientImpl.this.fileDataList.add(fileData);
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


    @Override
    public FileData getFileData(String fileName) throws IOException {

        final FileData[] fileData = {null};
        Files.walkFileTree(sourcePath, new FileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String filePath = file.toAbsolutePath().toString();
                if (filePath.endsWith(fileName)) {
                    System.out.println("We find the necessary file : " + file.toAbsolutePath());
                    fileData[0] = new FileData(fileName);
                    fileData[0].setCurrentFilePath(filePath);
                    byte[] array = Files.readAllBytes(Paths.get(filePath));
                    fileData[0].setContent(array);
                    return FileVisitResult.TERMINATE;
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }


        });
        return fileData[0];
    }


    public ObservableList<FileData> getFileDataListServer() {
        return fileDataListServer;
    }

}




