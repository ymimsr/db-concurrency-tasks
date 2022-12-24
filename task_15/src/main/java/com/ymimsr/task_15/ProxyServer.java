package com.ymimsr.task_15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ProxyServer {

    private final int fromP;
    private final int toP;
    private final String address;

    public ProxyServer(int fromP, int toP, String address) {
        this.fromP = fromP;
        this.toP = toP;
        this.address = address;
    }

    public static void main(String[] args) {
        int fromP = Integer.parseInt(args[0]);
        String address = args[2];
        int toP = Integer.parseInt(args[1]);

        new ProxyServer(fromP, toP, address).start();
    }

    public void start() {
        try {
            Selector selector = Selector.open();

            InetSocketAddress toServerSocketAddress = new InetSocketAddress(InetAddress.getByName(address), toP);
            SocketChannel toSocketChannel = SocketChannel.open(toServerSocketAddress);

            InetSocketAddress thisServerSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), fromP);
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(thisServerSocketAddress);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, serverSocketChannel.validOps());

            while (selector.select() > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();

                while (selectionKeyIterator.hasNext()) {
                    SelectionKey selectionKey = selectionKeyIterator.next();
                    selectionKeyIterator.remove();
                    if (selectionKey.isAcceptable()) {
                        System.out.println("Proxy server accepted connection");
                        SocketChannel clientSocket = serverSocketChannel.accept();
                        clientSocket.configureBlocking(false);
                        clientSocket.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        System.out.println("Proxy server got message");
                        SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer clientBuffer = ByteBuffer.allocate(Constants.BUFFER_SIZE);
                        clientChannel.read(clientBuffer);
                        clientBuffer.flip();
                        toSocketChannel.write(clientBuffer);

                        ByteBuffer serverBuffer = ByteBuffer.allocate(Constants.BUFFER_SIZE);
                        toSocketChannel.read(serverBuffer);
                        serverBuffer.flip();
                        clientChannel.write(serverBuffer);
                    }
                }
            }

            selector.close();
            toSocketChannel.close();
            serverSocketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
