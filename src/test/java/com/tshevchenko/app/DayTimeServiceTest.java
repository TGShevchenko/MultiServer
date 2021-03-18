package com.tshevchenko.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.ByteBuffer;

/**
 * Unit test for DayTime Service
 */
@DisplayName("DayTimeServiceTest")
class DayTimeServiceTest {

    @Test
    public void testServiceProcessingResult() {
        // Creating a service instance
        IService dayTimeService = new DayTimeService();
        final ByteBuffer inputBuffer = ByteBuffer.allocate(64);

        //Processing the service
        ByteBuffer resultBuffer = dayTimeService.processService(inputBuffer);

        //Check the length of the result
        Assertions.assertEquals(30, resultBuffer.capacity());
    }
}