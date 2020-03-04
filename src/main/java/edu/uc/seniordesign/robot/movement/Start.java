package edu.uc.seniordesign.robot.movement;

public class Start 
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
		while (ultrasonicSensor.distanceFromObject() > .001)
		{
			System.out.print("Distance: " + ultrasonicSensor.distanceFromObject() + " cm \n");
		}
		System.out.print("END TEST \n");
	}
}
