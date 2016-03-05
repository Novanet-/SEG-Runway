package application.model;

import java.util.ArrayList;
import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Runway
{

	//TODO: Change obstacle array to a variable that contains one instance of an obstacle, either one obstacle or null

	//private static final double stripEnd = 50.0;
	//private static final double resa     = 200.0;
	private final int                      runwayID;
	private final String                   alignment;
	private final double                   TORA;
	private final double                   TODA;
	private final double                   ASDA;
	private final double                   LDA;
	private final double                   displacedThreshold;
	private final ObservableList<Obstacle> obstacles;
	
	// From Redeclaration
	private static final double clearway        = 0.0; //75.0
	private static final double stopway         = 0.0; //60.0
	private static final double resa            = 240.0; //240.0
	private static final double stripEnd        = 60.0;
	private static final double blastProtection = 300.0; //300.0
	private static final double runwayStrip     = 150.0; //150.0

	public Runway(final int runwayID, final String alignment, final double TORA, final double TODA, final double ASDA, final double LDA, final double displacedThreshold)
	{
		if (alignment == null || alignment.equals(""))
			throw new IllegalArgumentException();

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


	public static Integer calculateSecondPosition(Integer firstPosition)
	{
		if (firstPosition > 17)
		{
			return firstPosition - 18;
		}
		else
		{
			return firstPosition + 18;
		}
	}


	public static String getSecondaryPosition(final String primaryPosition, String secondaryPosition)
	{
		switch (primaryPosition)
		{
			case "L":
				secondaryPosition = "R";
				break;
			case "C":
				secondaryPosition = "C";
				break;
			case "R":
				secondaryPosition = "L";
		}
		return secondaryPosition;
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


	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof Runway))
			return false;

		Runway o = (Runway) other;
		return getRunwayID() == o.getRunwayID() &&
				getAlignment().equals(o.getAlignment()) &&
				getTORA() == o.getTORA() &&
				getTODA() == o.getTODA() &&
				getASDA() == o.getASDA() &&
				getLDA() == o.getLDA() &&
				getDisplacedThreshold() == o.getDisplacedThreshold() &&
				getObstacles().equals(o.getObstacles());
	}
	
	public Runway redeclare()
	{
		final Obstacle o = obstacles.get(0);
		if (o.getPosition() > TORA / 2.0)
		{
			return redeclareTowards(o);
		}
		else
		{
			return redeclareAway(o);
		}
	}

	private Runway redeclareTowards(Obstacle o)
	{
		// Distance from Threshold - Slope Calculation - Strip End
		final double newTORA = o.getPosition() - (o.getHeight() * 50.0) - stripEnd;
		
		// Distance from Threshold - RESA - Strip End
		final double newLDA = o.getPosition() - resa - stripEnd;
		
		return new Runway(getRunwayID(), getAlignment(), newTORA, newTORA, newTORA, newLDA, getDisplacedThreshold());
	}

	private Runway redeclareAway(Obstacle o)
	{
		// Original TORA - Blast Protection - Dist from Threshold -
		// Displaced Threshold
		final double newTORA = getTORA() - o.getBlastProtection() - o.getPosition() - getDisplacedThreshold();
		
		// (R) TORA + STOPWAY
		final double newTODA = newTORA + stopway;
		
		// (R) TORA + CLEARWAY
		final double newASDA = newTORA + clearway;
		
		// Original LDA - Dist from Threshold - Strip End - Slop Calculation
		final double newLDA = getLDA() - o.getPosition() - stripEnd - (o.getHeight() * 50.0);
		
		return new Runway(getRunwayID(), getAlignment(), newTORA, newTODA, newASDA, newLDA, getDisplacedThreshold());
	}


	public static double getClearway()
	{
		return clearway;
	}


	public static double getStopway()
	{
		return stopway;
	}


	public static double getResa()
	{
		return resa;
	}


	public static double getStripEnd()
	{
		return stripEnd;
	}


	public static double getBlastProtection()
	{
		return blastProtection;
	}


	public static double getRunwayStrip()
	{
		return runwayStrip;
	}
}