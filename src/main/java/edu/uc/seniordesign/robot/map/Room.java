package edu.uc.seniordesign.robot.map;

public class Room
{
    public String[] toBathroom()
    {
        return new String[]{"forward", "8", "left", "forward", "23", "stop"};
    }

    public String[] toBedroom1()
    {
        return new String[]{"forward", "8", "left", "forward", "20", "right", "forward", "6", "left", "forward", "4", "stop"};
    }

    public String[] toBedroom2()
    {
        return new String[]{"forward", "8", "left", "forward", "20", "left", "forward", "4", "right", "forward", "2", "stop"};
    }

    public String[] toDinningRoom()
    {
        return new String[]{"forward", "12", "left", "forward", "2", "right", "forward", "3", "stop"};
    }

    public String[] toLivingRoom()
    {
        return new String[]{"forward", "4", "left", "forward", "2", "stop"};
    }

    public String[] toKitchen()
    {
        return new String[]{"forward", "8", "left", "forward", "14", "right", "forward", "7", "stop"};
    }
}
