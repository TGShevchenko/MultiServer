package com.tshevchenko.app;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class implements an echo functionality (RFC 862)
 */
public class EchoService implements IService {
    private final Logger logger = Logger.getLogger(EchoService.class.getName());

    /**
     * Method makes the Daytime processing
     * @param receivedData
     * @return
     */
    public ByteBuffer processService(ByteBuffer receivedData) {
        logger.log(Level.INFO, "EchoService starts to process...");
        return receivedData;
    }
}
