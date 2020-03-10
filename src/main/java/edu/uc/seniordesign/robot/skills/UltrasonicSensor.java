package edu.uc.seniordesign.robot.skills;

import com.pi4j.io.gpio.*;

public class UltrasonicSensor 
{
	private final static int CONVERT_NANO_TIME_TO_CM = 58200;
	public GpioPinDigitalOutput centerSensorTrigPin;
	public GpioPinDigitalInput centerSensorEchoPin;
	public GpioPinDigitalOutput leftSensorTrigPin;
	public GpioPinDigitalInput leftSensorEchoPin;
	public GpioPinDigitalOutput rightSensorTrigPin;
	public GpioPinDigitalInput rightSensorEchoPin;
	private int timeUntilTimeout;
	private long startTime;
	private long endTime;

	public UltrasonicSensor(GpioController gpioController)
	{
		GpioPinDigitalOutput centerSensorTrigPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_05, PinState.LOW);
		GpioPinDigitalInput centerSensorEchoPin = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
		GpioPinDigitalOutput leftSensorTrigPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_06, PinState.LOW);
		GpioPinDigitalInput leftSensorEchoPin = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_10, PinPullResistance.PULL_DOWN);
		GpioPinDigitalOutput rightSensorTrigPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_27, PinState.LOW);
		GpioPinDigitalInput rightSensorEchoPin = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_28, PinPullResistance.PULL_DOWN);
		centerSensorTrigPin.setShutdownOptions(true, PinState.LOW);
		centerSensorEchoPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
		leftSensorTrigPin.setShutdownOptions(true, PinState.LOW);
		leftSensorEchoPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
		rightSensorTrigPin.setShutdownOptions(true, PinState.LOW);
		rightSensorEchoPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	}

	public long[] distanceFromObject()
	{
		System.out.print("Ultrasonic Sensor Start \n");
		waitForNextPulse();
		System.out.print("Ultrasonic Sensor Pulse Finish \n");
		return addDistanceToArray();
	}

	private void waitForNextPulse()
	{
		try
		{
			Thread.sleep(500);
			transmitUltrasonicPulse();
		}
		catch(InterruptedException e)
		{
			System.out.print("Ultrasonic Sensor Error \n");
		}
	}

	private void transmitUltrasonicPulse() throws InterruptedException
	{
		centerSensorTrigPin.high();
		leftSensorTrigPin.high();
		rightSensorTrigPin.high();
		Thread.sleep(0, 10000);
		centerSensorTrigPin.low();
		leftSensorTrigPin.low();
		rightSensorTrigPin.low();
	}

	private long[] addDistanceToArray()
	{
		GpioPinDigitalInput[] sensorEchoPins = {centerSensorEchoPin, leftSensorEchoPin, rightSensorEchoPin};
		long[] distances = new long[sensorEchoPins.length];
		int i = 0;
		for (GpioPinDigitalInput sensorEchoPin : sensorEchoPins)
		{
			distances[i++] = measureDistanceInCM(sensorEchoPin);
		}
		return distances;
	}

	private long measureDistanceInCM(GpioPinDigitalInput sensorEchoPin)
	{
		timeUntilTimeout = 2000;
		System.out.print("Ultrasonic Sensor is Low \n");
		while (sensorEchoPin.isLow() && !isTimeout()) {}
		System.out.print("Ultrasonic Sensor is High \n");
		startTime = System.nanoTime();
		while (sensorEchoPin.isHigh()) {}
		System.out.print("Ultrasonic Sensor is Low2 \n");
		endTime = System.nanoTime();
		return ((endTime - startTime) / CONVERT_NANO_TIME_TO_CM);
	}

	private boolean isTimeout()
	{
		return timeUntilTimeout-- <= 0;
	}
}
