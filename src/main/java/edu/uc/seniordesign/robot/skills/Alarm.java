package edu.uc.seniordesign.robot.skills;

import com.pi4j.io.gpio.*;

public class Alarm
{
    private final GpioController gpioController = GpioFactory.getInstance();
    private final GpioPinDigitalOutput gpioAlarmPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_26, PinState.LOW);

    private void init()
    {
        gpioAlarmPin.setShutdownOptions(true, PinState.LOW);
    }

    public void soundAlarm()
    {
        init();
        System.out.print("Alarm is off \n");
        gpioAlarmPin.pulse(5000, true);
        System.out.print("Alarm is on for 5 seconds \n");
        System.out.print("Alarm is off \n");
        shutdown();
    }

    private void shutdown()
    {
        gpioController.shutdown();
        gpioController.unprovisionPin(gpioAlarmPin);
    }
}
