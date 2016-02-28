package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;

public class Runway
{

	private static final double stripEnd = 50.0;
	private static final double resa     = 200.0;
	private final int                      runwayID;
	private final String                   alignment;
	private final double                   TORA;
	private final double                   TODA;
	private final double                   ASDA;
	private final double                   LDA;
	private final double                   displacedThreshold;
	private final ObservableList<Obstacle> obstacles;


	public Runway(final int runwayID, final String alignment, final double TORA, final double TODA, final double ASDA, final double LDA, final double displacedThreshold)
	{
		this.runwayID = runwayID;
		this.alignment = alignment;
		this.TORA = TORA;
		this.TODA = TODA;
		this.ASDA = ASDA;
		this.LDA = LDA;
		this.displacedThreshold = displacedThreshold;

		final Collection<Obstacle> list = new ArrayList<Obstacle>();
		obstacles = FXCollections.observableArrayList(list);
		final Obstacle obstacle = new Obstacle("Test1", 12.0, 3646.0, 300.0);
		obstacles.add(obstacle);
	}


	public final void addObstacle(Obstacle obstacle)
	{
		obstacles.add(obstacle);
	}


	public final void removeObstacle()
	{
		if (!obstacles.isEmpty())
		{
			obstacles.remove(0);
		}
		else
		{
			throw new NullPointerException("No obstacles in list");
		}
	}


	public final int getRunwayID()
	{
		return runwayID;
	}


	public final String getAlignment()
	{
		return alignment;
	}


	public final ObservableList<Obstacle> getObstacles()
	{
		return obstacles;
	}


	public final double getTORA()
	{
		return TORA;
	}


	public final double getTODA()
	{
		return TODA;
	}


	public final double getASDA()
	{
		return ASDA;
	}


	public final double getLDA()
	{
		return LDA;
	}


	public final double getDisplacedThreshold()
	{
		return displacedThreshold;
	}


	@Override
	public final String toString()
	{
		return "Runway{" + getAlignment() + '}';
	}

}