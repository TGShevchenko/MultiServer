package com.tshevchenko.app;

import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/**
 *
 */
public class UDPRunner implements IServerRunner{
    private final int portNumber;

    //A set of variables that are being used in message exchanging between clients and servers
    protected DatagramPacket receivePacket;
    protected DatagramSocket socket;

    // Used to control termination of the main running server loop
    private volatile boolean isActive = true;

    IService service;

    public UDPRunner(int portNumber){
        this.portNumber = portNumber;
        System.out.println("UDPRunner::UDPRunner. port=" + portNumber);
    }

    /**
     *
     * @param service
     */
    public void setService(IService service){
        this.service = service;
        System.out.println("UDPRunner::setService. service=" + service.getClass());
    }
    /**
     * Method updates a running state, which is used to interrupt
     * the main server processingloop
     * @param state
     */
    @Override
    public void updateRunningState(boolean state) {
        isActive = state;
    }

    /**
     * A main processing loop for incoming UDP connections.
     * All the server- specific logic is dedicated to a serviceUDP method
     */
    public void processRequests(){
        System.out.println("UDPRunner::processRequests. START");
        try {
            socket = new DatagramSocket(new InetSocketAddress(portNumber));
            socket.setSoTimeout(Constants.SOCKET_TIMEOUT_MILLIS);
            System.out.println("Server bound to port " + socket.getLocalPort());
            receivePacket =
                    new DatagramPacket(new byte[Constants.MAX_RECEIVED_DATA_LENGH],
                            Constants.MAX_RECEIVED_DATA_LENGH);
        }catch (Throwable e) {
            System.out.println("Exception in processRequests: " + e);
        }
        while(isActive) {
            try {
                socket.receive(receivePacket);
                //We are calling a service function that calculated all the needed data manipulation
                //and returns back a ByteBuffer object
                ByteBuffer receivedData = ByteBuffer.allocate(Constants.MAX_RECEIVED_DATA_LENGH);
                receivedData.get(receivePacket.getData());
                ByteBuffer response = service.runService(receivedData);
                DatagramPacket sendPacket = new DatagramPacket(response.array(), response.limit());
                // We send the packet back to the sender
                socket.send(sendPacket);
            } catch (InterruptedIOException e) {
            } catch (Throwable e) {
            }
        }
        try {
            socket.close();
        } catch (Throwable e) {
        }
        System.out.println("UDPRunner::processRequests. FINISH");
    }

}