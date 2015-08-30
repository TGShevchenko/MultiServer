package com.tshevchenko.app;

import java.nio.ByteBuffer;

/**
 * Class implements an echo functionality (RFC 862)
 */
public class EchoServer extends Server implements IService {
    public EchoServer(IServerRunner serverRunner){
        super(serverRunner);
        serverRunner.setService(this);
        System.out.println("EchoServer::EchoServer");
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
