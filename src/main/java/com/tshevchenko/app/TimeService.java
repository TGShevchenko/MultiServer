package com.tshevchenko.app;

import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class implements a server Time functionality (RFC 868)
 */
public class TimeService implements IService {
    private final Logger logger = Logger.getLogger(TimeService.class.getName());

    /**
     * Method makes the same processing for both UDP and TCP services
     * @return
     */
    public ByteBuffer processService(ByteBuffer inputData) {
        logger.log(Level.INFO, "TimeService starts to process...");

        ByteBuffer response = ByteBuffer.allocate(16);
        Calendar calendar = Calendar.getInstance(java.util.TimeZone.getTimeZone("UTC"));
        calendar.set(1900, Calendar.JANUARY, 1, 0, 0, 0);
        long resultSecs = ((System.currentTimeMillis() + Math.abs(calendar.getTime().getTime())) / 1000);
        response.putInt((int) ((resultSecs >> 24) & 0xFF));
        response.putInt((int) ((resultSecs >> 16) & 0xFF));
        response.putInt((int) ((resultSecs >> 8) & 0xFF));
        response.putInt((int) (resultSecs & 0xFF));

        return response;
    }
}
