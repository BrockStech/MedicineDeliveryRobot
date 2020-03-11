package edu.uc.seniordesign.robot.skills;

import com.pi4j.io.gpio.*;

public class Alarm
{
    private GpioPinDigitalOutput gpioAlarmPin;

    public Alarm(GpioController gpioController)
    {
        gpioAlarmPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_26, PinState.LOW);
        gpioAlarmPin.setShutdownOptions(true, PinState.LOW);
    }

    public void soundAlarm()
    {
        System.out.print("Alarm is off \n");
        gpioAlarmPin.pulse(300, true);
        System.out.print("Alarm is on for .3 seconds \n");
        System.out.print("Alarm is off \n");
    }

    public void soundAlarmBackwards()
    {
        gpioAlarmPin.high();
        System.out.print("Alarm is on \n");
        try
        {
            Thread.sleep(300);
        }
        catch(InterruptedException e)
        {
            System.out.print("Alarm Error! \n");
        }
        gpioAlarmPin.low();
        System.out.print("Alarm is off \n");
    }
}
