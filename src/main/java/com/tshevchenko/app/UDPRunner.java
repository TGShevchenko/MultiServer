package com.tshevchenko.app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that implements the IServerRunner with accepting UDP connections.
 */
public class UDPRunner implements IServerRunner {
    private final Logger logger = Logger.getLogger(UDPRunner.class.getName());

    private final int portNumber;

    //A set of variables that are being used in message exchanging between clients and servers
    protected DatagramPacket receivePacket;
    protected DatagramSocket socket;

    // Used to control termination of the main running server loop
    private volatile boolean isActive = true;

    // An interface variable for a service instance that will process one of the service functions:
    // Echo, Time or Daytime
    private IService service;

    /**
     * Constructor that takes a port number and two interfaces: IServer and IService
     * @param portNumber
     * @param service
     */
    public UDPRunner(int portNumber, IService service) {
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
     * A main processing loop for incoming UDP connections.
     * All the server- specific logic is dedicated to a serviceUDP method
     */
    public boolean processRequests(){
        logger.log(Level.INFO, "UDPRunner: Started processing requests...");
        boolean processingResult = true;
        try {
            socket = new DatagramSocket(new InetSocketAddress(portNumber));
            socket.setSoTimeout(Constants.SOCKET_TIMEOUT_MILLIS);
            receivePacket =
                    new DatagramPacket(new byte[Constants.MAX_RECEIVED_DATA_LENGH],
                            Constants.MAX_RECEIVED_DATA_LENGH);
        }catch (IOException ie) {
            logger.log(Level.SEVERE, ie.toString(), ie);
            return false;
        }
        while(isActive) {
            try {
                socket.receive(receivePacket);
                //We are calling a service function that calculated all the needed data manipulation
                //and returns back a ByteBuffer object
                ByteBuffer receivedData = ByteBuffer.allocate(Constants.MAX_RECEIVED_DATA_LENGH);
                receivedData.get(receivePacket.getData());
                ByteBuffer response = service.processService(receivedData);
                DatagramPacket sendPacket = new DatagramPacket(response.array(), response.limit());
                // We send the packet back to the sender
                socket.send(sendPacket);
            } catch (IOException ie) {
                logger.log(Level.SEVERE, ie.toString(), ie);
                processingResult = false;
            }
        }
        socket.close();
        logger.log(Level.INFO, "UDPRunner: Finished processing requests.");
        return processingResult;
    }
}
