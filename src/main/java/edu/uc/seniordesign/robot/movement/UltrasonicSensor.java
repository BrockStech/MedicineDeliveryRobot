package edu.uc.seniordesign.robot.movement;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.pi4j.io.gpio.*;

public class UltrasonicSensor 
{
	private final static int CONVERT_NANO_TIME_TO_CM= 58200;
	private final GpioController gpioController = GpioFactory.getInstance();
	private GpioPinDigitalOutput sensorTrigPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_05, PinState.LOW);
	private GpioPinDigitalInput sensorEchoPin = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
	private long startTime;
	private long endTime;
	private static final Logger LOGGER = Logger.getLogger(UltrasonicSensor.class.getName());

	public long distanceFromObject()
	{
		// TURN OFF AND REOPEN PINS
		System.out.print("Ultrasonic Sensor Start \n");
		waitForNextPulse();
		System.out.print("Ultrasonic Sensor Pulse Finish \n");
		return measureDistanceInCM();
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
			System.out.print("Ultrasonic Sensor Error \n");
		}
	}

	private void transmitUltrasonicPulse() throws InterruptedException
	{
		sensorTrigPin.high();
		Thread.sleep(0, 10000);
		sensorTrigPin.low();
	}
	
	private long measureDistanceInCM()
	{
		int countdown = 5000;
		System.out.print("Ultrasonic Sensor is Low \n");
		while (sensorEchoPin.isLow() && countdown > 0) {countdown--;}
		System.out.print("Ultrasonic Sensor is High \n");
		if (countdown == 0)
		{
			System.out.print("TIMEOUT \n");
		}
		startTime = System.nanoTime();
		while (sensorEchoPin.isHigh()) {}
		System.out.print("Ultrasonic Sensor is Low2 \n");
		endTime = System.nanoTime();
		return ((endTime - startTime) / CONVERT_NANO_TIME_TO_CM);
	}
}
