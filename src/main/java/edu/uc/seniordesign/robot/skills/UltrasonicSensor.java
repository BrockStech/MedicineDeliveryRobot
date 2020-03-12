package edu.uc.seniordesign.robot.skills;

import com.pi4j.io.gpio.*;

public class UltrasonicSensor 
{
	private final static int CONVERT_NANO_TIME_TO_CM = 58310;
	private final int TIME_UNTIL_TRIG_TIMEOUT = 1000;
	private final int TIME_UNTIL_ECHO_TIMEOUT = 17493000; // Max Distance of 3 meters
	private long startTime, endTime;

	public GpioPinDigitalOutput trigPinOutput;
	public GpioPinDigitalInput echoPinInput;

	public UltrasonicSensor(GpioController gpioController, int TrigPin, int EchoPin)
	{
		trigPinOutput = gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(TrigPin), PinState.HIGH);
		echoPinInput = gpioController.provisionDigitalInputPin(RaspiPin.getPinByAddress(EchoPin), PinPullResistance.PULL_DOWN);
		trigPinOutput.setShutdownOptions(true, PinState.LOW);
		echoPinInput.setShutdownOptions(true);
	}

	public long nearestObjectDistance()
	{
		System.out.print("Ultrasonic Sensor Start \n");
		long distance = -1;
		try
		{
			transmitUltrasonicPulse();
			distance = measurePulseDistanceInCM();
		}
		catch(InterruptedException e)
		{
			System.out.print("Ultrasonic Sensor Error \n");
		}
		return distance;
	}

	private void transmitUltrasonicPulse() throws InterruptedException
	{
		trigPinOutput.low();
		Thread.sleep(0, 2000); //Give short wait time before generating sonic burst
		trigPinOutput.high();
		Thread.sleep(0, 10000); //Generates 8 cycle ultrasonic burst
		trigPinOutput.low();
	}

	private long measurePulseDistanceInCM() throws InterruptedException
	{
		int trigTimeout = 0, echoTimeout = 0;
		System.out.print("Ultrasonic Echo Sensor is Low \n");
		while (echoPinInput.isLow())
		{
			Thread.sleep(0, 1);
			trigTimeout ++;
			if (trigTimeout >= TIME_UNTIL_TRIG_TIMEOUT) { return -1; }
		}
		System.out.print("Ultrasonic Echo Sensor is High \n");
		startTime = System.nanoTime();
		while (echoPinInput.isHigh())
		{
			Thread.sleep(0, 1);
			echoTimeout++;
			if (echoTimeout >= TIME_UNTIL_ECHO_TIMEOUT) { return -1; }
		}
		endTime = System.nanoTime();
		return ((endTime - startTime) / CONVERT_NANO_TIME_TO_CM);
	}
}
