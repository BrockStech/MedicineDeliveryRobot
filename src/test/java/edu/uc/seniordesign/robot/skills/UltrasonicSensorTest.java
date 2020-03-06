package edu.uc.seniordesign.robot.skills;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import edu.uc.seniordesign.robot.mockGpio.MockGpioFactory;
import edu.uc.seniordesign.robot.mockGpio.MockGpioProvider;
import edu.uc.seniordesign.robot.mockGpio.MockPin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class UltrasonicSensorTest
{
    private static MockGpioProvider provider;
    private static GpioController gpio;
    private static GpioPinDigitalInput pin;
    private static PinState pinMonitoredState;

    @BeforeAll
    public static void setup()
    {
        provider = MockGpioFactory.getMockProvider();
        gpio = MockGpioFactory.getInstance();
        pin = gpio.provisionDigitalInputPin(MockPin.DIGITAL_INPUT_PIN,  "digitalInputPin", PinPullResistance.PULL_DOWN);

        pin.addListener(new GpioPinListenerDigital()
        {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event)
            {
                if (event.getPin() == pin)
                {
                    pinMonitoredState = event.getState();
                }
            }
        });
    }

    @Test
    public void testPinHiEvent() throws InterruptedException {
        provider.setMockState(MockPin.DIGITAL_INPUT_PIN, PinState.LOW);
        Thread.sleep(0,10000);
        pinMonitoredState = null;
        provider.setMockState(MockPin.DIGITAL_INPUT_PIN, PinState.HIGH);
        Thread.sleep(0,10000);
        assertEquals(PinState.HIGH, pinMonitoredState);
    }
}