package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Runway
{

	private final RunwayParameters         runwayParameters;
	private final RunwayDetails            runwayDetails;
	private final IntegerProperty          runwayID;
	private final StringProperty           alignment;
	private       ObservableList<Obstacle> obstacleArray;


	public Runway(final RunwayParameters runwayParameters, final RunwayDetails runwayDetails, final IntegerProperty runwayID, final StringProperty alignment,
			final ObservableList<Obstacle> obstacleArray)
	{
		this.runwayParameters = runwayParameters;
		this.runwayDetails = runwayDetails;
		this.runwayID = runwayID;
		this.alignment = alignment;
		this.obstacleArray = obstacleArray;
	}


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