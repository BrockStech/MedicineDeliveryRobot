package edu.uc.seniordesign.robot.UI;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.router.Route;
import edu.uc.seniordesign.robot.database.Results;
import edu.uc.seniordesign.robot.movement.DeliveryTime;
import edu.uc.seniordesign.robot.movement.Start;

@Route
public class MainView extends VerticalLayout {

  /**
	 * 
	 */
	private static final long serialVersionUID = 558175629664324863L;
	private boolean mobile = false;
	Results results = new Results();
	ArrayList<String> scheduledDeliveryTimes = new ArrayList<String>();
	Start startRobot = new Start();
	DeliveryTime deliveryTime = new DeliveryTime();
	

public MainView() {
	// Center Page
	setSizeFull();
	setAlignItems(Alignment.CENTER);
	getStyle().set("backgroundColor", "#e85151");
	
	// Headers
	H1 titleText = new H1("The Medicine Delivery Robot");
	titleText.getStyle().set("color", "black");
	H3 callRobotText = new H3("Request Medicine Delivery Robot");
	callRobotText.getStyle().set("color", "black");
	H3 scheduleRobotText = new H3("Schedule Medicine Delivery Robot");
	scheduleRobotText.getStyle().set("color", "black");
	H4 subtitleText = new H4("Choose how you want your medicine delivered!");
	subtitleText.getStyle().set("color", "black");
	H5 deliverySetupText = new H5();
	deliverySetupText.getStyle().set("color", "black");
    
	
	// Initialize Progress Bar
	ProgressBar progressBar = new ProgressBar();
    progressBar.setIndeterminate(true);
    progressBar.setVisible(false);
    
    // Bedroom 1 Button
    Button bedroom1Button = new Button("Bedroom 1");
    createButton(bedroom1Button);
    
    // Bedroom 2 Button
    Button bedroom2Button = new Button("Bedroom 2");
    createButton(bedroom2Button);
    
    // Living Room Button
    Button livingRoomButton = new Button("Living Room");
    createButton(livingRoomButton);
    
    // Living Room Button
    Button dinningRoomButton = new Button("Dinning Room");
    createButton(dinningRoomButton);
    
    // Kitchen Button
    Button kitchenButton = new Button("Kitchen");
    createButton(kitchenButton);
    
    // Bathroom Button
    Button bathroomButton = new Button("Bathroom");
    createButton(bathroomButton);
	
    // Create vertical layout for the 3 of 6 rooms (popular)
 	VerticalLayout popularRoomLayout = new VerticalLayout();
 	popularRoomLayout.setAlignItems(Alignment.CENTER);
 	popularRoomLayout.add(bedroom1Button, bedroom2Button, livingRoomButton);
 	
 	// Create vertical layout for the 3 of 6 rooms (unpopular)
  	VerticalLayout unpopularRoomLayout = new VerticalLayout();
  	unpopularRoomLayout.setAlignItems(Alignment.CENTER);
  	unpopularRoomLayout.add(dinningRoomButton, kitchenButton, bathroomButton);
 	
  	// HorizontalLayout for 6 Rooms
    HorizontalLayout roomsLayout = new HorizontalLayout();
    roomsLayout.add(popularRoomLayout, unpopularRoomLayout);
    
    // Create vertical layout for the delivery robot buttons
	VerticalLayout callRobotLayout = new VerticalLayout();
	callRobotLayout.setAlignItems(Alignment.CENTER);
	callRobotLayout.add(callRobotText, roomsLayout);
	
	//Create Reset Scheduled Times Button
	Button resetTimeButton = new Button("Reset");
	resetTimeButton.getStyle().set("backgroundColor", "white");
	resetTimeButton.getStyle().set("color", "black");
	resetTimeButton.setVisible(false);
	
	//Create Reset Scheduled Times Button
	Button submitTimeButton = new Button("Submit");
	submitTimeButton.getStyle().set("backgroundColor", "white");
	submitTimeButton.getStyle().set("color", "black");
	submitTimeButton.setVisible(false);
	
    
	// Create time picker/scheduler 
	VerticalLayout robotScheduleLayout = new VerticalLayout();
    TimePicker scheduleRobotTime = new TimePicker();
    scheduleRobotTime.setClearButtonVisible(true);
    scheduleRobotTime.setStep(Duration.ofMinutes(15));
    scheduleRobotTime.getStyle().set("backgroundColor", "white");
    VerticalLayout scheduledRobotTimes = new VerticalLayout();
    deliverySetupText.add("You don't have any times for delivery setup. You can add scheduled delivery times by using the above dropdown.");
    robotScheduleLayout.add(scheduleRobotText, scheduleRobotTime, deliverySetupText);
    scheduledDeliveryTimes = results.fetchResults();
    if (null != scheduledDeliveryTimes && !scheduledDeliveryTimes.isEmpty())
	{
    	addDeliveryTimesToList(scheduledDeliveryTimes, scheduledRobotTimes, robotScheduleLayout);
	 	resetTimeButton.setVisible(true);
	 	submitTimeButton.setVisible(true);
	 	deliverySetupText.setText("Your medicine will be delivered to you at the following times:");
	}
    
    scheduleRobotTime.addValueChangeListener(event -> 
    {
      if (null != scheduleRobotTime.getValue())
      {
    	  scheduledRobotTimes.removeAll();
    	  String addNewTime = dateTimeFormatter(String.valueOf(scheduleRobotTime.getValue()));
    	  scheduledDeliveryTimes.add(addNewTime);
    	  addDeliveryTimesToList(scheduledDeliveryTimes, scheduledRobotTimes, robotScheduleLayout);
		  resetTimeButton.setVisible(true);
		  submitTimeButton.setVisible(true);
		  deliverySetupText.setText("Your medicine will be delivered to you at the following times:");
      }
    });
    
    // Reset Delivery Times
    resetTimeButton.addClickListener(event ->
	{
		scheduledRobotTimes.removeAll();
		results.insertResults(new ArrayList<String>());
		scheduledDeliveryTimes = new ArrayList<String>();
		resetTimeButton.setVisible(false);
		submitTimeButton.setVisible(false);
		deliverySetupText.setText("You don't have any times for delivery setup. You can add scheduled delivery times by using the above dropdown.");
	});
    
    submitTimeButton.addClickListener(event ->
	{
		results.insertResults(scheduledDeliveryTimes);
	});
    
    // HorizontalLayout for Submit and Reset Buttons
    HorizontalLayout resetSubmitLayout = new HorizontalLayout();
    resetSubmitLayout.add(resetTimeButton, submitTimeButton);
    
    // Create Reset Delivery Time Layout
    VerticalLayout resetDeliveryTimesLayout = new VerticalLayout();
    resetDeliveryTimesLayout.add(robotScheduleLayout, resetSubmitLayout);
    
    // Create Call Delivery Robot Layout
    VerticalLayout callDeliveryRobotLayout = new VerticalLayout();
    callDeliveryRobotLayout.add(callRobotLayout);
    
    // Create Delivery Method Layout
    HorizontalLayout selectDeliveryMethodlayout = new HorizontalLayout();
    selectDeliveryMethodlayout.getStyle().set("border", "17px solid #9E9E9E");
    selectDeliveryMethodlayout.add(callDeliveryRobotLayout, resetDeliveryTimesLayout);
    selectDeliveryMethodlayout.setWidth("1200px");
    
    if (mobile)
    {
    	add(titleText, subtitleText, callRobotLayout, progressBar, resetDeliveryTimesLayout);
    }
    else
    {
    	add(titleText, subtitleText,selectDeliveryMethodlayout,progressBar);
    }
  }

  // Convert Time from military format
  private String dateTimeFormatter(String date)
  {
	  try 
	  {
		  DateFormat inputDateFormat = new SimpleDateFormat("HH:mm");
		  DateFormat outputDateFormat = new SimpleDateFormat("hh:mm a");
		  Date formattedDate = inputDateFormat.parse(date);
		  return outputDateFormat.format(formattedDate);
	  }
	  catch (ParseException e) 
	  {
		  return date;
	  }
  }
	  
  private void addDeliveryTimesToList(ArrayList<String> scheduledDeliveryTimes, VerticalLayout scheduledRobotTimes, VerticalLayout robotScheduleLayout)
  {
	  for (String addNewTime : scheduledDeliveryTimes)
	  {
		  H4 scheduledTimesList = new H4(addNewTime);
		  scheduledTimesList.getStyle().set("color", "black");
		  scheduledRobotTimes.add(scheduledTimesList);
		  robotScheduleLayout.add(scheduledRobotTimes);
	  }  
  }
  
  private void createButton(Button button)
  {
	  button.getStyle().set("backgroundColor", "white");
	  button.getStyle().set("color", "black");
	  button.setWidth("150px");
	  button.addClickListener(event -> 
	  { 
		  startRobot.startRobot();
	  });
  }
}
