package edu.uc.seniordesign.robot.skills;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import edu.uc.seniordesign.robot.mockGpio.MockGpioFactory;
import edu.uc.seniordesign.robot.mockGpio.MockPin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

class AlarmTest
{

    private static GpioController gpio;
    private static GpioPinDigitalOutput pin;

    @BeforeAll
    public static void setup()
    {
        gpio = MockGpioFactory.getInstance();
        pin = gpio.provisionDigitalOutputPin(MockPin.DIGITAL_OUTPUT_PIN,  "digitalOutputPin", PinState.LOW);
    }

    @Test
    void soundAlarm() throws InterruptedException
    {
        pin.low();
        pin.pulse(5000, PinState.HIGH);
        assertTrue(pin.isHigh());
        Thread.sleep(5000);
        assertTrue(pin.isLow());
    }
}