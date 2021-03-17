package com.tshevchenko.app;

import java.nio.ByteBuffer;

/**
 * Interface for processing different kind of services.
 * It has different implementations for DateTime, Echo or Time servers
 */
public interface IService {
    ByteBuffer processService(ByteBuffer inputData);
}
