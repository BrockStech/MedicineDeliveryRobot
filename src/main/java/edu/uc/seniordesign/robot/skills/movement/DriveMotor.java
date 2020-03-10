package edu.uc.seniordesign.robot.skills.movement;

import com.pi4j.io.gpio.*;

public class DriveMotor
{
    // Plan to use GPIO pins 21 - 25 (As needed)
    public GpioPinDigitalOutput gpioMotorPin;

    public DriveMotor(GpioController gpioController)
    {
        //TODO
        gpioMotorPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_21, PinState.LOW);
        gpioMotorPin.setShutdownOptions(true, PinState.LOW);
    }

    public void forward()
    {
        //TODO
        System.out.print("TestIndexMotor is off \n");
        gpioMotorPin.pulse(5000, true);
        System.out.print("TestIndexMotor is on for 5 seconds \n");
        System.out.print("TestIndexMotor is off \n");
    }

    public void backwards()
    {
        //TODO
        System.out.print("TestIndexMotor is off \n");
        gpioMotorPin.pulse(5000, true);
        System.out.print("TestIndexMotor is on for 5 seconds \n");
        System.out.print("TestIndexMotor is off \n");
    }

    public void left()
    {
        //TODO
        System.out.print("TestIndexMotor is off \n");
        gpioMotorPin.pulse(5000, true);
        System.out.print("TestIndexMotor is on for 5 seconds \n");
        System.out.print("TestIndexMotor is off \n");
    }

    public void right()
    {
        //TODO
        System.out.print("TestIndexMotor is off \n");
        gpioMotorPin.pulse(5000, true);
        System.out.print("TestIndexMotor is on for 5 seconds \n");
        System.out.print("TestIndexMotor is off \n");
    }

    public void stop()
    {
        //TODO
        System.out.print("TestIndexMotor is off \n");
        gpioMotorPin.pulse(5000, true);
        System.out.print("TestIndexMotor is on for 5 seconds \n");
        System.out.print("TestIndexMotor is off \n");
    }
}
