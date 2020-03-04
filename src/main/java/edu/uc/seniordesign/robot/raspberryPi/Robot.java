package edu.uc.seniordesign.robot.raspberryPi;

import edu.uc.seniordesign.robot.skills.IndexMotor;
import edu.uc.seniordesign.robot.skills.UltrasonicSensor;

public class Robot
{
	IndexMotor indexMotor = new IndexMotor();
	UltrasonicSensor ultrasonicSensor = new UltrasonicSensor();
	
	public void testIndexMotor()
	{
		System.out.print("TestIndexMotor has been started \n");
		indexMotor.rotateMotor();
		System.out.print("TestIndexMotor has Finished \n");
	}
	
	public void testSensors()
	{
		System.out.print("TestSensors has been started\n");
		long distanceFromObjectInCM = ultrasonicSensor.distanceFromObject();
		while (distanceFromObjectInCM > 5)
		{
			System.out.print("Distance: " + distanceFromObjectInCM + " cm \n");
			distanceFromObjectInCM = ultrasonicSensor.distanceFromObject();
		}
		System.out.print("END TEST \n");
	}
}
