package com.tshevchenko.app;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class implements an echo functionality (RFC 862)
 */
public class EchoServer extends Server implements IService {
    private final Logger logger = Logger.getLogger(EchoServer.class.getName());

    public EchoServer(IServerRunner serverRunner){
        super(serverRunner);
        serverRunner.setService(this);
    }

    /**
     * Method makes the Daytime processing
     * @param receivedData
     * @return
     */
    public ByteBuffer processService(ByteBuffer receivedData) {
        logger.log(Level.INFO, "EchoServer starts to process...");
        return receivedData;
    }
}
