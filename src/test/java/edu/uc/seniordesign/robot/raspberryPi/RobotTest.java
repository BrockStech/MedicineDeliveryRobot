package edu.uc.seniordesign.robot.raspberryPi;

import com.pi4j.io.gpio.GpioController;
import edu.uc.seniordesign.robot.skills.UltrasonicSensor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RobotTest
{
    private static class MockedRobot extends Robot
    {
        @Override
        protected void equipRobotWithSkills() { }
    }

    private static MockedRobot robot;
    private static UltrasonicSensor ultrasonicSensor;
    private static GpioController gpioController;

    @BeforeAll
    public static void setup()
    {
        robot = new MockedRobot();
        gpioController = mock(GpioController.class);
        robot.ultrasonicSensor = mock(UltrasonicSensor.class);
        when(robot.ultrasonicSensor.distanceFromObject()).thenReturn(new long[]{6, 8 ,10});
    }

    @Test
    void deliverMedicineToBathroom()
    {

    }

    @Test
    void scanSensorsForTimeFive()
    {
        long time = robot.scanSensorsForTime(1);
        assert(time <= 0);
    }

    @Test
    void scanSensorsForTimeZero()
    {
        long time = robot.scanSensorsForTime(0);
        assert(time <= 0);
    }
}