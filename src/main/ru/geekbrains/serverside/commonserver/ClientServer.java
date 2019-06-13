package main.ru.geekbrains.serverside.commonserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientServer
{
    public static final int PORT = 8189;

    public static void main(String[] args) {
        ClientServer clientServer = new ClientServer();
        clientServer.start(PORT); }

    private void start(int port)
    {
        Socket socket =  null;
        ServerSocket serverSocket = null;
        try
        {
            serverSocket = new ServerSocket(PORT);
            while (true)
            {
                socket = serverSocket.accept();
                System.out.println("New connection!");
                ClientHandler client = new ClientHandler(socket);
                client.start();
                client.sendListFilesServer();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
                serverSocket.close();
                System.out.println("Server finished");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }


}
