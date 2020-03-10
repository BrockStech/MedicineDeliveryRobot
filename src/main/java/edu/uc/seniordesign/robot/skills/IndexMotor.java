package edu.uc.seniordesign.robot.skills;

import com.pi4j.io.gpio.*;

public class IndexMotor
{
	private GpioPinDigitalOutput gpioMotorPin;

	public IndexMotor(GpioController gpioController)
	{
		gpioMotorPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW);
		gpioMotorPin.setShutdownOptions(true, PinState.LOW);
	}

	public void rotateMotor(GpioController gpioController)
	{
		System.out.print("TestIndexMotor is off \n");
		gpioMotorPin.pulse(5000, true);
		System.out.print("TestIndexMotor is on for 5 seconds \n");
		System.out.print("TestIndexMotor is off \n");
	}
}


