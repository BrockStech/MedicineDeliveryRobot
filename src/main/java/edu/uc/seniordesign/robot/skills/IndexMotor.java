package edu.uc.seniordesign.robot.skills;

import com.pi4j.io.gpio.*;

public class IndexMotor
{
	private GpioPinDigitalOutput gpioMotorPin;

	public IndexMotor(GpioController gpioController)
	{
		gpioMotorPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.HIGH);
		gpioMotorPin.setShutdownOptions(true);
	}

	/**
	 * NOTE: For the Index Motor low is on and high is off!
	 */
	public void dispenseMedicine()
	{
		gpioMotorPin.low();
		System.out.print("Index Motor is on \n");
		try
		{
			Thread.sleep(300);
		}
		catch(InterruptedException e)
		{
			System.out.print("Index Motor Error! \n");
		}
		gpioMotorPin.high();
		System.out.print("Index Motor is off \n");
	}
}


