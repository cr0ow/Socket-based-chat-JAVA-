package com.company;

import java.io.*;
import java.net.Socket;

public class Client {
    protected Socket socket;
    protected BufferedReader reader;
    protected PrintWriter writer;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 8080);
        Window window = new Window(client.reader, client.writer);
        window.start();
        Receiver receiver = new Receiver(client.socket, window);
        receiver.start();
    }
}
