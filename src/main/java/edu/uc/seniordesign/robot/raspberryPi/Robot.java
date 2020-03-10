package edu.uc.seniordesign.robot.raspberryPi;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import edu.uc.seniordesign.robot.map.Room;
import edu.uc.seniordesign.robot.skills.Alarm;
import edu.uc.seniordesign.robot.skills.IndexMotor;
import edu.uc.seniordesign.robot.skills.UltrasonicSensor;
import edu.uc.seniordesign.robot.skills.movement.DriveMotor;

import java.util.Arrays;
import java.util.Collections;

public class Robot
{
	GpioController gpioController;
	DriveMotor driveMotor;
	UltrasonicSensor ultrasonicSensor;
	IndexMotor indexMotor;
	Alarm alarm;
	Room room;
	private static final long SECONDS_TO_MILLISECONDS = 1000;

	public Robot()
	{
		equipRobotWithSkills();
	}

	protected void equipRobotWithSkills()
	{
		gpioController = GpioFactory.getInstance();
		driveMotor = new DriveMotor(gpioController);
		ultrasonicSensor = new UltrasonicSensor(gpioController);
		indexMotor = new IndexMotor(gpioController);
		alarm = new Alarm(gpioController);
		room = new Room();
	}

	public void testIndexMotor()
	{
		System.out.print("TestIndexMotor has been started \n");
		indexMotor.rotateMotor(gpioController);
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

	public void deliverMedicineToBathroom(Room room)
	{
		String[] directions = room.toBathroom();
		readMap(directions);
	}

	public void completeArrivalTasks()
	{
		//TODO: rotate medicine dispenser
		//TODO: sound alarm
		//TODO: wait to leave
	}

	public void returnToHomeBase(String[] directions)
	{
		// TODO: Turn 180 degrees
		Collections.reverse(Arrays.asList(directions));
		readMap(directions);
	}

	protected void readMap(String[] directions)
	{
		for (int i = 0; i < directions.length; i++)
		{
			switch (directions[i])
			{
				case "left":
					driveMotor.left();
					break;
				case "right":
					driveMotor.right();
					break;
				case "forward":
					driveMotor.forward();
					break;
				case "backwards":
					driveMotor.backwards();
					break;
				case "stop":
					driveMotor.stop();
					break;
				default:
					long obstacleAvoidanceTimeAdded = scanSensorsForTime(convertStringToLong(directions[i]));
					driveMotor.stop();
					if (obstacleAvoidanceTimeAdded > 0)
					{
						directions[i] = String.valueOf(convertStringToLong(directions[i]) - obstacleAvoidanceTimeAdded);
						directions[i-1] = "34";
					}
			}
		}
	}

	protected long convertStringToLong(String direction)
	{
		long timeInSecondsToScanSensors;
		try
		{
			timeInSecondsToScanSensors = Long.parseLong(direction);
		}
		catch (NumberFormatException nfe)
		{
			return 0;
		}
		return timeInSecondsToScanSensors;
	}

	protected long scanSensorsForTime(long timeInSecondsToScanSensors)
	{
		long scanSensorsLength = timeInSecondsToScanSensors * SECONDS_TO_MILLISECONDS;
		long endTime = System.currentTimeMillis() + scanSensorsLength;
		while (System.currentTimeMillis() < endTime && surroundingAreaIsSafe()) { }
		return endTime - System.currentTimeMillis();
	}

	protected boolean surroundingAreaIsSafe()
	{
		long[] distancesFromObject = ultrasonicSensor.distanceFromObject();
		return distancesFromObject[0] > 5 && distancesFromObject[1] > 5 && distancesFromObject[2] > 5;
	}
}
