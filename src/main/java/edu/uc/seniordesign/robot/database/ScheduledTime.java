package edu.uc.seniordesign.robot.database;

import java.util.ArrayList;

public class ScheduledTime 
{
	private int id;
	private ArrayList<String> deliveryTime;
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public ArrayList<String> getDeliveryTime() 
	{
		return deliveryTime;
	}
	
	public void setDeliveryTime(ArrayList<String> deliveryTime) 
	{
		this.deliveryTime = deliveryTime;
	}
}
