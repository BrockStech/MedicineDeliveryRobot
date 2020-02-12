package edu.uc.seniordesign.robot.movement;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

// Note: Most install pi4J first
// curl -sSL https://pi4j.com/install | sudo bash
// Also included in pom.xml

public class IndexMotor 
{
	private GpioStepperMotorComponent initMotor()
	{
		// May need to use different pins later (01 - 04 are being used now)
		final GpioController gpioController = GpioFactory.getInstance();
		final GpioPinDigitalOutput[] gpioPins = {gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW), gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW), gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW), gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW)};
		
		gpioController.setShutdownOptions(true, PinState.LOW, gpioPins);
		
		GpioStepperMotorComponent IndexMotor = new GpioStepperMotorComponent(gpioPins);
		
		// Will need to play with these values
		IndexMotor.setStepInterval(10);
		IndexMotor.setStepSequence(new byte[] {(byte) 0b0001, (byte) 0b0010, (byte) 0b0100, (byte) 0b1000});
		
		// We need to calculate 1/24 of a full rotation
		IndexMotor.setStepsPerRevolution(1000);
		return IndexMotor;
	}
	
	public void rotateMotor()
	{
		GpioStepperMotorComponent IndexMotor = initMotor();
		IndexMotor.rotate(1);
		IndexMotor.stop();
	}
}
