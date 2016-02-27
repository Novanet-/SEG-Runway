package application.model;


public class Redeclaration
{

	//private static final IntegerProperty displacedThreshold;
	private static final double clearway        = 0.0; //75.0
	private static final double stopway         = 0.0; //60.0
	private static final double resa            = 240.0; //240.0
	private static final double stripEnd        = 60;
	private static final double blastProtection = 300.0; //300.0
	private static final double runwayStrip     = 150.0; //150.0


	public static Runway redeclareParameters(Runway runway)
	{
		final Obstacle obstacle = runway.getObstacles().get(0);
		final double newTORA;
		final double newTODA;
		final double newASDA;
		final double newLDA;

		if (!takeOffTowards(runway))
		{
			// Original TORA - Blast Protection - Dist from Threshold -
			// Displaced Threshold
			newTORA = runway.getTORA() - obstacle.getBlastProtection() - obstacle.getPosition()
					- runway.getDisplacedThreshold();
			newTODA = newTORA + stopway; // (R) TORA + STOPWAY
			newASDA = newTORA + clearway; // (R) TORA + CLEARWAY
			// Original LDA - Dist from Threshold - Strip End - Slop Calculation
			newLDA = runway.getLDA() - obstacle.getPosition() - stripEnd - (obstacle.getHeight() * 50);
		}
		else
		{
			// Distance from Threshold - Slope Calculation - Strip End
			newTORA = obstacle.getPosition() - (obstacle.getHeight() * 50) - stripEnd;
			newTODA = newTORA; // (R) TORA
			newASDA = newTORA; // (R) TORA
			// Distance from Threshold - RESA - Strip End
			newLDA = obstacle.getPosition() - resa - stripEnd;
		}

		Runway newRunway = new Runway(runway.getRunwayID(), runway.getAlignment(), newTORA, newTODA, newASDA, newLDA,
				runway.getDisplacedThreshold());
		System.out.println(
				newTORA + ", " + newTODA + ", " + newASDA + ", " + newLDA + ", " + runway.getDisplacedThreshold());
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
		Obstacle obstacle = runway.getObstacles().get(0);
		double TORA = runway.getTORA();
		return obstacle.getPosition() > TORA / 2;

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
