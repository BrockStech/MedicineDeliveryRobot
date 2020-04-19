package edu.uc.seniordesign.robot.raspberryPi;

import edu.uc.seniordesign.robot.skills.UltrasonicSensor;

public class RobotTesting
{
    Robot robot = new Robot();
    public void testIndexMotor()
    {
        System.out.print("TestIndexMotor has been started \n");
        robot.indexMotor.dispenseMedicine();
        System.out.print("TestIndexMotor has Finished \n");
    }

    public void testSensors()
    {
        System.out.print("Test Sensors has been started\n");
        long sensorDistance = -1;
        while (sensorDistance != 27)
        {
            int i = 0;
            for (UltrasonicSensor ultrasonicSensor : robot.ultrasonicSensors)
            {
                sensorDistance = ultrasonicSensor.nearestObjectDistance();
                i++;
                if (sensorDistance >= 0 && sensorDistance < 10)
                {
                    System.out.print("Sensor " + i + ": Distance from object: " + sensorDistance + "\n");
                    System.out.print("Object too close!! Returning false\n");
                }
                System.out.print("Sensor " + i + ": Distance from object: " + sensorDistance + "\n");
            }
            pauseForSensors();
        }
        System.out.print("Exit Test Sensors \n");
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
}
