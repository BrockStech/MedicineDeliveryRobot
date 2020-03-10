package edu.uc.seniordesign.robot.skills;

import com.pi4j.io.gpio.*;

public class Alarm
{
    private GpioPinDigitalOutput gpioAlarmPin;

    public Alarm(GpioController gpioController)
    {
        GpioPinDigitalOutput gpioAlarmPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_26, PinState.LOW);
        gpioAlarmPin.setShutdownOptions(true, PinState.LOW);
    }

    public void soundAlarm(GpioController gpioController)
    {
        System.out.print("Alarm is off \n");
        gpioAlarmPin.pulse(5000, true);
        System.out.print("Alarm is on for 5 seconds \n");
        System.out.print("Alarm is off \n");
    }
}
