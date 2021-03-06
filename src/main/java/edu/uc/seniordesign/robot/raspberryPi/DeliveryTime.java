package edu.uc.seniordesign.robot.raspberryPi;

import edu.uc.seniordesign.robot.database.Results;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TreeSet;

public class DeliveryTime 
{
	Results results = new Results();
	
	private ArrayList<String> getDeliveryTimes()
	{
		return results.fetchResults();
	}
	
	private LocalTime nextDeliveryTime()
	{
		ArrayList<String> times = getDeliveryTimes();
		TreeSet<LocalTime> deliveryTimes = new TreeSet<>();
		for (String time : times)
		{
			deliveryTimes.add(LocalTime.parse(time, DateTimeFormatter.ofPattern("hh:mm a")));
		}
		return deliveryTimes.ceiling(LocalTime.now());
	}
	
	public void automatedDelivery()
	{
		System.out.print(nextDeliveryTime());
	}
}
