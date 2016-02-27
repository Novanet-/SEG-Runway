package application.model;

import java.util.ArrayList;
import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Runway
{
	private final double					TORA;
	private final double					TODA;
	private final double					ASDA;
	private final double					LDA;
	private final double					displacedThreshold;
	private final int						runwayID;
	private final String					alignment;
	private final ObservableList<Obstacle>	obstacles;
	private final double					stripEnd	= 50;
	private final double					resa		= 200;

	public Runway(final int runwayID, final String alignment, final double TORA, final double TODA, final double ASDA,
			final double LDA, final double displacedThreshold)
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
		Obstacle obstacle = new Obstacle("Test1", 12, 3646, 300);
		obstacles.add(obstacle);
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

	public final void addObstacle(Obstacle obstacle)
	{
		obstacles.add(obstacle);
	}

	@Override
	public String toString()
	{
		return "Runway{" + getAlignment() + '}';
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
}