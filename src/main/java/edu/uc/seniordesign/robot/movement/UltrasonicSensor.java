package edu.uc.seniordesign.robot.movement;

import com.pi4j.io.gpio.*;

public class UltrasonicSensor 
{
	private final static int CONVERT_NANO_TIME_TO_CM = 58200;
	private final GpioController gpioController = GpioFactory.getInstance();
	private final GpioPinDigitalOutput sensorTrigPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_05, PinState.LOW);
	private final GpioPinDigitalInput sensorEchoPin = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
	private long startTime;
	private long endTime;

	public long distanceFromObject()
	{
		initSensor();
		System.out.print("Ultrasonic Sensor Start \n");
		waitForNextPulse();
		System.out.print("Ultrasonic Sensor Pulse Finish \n");
		shutdownSensor();
		return measureDistanceInCM();
	}

	private void initSensor()
	{
		sensorTrigPin.setShutdownOptions(true, PinState.LOW);
		sensorEchoPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
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
		System.out.print("Ultrasonic Sensor is Low \n");
		while (sensorEchoPin.isLow()) {}
		System.out.print("Ultrasonic Sensor is High \n");
		startTime = System.nanoTime();
		while (sensorEchoPin.isHigh()) {}
		System.out.print("Ultrasonic Sensor is Low2 \n");
		endTime = System.nanoTime();
		return ((endTime - startTime) / CONVERT_NANO_TIME_TO_CM);
	}

	private void shutdownSensor()
	{
		gpioController.shutdown();
		gpioController.unprovisionPin(sensorTrigPin);
		gpioController.unprovisionPin(sensorEchoPin);
	}
}
