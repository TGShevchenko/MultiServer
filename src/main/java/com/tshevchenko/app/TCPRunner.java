package com.tshevchenko.app;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that implements the IServerRunner with accepting TCP connections.
 */
public class TCPRunner implements IServerRunner {
    private final Logger logger = Logger.getLogger(TCPRunner.class.getName());

    private final int portNumber;

    // A set of variables that are being used in message exchanging between clients and servers
    private ServerSocketChannel serverSocketChannel;
    private SocketChannel socketChannel;

    // Used to control termination of the main server loop
    private volatile boolean isActive = true;

    // An interface variable for a service instance that will process one of the service functions:
    // Echo, Time or Daytime
    private IService service;

    /**
     * Constructor that takes a port number and two interfaces: IServer and IService
     * @param portNumber
     * @param service
     */
    public TCPRunner(int portNumber, IService service) {
        this.portNumber = portNumber;
        this.service = service;
    }

    /**
     * Method updates a running state, which is used to interrupt
     * the main server processing loop
     * @param state
     */
    public void updateRunningState(boolean state) {
        isActive = state;
    }

    public boolean getRunningState() { return isActive; }

    /**
     * A main processing loop for incoming TCP connections.
     * All the server- specific logic is dedicated to a serviceTCP method
     */
    public boolean processRequests(){
        logger.log(Level.INFO, "TCPRunner: Start processing requests...");
        boolean processingResult = true;
        try{
            // Create a channel to listen for connections on.
            serverSocketChannel = ServerSocketChannel.open();
            // Bind the channel to a local port.
            serverSocketChannel.socket().bind(new InetSocketAddress(portNumber));
        }catch(IOException ie) {
            logger.log(Level.SEVERE, ie.toString(), ie);
            return false;
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
                ByteBuffer response = service.processService(receivedData);
                // Send the response to the client and disconnect.
                socketChannel.write(response);
                socketChannel.close();
            } catch (IOException ie) {
                logger.log(Level.SEVERE, ie.toString(), ie);
                processingResult = false;
            }
        }
        try{
            serverSocketChannel.close();
        } catch(IOException ie){
            logger.log(Level.SEVERE, ie.toString(), ie);
            processingResult = false;
        }
        logger.log(Level.INFO, "TCPRunner: Finished processing requests...");
        return processingResult;
    }

}
