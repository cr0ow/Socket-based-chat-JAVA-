package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Receiver extends Thread {
    private final BufferedReader reader;
    private final Window window;

    public Receiver(Socket socket, Window window) throws IOException {
        reader =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.window = window;
    }

    public void run() {
        String message;
        try {
            while((message = reader.readLine()) != null) {
                System.out.println(message);
                if(window.getMessages().getText().equals(""))
                    window.getMessages().setText(window.getMessages().getText() + message);
                else
                    window.getMessages().setText(window.getMessages().getText() + "\n" + message);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
