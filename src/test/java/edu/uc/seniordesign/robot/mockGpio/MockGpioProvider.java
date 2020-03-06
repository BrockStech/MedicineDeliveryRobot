package edu.uc.seniordesign.robot.mockGpio;

import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.GpioProviderBase;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

public class MockGpioProvider extends GpioProviderBase implements GpioProvider
{
    public static final String NAME = "MockGpioProvider";

    @Override
    public String getName()
    {
        return NAME;
    }

    public void setMockState(Pin pin, PinState state) {
        getPinCache(pin).setState(state);
        dispatchPinDigitalStateChangeEvent(pin, state);
    }

    public void setMockAnalogValue(Pin pin, double value) {
        getPinCache(pin).setAnalogValue(value);
        dispatchPinAnalogValueChangeEvent(pin, value);
    }

}