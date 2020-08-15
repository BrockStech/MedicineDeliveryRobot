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
	private static final long SECONDS_TO_MILLISECONDS = 1000;
	GpioController gpioController;
	DriveMotor driveMotor;
	UltrasonicSensor centerUltrasonicSensor;
	UltrasonicSensor leftUltrasonicSensor;
	UltrasonicSensor rightUltrasonicSensor;
	UltrasonicSensor[] ultrasonicSensors;
	IndexMotor indexMotor;
	Alarm alarm;
	Room room;

	public Robot()
	{
		equipRobotWithSkills();
	}

	protected void equipRobotWithSkills()
	{
		resetGPIOController();
		gpioController = GpioFactory.getInstance();
		driveMotor = new DriveMotor(gpioController);
		centerUltrasonicSensor = new UltrasonicSensor(gpioController, 5, 4);
		leftUltrasonicSensor = new UltrasonicSensor(gpioController, 6, 10);
		rightUltrasonicSensor = new UltrasonicSensor(gpioController, 27, 28);
		ultrasonicSensors = new UltrasonicSensor[]{centerUltrasonicSensor, leftUltrasonicSensor, rightUltrasonicSensor};
		indexMotor = new IndexMotor(gpioController);
		alarm = new Alarm(gpioController);
		room = new Room();
	}

	private void resetGPIOController()
	{
		gpioController = GpioFactory.getInstance();
		gpioController.unexportAll();
		gpioController.shutdown();
	}

	public void deliverMedicineToBathroom()
	{
		deliverMedicine(room.toBathroom());
	}

	public void deliverMedicineToBedroom1()
	{
		deliverMedicine(room.toBedroom1());
	}

	public void deliverMedicineToBedroom2()
	{
		deliverMedicine(room.toBedroom2());
	}

	public void deliverMedicineToLivingRoom()
	{
		deliverMedicine(room.toLivingRoom());
	}

	public void deliverMedicineToKitchen()
	{
		deliverMedicine(room.toKitchen());
	}

	public void deliverMedicineToDinningRoom()
	{
		deliverMedicine(room.toDinningRoom());
	}

	protected void deliverMedicine(String[] directions)
	{
		readMap(directions);
		completeArrivalTasks();
		returnToHomeBase(directions);
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
		for (UltrasonicSensor ultrasonicSensor : ultrasonicSensors)
		{
			Long distance = ultrasonicSensor.nearestObjectDistance();
			if (distance >= 0 && distance < 5)
			{
				return false;
			}
		}
		pauseForSensors();
		return true;
	}

	private void pauseForSensors()
	{
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	private void completeArrivalTasks()
	{
		alarm.soundAlarm();
		indexMotor.dispenseMedicine();
		driveMotor.left();
		driveMotor.left();
	}

	private void returnToHomeBase(String[] directions)
	{
		Collections.reverse(Arrays.asList(directions));
		readMap(directions);
	}
}
