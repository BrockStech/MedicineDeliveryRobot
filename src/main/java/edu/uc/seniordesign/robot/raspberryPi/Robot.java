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
		long[] distancesFromObject = ultrasonicSensor.distanceFromObject();
		while (distancesFromObject[0] > 5 && distancesFromObject[1] > 5 && distancesFromObject[2] > 5)
		{
			System.out.print("Center Sensor Distance: " + distancesFromObject[0] + " cm \n");
			System.out.print("Left Sensor Distance: " + distancesFromObject[1] + " cm \n");
			System.out.print("Right Sensor Distance: " + distancesFromObject[2] + " cm \n");
			distancesFromObject = ultrasonicSensor.distanceFromObject();
		}
		System.out.print("END TEST \n");
	}
}
