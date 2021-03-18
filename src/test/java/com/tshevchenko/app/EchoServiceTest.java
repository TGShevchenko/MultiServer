package com.tshevchenko.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.ByteBuffer;

/**
 * Unit test for Echo Service
 */
@DisplayName("EchoServiceTest")
class EchoServiceTest {

    @Test
    public void testServiceProcessingResult() {
        // Creating a service instance
        IService echoService = new EchoService();
        final ByteBuffer inputBuffer = ByteBuffer.allocate(123);

        //Processing the service
        ByteBuffer resultBuffer = echoService.processService(inputBuffer);

        //Check the length of the result
        Assertions.assertEquals(123, resultBuffer.capacity());
    }
}