package com.tshevchenko.app;

import java.nio.ByteBuffer;

/**
 *
 */
public interface IService {
    public ByteBuffer runService(ByteBuffer inputData);
}
