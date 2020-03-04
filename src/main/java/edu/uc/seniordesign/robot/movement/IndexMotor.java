package edu.uc.seniordesign.robot.movement;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class IndexMotor 
{
	private final GpioController gpioController = GpioFactory.getInstance();
	private GpioPinDigitalOutput gpioMotorPin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW);

	private GpioPinDigitalOutput initMotor()
	{
		gpioController.setShutdownOptions(true, PinState.LOW, gpioMotorPin);
		return gpioMotorPin;
	}
	
	public void rotateMotor()
	{
		// TURN OFF AND REOPEN PINS
		GpioPinDigitalOutput indexMotor = initMotor();
		System.out.print("TestIndexMotor is off \n");
		indexMotor.high();
		System.out.print("TestIndexMotor is on \n");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		indexMotor.low();
		System.out.print("TestIndexMotor is off \n");
	}
}
