package edu.uc.seniordesign.robot.movement;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

public class UltrasonicSensor 
{
	private final static int CONVERT_NANO_TIME_TO_CM= 58200;
	private final GpioController gpioController = GpioFactory.getInstance();
	private GpioPinDigitalOutput sensorOutputPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_04);
	private GpioPinDigitalInput sensorInputPin = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN);
	private long startTime;
	private long endTime;
	private static final Logger LOGGER = Logger.getLogger(UltrasonicSensor.class.getName());
		
	private void transmitUltrasonicPulse() throws InterruptedException
	{
		sensorOutputPin.high();
		Thread.sleep((long) 0.01);
		sensorOutputPin.low();
	}
	
	private void waitForNextPulse() 
	{
		try
		{
			Thread.sleep(2000);
			transmitUltrasonicPulse();
		}
		catch(InterruptedException e)
		{
			LOGGER.warning(e.getMessage());
		}
	}
	
	private long measureDistanceInCM()
	{
		while (sensorInputPin.isLow()) {}
		startTime = System.nanoTime();
		while (sensorInputPin.isHigh()) {}
		endTime = System.nanoTime();
		return ((endTime - startTime) / CONVERT_NANO_TIME_TO_CM);
	}
	
	public long distanceFromObject()
	{
		waitForNextPulse();
		return measureDistanceInCM();
	}
}
