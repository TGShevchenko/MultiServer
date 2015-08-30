package com.tshevchenko.app;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 *
 */
public class TCPRunner implements IServerRunner{
    private final int portNumber;

    // The TCP server socket used to receive connection requests
    // private ServerSocket serverSocket = null;

    // A set of variables that are being used in message exchanging between clients and servers
    private ServerSocketChannel serverSocketChannel;
    private SocketChannel socketChannel;

    // Used to control termination of the main running server loop
    private volatile boolean isActive = true;

    // An interface variable for a service instance that will process one of the service functions:
    // Echo, Time or Daytime
    private IService service;

    public TCPRunner(int portNumber){
        this.portNumber = portNumber;
        System.out.println("TCPRunner::TCPRunner. port=" + portNumber);
    }

    /**
     *
     * @param service
     */
    public void setService(IService service){
        this.service = service;
        System.out.println("TCPRunner::setService. service=" + service.getClass());
    }

    /**
     * Method updates a running state, which is used to interrupt
     * the main server processingloop
     * @param state
     */
    @Override
    public void updateRunningState(boolean state) {
        isActive = state;
        try{
            serverSocketChannel.close();
        } catch(Throwable e){
        }
        System.out.println("TCPRunner::updateRunningState. state=" + state);
    }

    /**
     * A main processing loop for incoming TCP connections.
     * All the server- specific logic is dedicated to a serviceTCP method
     */
    @Override
    public void processRequests(){
        System.out.println("TCPRunner::processRequests. START");
        try{
            // Create a channel to listen for connections on.
            serverSocketChannel = ServerSocketChannel.open();
            // Bind the channel to a local port.
            serverSocketChannel.socket().bind(new InetSocketAddress(portNumber));
        }catch(Throwable e) {
        }

        // Loop until an isActive is false, processing client connections
        while(isActive) {
            try {
                // Wait for a client to connect
                socketChannel = serverSocketChannel.accept();
                //We are calling a service function that calculates all the needed data manipulation
                //and returns back a ByteBuffer object
                ByteBuffer receivedData = ByteBuffer.allocate(Constants.MAX_RECEIVED_DATA_LENGH);
                socketChannel.read(receivedData);
                ByteBuffer response = service.runService(receivedData);
                // Send the response to the client and disconnect.
                socketChannel.write(response);
                socketChannel.close();
            } catch (Throwable e) {
            }
        }
        System.out.println("TCPRunner::processRequests. FINISH");
    }
}
