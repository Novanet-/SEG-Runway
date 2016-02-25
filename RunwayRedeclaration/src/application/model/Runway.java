package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;

public class Runway
{

	private final RunwayParameters         runwayParameters;
	// private final RunwayDetails runwayDetails;
	private final IntegerProperty          runwayID;
	private final StringProperty           alignment;
	private final ObservableList<Obstacle> obstacleArray;
	private final double stripEnd = 50;
	private final double resa     = 200;


	//TODO: add stuff like stripd end and resa to runway details
	public Runway(final RunwayParameters runwayParameters, RunwayDetails runwayDetails, final IntegerProperty runwayID, final StringProperty alignment)
	{
		runwayDetails = new RunwayDetails(null, null, null, null, null, null, null);
		if (runwayParameters == null ||
				runwayDetails == null ||
				alignment == null ||
				runwayID == null ||
				alignment.getValue().equals(""))
			throw new IllegalArgumentException();
		
		this.runwayParameters = runwayParameters;
		// this.runwayDetails = runwayDetails;
		this.runwayID = runwayID;
		this.alignment = alignment;
		final Collection<Obstacle> list = new ArrayList<Obstacle>();
		obstacleArray = FXCollections.observableArrayList(list);
		Obstacle obstacle = new Obstacle(new SimpleStringProperty("TEst1"), new SimpleDoubleProperty(12), new SimpleDoubleProperty(3646), new SimpleDoubleProperty(300));
		obstacleArray.add(obstacle);
	}


	/**
	 * @return
	 */
	public final int getRunwayID()
	{
		return runwayID.getValue();
	}


	/**
	 * @return
	 */
	public final String getAlignment()
	{
		return alignment.getValueSafe();
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
		return "Runway{" + alignment.getValue() + '}';
	}
}