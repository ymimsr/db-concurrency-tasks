package com.ymimsr.task_15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final int fromP;
    private final int toP;
    private final String address;

    public Server(int fromP, int toP, String address) {
        this.fromP = fromP;
        this.toP = toP;
        this.address = address;
    }

    public void start() {
        try (
                ServerSocket serverSocket = new ServerSocket(fromP);
                Socket fromSocket = serverSocket.accept();
                Socket toSocket = new Socket(address, toP);
                BufferedReader in = new BufferedReader(new InputStreamReader(fromSocket.getInputStream()));
                PrintWriter out = new PrintWriter(toSocket.getOutputStream())
                ) {
            while (fromSocket.isConnected() && toSocket.isConnected()) {
                out.println(in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
