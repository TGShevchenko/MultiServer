package com.tshevchenko.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.ByteBuffer;

/**
 * Unit test for Time Service
 */
@DisplayName("TimeServiceTest")
class TimeServiceTest {

    @Test
    public void testServiceProcessingResult() {
        // Creating a service instance
        IService timeService = new TimeService();
        final ByteBuffer inputBuffer = ByteBuffer.allocate(24);

        //Processing the service
        ByteBuffer resultBuffer = timeService.processService(inputBuffer);

        //Check the length of the result
        Assertions.assertEquals(16, resultBuffer.position());
    }
}