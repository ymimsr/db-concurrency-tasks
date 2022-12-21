package com.ymimsr.task_15;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClient {

    public static void main(String[] args) {
        String HOST = "127.0.0.1";
        int fromPort = 322;
        int toPort = 8080;

        try (
                Socket socket = new Socket(HOST, fromPort);
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream())
        )
        {
            printWriter.println("12312312312312312312312");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
