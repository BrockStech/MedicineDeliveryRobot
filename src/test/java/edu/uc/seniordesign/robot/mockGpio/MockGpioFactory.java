package edu.uc.seniordesign.robot.mockGpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.impl.GpioControllerImpl;

public class MockGpioFactory
{
    private static MockGpioProvider provider = null;
    private MockGpioFactory() {}

    public static GpioController getInstance()
    {
        return new GpioControllerImpl(getMockProvider());
    }


    public static MockGpioProvider getMockProvider() {
        if (provider == null)
        {
            provider = new MockGpioProvider();
        }
        return provider;
    }
}