package com.tshevchenko.app;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class implements a server Daytime functionality (RFC 867)
 */
public class DayTimeService implements IService {
    private final Logger logger = Logger.getLogger(DayTimeService.class.getName());

    // Get an encoder for converting strings to bytes
    protected final CharsetEncoder encoder = Charset.forName("US-ASCII").newEncoder();

    /**
     * Method makes the Daytime processing
     * @param receivedData
     * @return
     */
    public ByteBuffer processService(ByteBuffer receivedData) {
        logger.log(Level.INFO, "DayTimeService starts to process...");
        ByteBuffer response = null;
        try {
            // Builds a response string, wrap, and encode to bytes
            String date = new java.util.Date().toString() + "\r\n";
            response = encoder.encode(CharBuffer.wrap(date));
        } catch(java.nio.charset.CharacterCodingException cce){
            logger.log(Level.SEVERE, cce.toString(), cce);
        }
        return response;
    }
}
