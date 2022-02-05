package com.company;

import java.io.*;
import java.net.Socket;

public class ClientService extends Thread {
    protected final Socket socket;
    private final Server server;
    BufferedReader reader;
    PrintWriter writer;

    public ClientService(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            String message;
            while((message = reader.readLine()) != null) {
                System.out.println(message);
                for(ClientService user : server.clients)
                    user.writer.println(message);
            }
            socket.close();
            reader.close();
            writer.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
