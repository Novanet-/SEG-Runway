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
		final RunwayParameters runwayParameters = runway.getRunwayParameters();
		final Obstacle obstacle = runway.getObstacles().get(0);
		double newTORA;
		double newTODA;
		double newASDA;
		double newLDA;

		if (!takeOffTowards(runway))
		{
			newTORA = runwayParameters.getTORA() - obstacle.getBlastProtection() - obstacle.getPosition() - runwayParameters
					.getDisplacedThreshold(); //Original TORA - Blast Protection - Distance from Threshold - Displaced Threshold
			newTODA = newTORA + stopway; //(R) TORA + STOPWAY
			newASDA = newTORA + clearway; //(R) TORA + CLEARWAY
			newLDA = runwayParameters.getLDA() - obstacle.getPosition() - stripEnd - (obstacle.getHeight()
					* 50); //Original LDA - Distance from Threshold – Strip End - Slope Calculation
		}
		else
		{
			newTORA = obstacle.getPosition() - (obstacle.getHeight() * 50) - stripEnd; // Distance from Threshold - Slope Calculation - Strip End
			newTODA = newTORA; //(R) TORA
			newASDA = newTORA; //(R) TORA
			newLDA = obstacle.getPosition() - resa - stripEnd; // Distance from Threshold - RESA - Strip End
		}
		RunwayParameters newRunwayParameters = new RunwayParameters(newTORA, newTODA, newASDA, newLDA,
				runwayParameters.getDisplacedThreshold());
		Runway newRunway = new Runway(newRunwayParameters, null, runway.getRunwayID(), runway.getAlignment());
		System.out.println(newTORA + " , " + newTODA + " ," + newASDA + " ," + newLDA + " ," + runwayParameters.getDisplacedThreshold());
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
		double TORA = runway.getRunwayParameters().getTORA();
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
