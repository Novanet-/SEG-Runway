package application.model;

public class Redeclaration
{

	//private static final IntegerProperty displacedThreshold;
	private static final double clearway        = 0.0; //75.0
	private static final double stopway         = 0.0; //60.0
	private static final double resa            = 240.0; //240.0
	private static final double stripEnd        = 60.0;
	private static final double blastProtection = 300.0; //300.0
	private static final double runwayStrip     = 150.0; //150.0

	//TODO: make obstacle distance from centreline matter

	private Redeclaration()
	{
	}


	public static Runway redeclareParameters(Runway runway)
	{
		final Obstacle obstacle = runway.getObstacles().get(0);
		final Runway newRunway;

		if (takeOffTowards(runway))
		{
			newRunway = redeclareTowards(runway, obstacle);
		}
		else
		{
			newRunway = redeclareAway(runway, obstacle);
		}

		return newRunway;
	}


	/**
	 * Checks if obstacle is more than half way along the runway, if so then it is towards, otherwise it is away/over
	 *
	 * @param runway
	 * @return
	 */
	private static boolean takeOffTowards(Runway runway)
	{
		final Obstacle obstacle = runway.getObstacles().get(0);
		final double TORA = runway.getTORA();
		return obstacle.getPosition() > TORA / 2.0;

	}


	private static Runway redeclareAway(Runway runway, Obstacle obstacle)
	{
		// Original TORA - Blast Protection - Dist from Threshold -
		// Displaced Threshold
		final double newTORA = runway.getTORA() - obstacle.getBlastProtection() - obstacle.getPosition() - runway.getDisplacedThreshold();
		final double newTODA = newTORA + stopway; // (R) TORA + STOPWAY
		final double newASDA = newTORA + clearway; // (R) TORA + CLEARWAY
		// Original LDA - Dist from Threshold - Strip End - Slop Calculation
		final double newLDA = runway.getLDA() - obstacle.getPosition() - stripEnd - (obstacle.getHeight() * 50.0);
		return new Runway(runway.getRunwayID(), runway.getAlignment(), newTORA, newTODA, newASDA, newLDA, runway.getDisplacedThreshold());
	}


	private static Runway redeclareTowards(Runway runway, Obstacle obstacle)
	{
		// Distance from Threshold - Slope Calculation - Strip End
		final double newTORA = obstacle.getPosition() - (obstacle.getHeight() * 50.0) - stripEnd;
		// Distance from Threshold - RESA - Strip End
		final double newLDA = obstacle.getPosition() - resa - stripEnd;
		return new Runway(runway.getRunwayID(), runway.getAlignment(), newTORA, newTORA, newTORA, newLDA, runway.getDisplacedThreshold());
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
