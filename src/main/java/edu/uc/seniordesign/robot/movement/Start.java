package edu.uc.seniordesign.robot.movement;

public class Start 
{
	IndexMotor indexMotor = new IndexMotor();
	UltrasonicSensor ultrasonicSensor = new UltrasonicSensor();
	
	public void testIndexMotor()
	{
		System.out.print("TestIndexMotor has been started\n");
		indexMotor.rotateMotor();
	}
	
	public void testSensors()
	{
		System.out.print("TestSensors has been started\n");
		while (ultrasonicSensor.distanceFromObject() > 1)
		{
			System.out.print("Distance: " + ultrasonicSensor.distanceFromObject() + " cm \n");
		}
	}
}
