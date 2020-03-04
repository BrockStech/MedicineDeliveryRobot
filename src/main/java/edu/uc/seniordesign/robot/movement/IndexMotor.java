package edu.uc.seniordesign.robot.movement;

import com.pi4j.io.gpio.*;

public class IndexMotor 
{
	private final GpioController gpioController = GpioFactory.getInstance();
	private final GpioPinDigitalOutput gpioMotorPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW);

	private void initMotor()
	{
		gpioMotorPin.setShutdownOptions(true, PinState.LOW);
	}
	
	public void rotateMotor()
	{
		initMotor();
		System.out.print("TestIndexMotor is off \n");
		gpioMotorPin.pulse(5000, true);
		System.out.print("TestIndexMotor is on for 5 seconds \n");
		System.out.print("TestIndexMotor is off \n");
		shutdownSensor();
	}

	private void shutdownSensor()
	{
		gpioController.shutdown();
		gpioController.unprovisionPin(gpioMotorPin);
	}
}
