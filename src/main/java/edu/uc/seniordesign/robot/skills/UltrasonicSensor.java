package edu.uc.seniordesign.robot.skills;

import com.pi4j.io.gpio.*;

public class UltrasonicSensor 
{
	private final static int CONVERT_NANO_TIME_TO_CM = 58200;
	private GpioController gpioController = GpioFactory.getInstance();
	private GpioPinDigitalOutput centerSensorTrigPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_05, PinState.LOW);
	private GpioPinDigitalInput centerSensorEchoPin = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
	private GpioPinDigitalOutput leftSensorTrigPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_06, PinState.LOW);
	private GpioPinDigitalInput leftSensorEchoPin = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_10, PinPullResistance.PULL_DOWN);
	private GpioPinDigitalOutput rightSensorTrigPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_27, PinState.LOW);
	private GpioPinDigitalInput rightSensorEchoPin = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_28, PinPullResistance.PULL_DOWN);
	private int timeUntilTimeout;
	private long startTime;
	private long endTime;

	public long[] distanceFromObject()
	{
		init();
		System.out.print("Ultrasonic Sensor Start \n");
		waitForNextPulse();
		System.out.print("Ultrasonic Sensor Pulse Finish \n");
		shutdown();
		return addDistanceToArray();
	}

	private void init()
	{
		centerSensorTrigPin.setShutdownOptions(true, PinState.LOW);
		centerSensorEchoPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
		leftSensorTrigPin.setShutdownOptions(true, PinState.LOW);
		leftSensorEchoPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
		rightSensorTrigPin.setShutdownOptions(true, PinState.LOW);
		rightSensorEchoPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
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
		timeUntilTimeout = 5000;
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

	private void shutdown()
	{
		gpioController.shutdown();
		gpioController.unprovisionPin(centerSensorTrigPin);
		gpioController.unprovisionPin(centerSensorEchoPin);
		gpioController.unprovisionPin(leftSensorTrigPin);
		gpioController.unprovisionPin(leftSensorEchoPin);
		gpioController.unprovisionPin(rightSensorTrigPin);
		gpioController.unprovisionPin(rightSensorEchoPin);
	}
}
