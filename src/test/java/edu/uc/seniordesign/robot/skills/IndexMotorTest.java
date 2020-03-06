package edu.uc.seniordesign.robot.skills;

import com.pi4j.io.gpio.*;
import edu.uc.seniordesign.robot.mockGpio.MockGpioFactory;
import edu.uc.seniordesign.robot.mockGpio.MockPin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IndexMotorTest
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
    public void testPinProvisioned()
    {
        Collection<GpioPin> pins = gpio.getProvisionedPins();
        assertTrue(pins.contains(pin));
    }

    @Test
    public void testPinMode()
    {
        assertEquals(PinMode.DIGITAL_OUTPUT, pin.getMode());
    }

    @Test
    public void rotateMotor() throws InterruptedException
    {
        pin.low();
        pin.pulse(5000, PinState.HIGH);
        assertTrue(pin.isHigh());
        Thread.sleep(5000);
        assertTrue(pin.isLow());
    }
}
