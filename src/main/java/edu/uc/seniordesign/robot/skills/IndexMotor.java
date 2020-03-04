package edu.uc.seniordesign.robot.skills;

import com.pi4j.io.gpio.*;

public class IndexMotor 
{
	private final GpioController gpioController = GpioFactory.getInstance();
	private final GpioPinDigitalOutput gpioMotorPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW);

	private void init()
	{
		gpioMotorPin.setShutdownOptions(true, PinState.LOW);
	}
	
	public void rotateMotor()
	{
		init();
		System.out.print("TestIndexMotor is off \n");
		gpioMotorPin.pulse(5000, true);
		System.out.print("TestIndexMotor is on for 5 seconds \n");
		System.out.print("TestIndexMotor is off \n");
		shutdown();
	}

	private void shutdown()
	{
		gpioController.shutdown();
		gpioController.unprovisionPin(gpioMotorPin);
	}
}
