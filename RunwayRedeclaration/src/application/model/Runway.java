package application.model;

import java.util.ArrayList;
import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Runway
{

	private final RunwayParameters			runwayParameters;
	private final int						runwayID;
	private final String					alignment;
	private final ObservableList<Obstacle>	obstacleArray;
	private final double					stripEnd	= 50;
	private final double					resa		= 200;


	//TODO: add stuff like stripd end and resa to runway details
	public Runway(final RunwayParameters runwayParameters, final int runwayID, final String alignment)
	{
		//if (runwayParameters == null || runwayDetails == null || alignment == null || alignment.equals(""))
		//	throw new IllegalArgumentException();
		
		this.runwayParameters = runwayParameters;
		this.runwayID = runwayID;
		this.alignment = alignment;
		final Collection<Obstacle> list = new ArrayList<Obstacle>();
		obstacleArray = FXCollections.observableArrayList(list);
		Obstacle obstacle = new Obstacle("Test1", 12, 3646, 300);
		obstacleArray.add(obstacle);
	}


	/**
	 * @return
	 */
	public final int getRunwayID()
	{
		return runwayID;
	}


	/**
	 * @return
	 */
	public final String getAlignment()
	{
		return alignment;
	}


	/**
	 * @return
	 */
	public final RunwayParameters getRunwayParameters()
	{
		return runwayParameters;
	}

	/**
	 * @return
	 */
	// public final RunwayDetails getRunwayDetails()
	// {
	// return runwayDetails;
	// }


	/**
	 * @return
	 */
	public final ObservableList<Obstacle> getObstacles()
	{
		return obstacleArray;
	}


	/**
	 * @param obstacle
	 */
	public final void addObstacle(Obstacle obstacle)
	{
		obstacleArray.add(obstacle);
	}


	/**
	 * @return
	 */
	@Override
	public String toString()
	{
		return "Runway{" + getAlignment() + '}';
	}
}