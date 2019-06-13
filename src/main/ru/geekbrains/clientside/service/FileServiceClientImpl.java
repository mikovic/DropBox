package main.ru.geekbrains.clientside.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.ru.geekbrains.clientside.Connect;
import main.ru.geekbrains.clientside.model.FileData;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileServiceClientImpl implements FileServiceClient {

    Path sourcePath = Paths.get("sourcefilesonclient");

    ObservableList<FileData> fileDataList = FXCollections.observableArrayList();



    public ObservableList<FileData> getFileDataList() {
        return fileDataList;
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


}




