package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    protected Set<ClientService> clients = new HashSet<>();
    private ServerSocket server = null;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8080);
        try {
            while(true) {
                Socket client = server.server.accept();
                ClientService newClient = new ClientService(client, server);
                server.clients.add(newClient);
                newClient.start();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            for(ClientService cs : server.clients) {
                try {
                    cs.socket.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
