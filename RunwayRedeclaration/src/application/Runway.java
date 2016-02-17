package application;

import java.util.ArrayList;

public class Runway
{

	private int runwayID;
	private char alignment;
	private RunwayParameters runwayParameters = new RunwayParameters();
	private RunwayDetails runwayDetails = new RunwayDetails();
	private ArrayList<Obstacle> obstacleArray;

	public int getRunwayID()
	{
		return runwayID;
	}

	public char getAlignment()
	{
		return alignment;
	}

	public RunwayParameters getRunwayParameters()
	{
		return runwayParameters;
	}

	public RunwayDetails getRunwayDetails()
	{
		return runwayDetails;
	}

	public ArrayList<Obstacle> getObstacles()
	{
		return obstacleArray;
	}

	public void addObstacle(Obstacle obstacle)
	{
		this.obstacleArray.add(obstacle);
	}

}