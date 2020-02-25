package edu.uc.seniordesign.robot.movement;

public class Start 
{
	IndexMotor indexMotor = new IndexMotor();
	
	public void startRobot()
	{
		System.out.print("Robot delivery has been started\n");
		indexMotor.rotateMotor();
	}
}
