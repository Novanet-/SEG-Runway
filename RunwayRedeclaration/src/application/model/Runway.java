package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Runway
{

	private final RunwayParameters runwayParameters = new RunwayParameters();
	private final RunwayDetails    runwayDetails    = new RunwayDetails();
	private IntegerProperty          runwayID;
	private StringProperty           alignment;
	private ObservableList<Obstacle> obstacleArray;


	public int getRunwayID()
	{
		return runwayID.getValue();
	}


	public String getAlignment()
	{
		return alignment.getValueSafe();
	}


	public RunwayParameters getRunwayParameters()
	{
		return runwayParameters;
	}


	public RunwayDetails getRunwayDetails()
	{
		return runwayDetails;
	}


	public ObservableList<Obstacle> getObstacles()
	{
		return obstacleArray;
	}


	public void addObstacle(Obstacle obstacle)
	{
		this.obstacleArray.add(obstacle);
	}

}