package com.ymimsr.task_15;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {

    private final int port;

    public Client(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        new Client(port).start();
    }

    private void start() {
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
            SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);

            Scanner scanner = new Scanner(System.in);
            String nextLine;
            while (!(nextLine = scanner.next()).equals("")) {
                ByteBuffer clientMessage = ByteBuffer.allocate(Constants.BUFFER_SIZE);
                clientMessage.put(nextLine.getBytes());
                clientMessage.flip();
                socketChannel.write(clientMessage);

                ByteBuffer serverMessage = ByteBuffer.allocate(Constants.BUFFER_SIZE);
                socketChannel.read(serverMessage);
                serverMessage.flip();
                System.out.println("Server returned message: " + (new String(serverMessage.array())));
            }

            socketChannel.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}