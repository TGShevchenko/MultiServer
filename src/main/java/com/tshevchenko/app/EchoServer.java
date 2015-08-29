package com.tshevchenko.app;

import java.net.DatagramPacket;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;

/**
 * Class implements an echo functionality (RFC 862)
 */
public class EchoServer extends Server implements IServerObserver, IService {
    public EchoServer(IServerRunner serverRunner){
        super(serverRunner);
        serverRunner.setService(this);
        System.out.println("EchoServer::EchoServer");
    }

    @Override
    public void updateRunningState(boolean state){
        serverRunner.updateRunningState(state);
        System.out.println("EchoServer::updateRunningState");
    }

    /**
     * Method makes the Daytime processing
     * @param receivedData
     * @return
     */
    @Override
    public ByteBuffer runService(ByteBuffer receivedData) {
        System.out.println("EchoServer::runService");
        return receivedData;
    }
}
