package com.ymimsr.task_15;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

    private final int port;
    private final String address;

    public Server(int port, String address) {
        this.port = port;
        this.address = address;
    }

    public static void main(String[] args) {
        // same port as toP in ProxyServer
        int port = Integer.parseInt(args[0]);
        String address = args[1];

        new Server(port, address).start();
    }

    private void start() {
        try {
            Selector selector = Selector.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getByName(address), port);
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(inetSocketAddress);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, serverSocketChannel.validOps());

            while (selector.select() > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();

                while (selectionKeyIterator.hasNext()) {
                    SelectionKey selectionKey = selectionKeyIterator.next();
                    selectionKeyIterator.remove();
                    if (selectionKey.isAcceptable()) {
                        System.out.println("Connection Accepted");
                        SocketChannel proxyServerSocket = serverSocketChannel.accept();
                        proxyServerSocket.configureBlocking(false);
                        proxyServerSocket.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        SocketChannel proxyServerChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer clientMessage = ByteBuffer.allocate(Constants.BUFFER_SIZE);
                        proxyServerChannel.read(clientMessage);
                        clientMessage.flip();

                        System.out.println("Received: " + new String(clientMessage.array()));

                        ByteBuffer serverMessage = ByteBuffer.allocate(Constants.BUFFER_SIZE);
                        serverMessage.put("OK".getBytes());
                        serverMessage.flip();
                        proxyServerChannel.write(serverMessage);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
